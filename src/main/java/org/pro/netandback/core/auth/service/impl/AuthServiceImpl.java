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
import org.pro.netandback.core.validate.auth.AuthValidate;
import org.pro.netandback.core.validate.jwt.JwtTokenValidate;
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
	private final AuthValidate authValidate;
	private final JwtTokenValidate jwtTokenValidate;

	@Override
	@Transactional
	public User signup(SignUpRequest request) {
		authValidate.validateDuplicateEmail(userRepository.existsByEmail(request.getEmail()));
		User user = userMapper.toEntity(request);
		user.setPassword(passwordEncoder.encode(request.getPassword()));
		authValidate.validateSignupParam(user.getUserType());
		return userRepository.save(user);
	}

	@Override
	public void logout(String email) {
		blacklistService.blacklistTokens(email);
	}

	@Override
	public TokenResponse reissueRefreshToken(String oldRefreshToken) {
		String email = jwtTokenValidate.validateAndGetEmail(oldRefreshToken);
		return issueTokens(email);
	}

	private TokenResponse issueTokens(String email) {
		String accessToken  = jwtProvider.createAccessToken(email);
		String refreshToken = jwtProvider.createRefreshToken(email);
		refreshTokenDao.rotateRefreshToken(email, refreshToken, REFRESH_TOKEN_TTL);
		return new TokenResponse(accessToken, refreshToken);
	}
}
