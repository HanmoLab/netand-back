package org.pro.netandback.domain.notify.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.pro.netandback.domain.notify.sse.SseEmitterRepository;
import org.pro.netandback.domain.user.model.entity.User;
import org.pro.netandback.common.annotation.CurrentUser;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequestMapping("/api/notifications/stream")
@RequiredArgsConstructor
public class NotificationStreamController {

	private final SseEmitterRepository emitterRepo;

	@Operation(summary = "알림" ,tags = "알림")
	@GetMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public SseEmitter subscribe(@CurrentUser User user) {
		SseEmitter emitter = new SseEmitter(60L * 60 * 1000);
		return emitterRepo.save(user.getId(), emitter);
	}
}