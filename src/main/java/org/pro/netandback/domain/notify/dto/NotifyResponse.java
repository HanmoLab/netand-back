package org.pro.netandback.domain.notify.dto;

import lombok.*;
import java.time.LocalDateTime;

import org.pro.netandback.domain.notify.domain.type.NotificationType;

@Getter
@AllArgsConstructor
public class NotifyResponse{
	private Long id;
	private String content;
	private NotificationType notificationType;
	private Long targetId;
	private Boolean isRead;
	private LocalDateTime createdAt;
}