package org.pro.netandback.domain.issue.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.pro.netandback.domain.company.model.entity.Company;
import org.pro.netandback.domain.issue.dto.request.IssueCreateRequest;
import org.pro.netandback.domain.issue.model.entity.Issue;
import org.pro.netandback.domain.product.model.entity.Product;
import org.pro.netandback.domain.user.model.entity.User;

@Mapper(componentModel = "spring")
public interface IssueMapper {
    @Mapping(target = "id", ignore = true)
    Issue toIssue(IssueCreateRequest issueCreateRequest, Company company, Product product, User reporter, User assignee);
}
