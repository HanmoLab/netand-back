package org.pro.netandback.domain.notify.emitter;

import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
public class NotificationEmitterRegistry {
	private final Map<Long, SseEmitter> emitters = new ConcurrentHashMap<>();

	public SseEmitter save(Long userId, SseEmitter emitter) {
		emitters.put(userId, emitter);
		emitter.onCompletion(() -> emitters.remove(userId));
		emitter.onTimeout(() -> emitters.remove(userId));
		return emitter;
	}

	public void push(Long userId, Object data) {
		SseEmitter emitter = emitters.get(userId);
		if (emitter == null) return;
		try {
			emitter.send(SseEmitter.event().name("notification").data(data));
		} catch (IOException ex) {
			log.warn("SSE push 실패: {}", ex.getMessage());
			emitters.remove(userId);
		}
	}

	@PreDestroy
	public void shutdown() { emitters.values().forEach(SseEmitter::complete); }
}