package org.pro.netandback.core.auth.service.impl;

import java.time.Duration;

import org.pro.netandback.core.auth.dao.RefreshTokenDao;
import org.pro.netandback.core.auth.dto.request.SignUpRequest;
import org.pro.netandback.core.auth.dto.response.TokenResponse;
import org.pro.netandback.core.auth.jwt.JwtProvider;
import org.pro.netandback.core.auth.service.AuthService;
import org.pro.netandback.core.auth.service.BlacklistService;
import org.pro.netandback.core.error.ErrorCode;
import org.pro.netandback.core.error.exception.BusinessException;
import org.pro.netandback.core.error.exception.EmailAlreadyExistsException;
import org.pro.netandback.core.error.exception.InvalidValueException;
import org.pro.netandback.core.error.exception.JwtAuthenticationException;
import org.pro.netandback.domain.user.model.entity.User;
import org.pro.netandback.domain.user.model.mapper.UserMapper;
import org.pro.netandback.domain.user.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {

	private static final Duration REFRESH_TOKEN_TTL = Duration.ofDays(30);

	private final UserRepository userRepository;
	private final UserMapper userMapper;
	private final PasswordEncoder passwordEncoder;
	private final BlacklistService blacklistService;
	private final RefreshTokenDao refreshTokenDao;
	private final JwtProvider jwtProvider;

	//아직 테스트용입니다. 리팩토링 예정
	@Override
	@Transactional
	public User signup(SignUpRequest request) {
		userRepository.findByEmail(request.getEmail()).ifPresent(u -> {
			throw new EmailAlreadyExistsException(ErrorCode.EMAIL_ALREADY_EXISTS);
		});
		User user = userMapper.toEntity(request);
		String encoded = passwordEncoder.encode(request.getPassword());
		user.setPassword(encoded);
		if (user.getUserType() == null) {
			throw new InvalidValueException(ErrorCode.INVALID_REQUEST);
		}
		return userRepository.save(user);
	}

	@Override
	public void logout(String email) {
		blacklistService.blacklistTokens(email);
	}

	@Override
	public TokenResponse reissueRefreshToken(String oldRefreshToken) {
		String email = refreshTokenDao.findUserIdByRefreshToken(oldRefreshToken)
			.orElseThrow(() -> new JwtAuthenticationException(ErrorCode.JWT_NOT_FOUND));
		if (!jwtProvider.validateRefreshToken(oldRefreshToken)) {
			refreshTokenDao.removeRefreshToken(oldRefreshToken);
			throw new JwtAuthenticationException(ErrorCode.EXPIRED_JWT);
		}
		return issueTokens(email);
	}

	private TokenResponse issueTokens(String email) {
		String accessToken  = jwtProvider.createAccessToken(email);
		String refreshToken = jwtProvider.createRefreshToken(email);
		refreshTokenDao.rotateRefreshToken(email, refreshToken, REFRESH_TOKEN_TTL);
		return new TokenResponse(accessToken, refreshToken);
	}
}
