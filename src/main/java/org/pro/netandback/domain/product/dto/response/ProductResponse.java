package org.pro.netandback.domain.product.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ProductResponse {
	private String code;
	private Long   companyId;
	private String companyName;
	private String name;
}