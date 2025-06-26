package org.pro.netandback.domain.issueassignment.repository;

import org.pro.netandback.domain.issue.model.entity.Issue;
import org.pro.netandback.domain.issueassignment.model.entity.IssueAssignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IssueAssignmentRepository extends JpaRepository<IssueAssignment, Long> {
    Optional<IssueAssignment> findByIssue(Issue issue);
}
