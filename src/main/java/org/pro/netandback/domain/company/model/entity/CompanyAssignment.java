package org.pro.netandback.domain.company.model.entity;

import org.pro.netandback.common.entity.BaseTime;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "company_assignments")
@AllArgsConstructor
@NoArgsConstructor
public class CompanyAssignment extends BaseTime {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "company_id", nullable = false)
	private Long companyId;

	@Column(name = "user_id", nullable = false)
	private Long userId;
}
