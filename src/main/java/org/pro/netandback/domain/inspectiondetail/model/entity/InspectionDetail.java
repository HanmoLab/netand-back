package org.pro.netandback.domain.inspectiondetail.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.pro.netandback.common.entity.BaseTime;
import org.pro.netandback.domain.inspection.model.entity.Inspection;

@Entity
@Table(name = "inspection_details")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class InspectionDetail extends BaseTime {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	// 어떤 Inspection에 속하는 세부 항목인지 참조
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "inspection_id", nullable = false)
	private Inspection inspection;

	@Column(name = "item_name", length = 100, nullable = false)
	private String itemName;

	@Column(name = "measured_value", length = 100)
	private String measuredValue;

	@Column(name = "check_method", length = 100, nullable = false)
	private String checkMethod;

	@Column(name = "check_result", length = 200, nullable = false)
	private String checkResult;
}
