package org.pro.netandback.domain.issue.repository;


import org.pro.netandback.domain.issue.model.entity.Issue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IssueRepository extends JpaRepository<Issue, Long> {

}
