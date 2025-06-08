package org.pro.netandback.domain.user.service.impl;

import org.pro.netandback.core.auth.dto.request.WithdrawalRequest;
import org.pro.netandback.core.auth.dto.response.UserResponse;
import org.pro.netandback.domain.user.model.entity.User;
import org.pro.netandback.domain.user.model.mapper.UserToResponseMapper;
import org.pro.netandback.domain.user.repository.UserRepository;
import org.pro.netandback.domain.user.service.UserService;
import org.pro.netandback.domain.user.validate.UserValidate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
	private final UserRepository userRepository;
	private final UserToResponseMapper userToResponseMapper;
	private final UserValidate userValidate;
	@Override
	public UserResponse getCurrentUserProfile(User currentUser) {
		return userToResponseMapper.toResponse(currentUser);
	}

	@Override
	@Transactional
	public void withdraw(User currentUser, WithdrawalRequest request) {
		if (request.getPassword() != null) {
			userValidate.validatePasswordMatch(request.getPassword(), currentUser.getPassword());
		}
		userRepository.delete(currentUser);
	}
}
