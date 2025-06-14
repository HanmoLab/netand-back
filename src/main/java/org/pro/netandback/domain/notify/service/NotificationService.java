package org.pro.netandback.domain.notify.service;

import org.pro.netandback.domain.notify.domain.type.NotificationType;
import org.pro.netandback.domain.notify.dto.NotifyResponse;
import org.pro.netandback.domain.user.model.entity.User;
import java.util.List;

public interface NotificationService {
	void notificSend(User receiver, NotificationType type, String content, Long targetId);
	void notificRead(Long notificationId);
	List<NotifyResponse> notificList(User receiver);
}