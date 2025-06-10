package org.pro.netandback.domain.s3.service;

import java.io.IOException;
import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

public interface ProfileImageService {
	String uploadProfileImage(Long userId, MultipartFile file) throws IOException;
	Optional<String> getProfileImageKey(Long userId);
}
