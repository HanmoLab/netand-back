package org.pro.netandback.domain.user.service;

import static org.mockito.BDDMockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.pro.netandback.core.auth.dto.request.WithdrawalRequest;
import org.pro.netandback.core.auth.dto.response.UserResponse;
import org.pro.netandback.core.auth.mapper.UserResponseMapper;
import org.pro.netandback.core.error.exception.BusinessException;
import org.pro.netandback.domain.user.model.entity.User;
import org.pro.netandback.domain.user.repository.UserRepository;
import org.pro.netandback.domain.user.service.impl.UserServiceImpl;
import org.springframework.security.crypto.password.PasswordEncoder;

@DisplayName("UserService 기능 테스트")
class UserServiceTest {

	@Mock
	private UserRepository userRepository;

	@Mock
	private PasswordEncoder passwordEncoder;

	@Mock
	private UserResponseMapper userResponseMapper;

	@InjectMocks
	private UserServiceImpl userService;

	private User currentUser;
	private UserResponse userResponse;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);

		currentUser = User.builder()
			.id(1L)
			.name("Test User")
			.email("test@example.com")
			.phone("010-1234-5678")
			.userType(null)
			.emailVerified(true)
			.build();

		userResponse = UserResponse.builder()
			.id(1L)
			.name("Test User")
			.email("test@example.com")
			.phone("010-1234-5678")
			.userType(null)
			.emailVerified(true)
			.createdAt(currentUser.getCreatedAt())
			.updatedAt(currentUser.getUpdatedAt())
			.build();
	}

	@Test
	@DisplayName("내 정보 조회 시 매퍼 호출하고 DTO 반환")
	void getProfile_returnsUserProfileResponse() {
		// given
		given(userResponseMapper.userResponse(currentUser)).willReturn(userResponse);

		// when
		UserResponse result = userService.getProfile(currentUser);

		// then
		assertSame(userResponse, result);
		then(userResponseMapper).should().userResponse(currentUser);
	}

	@Test
	@DisplayName("잘못된 비밀번호로 회원 탈퇴 요청 시 BusinessException 발생")
	void withdraw_withWrongPassword_throwsBusinessException() {
		// given
		WithdrawalRequest req = new WithdrawalRequest();
		req.setPassword("wrong");
		given(passwordEncoder.matches("wrong", currentUser.getPassword()))
			.willReturn(false);

		// when & then
		assertThrows(BusinessException.class,
			() -> userService.withdraw(currentUser, req)
		);
	}

	@Test
	@DisplayName("올바른 비밀번호로 회원 탈퇴 요청 시 리포지토리 delete 호출")
	void withdraw_withCorrectPassword_deletesUser() {
		// given
		WithdrawalRequest req = new WithdrawalRequest();
		req.setPassword("correct");
		given(passwordEncoder.matches("correct", currentUser.getPassword()))
			.willReturn(true);

		// when
		userService.withdraw(currentUser, req);

		// then
		then(userRepository).should().delete(currentUser);
	}
}
