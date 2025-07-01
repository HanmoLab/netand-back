package org.pro.netandback.domain.issue.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.pro.netandback.domain.company.model.entity.Company;
import org.pro.netandback.domain.company.validate.CompanyValidate;
import org.pro.netandback.domain.issue.dto.request.IssueCreateRequest;
import org.pro.netandback.domain.issue.dto.request.IssueUpdateRequest;
import org.pro.netandback.domain.issue.dto.response.IssueCreateResponse;
import org.pro.netandback.domain.issue.dto.response.IssueDetailResponse;
import org.pro.netandback.domain.issue.dto.response.IssueListResponse;
import org.pro.netandback.domain.issue.mapper.IssueMapper;
import org.pro.netandback.domain.issue.model.entity.Issue;
import org.pro.netandback.domain.issue.repository.IssueRepository;
import org.pro.netandback.domain.issue.validate.IssueValidate;
import org.pro.netandback.domain.product.model.entity.Product;
import org.pro.netandback.domain.product.validate.ProductValidate;
import org.pro.netandback.domain.user.model.entity.User;
import org.pro.netandback.domain.issue.service.IssueService;
import org.pro.netandback.domain.user.validate.UserValidate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class IssueServiceImpl implements IssueService {
    private final IssueRepository issueRepository;
    private final IssueMapper issueMapper;
    private final IssueValidate issueValidate;
    private final CompanyValidate companyValidate;
    private final ProductValidate productValidate;

    @Transactional
    public IssueCreateResponse createIssue(User currentUser, IssueCreateRequest request) {
        Company company = companyValidate.validateCompanyById(request.getCompanyId());
        Product product = productValidate.validateProductByCode(request.getProductCode());
        Issue issue = issueMapper.toIssue(request, company, product, currentUser);
        Issue savedIssue = issueRepository.save(issue);
        return issueMapper.toIssueCreateResponse(savedIssue);
    }

    public Page<IssueListResponse> getIssueList(Pageable pageable) {
        Pageable sortedPageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<Issue> issues = issueRepository.findAllWithCompanyAndProduct(sortedPageable);
        return issueMapper.toIssuePageResponse(issues);
    }

    public IssueDetailResponse getIssueDetail(User currentUser, Long issueId) {
        Issue issue = issueValidate.getIssueByIdOrThrow(issueId);
        issueValidate.validateIssueOwner(currentUser, issue);
        return issueMapper.toIssueDetailResponse(issue);
    }

    @Transactional
    public IssueDetailResponse updateIssue(User currentUser, Long issueId, IssueUpdateRequest request) {
        Issue issue = issueValidate.getIssueByIdOrThrow(issueId);
        issueValidate.validateIssueOwner(currentUser, issue);
        issue.update(request.getTitle(), request.getDescription(), request.getStatus(), request.getPriority(), request.getIssueType(), request.getDueDate());
        Issue savedIssue = issueRepository.save(issue);
        return issueMapper.toIssueDetailResponse(savedIssue);
    }


    @Transactional
    public void deleteIssue(User currentUser, Long issueId) {
        Issue issue = issueValidate.getIssueByIdOrThrow(issueId);
        issueValidate.validateIssueOwner(currentUser, issue);
        issueRepository.delete(issue);
    }

}
