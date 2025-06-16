package org.pro.netandback.domain.inspectiondetail.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.pro.netandback.common.entity.BaseTime;
import org.pro.netandback.domain.inspection.model.entity.Inspection;
import org.pro.netandback.domain.inspectiondetail.dto.request.InspectionDetailRequest;

@Entity
@Table(name = "inspection_details")
@Getter
@NoArgsConstructor
@AllArgsConstructor
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

	@Column(name = "system_check", length = 100)
	private String systemCheck;

	@Column(name = "measured_value", length = 100)
	private String measuredValue;

	@Column(name = "check_method", length = 100, nullable = false)
	private String checkMethod;

	@Column(name = "check_result", length = 200, nullable = false)
	private String checkResult;

	public static InspectionDetail from(InspectionDetailRequest dto, Inspection inspection) {
		InspectionDetail d = new InspectionDetail();
		d.inspection    = inspection;
		d.itemName      = dto.getItemName();
		d.systemCheck   = dto.getSystemCheck();
		d.measuredValue = dto.getMeasuredValue();
		d.checkMethod   = dto.getCheckMethod();
		d.checkResult   = dto.getCheckResult();
		return d;
	}

	public void setInspection(Inspection inspection) {
		this.inspection = inspection;
	}
}