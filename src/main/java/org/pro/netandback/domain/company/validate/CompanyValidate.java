package org.pro.netandback.domain.company.validate;

import org.pro.netandback.core.error.ErrorCode;
import org.pro.netandback.domain.company.exception.CompanyNotFoundException;
import org.pro.netandback.domain.company.model.entity.Company;
import org.pro.netandback.domain.company.repository.CompanyRepository;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CompanyValidate {
	private final CompanyRepository companyRepository;

	public Company validateCompany(String companyName) {
		return companyRepository.findByName(companyName)
			.orElseThrow(() -> new CompanyNotFoundException(ErrorCode.COMPANY_NOT_FOUND));
	}

	public Company validateCompanyById(Long companyId) {
		return companyRepository.findById(companyId)
			.orElseThrow(() -> new CompanyNotFoundException(ErrorCode.COMPANY_NOT_FOUND));
	}
}
