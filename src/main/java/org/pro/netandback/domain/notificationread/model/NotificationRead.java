package org.pro.netandback.domain.notificationread.model;


import jakarta.persistence.*;
import java.time.LocalDateTime;

import org.pro.netandback.common.entity.BaseTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "notification_reads")
@NoArgsConstructor
@AllArgsConstructor
public class NotificationRead extends BaseTime {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "notification_id", nullable = false)
	private Long notificationId;

	@Column(name = "user_id", nullable = false)
	private Long userId;

	@Column(name = "read_at")
	private LocalDateTime readAt;
}
