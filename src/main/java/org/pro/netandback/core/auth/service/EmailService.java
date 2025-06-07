package org.pro.netandback.core.auth.service;

public interface EmailService {
	void sendCode(String email);
	void verifyCode(String code);
	boolean isEmailVerified(String email);
	void clearVerified(String email);
}

