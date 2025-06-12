// src/main/java/org/pro/netandback/domain/s3/service/ProfileImageService.java
package org.pro.netandback.domain.s3.service;

import org.springframework.web.multipart.MultipartFile;

public interface ProfileImageService {

	String uploadProfileImage(Long userId, MultipartFile file);

	String getProfileImageUrl(Long userId);

	void deleteProfileImage(Long userId);
}
