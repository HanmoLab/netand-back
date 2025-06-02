package org.pro.netandback.domain.company.model.entity;

import org.pro.netandback.common.entity.BaseTime;
import org.pro.netandback.domain.company.model.type.CompanyType;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "companies")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Company extends BaseTime {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "name", length = 100, nullable = false, unique = true)
	private String name;

	// 고객사 파트너사 구분 (Enum: CUSTOMER / PARTNER)
	@Enumerated(EnumType.STRING)
	@Column(name = "type", nullable = false)
	private CompanyType type;
}
