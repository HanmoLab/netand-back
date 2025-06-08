package org.pro.netandback.core.auth.service.impl;

import org.pro.netandback.core.auth.dao.EmailCodeDao;
import org.pro.netandback.core.auth.service.EmailService;
import org.pro.netandback.core.error.ErrorCode;
import org.pro.netandback.core.error.exception.EmailSendFailedException;
import org.pro.netandback.core.util.EmailUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class EmailServiceImpl implements EmailService {

	private final EmailCodeDao emailCodeDao;
	private final JavaMailSender mailSender;

	private static final long CODE_TTL_SECONDS     = 5 * 60;
	private static final long VERIFIED_TTL_SECONDS = 30 * 60;

	@Value("${spring.mail.username}")
	private String fromAddress;

	@Override
	public void sendCode(String email) {
		String code = EmailUtil.generateCode();
		emailCodeDao.saveCode(code, email, CODE_TTL_SECONDS);

		String subject  = EmailUtil.buildSubject();
		String htmlBody = EmailUtil.buildHtmlBody(code, CODE_TTL_SECONDS / 60);

		try {
			MimeMessage message = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, "UTF-8");
			helper.setFrom(fromAddress);
			helper.setTo(email);
			helper.setSubject(subject);
			helper.setText(htmlBody, true);
			mailSender.send(message);
		} catch (MessagingException | MailException ex) {
			throw new EmailSendFailedException(
				ErrorCode.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	@Transactional
	public void verifyCode(String code) {
		String email = emailCodeDao.getEmail(code);
		if (email == null) {throw new EmailSendFailedException(ErrorCode.EMAIL_CODE_EXPIRED);}
		emailCodeDao.markVerified(email, VERIFIED_TTL_SECONDS);
		emailCodeDao.deleteCode(code);
	}

	@Override
	public boolean isEmailVerified(String email) {
		return emailCodeDao.isVerified(email);
	}

	@Override
	public void clearVerified(String email) {
		emailCodeDao.clearVerified(email);
	}
}

