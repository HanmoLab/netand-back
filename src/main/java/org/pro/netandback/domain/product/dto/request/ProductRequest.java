package org.pro.netandback.domain.product.dto.request;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductRequest {
	private String code;
	private Long companyId;   // 제품을 등록할 회사 ID
	private String name;
}