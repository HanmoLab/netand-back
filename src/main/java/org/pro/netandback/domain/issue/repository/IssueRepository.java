package org.pro.netandback.domain.issue.repository;


import org.pro.netandback.domain.issue.model.entity.Issue;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IssueRepository extends JpaRepository<Issue, Long> {

    @Query("SELECT i FROM Issue i")
    @EntityGraph(attributePaths = {"company", "product", "assignee"})
    Page<Issue> findAllWithCompanyAndProduct(Pageable pageable);
}
