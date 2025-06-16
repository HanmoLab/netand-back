// --- src/main/java/org/pro/netandback/domain/notify/validate/NotifyValidate.java ---
package org.pro.netandback.domain.notify.validate;

import org.pro.netandback.core.error.ErrorCode;
import org.pro.netandback.domain.notify.model.entity.Notify;
import org.pro.netandback.domain.notify.exception.NotificationAccessDeniedException;
import org.pro.netandback.domain.notify.exception.NotificationNotFoundException;
import org.pro.netandback.domain.notify.repository.NotifyRepository;
import org.pro.netandback.domain.user.model.entity.User;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class NotifyValidate {
	private final NotifyRepository notifyRepository;

	public Notify validateExists(Long id) {
		return notifyRepository.findById(id)
			.orElseThrow(() -> new NotificationNotFoundException(ErrorCode.NOTIFICATION_NOT_FOUND));
	}

	public Notify validateReceiver(Long notificationId, User user) {
		Notify notify = validateExists(notificationId);
		if (!notify.getReceiver().getId().equals(user.getId())) {
			throw new NotificationAccessDeniedException(ErrorCode.NOTIFICATION_ACCESS_DENIED);
		}
		return notify;
	}
}
