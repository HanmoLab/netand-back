package org.pro.netandback.core.error;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ErrorCode {

	// Common
	INVALID_INPUT_VALUE(400, "C001", "잘못된 입력 값입니다."),
	METHOD_NOT_ALLOWED(405, "C002", "허용되지 않은 메서드입니다."),
	ENTITY_NOT_FOUND(400, "C003", "엔티티를 찾을 수 없습니다."),
	INTERNAL_SERVER_ERROR(500, "C004", "서버 내부 오류가 발생했습니다."),
	INVALID_TYPE_VALUE(400, "C005", "유효하지 않은 타입 값입니다."),
	HANDLE_ACCESS_DENIED(403, "C006", "접근이 거부되었습니다."),
	INVALID_REQUEST (400, "C007", "유효하지 않은 요청입니다."),

	// JWT
	EXPIRED_JWT(403, "J001", "만료된 JWT 토큰입니다."),
	UNSUPPORTED_JWT(403, "J002", "지원되지 않는 JWT 토큰입니다."),
	SIGNATURE_INVALID_JWT(403, "JOO3", "사용중인 시그니처키입니다."),
	JWT_NOT_FOUND(403, "J004", "JWT 토큰을 찾을 수 없습니다."),
	AUTHENTICATION_FAILED(403, "J005", "인증에 실패했습니다."),

	// User
	USER_NOT_FOUND(404, "U001", "사용자를 찾을 수 없습니다."),
	USER_ALREADY_REGISTERED(409, "U002", "이미 가입된 사용자입니다."),
	EMAIL_ALREADY_EXISTS(409, "U003", "이미 사용 중인 이메일입니다."),
	EMAIL_NOT_VERIFIED(400, "U004", "이메일 인증이 필요합니다."),
	EMAIL_CODE_EXPIRED(400, "U005", "인증 코드가 만료되었거나 존재하지 않습니다."),
	EMAIL_CODE_MISMATCH(400, "U006", "인증 코드가 일치하지 않습니다."),
	PASSWORD_MISMATCH(400, "U007", "비밀번호가 일치하지 않습니다."),

	// S3
	PROFILE_IMAGE_UPLOAD_FAILED(500, "P001", "프로필 이미지 업로드에 실패했습니다."),
	PROFILE_IMAGE_NOT_FOUND   (404, "P002", "프로필 이미지가 존재하지 않습니다."),
	PROFILE_IMAGE_DELETE_FAILED(500, "P003", "프로필 이미지 삭제에 실패했습니다."),

	// Inspection (정기점검)
	INSPECTION_NOT_FOUND(404, "I001", "정기점검 정보를 찾을 수 없습니다."),
	INSPECTION_ALREADY_EXISTS(409, "I002", "이미 등록된 정기점검입니다."),
	INVALID_INSPECTION_DATE(400, "I003", "유효하지 않은 점검일자입니다."),

	// Product (제품)
	PRODUCT_NOT_FOUND(404, "PR001", "제품 정보를 찾을 수 없습니다."),
	PRODUCT_ALREADY_EXISTS(409, "PR002", "이미 등록된 제품입니다."),
	INVALID_PRODUCT_CODE(400, "PR003", "유효하지 않은 제품 코드입니다."),

	// Company (회사)
	COMPANY_NOT_FOUND(404, "C101", "회사 정보를 찾을 수 없습니다."),
	COMPANY_ALREADY_EXISTS(409, "C102", "이미 등록된 회사입니다."),
	INVALID_COMPANY_CODE(400, "C103", "유효하지 않은 회사 코드입니다."),

	// Notification (알림)
	NOTIFICATION_NOT_FOUND(404, "NT001", "요청하신 알림을 찾을 수 없습니다."),
	NOTIFICATION_ACCESS_DENIED(403, "NT002", "해당 알림에 대한 접근이 거부되었습니다.");


	private final String code;
	private final String message;
	private int status;

	ErrorCode(final int status, final String code, final String message) {
		this.status = status;
		this.message = message;
		this.code = code;
	}

	public String getMessage() {
		return this.message;
	}

	public String getCode() {
		return code;
	}

	public int getStatus() {
		return status;
	}
}
