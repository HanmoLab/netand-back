package org.pro.netandback.domain.user.service;

import org.pro.netandback.core.auth.dto.request.WithdrawalRequest;
import org.pro.netandback.core.auth.dto.response.UserResponse;
import org.pro.netandback.domain.user.model.entity.User;

public interface UserService {

	UserResponse getCurrentUserProfile(User currentUser);

	void withdraw(User currentUser, WithdrawalRequest request);
}
