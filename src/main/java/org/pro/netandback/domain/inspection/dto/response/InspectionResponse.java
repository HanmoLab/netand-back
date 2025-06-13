package org.pro.netandback.domain.inspection.dto.response;

import java.time.LocalDate;
import java.util.List;

import org.pro.netandback.domain.inspection.model.type.InspectionStatus;
import org.pro.netandback.domain.inspectiondetail.dto.request.InspectionDetailRequest;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InspectionResponse {
	private String companyName;
	private String productName;
	private String inspector;
	private LocalDate inspectionDate;
	private LocalDate nextInspectionDate;
	private String inspectionHistory;
	private String inspectionType;
	private InspectionStatus status;
	private List<InspectionDetailRequest> details;
}
