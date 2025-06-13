package org.pro.netandback.core.error.exception;

import org.pro.netandback.core.error.ErrorCode;

public class CompanyNotFoundException extends NotFoundException {
    public CompanyNotFoundException() {
        super(ErrorCode.COMPANY_NOT_FOUND);
    }
}
