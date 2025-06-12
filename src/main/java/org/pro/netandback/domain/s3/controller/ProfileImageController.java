package org.pro.netandback.domain.s3.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.pro.netandback.common.annotation.CurrentUser;
import org.pro.netandback.common.dto.ResponseDto;
import org.pro.netandback.core.annotation.ApiController;
import org.pro.netandback.domain.s3.service.ProfileImageService;
import org.pro.netandback.domain.user.model.entity.User;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@ApiController("/api/v1/users")
@RequiredArgsConstructor
public class ProfileImageController {
	private final ProfileImageService profileImageService;

	@Operation(summary = "프로필 이미지 등록/수정", tags = "프로필 이미지")
	@PostMapping(value = "/me/profile-image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<ResponseDto<String>> uploadProfileImage(@CurrentUser User currentUser, @Parameter(
		description = "업로드할 이미지 파일", required = true, content = @Content(mediaType = MediaType.MULTIPART_FORM_DATA_VALUE, schema = @Schema(type = "string", format = "binary")))
		@RequestPart("file") MultipartFile file) {
		return profileImageService.uploadProfileImage(currentUser.getId(), file);
	}

	@Operation(summary = "프로필 이미지 확인", tags = "프로필 이미지")
	@GetMapping("/me/profile-image")
	public ResponseEntity<ResponseDto<String>> getProfileImageUrl(@CurrentUser User currentUser) {
		return profileImageService.getProfileImageUrl(currentUser.getId());
	}
}
