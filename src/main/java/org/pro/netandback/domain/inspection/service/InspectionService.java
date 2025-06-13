package org.pro.netandback.domain.inspection.service;

import org.pro.netandback.domain.inspection.dto.request.InspectionRequest;
import org.pro.netandback.domain.inspection.dto.response.InspectionResponse;
import org.pro.netandback.domain.user.model.entity.User;

public interface InspectionService {
	InspectionResponse registerInspection(InspectionRequest request, User currentUser);
}