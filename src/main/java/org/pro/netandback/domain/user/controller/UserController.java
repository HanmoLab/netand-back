package org.pro.netandback.domain.user.controller;

import org.pro.netandback.common.annotation.CurrentUser;
import org.pro.netandback.common.dto.ResponseDto;
import org.pro.netandback.core.annotation.ApiController;
import org.pro.netandback.core.auth.dto.request.WithdrawalRequest;
import org.pro.netandback.core.auth.dto.response.UserResponse;
import org.pro.netandback.domain.user.model.entity.User;
import org.pro.netandback.domain.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

@ApiController("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
	private final UserService userService;

	@Operation(summary = "내 정보 조회", tags = {"사용자"})
	@GetMapping("/me")
	public ResponseEntity<ResponseDto<UserResponse>> getMyProfile(@CurrentUser User currentUser) {
		UserResponse profile = userService.getCurrentUserProfile(currentUser);
		return ResponseEntity.ok(ResponseDto.of(HttpStatus.OK, "내 정보 조회 성공", profile));
	}

	@Operation(summary = "회원 탈퇴", tags = {"사용자"})
	@DeleteMapping("/me")
	public ResponseEntity<ResponseDto<String>> withdraw(@CurrentUser User currentUser, @RequestBody WithdrawalRequest request) {
		userService.withdraw(currentUser, request);
		return ResponseEntity.ok(ResponseDto.of(HttpStatus.OK, "회원 탈퇴 성공"));
	}
}
