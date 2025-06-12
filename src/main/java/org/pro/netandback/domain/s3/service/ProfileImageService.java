package org.pro.netandback.domain.s3.service;

import org.pro.netandback.common.dto.ResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface ProfileImageService {
	ResponseEntity<ResponseDto<String>> uploadProfileImage(Long userId, MultipartFile file);
	ResponseEntity<ResponseDto<String>> getProfileImageUrl(Long userId);
}
