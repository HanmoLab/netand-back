package org.pro.netandback.core.auth.controller;

import org.pro.netandback.common.annotation.CurrentUser;
import org.pro.netandback.common.dto.ResponseDto;
import org.pro.netandback.core.annotation.ApiController;
import org.pro.netandback.core.auth.dto.request.LoginRequest;
import org.pro.netandback.core.auth.dto.request.RefreshRequest;
import org.pro.netandback.core.auth.dto.request.SignUpRequest;
import org.pro.netandback.core.auth.dto.response.TokenResponse;
import org.pro.netandback.core.auth.handler.LoginHandler;
import org.pro.netandback.core.auth.jwt.JwtProvider;
import org.pro.netandback.core.auth.service.AuthService;
import org.pro.netandback.domain.user.model.entity.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@ApiController("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

	private final LoginHandler loginHandler;
	private final AuthService authService;
	private final JwtProvider jwtProvider;

	@Operation(summary = "로그인", tags = "인증/인가")
	@PostMapping("/login")
	public ResponseEntity<ResponseDto<String>> login(@RequestBody LoginRequest loginRequest) {
		return loginHandler.login(loginRequest);
	}

	//아직 테스트용입니다. 리팩토링 예정
	@Operation(summary = "회원가입", tags = "인증/인가")
	@PostMapping("/signup")
	public ResponseEntity<ResponseDto<String>> signup(@RequestBody @Validated SignUpRequest request) {
		User created = authService.signup(request);
		ResponseDto<String> body = ResponseDto.of(HttpStatus.CREATED, "회원가입 성공");
		return ResponseEntity.status(HttpStatus.CREATED).body(body);
	}

	@Operation(summary = "토큰 재발급", tags = {"인증/인가"})
	@PostMapping("/reissue")
	public void reissue(@RequestBody RefreshRequest req, HttpServletResponse resp) {
		TokenResponse tokens = authService.reissueRefreshToken(req.getRefreshToken());
		jwtProvider.addTokenHeaders(resp, tokens);
	}

	@Operation(summary = "로그아웃", tags = {"인증/인가"})
	@PostMapping("/me/logout")
	public ResponseEntity<ResponseDto<String>> logout(@CurrentUser User user) {
		authService.logout(user.getEmail());   // email 기준으로 블랙리스트 처리
		return ResponseEntity.ok(ResponseDto.of(HttpStatus.OK, "로그아웃되었습니다."));
	}
}
