package org.pro.netandback.domain.product.controller;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

import org.pro.netandback.common.dto.ResponseDto;
import org.pro.netandback.common.annotation.CurrentUser;
import org.pro.netandback.core.annotation.ApiController;
import org.pro.netandback.domain.product.dto.request.ProductRequest;
import org.pro.netandback.domain.product.dto.response.ProductResponse;
import org.pro.netandback.domain.product.service.ProductService;
import org.pro.netandback.domain.user.model.entity.User;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@ApiController("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

	private final ProductService productService;

	@Operation(summary = "제품 등록", tags = "제품")
	@PostMapping
	public ResponseEntity<ResponseDto<ProductResponse>> createProduct(@RequestBody ProductRequest request, @CurrentUser User currentUser) {
		ProductResponse dto = productService.createProduct(request, currentUser);
		return ResponseEntity.ok(ResponseDto.of(HttpStatus.CREATED, "제품 등록 성공", dto));
	}

	@Operation(summary = "회사별 제품 목록 조회", tags = "제품")
	@GetMapping("/by-company/{companyId}")
	public ResponseEntity<ResponseDto<List<ProductResponse>>> getProductsByCompany(@PathVariable Long companyId, @CurrentUser User currentUser) {
		List<ProductResponse> list = productService.listByCompany(companyId, currentUser);
		return ResponseEntity.ok(ResponseDto.of(HttpStatus.OK, "회사별 제품 목록 조회 성공", list));
	}

	@Operation(summary = "제품 수정", tags = "제품")
	@PutMapping("/{productId}")
	public ResponseEntity<ResponseDto<ProductResponse>> updateProduct(@PathVariable Long productId, @RequestBody ProductRequest request, @CurrentUser User currentUser) {
		ProductResponse dto = productService.updateProduct(productId, request, currentUser);
		return ResponseEntity.ok(ResponseDto.of(HttpStatus.OK, "제품 수정 성공", dto));
	}

	@Operation(summary = "제품 삭제", tags = "제품")
	@DeleteMapping("/{productId}")
	public ResponseEntity<ResponseDto<Void>> deleteProduct(@PathVariable Long productId, @CurrentUser User currentUser) {
		productService.deleteProduct(productId, currentUser);
		return ResponseEntity.ok(ResponseDto.of(HttpStatus.OK, "제품 삭제 성공", null));
	}
}
