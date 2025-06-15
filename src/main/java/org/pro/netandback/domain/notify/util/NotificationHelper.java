package org.pro.netandback.domain.notify.util;

import lombok.RequiredArgsConstructor;
import org.pro.netandback.domain.notify.domain.type.NotificationType;
import org.pro.netandback.domain.notify.service.NotificationService;
import org.pro.netandback.domain.user.model.entity.User;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NotificationHelper {

	private final NotificationService notificationService;

	public void inspectionRegistered(User receiver, Long inspectionId) {
		notificationService.sendNotification(
			receiver,
			NotificationType.INSPECTION,
			String.format("정기점검이 등록되었습니다. Id: ", inspectionId),
			inspectionId
		);
	}
}
