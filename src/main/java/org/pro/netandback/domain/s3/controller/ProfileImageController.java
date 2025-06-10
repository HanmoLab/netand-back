package org.pro.netandback.domain.s3.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.pro.netandback.common.annotation.CurrentUser;
import org.pro.netandback.common.dto.ResponseDto;
import org.pro.netandback.core.config.S3Properties;
import org.pro.netandback.domain.s3.service.ProfileImageService;
import org.pro.netandback.domain.user.model.entity.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Slf4j
public class ProfileImageController {

	private final ProfileImageService profileImageService;
	private final S3Properties s3Properties;

	@Operation(summary = "프로필 이미지 등록/수정",tags = "프로필 이미지")
	@PostMapping(value = "/me/profile-image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<ResponseDto<String>> uploadProfileImage(@CurrentUser User currentUser, @Parameter(
		description = "업로드할 이미지 파일",
		required = true,
		content = @Content(mediaType = MediaType.MULTIPART_FORM_DATA_VALUE, schema = @Schema(type = "string", format = "binary"))) @RequestPart("file") MultipartFile file) {
		try {
			String key = profileImageService.uploadProfileImage(currentUser.getId(), file);
			String imageUrl = String.format(
				"https://%s.s3.%s.amazonaws.com/%s",
				s3Properties.getBucket(),
				s3Properties.getRegion(),
				key
			);
			return ResponseEntity.ok(
				ResponseDto.of(HttpStatus.OK, "프로필 이미지 업로드 성공", imageUrl)
			);
		} catch(Exception e) {
			log.error("프로필 이미지 업로드 중 예외", e);
			return ResponseEntity
				.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(ResponseDto.of(HttpStatus.INTERNAL_SERVER_ERROR, "프로필 이미지 업로드 실패", null));
		}

	}

	@Operation(summary = "프로필 이미지 확인", tags = "프로필 이미지")
	@GetMapping("/me/profile-image")
	public ResponseEntity<ResponseDto<String>> getProfileImageUrl(@CurrentUser User currentUser) {
		return profileImageService.getProfileImageKey(currentUser.getId())
			.map(key -> {
				String imageUrl = String.format(
					"https://%s.s3.%s.amazonaws.com/%s",
					s3Properties.getBucket(),
					s3Properties.getRegion(),
					key
				);
				return ResponseEntity.ok(ResponseDto.of(HttpStatus.OK, "프로필 이미지 조회 성공", imageUrl));
			})
			.orElseGet(() ->
				ResponseEntity.status(HttpStatus.NOT_FOUND).body(ResponseDto.of(HttpStatus.NOT_FOUND, "프로필 이미지가 없습니다", null)));
	}
}
