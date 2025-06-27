package org.pro.netandback.domain.product.validate;

import lombok.RequiredArgsConstructor;
import org.pro.netandback.core.error.ErrorCode;
import org.pro.netandback.core.error.exception.ProductNotFoundException;
import org.pro.netandback.domain.product.exception.ProductAlreadyExistsException;
import org.pro.netandback.domain.product.model.entity.Product;
import org.pro.netandback.domain.product.repository.ProductRepository;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ProductValidate {
    private final ProductRepository productRepository;

    public void validateNewCode(String code) {
        if (productRepository.existsByCode(code)) {
            throw new ProductAlreadyExistsException(ErrorCode.PRODUCT_ALREADY_EXISTS);
        }
    }

    public Product validateProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(ErrorCode.PRODUCT_NOT_FOUND));
    }

    public Product validateProductByCode(String code) {
        return productRepository.findByCode(code)
                .orElseThrow(() -> new ProductNotFoundException(ErrorCode.PRODUCT_NOT_FOUND));
    }

    public Product validateProductByName(String name) {
        return productRepository.findByName(name)
                .orElseThrow(() -> new ProductNotFoundException(ErrorCode.PRODUCT_NOT_FOUND));
    }
}