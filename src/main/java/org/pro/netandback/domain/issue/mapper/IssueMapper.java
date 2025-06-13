package org.pro.netandback.domain.issue.mapper;

import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.pro.netandback.domain.company.model.entity.Company;
import org.pro.netandback.domain.issue.dto.request.IssueCreateRequest;
import org.pro.netandback.domain.issue.dto.response.IssueCreateResponse;
import org.pro.netandback.domain.issue.dto.response.IssueDetailResponse;
import org.pro.netandback.domain.issue.model.entity.Issue;
import org.pro.netandback.domain.product.model.entity.Product;
import org.pro.netandback.domain.user.model.entity.User;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = false))
public interface IssueMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "company", source = "company")
    @Mapping(target = "product", source = "product")
    @Mapping(target = "reporter", source = "reporter")
    @Mapping(target = "assignee", source = "assignee")
    Issue toIssue(IssueCreateRequest issueCreateRequest, Company company, Product product, User reporter, User assignee);

    @Mapping(target = "id", source = "id")
    IssueCreateResponse toIssueCreateResponse(Issue issue);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "companyName", source = "company.name")
    @Mapping(target = "reporterName", source = "reporter.name")
    @Mapping(target = "assigneeName", source = "assignee.name")
    @Mapping(target = "productName", source = "product.name")
    @Mapping(target = "dueDate", expression = "java(issue.getDueDate() != null ? issue.getDueDate().toString() : null)")
    IssueDetailResponse toIssueDetailResponse(Issue issue);
}
