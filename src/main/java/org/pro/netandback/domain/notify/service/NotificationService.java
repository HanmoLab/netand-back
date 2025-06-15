package org.pro.netandback.domain.notify.service;

import java.util.List;
import org.pro.netandback.domain.notify.dto.NotifyResponse;
import org.pro.netandback.domain.notify.domain.type.NotificationType;
import org.pro.netandback.domain.user.model.entity.User;

public interface NotificationService {

	void sendNotification(User receiver, NotificationType type, String content, Long targetId);

	void readNotification(Long notificationId, User user);

	List<NotifyResponse> listNotifications(User user);

	void deleteNotification(Long notificationId, User user);
}
