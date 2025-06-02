package org.pro.netandback.core.auth.jwt;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.crypto.SecretKey;

import org.pro.netandback.core.auth.dto.response.TokenResponse;
import org.pro.netandback.core.error.ErrorCode;
import org.pro.netandback.core.error.exception.BusinessException;
import org.pro.netandback.domain.user.model.entity.User;
import org.pro.netandback.domain.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtProvider {
	private final UserRepository userRepository;

	@Value("${jwt.secret}")
	private String secretKeyString;
	private SecretKey secretKey;

	private static final String BEARER_PREFIX = "Bearer ";
	private static final String CLAIM_EMAIL = "email";
	private static final String TOKEN_SUBJECT = "netandBack";

	private static final long ACCESS_TOKEN_VALIDITY_MS = 30 * 60 * 1000L;
	private static final long REFRESH_TOKEN_VALIDITY_MS = 7L * 24 * 60 * 60 * 1000L;

	@PostConstruct
	public void init() {
		byte[] keyBytes = Decoders.BASE64.decode(secretKeyString);
		this.secretKey = Keys.hmacShaKeyFor(keyBytes);
	}

	public String createAccessToken(String email) {
		Date now = new Date();
		return Jwts.builder()
			.setSubject(TOKEN_SUBJECT)
			.claim(CLAIM_EMAIL, email)
			.setId(UUID.randomUUID().toString())
			.setIssuedAt(now)
			.setExpiration(new Date(now.getTime() + ACCESS_TOKEN_VALIDITY_MS))
			.signWith(secretKey, SignatureAlgorithm.HS256)
			.compact();
	}

	public String createRefreshToken(String email) {
		Date now = new Date();
		return Jwts.builder()
			.setSubject(TOKEN_SUBJECT)
			.claim(CLAIM_EMAIL, email)
			.setId(UUID.randomUUID().toString())
			.setIssuedAt(now)
			.setExpiration(new Date(now.getTime() + REFRESH_TOKEN_VALIDITY_MS))
			.signWith(secretKey, SignatureAlgorithm.HS256)
			.compact();
	}

	public String resolveAccessToken(HttpServletRequest req) {
		return resolveToken(req.getHeader("Authorization"));
	}

	public String resolveRefreshToken(HttpServletRequest req) {
		if (req.getCookies() == null)
			return null;
		for (Cookie c : req.getCookies()) {
			if ("refreshToken".equals(c.getName())) {
				return resolveToken(c.getValue());
			}
		}
		return null;
	}

	private String resolveToken(String header) {
		if (header != null && header.startsWith(BEARER_PREFIX)) {
			return header.substring(BEARER_PREFIX.length());
		}
		return null;
	}

	public boolean validateAccessToken(String token) {
		return validateToken(token, "Access");
	}

	public boolean validateRefreshToken(String token) {
		return validateToken(token, "Refresh");
	}

	public String getClaimEmail(String token) {
		Claims body = Jwts.parser()
			.verifyWith(secretKey)
			.build()
			.parseSignedClaims(token)
			.getBody();
		return body.get(CLAIM_EMAIL, String.class);
	}

	private boolean validateToken(String token, String type) {
		try {
			Jws<Claims> claims = Jwts.parser()
				.verifyWith(secretKey)
				.build()
				.parseSignedClaims(token);
			return !claims.getBody().getExpiration().before(new Date());
		} catch (ExpiredJwtException e) {
			log.info("{} Token 만료: {}", type, e.getMessage());
		} catch (JwtException | IllegalArgumentException e) {
			log.warn("{} Token 검증 실패: {}", type, e.getMessage());
		}
		return false;
	}

	public Claims parseClaimsOrThrow(String token) {
		try {
			return Jwts.parser()
				.verifyWith(secretKey)
				.build()
				.parseSignedClaims(token)
				.getBody();
		} catch (ExpiredJwtException e) {
			throw new BusinessException(ErrorCode.EXPIRED_JWT);
		} catch (UnsupportedJwtException | MalformedJwtException e) {
			throw new BusinessException(ErrorCode.UNSUPPORTED_JWT);
		} catch (SignatureException e) {
			throw new BusinessException(ErrorCode.SIGNATURE_INVALID_JWT);
		} catch (IllegalArgumentException e) {
			throw new BusinessException(ErrorCode.JWT_NOT_FOUND);
		}
	}

	public Authentication getAuthentication(String token) {
		String email = getClaimEmail(token);
		User user = userRepository.findByEmail(email)
			.orElseThrow(() -> new UsernameNotFoundException("User not found: " + email));
		Collection<GrantedAuthority> auths = List.of(new SimpleGrantedAuthority("ROLE_" + user.getUserType().name()));

		return new UsernamePasswordAuthenticationToken(user, null, auths);
	}

	public void addTokenHeaders(HttpServletResponse res, TokenResponse tokens) {
		String bearer = BEARER_PREFIX + tokens.getAccessToken();
		res.setHeader("Authorization", bearer);
	}

	public String resolveAccessTokenFromHeader() {
		ServletRequestAttributes attrs = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
		if (attrs == null)
			return null;
		HttpServletRequest req = attrs.getRequest();
		String raw = Optional.ofNullable(req.getHeader("ACCESS")).orElse(req.getHeader("Authorization"));
		if (raw != null && raw.startsWith("Bearer ")) {
			return raw.substring(7);
		}
		return null;
	}

	public long getRemainingAccessTokenValidity(String token) {
		Claims body = Jwts.parser()
			.setSigningKey(secretKey)
			.build()
			.parseClaimsJws(token)
			.getBody();

		Date exp = body.getExpiration();
		long secondsLeft = (exp.getTime() - System.currentTimeMillis()) / 1000;
		return Math.max(secondsLeft, 0L);
	}
}