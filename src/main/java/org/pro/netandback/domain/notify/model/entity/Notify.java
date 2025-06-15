package org.pro.netandback.domain.notify.model.entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.*;
import org.pro.netandback.common.entity.BaseTime;
import org.pro.netandback.domain.notify.model.type.NotificationType;
import org.pro.netandback.domain.user.model.entity.User;


@Entity
@Table(name = "notifications", indexes = @Index(name = "idx_notifications_user", columnList = "user_id"))
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Notify extends BaseTime {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(columnDefinition = "TEXT", nullable = false)
	private String content;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 20)
	private NotificationType notificationType;

	@Column(name = "target_id", nullable = false)
	private Long targetId;

	@Builder.Default
	@Column(nullable = false)
	private Boolean isRead = false;

	private LocalDateTime readAt;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	private User receiver;

	public void markAsRead() {
		if (!Boolean.TRUE.equals(isRead)) {
			this.isRead = true;
			this.readAt = java.time.LocalDateTime.now();
		}
	}
}
