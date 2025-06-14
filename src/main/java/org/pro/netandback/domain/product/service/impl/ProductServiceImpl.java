package org.pro.netandback.domain.product.service.impl;

import java.util.List;

import org.pro.netandback.domain.company.model.entity.Company;
import org.pro.netandback.domain.company.validate.CompanyValidate;
import org.pro.netandback.domain.product.dto.request.ProductRequest;
import org.pro.netandback.domain.product.dto.response.ProductResponse;
import org.pro.netandback.domain.product.model.entity.Product;
import org.pro.netandback.domain.product.model.mapper.ProductMapper;
import org.pro.netandback.domain.product.repository.ProductRepository;
import org.pro.netandback.domain.product.validate.ProductValidate;
import org.pro.netandback.domain.product.service.ProductService;
import org.pro.netandback.domain.user.model.entity.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

	private final ProductRepository productRepository;
	private final ProductMapper     productMapper;
	private final CompanyValidate   companyValidate;
	private final ProductValidate   productValidate;

	@Override
	@Transactional
	public ProductResponse createProduct(ProductRequest req, User currentUser) {
		productValidate.validateNewCode(req.getCode());
		Company company = companyValidate.validateCompanyById(req.getCompanyId());
		Product product = Product.builder()
			.code(req.getCode())
			.company(company)
			.name(req.getName())
			.build();
		Product saved = productRepository.save(product);
		return productMapper.toProductResponse(saved);
	}

	@Override
	@Transactional(readOnly = true)
	public List<ProductResponse> listByCompany(Long companyId, User currentUser) {
		companyValidate.validateCompanyById(companyId);
		List<Product> products = productRepository.findByCompanyId(companyId);
		return productMapper.toProductResponseList(products);
	}

	@Override
	@Transactional
	public ProductResponse updateProduct(Long productId, ProductRequest req, User currentUser) {
		Product existing = productValidate.validateProductById(productId);
		existing.setCompany(companyValidate.validateCompanyById(req.getCompanyId()));
		existing.setName(req.getName());
		Product updated = productRepository.save(existing);
		return productMapper.toProductResponse(updated);
	}

	@Override
	@Transactional
	public void deleteProduct(Long productId, User currentUser) {
		Product existing = productValidate.validateProductById(productId);
		productRepository.delete(existing);
	}
}
