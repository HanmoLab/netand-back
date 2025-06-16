package org.pro.netandback.domain.notify.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.pro.netandback.domain.notify.repository.NotifyRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;

@Slf4j
@Component
@RequiredArgsConstructor
public class NotificationCleanupScheduler {

	private final NotifyRepository notifyRepository;

	@Scheduled(cron = "0 0 3 * * *")
	public void cleanOldNotifications() {
		LocalDateTime threshold = LocalDateTime.now().minusDays(30);
		long deleted = notifyRepository.deleteByIsReadTrueAndReadAtBefore(threshold);
		if (deleted > 0) {
			log.info("Notification cleanup: {} rows deleted", deleted);
		}
	}
}