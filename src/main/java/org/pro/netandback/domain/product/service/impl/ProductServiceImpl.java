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
		// 1) code 중복 검사
		productValidate.validateNewCode(req.getCode());

		// 2) 회사 검증
		Company company = companyValidate.validateCompanyById(req.getCompanyId());

		// 3) 엔티티 생성
		Product product = Product.builder()
			.code(req.getCode())
			.company(company)
			.name(req.getName())
			.build();

		// 4) 저장 및 반환
		Product saved = productRepository.save(product);
		return productMapper.toProductResponse(saved);
	}

	@Override
	@Transactional(readOnly = true)
	public List<ProductResponse> listByCompany(Long companyId, User currentUser) {
		// 회사 검증
		companyValidate.validateCompanyById(companyId);

		List<Product> products = productRepository.findByCompanyId(companyId);
		return productMapper.toProductResponseList(products);
	}

	@Override
	@Transactional
	public ProductResponse updateProduct(Long productId, ProductRequest req, User currentUser) {
		// 1) 기존 제품 조회
		Product existing = productValidate.validateProductById(productId);

		// 2) (옵션) code 변경 불가: req.getCode()와 비교해 다르면 예외 던지거나 무시

		// 3) 회사 및 이름 업데이트
		existing.setCompany(companyValidate.validateCompanyById(req.getCompanyId()));
		existing.setName(req.getName());

		// 4) 저장 및 반환
		Product updated = productRepository.save(existing);
		return productMapper.toProductResponse(updated);
	}

	@Override
	@Transactional
	public void deleteProduct(Long productId, User currentUser) {
		// 1) 조회 및 검증
		Product existing = productValidate.validateProductById(productId);
		// 2) 삭제
		productRepository.delete(existing);
	}
}
