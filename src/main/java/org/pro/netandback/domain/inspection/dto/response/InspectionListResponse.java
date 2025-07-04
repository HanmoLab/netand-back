package org.pro.netandback.domain.inspection.dto.response;

import java.time.LocalDateTime;
import java.time.LocalDate;
import org.pro.netandback.domain.inspection.model.type.InspectionStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InspectionListResponse {
	private Long inspectionId;
	private LocalDateTime createdAt;
	private String companyName;
	private String productName;
	private LocalDate inspectionDate;
	private LocalDate nextInspectionDate;
	private InspectionStatus status;
	private String inspector;
}
