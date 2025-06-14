package org.pro.netandback.domain.product.validate;

import org.pro.netandback.core.error.ErrorCode;
import org.pro.netandback.domain.product.exception.ProductNotFoundException;
import org.pro.netandback.domain.product.model.entity.Product;
import org.pro.netandback.domain.product.repository.ProductRepository;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ProductValidate {
	private final ProductRepository productRepository;

	public Product validateProduct(String productName) {
		return productRepository.findByName(productName)
			.orElseThrow(() -> new ProductNotFoundException(ErrorCode.PRODUCT_NOT_FOUND));
	}
}
