package org.pro.netandback.domain.inspection.dto.request;

import lombok.Data;
import java.time.LocalDate;
import java.util.List;

import org.pro.netandback.domain.inspection.model.type.InspectionStatus;
import org.pro.netandback.domain.inspectiondetail.dto.request.InspectionDetailRequest;

@Data
public class InspectionRequest {
	private String companyName;
	private LocalDate inspectionDate;
	private LocalDate nextInspectionDate;
	private String productName;
	private String inspectionHistory;
	private String inspectionType;
	private InspectionStatus status;
	private List<InspectionDetailRequest> details;
}
