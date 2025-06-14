package org.pro.netandback.domain.notify.service.impl;

import lombok.RequiredArgsConstructor;

import org.pro.netandback.domain.notify.domain.entity.Notify;
import org.pro.netandback.domain.notify.domain.mapper.NotifyMapper;
import org.pro.netandback.domain.notify.domain.type.NotificationType;
import org.pro.netandback.domain.notify.dto.NotifyResponse;
import org.pro.netandback.domain.notify.repository.NotifyRepository;
import org.pro.netandback.domain.notify.service.NotificationService;
import org.pro.netandback.domain.notify.sse.SseEmitterRepository;
import org.pro.netandback.domain.notify.validate.NotifyValidate;
import org.pro.netandback.domain.user.model.entity.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NotificationServiceImpl implements NotificationService {

	private final NotifyRepository notifyRepository;
	private final NotifyValidate notifyValidate;
	private final NotifyMapper notifyMapper;
	private final SseEmitterRepository emitterRepo;

	@Override
	@Transactional
	public void notificSend(User receiver, NotificationType type, String content, Long targetId) {
		Notify notify = Notify.builder()
			.receiver(receiver)
			.notificationType(type)
			.content(content)
			.targetId(targetId)
			.build();
		Notify saved = notifyRepository.save(notify);

		emitterRepo.push(receiver.getId(), notifyMapper.toDto(saved));
	}

	@Override
	@Transactional
	public void notificRead(Long notificationId) {
		Notify notify = notifyValidate.validateExists(notificationId);
		notify.markAsRead();
	}

	@Override
	public List<NotifyResponse> notificList(User receiver) {
		return notifyRepository.findAllByReceiverOrderByCreatedAtDesc(receiver)
			.stream().map(notifyMapper::toDto).collect(Collectors.toList());
	}
}
