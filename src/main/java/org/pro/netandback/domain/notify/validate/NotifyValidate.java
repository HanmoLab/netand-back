package org.pro.netandback.domain.notify.validate;

import org.pro.netandback.domain.notify.domain.entity.Notify;
import org.pro.netandback.domain.notify.repository.NotifyRepository;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class NotifyValidate {
	private final NotifyRepository notifyRepository;

	public Notify validateExists(Long id) {
		return notifyRepository.findById(id)
			.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 알림입니다. (id=" + id + ")"));
	}
}
