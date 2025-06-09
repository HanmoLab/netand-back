package org.pro.netandback.core.auth.handler;

import java.time.Duration;

import org.pro.netandback.common.dto.ResponseDto;
import org.pro.netandback.core.auth.dao.RefreshTokenDao;
import org.pro.netandback.core.auth.dto.request.LoginRequest;
import org.pro.netandback.core.auth.jwt.JwtProvider;
import org.pro.netandback.core.auth.jwt.UserDetailsImpl;
import org.pro.netandback.core.error.ErrorCode;
import org.pro.netandback.core.error.exception.AuthFailedException;
import org.pro.netandback.core.validate.email.EmailValidate;
import org.pro.netandback.domain.user.model.entity.User;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class LoginHandler {

	private final AuthenticationManager authenticationManager;
	private final JwtProvider jwtProvider;
	private final RefreshTokenDao refreshTokenDao;
	private final EmailValidate emailValidate;
	private static final Duration REFRESH_TTL = Duration.ofDays(30);

	public ResponseEntity<ResponseDto<String>> login(LoginRequest loginRequest) {
		try {
			Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(
					loginRequest.getEmail(),
					loginRequest.getPassword()
				)
			);

			UserDetailsImpl principal = (UserDetailsImpl) authentication.getPrincipal();
			User user = principal.getUser();

			emailValidate.validateEmailVerified(user.getEmailVerified());

			String accessToken  = jwtProvider.createAccessToken(user.getEmail());
			String refreshToken = jwtProvider.createRefreshToken(user.getEmail());

			refreshTokenDao.rotateRefreshToken(user.getEmail(), refreshToken, REFRESH_TTL);

			HttpHeaders headers = new HttpHeaders();
			headers.set("Authorization", "Bearer " + accessToken);

			ResponseCookie cookie = ResponseCookie.from("refreshToken", refreshToken)
				.httpOnly(true)
				.secure(true)              // 배포할 때 HTTPS라면 true로 변경
				.path("/")
				.maxAge(Duration.ofDays(30))
				.sameSite("Lax")
				.build();
			headers.add(HttpHeaders.SET_COOKIE, cookie.toString());

			ResponseDto<String> body = ResponseDto.of(
				HttpStatus.OK,
				"로그인 성공"
			);

			return ResponseEntity
				.ok()
				.headers(headers)
				.body(body);
		}
		catch (BadCredentialsException ex) {
			throw new AuthFailedException(ErrorCode.AUTHENTICATION_FAILED);
		}
		catch (AuthenticationException ex) {
			throw new AuthFailedException(ErrorCode.AUTHENTICATION_FAILED);
		}
	}
}
