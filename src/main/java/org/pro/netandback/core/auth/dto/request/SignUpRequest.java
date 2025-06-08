package org.pro.netandback.core.auth.dto.request;

import java.time.LocalDate;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SignUpRequest {
	private String name;
	private String phone;
	private String userType;
	private String email;
	private String password;
}
