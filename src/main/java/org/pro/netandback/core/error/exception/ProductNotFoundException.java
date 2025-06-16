package org.pro.netandback.core.error.exception;

import org.pro.netandback.core.error.ErrorCode;

public class ProductNotFoundException extends NotFoundException {
    public ProductNotFoundException(ErrorCode productNotFound) {
        super(ErrorCode.PRODUCT_NOT_FOUND);
    }
}
