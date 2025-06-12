package org.pro.netandback.domain.s3.service.impl;

import lombok.RequiredArgsConstructor;
import org.pro.netandback.core.config.S3Properties;
import org.pro.netandback.common.dto.ResponseDto;
import org.pro.netandback.core.error.ErrorCode;
import org.pro.netandback.domain.s3.exception.ProfileImageException;
import org.pro.netandback.domain.s3.model.entity.S3Object;
import org.pro.netandback.domain.s3.model.type.S3OwnerType;
import org.pro.netandback.domain.s3.repository.S3ObjectRepository;
import org.pro.netandback.domain.s3.service.ProfileImageService;
import org.pro.netandback.domain.s3.service.S3Service;
import org.pro.netandback.domain.s3.util.S3KeyGenerator;
import org.pro.netandback.domain.s3.util.UrlUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.io.IOException;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ProfileImageServiceImpl implements ProfileImageService {
	private final S3Service s3Service;
	private final S3ObjectRepository repository;
	private final S3KeyGenerator keyGenerator;
	private final S3Properties s3Properties;

	@Override
	public ResponseEntity<ResponseDto<String>> uploadProfileImage(Long userId, MultipartFile file) {
		String key;
		try (InputStream in = file.getInputStream()) {
			key = keyGenerator.generateProfileImageKey(userId, file.getOriginalFilename());
			s3Service.upload(key, in, file.getSize(), file.getContentType());
		} catch (IOException e) {
			throw new ProfileImageException(ErrorCode.PROFILE_IMAGE_UPLOAD_FAILED);
		}

		// 이전 이미지 삭제
		List<String> oldKeys = repository
			.findByOwnerTypeAndOwnerId(S3OwnerType.USER_PROFILE, userId)
			.stream().map(S3Object::getS3Key).toList();
		if (!oldKeys.isEmpty()) {
			s3Service.delete(oldKeys);
		}
		repository.deleteByOwnerTypeAndOwnerId(S3OwnerType.USER_PROFILE, userId);

		S3Object obj = new S3Object();
		obj.setOwnerType(S3OwnerType.USER_PROFILE);
		obj.setOwnerId(userId);
		obj.setS3Key(key);
		repository.save(obj);

		String url = UrlUtils.buildS3Url(
			s3Properties.getBucket(),
			s3Properties.getRegion(),
			key
		);
		return ResponseEntity.ok(
			ResponseDto.of(HttpStatus.OK, "프로필 이미지 업로드 성공", url)
		);
	}

	@Override
	@Transactional(readOnly = true)
	public ResponseEntity<ResponseDto<String>> getProfileImageUrl(Long userId) {
		return repository
			.findFirstByOwnerTypeAndOwnerId(S3OwnerType.USER_PROFILE, userId)
			.map(obj -> {
				String url = UrlUtils.buildS3Url(
					s3Properties.getBucket(),
					s3Properties.getRegion(),
					obj.getS3Key()
				);
				return ResponseEntity.ok(
					ResponseDto.of(HttpStatus.OK, "프로필 이미지 조회 성공", url)
				);
			})
			.orElseThrow(() -> new ProfileImageException(ErrorCode.PROFILE_IMAGE_NOT_FOUND));
	}
}
