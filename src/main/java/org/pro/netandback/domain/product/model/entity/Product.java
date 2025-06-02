package org.pro.netandback.domain.product.model.entity;

import jakarta.persistence.*;
import lombok.*;
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
	@Column(name = "code", length = 20, nullable = false)
	private String code;

	@Column(name = "name", length = 100, nullable = false)
	private String name;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "company_id", nullable = false)
	private Company company;
}
