package org.pro.netandback.domain.product.model.entity;

import jakarta.persistence.*;
import lombok.*;

import org.hibernate.annotations.GenericGenerator;
import org.pro.netandback.common.entity.BaseTime;
import org.pro.netandback.domain.company.model.entity.Company;

@Entity
@Table(name = "products")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product extends BaseTime {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "code", length = 20, nullable = false, updatable = false)
	private String code;

	@Column(name = "name", length = 100, nullable = false)
	private String name;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "company_id", nullable = false)
	private Company company;

	public void setCompany(Company company) {
		this.company = company;
	}

	public void setName(String name) {
		this.name = name;
	}
}
