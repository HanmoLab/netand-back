package org.pro.netandback.core.auth.service;

import org.pro.netandback.core.auth.dto.request.SignUpRequest;
import org.pro.netandback.domain.user.model.entity.User;

public interface AuthService {

	User signup(SignUpRequest request);
}
