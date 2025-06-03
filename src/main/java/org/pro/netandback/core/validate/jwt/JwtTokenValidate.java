package org.pro.netandback.core.validate.jwt;

import org.pro.netandback.core.auth.dao.RefreshTokenDao;
import org.pro.netandback.core.auth.jwt.JwtProvider;
import org.pro.netandback.core.error.ErrorCode;
import org.pro.netandback.core.error.exception.JwtAuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;

@Component
public class JwtTokenValidate {
	private final JwtProvider jwtProvider;
	private final RefreshTokenDao refreshTokenDao;
	private final StringRedisTemplate stringRedisTemplate;


	public JwtTokenValidate(JwtProvider jwtProvider, StringRedisTemplate stringRedisTemplate, RefreshTokenDao refreshTokenDao) {
		this.jwtProvider = jwtProvider;
		this.stringRedisTemplate = stringRedisTemplate;
		this.refreshTokenDao = refreshTokenDao;
	}
	public String resolveAndValidate(HttpServletRequest request) {
		String token = jwtProvider.resolveAccessToken(request);
		String blackKey = "blacklist:access:" + token;
		if (token == null) {
			throw new JwtAuthenticationException(ErrorCode.JWT_NOT_FOUND);
		}
		if (!jwtProvider.validateAccessToken(token)) {
			throw new JwtAuthenticationException(ErrorCode.INVALID_TYPE_VALUE);
		}
		if (Boolean.TRUE.equals(stringRedisTemplate.hasKey(blackKey))) {
			throw new JwtAuthenticationException(ErrorCode.EXPIRED_JWT);
		}
		return token;
	}

	//리프레쉬가 유효한지 확인
	public String validateAndGetEmail(String refreshToken) {
		String email = refreshTokenDao.findUserIdByRefreshToken(refreshToken)
			.orElseThrow(() -> new JwtAuthenticationException(ErrorCode.JWT_NOT_FOUND));
		if (!jwtProvider.validateRefreshToken(refreshToken)) {
			refreshTokenDao.removeRefreshToken(refreshToken);
			throw new JwtAuthenticationException(ErrorCode.EXPIRED_JWT);
		}
		return email;
	}
}
