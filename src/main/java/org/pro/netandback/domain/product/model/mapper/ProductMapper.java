package org.pro.netandback.domain.product.model.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.pro.netandback.domain.product.dto.response.ProductResponse;
import org.pro.netandback.domain.product.model.entity.Product;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProductMapper {

	@Mapping(target = "companyId",   source = "company.id")
	@Mapping(target = "companyName", source = "company.name")
	ProductResponse toProductResponse(Product product);

	List<ProductResponse> toProductResponseList(List<Product> products);
}
