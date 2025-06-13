package org.pro.netandback.domain.product.validate;

import lombok.RequiredArgsConstructor;
import org.pro.netandback.core.error.exception.ProductNotFoundException;
import org.pro.netandback.domain.product.model.entity.Product;
import org.pro.netandback.domain.product.repository.ProductRepository;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ProductValidate {
    private final ProductRepository productRepository;

    public Product validateProductExists(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(ProductNotFoundException::new);
    }
}
