package org.pro.netandback.domain.issue.model.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import org.pro.netandback.common.entity.BaseTime;
import org.pro.netandback.domain.company.model.entity.Company;
import org.pro.netandback.domain.issue.model.type.IssueStatus;
import org.pro.netandback.domain.issue.model.type.IssueType;
import org.pro.netandback.domain.issue.model.type.Priority;
import org.pro.netandback.domain.product.model.entity.Product;
import org.pro.netandback.domain.user.model.entity.User;

@Entity
@Table(name = "issues")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Issue extends BaseTime {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "company_id", nullable = false)
	private Company company;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "reporter_id", nullable = false)
	private User reporter;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "assignee_id")
	private User assignee;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "product_id", nullable = false)
	private Product product;

	@Enumerated(EnumType.STRING)
	@Column(name = "issue_type", nullable = false)
	private IssueType issueType;

	@Enumerated(EnumType.STRING)
	@Column(name = "priority", nullable = false)
	private Priority priority;

	@Enumerated(EnumType.STRING)
	@Column(name = "status", nullable = false)
	private IssueStatus status;

	@Column(name = "title", length = 100, nullable = false)
	private String title;

	@Column(name = "description", columnDefinition = "TEXT", nullable = false)
	private String description;

	@Column(name = "due_date")
	private LocalDate dueDate;

	@Column(name = "progress", nullable = false)
	private int progress;

	public void update(String title, String description, IssueStatus status, Priority priority, IssueType issueType, LocalDate dueDate) {
		this.title = title;
		this.description = description;
		this.status = status;
		this.priority = priority;
		this.issueType = issueType;
		this.dueDate = dueDate;
	}

	public void assignTo(User assignee) {
		this.assignee = assignee;
	}
}

