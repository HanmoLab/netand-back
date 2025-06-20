package org.pro.netandback.core.auth.service;

import org.pro.netandback.core.auth.dto.request.EmailCodeVerifyRequest;
import org.pro.netandback.core.auth.dto.request.EmailRequest;
import org.pro.netandback.core.auth.dto.request.SignUpRequest;
import org.pro.netandback.core.auth.dto.response.TokenResponse;
import org.pro.netandback.domain.user.model.entity.User;

public interface AuthService {

	User signup(SignUpRequest request);
	void logout(String email);
	TokenResponse reissueRefreshToken(String refreshToken);
}
