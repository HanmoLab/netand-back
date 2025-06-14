package org.pro.netandback.domain.inspection.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.pro.netandback.domain.inspection.dto.response.InspectionResponse;
import org.pro.netandback.domain.inspection.model.entity.Inspection;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface InspectionMapper {

	@Mapping(target = "inspectionId",    source = "id")
	@Mapping(target = "companyName",        source = "company.name")
	@Mapping(target = "productName",        source = "product.name")
	@Mapping(target = "inspector",          source = "inspector.name")
	@Mapping(target = "inspectionDate",     source = "inspectionDate")
	@Mapping(target = "nextInspectionDate", source = "nextInspectionDate")
	@Mapping(target = "inspectionHistory",  source = "reportFilePath")
	@Mapping(target = "inspectionType",     source = "inspectionType")
	@Mapping(target = "status",             source = "status")
	@Mapping(target = "details",            source = "details")
	InspectionResponse toResponse(Inspection inspection);
}
