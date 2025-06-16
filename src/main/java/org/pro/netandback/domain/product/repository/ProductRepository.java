package org.pro.netandback.domain.product.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.pro.netandback.domain.product.model.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {
	Optional<Product> findByCode(String code);
	Optional<Product> findByName(String name);
	List<Product> findByCompanyId(Long companyId);
	boolean existsByCode(String code);
}
