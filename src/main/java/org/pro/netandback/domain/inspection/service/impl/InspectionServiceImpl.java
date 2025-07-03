package org.pro.netandback.domain.inspection.service.impl;

import java.util.List;

import lombok.RequiredArgsConstructor;

import org.pro.netandback.common.dto.PagedResponse;
import org.pro.netandback.domain.company.model.entity.Company;
import org.pro.netandback.domain.company.validate.CompanyValidate;
import org.pro.netandback.domain.inspection.dto.request.InspectionRequest;
import org.pro.netandback.domain.inspection.dto.response.InspectionListResponse;
import org.pro.netandback.domain.inspection.dto.response.InspectionResponse;
import org.pro.netandback.domain.inspection.model.entity.Inspection;
import org.pro.netandback.domain.inspection.model.mapper.InspectionMapper;
import org.pro.netandback.domain.inspection.repository.InspectionRepository;
import org.pro.netandback.domain.inspection.service.InspectionService;
import org.pro.netandback.domain.inspection.validate.InspectionValidate;
import org.pro.netandback.domain.notify.util.NotificationHelper;
import org.pro.netandback.domain.product.model.entity.Product;
import org.pro.netandback.domain.product.validate.ProductValidate;
import org.pro.netandback.domain.user.model.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class InspectionServiceImpl implements InspectionService {

	private final InspectionRepository inspectionRepository;
	private final InspectionMapper inspectionMapper;
	private final ProductValidate productValidate;
	private final CompanyValidate companyValidate;
	private final InspectionValidate inspectionValidate;
	private final NotificationHelper notificationHelper;

	@Override
	@Transactional
	public InspectionResponse registerInspection(InspectionRequest request, User currentUser) {
		Company company = companyValidate.validateCompany(request.getCompanyName());
		Product product = productValidate.validateProductByName(request.getProductName());
		Inspection inspection = Inspection.of(request, company, product, currentUser);
		Inspection saved = inspectionRepository.save(inspection);
		notificationHelper.inspectionRegistered(currentUser, saved.getId());
		return inspectionMapper.toResponse(saved);
	}

	@Transactional
	public InspectionResponse updateInspection(Long inspectionId, InspectionRequest request, User currentUser) {
		Inspection inspection = inspectionValidate.validateInspection(inspectionId);
		Company company = companyValidate.validateCompany(request.getCompanyName());
		Product product = productValidate.validateProductByName(request.getProductName());
		inspection.updateFromRequest(request, company, product, currentUser);
		Inspection saved = inspectionRepository.save(inspection);
		return inspectionMapper.toResponse(saved);
	}

	@Transactional(readOnly = true)
	public InspectionResponse getInspectionDetail(Long inspectionId, User currentUser) {
		Inspection inspection=inspectionValidate.validateInspection(inspectionId);
		return inspectionMapper.toResponse(inspection);
	}

	@Transactional
	public void deleteInspection(Long inspectionId, User currentUser) {
		Inspection inspection=inspectionValidate.validateInspection(inspectionId);
		inspectionRepository.delete(inspection);
	}

	@Transactional(readOnly = true)
	public PagedResponse<InspectionListResponse> listInspections(Pageable pageable) {
		Pageable sorted = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(Sort.Direction.DESC, "createdAt"));
		Page<Inspection> page = inspectionRepository.findAll(sorted);
		Page<InspectionListResponse> dtoPage = page.map(inspectionMapper::toInspectionListItem);
		return PagedResponse.pagedFrom(dtoPage);
	}

}
