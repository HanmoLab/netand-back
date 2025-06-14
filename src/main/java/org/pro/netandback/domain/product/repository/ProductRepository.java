package org.pro.netandback.domain.product.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.pro.netandback.domain.product.model.entity.Product;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
	Optional<Product> findByName(String name);
}
