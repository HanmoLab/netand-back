package org.pro.netandback.domain.inspectiondetail.dto.request;

import lombok.Data;

@Data
public class InspectionDetailRequest {
	private String itemName;
	private String systemCheck;
	private String checkMethod;
	private String checkResult;
}

