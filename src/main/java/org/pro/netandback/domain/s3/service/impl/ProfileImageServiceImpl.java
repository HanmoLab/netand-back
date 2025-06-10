package org.pro.netandback.domain.s3.service.impl;

import org.pro.netandback.domain.s3.model.entity.S3Object;
import org.pro.netandback.domain.s3.model.type.S3OwnerType;
import org.pro.netandback.domain.s3.repository.S3ObjectRepository;
import org.pro.netandback.domain.s3.service.ProfileImageService;
import org.pro.netandback.domain.s3.service.S3Service;
import org.pro.netandback.domain.s3.util.S3KeyGenerator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProfileImageServiceImpl implements ProfileImageService {
	private final S3Service s3Service;
	private final S3ObjectRepository repository;
	private final S3KeyGenerator keyGenerator;

	public ProfileImageServiceImpl(S3Service s3Service, S3ObjectRepository repository, S3KeyGenerator keyGenerator) {
		this.s3Service = s3Service;
		this.repository = repository;
		this.keyGenerator = keyGenerator;
	}

	@Override
	public String uploadProfileImage(Long userId, MultipartFile file) throws IOException {
		String key = keyGenerator.generateProfileImageKey(userId, file.getOriginalFilename());

		try (InputStream inputStream = file.getInputStream()) {
			s3Service.upload(key, inputStream, file.getSize(), file.getContentType());
		}

		List<String> oldKeys = repository.findByOwnerTypeAndOwnerId(S3OwnerType.USER_PROFILE, userId).stream()
			.map(S3Object::getS3Key)
			.toList();

		if (!oldKeys.isEmpty()) {
			s3Service.delete(oldKeys);
		}

		repository.deleteByOwnerTypeAndOwnerId(S3OwnerType.USER_PROFILE, userId);

		S3Object newObject = new S3Object();
		newObject.setOwnerType(S3OwnerType.USER_PROFILE);
		newObject.setOwnerId(userId);
		newObject.setS3Key(key);
		repository.save(newObject);

		return key;
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<String> getProfileImageKey(Long userId) {
		return repository.findFirstByOwnerTypeAndOwnerId(S3OwnerType.USER_PROFILE, userId).map(S3Object::getS3Key);
	}
}
