package org.pro.netandback.core.auth.controller;

import org.pro.netandback.common.dto.ResponseDto;
import org.pro.netandback.core.annotation.ApiController;
import org.pro.netandback.core.auth.dto.request.LoginRequest;
import org.pro.netandback.core.auth.dto.request.SignUpRequest;
import org.pro.netandback.core.auth.handler.LoginHandler;
import org.pro.netandback.core.auth.service.AuthService;
import org.pro.netandback.domain.user.model.entity.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

@ApiController("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

	private final LoginHandler loginHandler;
	private final AuthService authService;

	@PostMapping("/login")
	public ResponseEntity<ResponseDto<String>> login(@RequestBody LoginRequest loginRequest) {
		return loginHandler.login(loginRequest);
	}


	//아직 테스트용입니다. 리팩토링 예정
	@Operation(summary = "회원가입", tags = "Auth")
	@PostMapping("/signup")
	public ResponseEntity<ResponseDto<String>> signup(@RequestBody @Validated SignUpRequest request) {
		User created = authService.signup(request);
		ResponseDto<String> body = ResponseDto.of(HttpStatus.CREATED, "회원가입 성공");
		return ResponseEntity.status(HttpStatus.CREATED).body(body);
	}
}
