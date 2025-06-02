package org.pro.netandback.core.auth.service.impl;

import org.pro.netandback.core.auth.dto.request.SignUpRequest;
import org.pro.netandback.core.auth.service.AuthService;
import org.pro.netandback.core.error.ErrorCode;
import org.pro.netandback.core.error.exception.BusinessException;
import org.pro.netandback.core.error.exception.EmailAlreadyExistsException;
import org.pro.netandback.core.error.exception.InvalidValueException;
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

	private final UserRepository userRepository;
	private final UserMapper userMapper;
	private final PasswordEncoder passwordEncoder;

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
}
