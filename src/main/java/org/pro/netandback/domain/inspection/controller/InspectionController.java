package org.pro.netandback.domain.inspection.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

import org.pro.netandback.common.dto.ResponseDto;
import org.pro.netandback.domain.inspection.dto.request.InspectionRequest;
import org.pro.netandback.domain.inspection.dto.response.InspectionResponse;
import org.pro.netandback.domain.inspection.service.InspectionService;
import org.pro.netandback.domain.user.model.entity.User;
import org.pro.netandback.common.annotation.CurrentUser;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/inspections")
public class InspectionController {

	private final InspectionService inspectionService;

	@Operation(summary = "정기점검 등록",tags = "정기점검")
	@PostMapping
	public ResponseEntity<ResponseDto<InspectionResponse>>registerInspection(@RequestBody InspectionRequest request, @CurrentUser User currentUser) {
		InspectionResponse response = inspectionService.registerInspection(request, currentUser);
		return ResponseEntity.ok(ResponseDto.of(HttpStatus.CREATED, "정기점검 등록 성공", response));
	}

	@Operation(summary = "정기점검 수정", tags = "정기점검")
	@PutMapping("/{inspectionId}")
	public ResponseEntity<ResponseDto<InspectionResponse>> updateInspection(@PathVariable Long inspectionId, @RequestBody InspectionRequest request, @CurrentUser User currentUser) {
		InspectionResponse dto = inspectionService.updateInspection(inspectionId, request, currentUser);
		return ResponseEntity.ok(ResponseDto.of(HttpStatus.OK, "정기점검 수정 성공", dto));
	}

	@Operation(summary = "정기점검 단건 조회", tags = "정기점검")
	@GetMapping("/{inspectionId}")
	public ResponseEntity<ResponseDto<InspectionResponse>> getInspection(@PathVariable Long inspectionId, @CurrentUser User currentUser) {
		InspectionResponse result = inspectionService.getInspectionDetail(inspectionId, currentUser);
		return ResponseEntity.ok(ResponseDto.of(HttpStatus.OK, "정기점검 조회 성공", result));
	}

	@Operation(summary = "정기점검 삭제", tags = "정기점검")
	@DeleteMapping("/{inspectionId}")
	public ResponseEntity<ResponseDto<Void>> deleteInspection(@PathVariable Long inspectionId, @CurrentUser User currentUser) {
		inspectionService.deleteInspection(inspectionId, currentUser);
		return ResponseEntity.ok(ResponseDto.of(HttpStatus.OK, "정기점검 삭제 성공", null));
	}
}
