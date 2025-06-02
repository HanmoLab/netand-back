package org.pro.netandback.domain.inspection.model.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import org.pro.netandback.common.entity.BaseTime;
import org.pro.netandback.domain.company.model.entity.Company;
import org.pro.netandback.domain.inspection.model.type.InspectionStatus;
import org.pro.netandback.domain.product.model.entity.Product;

@Entity
@Table(name = "inspections")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Inspection extends BaseTime {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "company_id", nullable = false)
	private Company company;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "product_code", referencedColumnName = "code", nullable = false)
	private Product product;

	// 점검 일자
	@Column(name = "inspection_date", nullable = false)
	private LocalDate inspectionDate;

	// 다음 점검 일자 (nullable)
	@Column(name = "next_inspection_date")
	private LocalDate nextInspectionDate;

	@Enumerated(EnumType.STRING)
	@Column(name = "status", nullable = false)
	private InspectionStatus status;

	// 점검 유형 (문자열로 저장)
	@Column(name = "inspection_type", length = 50, nullable = false)
	private String inspectionType;

	// 보고서 파일 경로 (저장할 때만 사용)
	@Column(name = "report_file_path", length = 255)
	private String reportFilePath;

}
