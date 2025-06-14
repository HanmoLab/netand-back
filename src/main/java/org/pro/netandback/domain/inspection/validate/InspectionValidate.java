package org.pro.netandback.domain.inspection.validate;

import org.pro.netandback.core.error.ErrorCode;
import org.pro.netandback.domain.inspection.exception.InspectionNotFoundException;
import org.pro.netandback.domain.inspection.model.entity.Inspection;
import org.pro.netandback.domain.inspection.repository.InspectionRepository;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class InspectionValidate {

	private final InspectionRepository inspectionRepository;

	public Inspection validateInspection(Long inspectionId) {
		return inspectionRepository.findById(inspectionId)
			.orElseThrow(() -> new InspectionNotFoundException(ErrorCode.INSPECTION_NOT_FOUND));
	}
}
