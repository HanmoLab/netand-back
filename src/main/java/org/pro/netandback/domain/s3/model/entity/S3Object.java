package org.pro.netandback.domain.s3.model.entity;

import org.pro.netandback.common.entity.BaseTime;
import org.pro.netandback.domain.s3.model.type.S3OwnerType;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "s3_objects")
@NoArgsConstructor
@AllArgsConstructor
public class S3Object extends BaseTime {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Enumerated(EnumType.STRING)
	@Column(name = "owner_type", length = 30, nullable = false)
	private S3OwnerType ownerType;

	@Column(name = "owner_id", nullable = false)
	private Long ownerId;

	@Column(name = "s3_key", length = 255, nullable = false)
	private String s3Key;
}

