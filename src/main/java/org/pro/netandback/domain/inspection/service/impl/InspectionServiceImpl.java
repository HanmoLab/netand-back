// InspectionServiceImpl.java
package org.pro.netandback.domain.inspection.service.impl;

import lombok.RequiredArgsConstructor;
import org.pro.netandback.core.error.ErrorCode;
import org.pro.netandback.domain.company.exception.CompanyNotFoundException;
import org.pro.netandback.domain.company.model.entity.Company;
import org.pro.netandback.domain.company.model.repository.CompanyRepository;
import org.pro.netandback.domain.inspection.dto.request.InspectionRequest;
import org.pro.netandback.domain.inspection.dto.response.InspectionResponse;
import org.pro.netandback.domain.inspection.model.entity.Inspection;
import org.pro.netandback.domain.inspection.model.mapper.InspectionMapper;
import org.pro.netandback.domain.inspection.repository.InspectionRepository;
import org.pro.netandback.domain.inspection.service.InspectionService;
import org.pro.netandback.domain.product.exception.ProductNotFoundException;
import org.pro.netandback.domain.product.model.entity.Product;
import org.pro.netandback.domain.product.repository.ProductRepository;
import org.pro.netandback.domain.user.model.entity.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class InspectionServiceImpl implements InspectionService {

	private final InspectionRepository inspectionRepository;
	private final CompanyRepository      companyRepository;
	private final ProductRepository      productRepository;
	private final InspectionMapper       inspectionMapper;

	@Override
	@Transactional
	public InspectionResponse registerInspection(InspectionRequest request, User currentUser) {
		Company company = companyRepository.findByName(request.getCompanyName())
			.orElseThrow(() -> new CompanyNotFoundException(ErrorCode.COMPANY_NOT_FOUND));
		Product product = productRepository.findByName(request.getProductName())
			.orElseThrow(() -> new ProductNotFoundException(ErrorCode.PRODUCT_NOT_FOUND));

		Inspection inspection = Inspection.of(request, company, product, currentUser);
		Inspection saved     = inspectionRepository.save(inspection);

		return inspectionMapper.toResponse(saved);
	}
}
