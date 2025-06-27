package org.pro.netandback.domain.issuecomment.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.pro.netandback.domain.issue.model.entity.Issue;
import org.pro.netandback.domain.issuecomment.dto.request.IssueCommentRequest;
import org.pro.netandback.domain.issuecomment.dto.response.IssueCommentResponse;
import org.pro.netandback.domain.issuecomment.model.entity.IssueComment;
import org.pro.netandback.domain.user.model.entity.User;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IssueCommentMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "issue", source = "issue")
    @Mapping(target = "writer", source = "writer")
    @Mapping(target = "content", source = "request.content")
    IssueComment toIssueComment(Issue issue, User writer, IssueCommentRequest request);

    @Mapping(target = "writerName", source = "writer.name")
    IssueCommentResponse toIssueCommentResponse(IssueComment comment);

    List<IssueCommentResponse> toIssueCommentResponseList(List<IssueComment> comments);
}
