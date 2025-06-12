package org.pro.netandback.domain.issue.service.impl;

import lombok.RequiredArgsConstructor;
import org.pro.netandback.domain.company.model.entity.Company;
import org.pro.netandback.domain.company.repository.CompanyRepository;
import org.pro.netandback.domain.issue.dto.request.IssueCreateRequest;
import org.pro.netandback.domain.issue.dto.response.IssueCreateResponse;
import org.pro.netandback.domain.issue.mapper.IssueMapper;
import org.pro.netandback.domain.issue.model.entity.Issue;
import org.pro.netandback.domain.issue.repository.IssueRepository;
import org.pro.netandback.domain.product.model.entity.Product;
import org.pro.netandback.domain.product.repository.ProductRepository;
import org.pro.netandback.domain.user.model.entity.User;
import org.pro.netandback.domain.user.repository.UserRepository;
import org.pro.netandback.domain.issue.service.IssueService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class IssueServiceImpl implements IssueService {
    private final IssueRepository issueRepository;
    private final CompanyRepository companyRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final IssueMapper issueMapper;

    public IssueCreateResponse createIssue(User reporter, IssueCreateRequest issueCreateRequest) {
        Company company = companyRepository.findById(issueCreateRequest.getCompanyId())
                .orElseThrow(() -> new IllegalArgumentException("Company not found"));
        Product product = productRepository.findById(issueCreateRequest.getProductId())
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));
        User assignee = userRepository.findById(issueCreateRequest.getAssigneeId())
                .orElseThrow(() -> new IllegalArgumentException("Assignee not found"));

        Issue issue = issueMapper.toIssue(issueCreateRequest, company, product, reporter, assignee);

        Issue savedIssue = issueRepository.save(issue);

        return new IssueCreateResponse(savedIssue.getId());
    }
}
