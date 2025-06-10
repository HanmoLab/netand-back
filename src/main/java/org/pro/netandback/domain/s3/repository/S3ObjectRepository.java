package org.pro.netandback.domain.s3.repository;

import java.util.List;
import java.util.Optional;

import org.pro.netandback.domain.s3.model.entity.S3Object;
import org.pro.netandback.domain.s3.model.type.S3OwnerType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface S3ObjectRepository extends JpaRepository<S3Object, Long> {
	List<S3Object> findByOwnerTypeAndOwnerId(S3OwnerType ownerType, Long ownerId);

	void deleteByOwnerTypeAndOwnerId(S3OwnerType ownerType, Long ownerId);
	Optional<S3Object> findFirstByOwnerTypeAndOwnerId(S3OwnerType ownerType, Long ownerId);
}