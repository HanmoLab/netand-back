package org.pro.netandback.domain.notify.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.pro.netandback.common.annotation.CurrentUser;
import org.pro.netandback.common.dto.ResponseDto;
import org.pro.netandback.core.annotation.ApiController;
import org.pro.netandback.domain.notify.dto.NotifyResponse;
import org.pro.netandback.domain.notify.service.NotificationService;
import org.pro.netandback.domain.user.model.entity.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@ApiController("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {

	private final NotificationService notificationService;

	@Operation(summary = "내 알림 목록 조회", tags = "알림")
	@GetMapping
	public ResponseEntity<ResponseDto<List<NotifyResponse>>> getAllNotifications(@CurrentUser User user) {
		List<NotifyResponse> notifications = notificationService.listNotifications(user);
		return ResponseEntity.ok(ResponseDto.of(HttpStatus.OK, "내 알림 목록 조회 성공", notifications));
	}

	@Operation(summary = "알림 읽음 처리", tags = "알림")
	@PatchMapping("/{notificationId}/read")
	public ResponseEntity<ResponseDto<String>> markAsRead(@PathVariable Long notificationId, @CurrentUser User user) {
		notificationService.readNotification(notificationId, user);
		String message = String.format("알림[id=%d] 읽음 처리 완료", notificationId);
		return ResponseEntity.ok(ResponseDto.of(HttpStatus.OK, message));
	}

	@Operation(summary = "알림 삭제", tags = "알림")
	@DeleteMapping("/{notificationId}")
	public ResponseEntity<ResponseDto<String>> deleteNotification(@PathVariable Long notificationId, @CurrentUser User user) {
		notificationService.deleteNotification(notificationId, user);
		String message = String.format("알림[id=%d] 삭제 완료", notificationId);
		return ResponseEntity.ok(ResponseDto.of(HttpStatus.OK, message));
	}
}
