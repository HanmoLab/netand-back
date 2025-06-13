package org.pro.netandback.domain.company.validate;

import lombok.RequiredArgsConstructor;
import org.pro.netandback.core.error.exception.CompanyNotFoundException;
import org.pro.netandback.domain.company.model.entity.Company;
import org.pro.netandback.domain.company.repository.CompanyRepository;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class CompanyValidate {
    private final CompanyRepository companyRepository;

    public Company validateCompanyExists(Long companyId) {
        return companyRepository.findById(companyId)
                .orElseThrow(CompanyNotFoundException::new);
    }
}
