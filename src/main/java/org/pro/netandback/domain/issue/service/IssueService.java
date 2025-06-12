package org.pro.netandback.domain.issue.service;

import org.pro.netandback.domain.issue.dto.request.IssueCreateRequest;
import org.pro.netandback.domain.issue.dto.response.IssueCreateResponse;
import org.pro.netandback.domain.user.model.entity.User;

public interface IssueService {
    IssueCreateResponse createIssue(User reporter, IssueCreateRequest issueCreateRequest);
}
