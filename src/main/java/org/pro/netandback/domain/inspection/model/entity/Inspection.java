package org.pro.netandback.domain.inspection.model.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.pro.netandback.common.entity.BaseTime;
import org.pro.netandback.domain.company.model.entity.Company;
import org.pro.netandback.domain.inspection.dto.request.InspectionRequest;
import org.pro.netandback.domain.inspection.model.type.InspectionStatus;
import org.pro.netandback.domain.inspectiondetail.dto.request.InspectionDetailRequest;
import org.pro.netandback.domain.inspectiondetail.model.entity.InspectionDetail;
import org.pro.netandback.domain.product.model.entity.Product;
import org.pro.netandback.domain.user.model.entity.User;

@Entity
@Table(name = "inspections")
@Getter
@NoArgsConstructor
@AllArgsConstructor
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

	@OneToMany(mappedBy = "inspection", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<InspectionDetail> details = new ArrayList<>();

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "inspector_id", nullable = false)
	private User inspector;

	public static Inspection of(InspectionRequest req, Company company, Product product, User inspector) {Inspection insp = new Inspection();
		insp.company            = company;
		insp.product            = product;
		insp.inspectionDate     = req.getInspectionDate();
		insp.nextInspectionDate = req.getNextInspectionDate();
		insp.status             = req.getStatus();
		insp.inspectionType     = req.getInspectionType();
		insp.reportFilePath     = req.getInspectionHistory();
		insp.inspector          = inspector;

		if (req.getDetails() != null) {
			for (InspectionDetailRequest dto : req.getDetails()) {
				insp.addDetail(InspectionDetail.from(dto, insp));
			}
		}
		return insp;
	}

	public void addDetail(InspectionDetail detail) {
		detail.setInspection(this);
		this.details.add(detail);
	}
}