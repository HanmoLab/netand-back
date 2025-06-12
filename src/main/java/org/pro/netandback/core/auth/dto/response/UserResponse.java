package org.pro.netandback.core.auth.dto.response;

import java.time.LocalDateTime;

import org.pro.netandback.domain.user.model.type.UserType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Getter
@Schema
@Builder
public class UserResponse {
	private Long id;
	private String name;
	private String email;
	private String phone;
	private UserType userType;
	private Boolean emailVerified;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	private String profileImageUrl;
}

