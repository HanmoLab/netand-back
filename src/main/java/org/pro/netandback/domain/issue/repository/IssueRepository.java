package org.pro.netandback.domain.issue.repository;


import io.lettuce.core.dynamic.annotation.Param;
import org.pro.netandback.domain.issue.model.entity.Issue;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IssueRepository extends JpaRepository<Issue, Long> {
    @Query("""
        SELECT i FROM Issue i
        WHERE (:companyName IS NULL OR :companyName = '' OR i.product.company.name = :companyName)
        AND (:productName IS NULL OR :productName = '' OR i.product.name = :productName)
    """)
    Page<Issue> findByCondition(@Param("companyName") String companyName, @Param("productName") String productName, Pageable pageable);
}
