package org.pro.netandback.domain.user.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

import org.pro.netandback.common.entity.BaseTime;
import org.pro.netandback.domain.company.model.entity.Company;
import org.pro.netandback.domain.user.model.type.UserType;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User extends BaseTime {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "name", length = 100, nullable = false)
	private String name;

	@Column(name = "birth_date")
	private LocalDate birthDate;

	@Column(name = "phone", length = 20)
	private String phone;

	@Enumerated(EnumType.STRING)
	@Column(name = "user_type", nullable = false)
	private UserType userType;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "company_id")
	private Company company;

	@Column(name = "employee_number", length = 50)
	private String employeeNumber;

	@Column(name = "email", length = 255, nullable = false, unique = true)
	private String email;

	@Column(name = "password", length = 255, nullable = false)
	private String password;

	@Column(name = "email_verified", nullable = false)
	private Boolean emailVerified = false;
}
