package org.pro.netandback.domain.inspection.repository;

import org.pro.netandback.domain.inspection.model.entity.Inspection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface InspectionRepositoryCustom {
	Page<Inspection> searchByCompanyAndProduct(
		String companyName,
		String productName,
		Pageable pageable
	);
}
