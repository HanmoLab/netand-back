package org.pro.netandback.domain.issueassignment.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.pro.netandback.common.entity.BaseTime;
import org.pro.netandback.domain.issue.model.entity.Issue;
import org.pro.netandback.domain.issue.model.type.Priority;
import org.pro.netandback.domain.user.model.entity.User;

@Entity
@Table(name = "issue_assignments")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IssueAssignment extends BaseTime {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	// 할당된 이슈
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "issue_id", nullable = false)
	private Issue issue;

	// 할당된 담당자
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "assignee_id", nullable = false)
	private User assignee;

//	@Enumerated(EnumType.STRING)
//	@Column(name = "priority", nullable = false)
//	private Priority priority;

	public void updateAssignee(User newAssignee) {
		this.assignee = newAssignee;
	}

}

