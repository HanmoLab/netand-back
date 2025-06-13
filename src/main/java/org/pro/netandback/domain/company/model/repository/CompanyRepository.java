package org.pro.netandback.domain.company.model.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.pro.netandback.domain.company.model.entity.Company;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {
	Optional<Company> findByName(String name);
}
