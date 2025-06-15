// package: org.pro.netandback.domain.notify.service.impl

package org.pro.netandback.domain.notify.service.impl;

import lombok.RequiredArgsConstructor;
import org.pro.netandback.domain.notify.domain.entity.Notify;
import org.pro.netandback.domain.notify.domain.mapper.NotifyMapper;
import org.pro.netandback.domain.notify.domain.type.NotificationType;
import org.pro.netandback.domain.notify.dto.NotifyResponse;
import org.pro.netandback.domain.notify.repository.NotifyRepository;
import org.pro.netandback.domain.notify.sse.SseEmitterRepository;
import org.pro.netandback.domain.notify.validate.NotifyValidate;
import org.pro.netandback.domain.notify.service.NotificationService;
import org.pro.netandback.domain.user.model.entity.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
	public void sendNotification(User receiver, NotificationType type, String content, Long targetId) {
		Notify notify = Notify.builder()
			.receiver(receiver)
			.notificationType(type)
			.content(content)
			.targetId(targetId)
			.build();

		Notify saved = notifyRepository.save(notify);
		emitterRepo.push(receiver.getId(), notifyMapper.toNotifyDto(saved));
	}

	@Override
	@Transactional
	public void readNotification(Long notificationId, User user) {
		Notify notify = notifyValidate.validateReceiver(notificationId, user);
		notify.markAsRead();
	}

	@Override
	public List<NotifyResponse> listNotifications(User user) {
		return notifyMapper.toNotifyDtoList(notifyRepository.findAllByReceiverOrderByCreatedAtDesc(user));
	}

	@Override
	@Transactional
	public void deleteNotification(Long notificationId, User user) {
		notifyValidate.validateReceiver(notificationId, user);
		notifyRepository.deleteByIdAndReceiverId(notificationId, user.getId());
	}
}
