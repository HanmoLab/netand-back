package org.pro.netandback.domain.issuecomment.repository;

import org.pro.netandback.domain.issuecomment.model.entity.IssueComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IssueCommentRepository extends JpaRepository<IssueComment, Long> {
    List<IssueComment> findByIssueIdOrderByCreatedAtAsc(Long issueId);
}
