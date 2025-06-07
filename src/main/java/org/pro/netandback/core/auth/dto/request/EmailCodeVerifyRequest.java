package org.pro.netandback.core.auth.dto.request;

import lombok.Data;

@Data
public class EmailCodeVerifyRequest {
	private String code;
}
