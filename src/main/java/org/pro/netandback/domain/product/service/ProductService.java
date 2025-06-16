package org.pro.netandback.domain.product.service;

import java.util.List;

import org.pro.netandback.domain.product.dto.request.ProductRequest;
import org.pro.netandback.domain.product.dto.response.ProductResponse;
import org.pro.netandback.domain.user.model.entity.User;

public interface ProductService {
	ProductResponse createProduct(ProductRequest req, User currentUser);
	List<ProductResponse> listByCompany(Long companyId, User currentUser);
	ProductResponse updateProduct(Long productId, ProductRequest req, User currentUser);
	void deleteProduct(Long productId, User currentUser);
}
