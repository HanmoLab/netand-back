package org.pro.netandback.domain.issue.service.impl;

import lombok.RequiredArgsConstructor;
import org.pro.netandback.domain.company.model.entity.Company;
import org.pro.netandback.domain.company.validate.CompanyValidate;
import org.pro.netandback.domain.issue.dto.request.IssueCreateRequest;
import org.pro.netandback.domain.issue.dto.response.IssueCreateResponse;
import org.pro.netandback.domain.issue.dto.response.IssueDetailResponse;
import org.pro.netandback.domain.issue.mapper.IssueMapper;
import org.pro.netandback.domain.issue.model.entity.Issue;
import org.pro.netandback.domain.issue.repository.IssueRepository;
import org.pro.netandback.domain.issue.validate.IssueValidate;
import org.pro.netandback.domain.product.model.entity.Product;
import org.pro.netandback.domain.product.validate.ProductValidate;
import org.pro.netandback.domain.user.model.entity.User;
import org.pro.netandback.domain.issue.service.IssueService;
import org.pro.netandback.domain.user.validate.UserValidate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class IssueServiceImpl implements IssueService {
    private final IssueRepository issueRepository;
    private final IssueMapper issueMapper;
    private final IssueValidate issueValidate;
    private final CompanyValidate companyValidate;
    private final ProductValidate productValidate;
    private final UserValidate userValidate;

    public IssueCreateResponse createIssue(User reporter, IssueCreateRequest issueCreateRequest) {
        Company company = companyValidate.validateCompanyExists(issueCreateRequest.getCompanyId());
        Product product = productValidate.validateProductExists(issueCreateRequest.getProductCode());
        User assignee = userValidate.validateUserExists(issueCreateRequest.getAssigneeId());

        Issue issue = issueMapper.toIssue(issueCreateRequest, company, product, reporter, assignee);
        Issue savedIssue = issueRepository.save(issue);
        return issueMapper.toIssueCreateResponse(savedIssue);
    }

    public IssueDetailResponse getIssueDetail(Long issueId) {
        Issue issue = issueValidate.validateIssueExists(issueId);
        return issueMapper.toIssueDetailResponse(issue);
    }
}
