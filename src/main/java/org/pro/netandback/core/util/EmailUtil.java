package org.pro.netandback.core.util;

import java.security.SecureRandom;

public class EmailUtil {
	private static final SecureRandom RANDOM = new SecureRandom();

	public static String generateCode() {
		return String.format("%06d", RANDOM.nextInt(1_000_000));
	}

	public static String buildSubject() {
		return "[Netand] 이메일 인증 코드 안내";
	}

	public static String buildHtmlBody(String code, long validMinutes) {
		return "<!DOCTYPE html>" +
			"<html lang='ko'>" +
			"<head>" +
			"  <meta charset='UTF-8'/>" +
			"  <meta name='viewport' content='width=device-width, initial-scale=1.0'/>" +
			"  <title>이메일 인증</title>" +
			"</head>" +
			"<body style='margin:0;padding:0;background-color:#f5f5f5;'>" +
			"  <div style='display:flex;justify-content:center;padding:40px 0;'>" +
			"    <div style='max-width:600px;width:100%;background:#ffffff;border:1px solid #e0e0e0;border-radius:8px;overflow:hidden;text-align:center;'>" +
			"      <div style='background:#4A90E2;color:#ffffff;padding:20px;font-size:24px;'>Netand 이메일 인증</div>" +
			"      <div style='padding:30px;text-align:center;color:#333333;'>" +
			"        <div style='border:1px solid #e0e0e0;padding:20px;border-radius:4px;background:#f9f9f9;'>" +
			"          <p style='margin:0 0 24px;font-size:16px;'>아래 <strong>6자리 인증 코드</strong>를 입력해 주세요. 유효 시간은 <strong>" + validMinutes + "분</strong>입니다.</p>" +
			"          <div style='margin:0 0 24px;'>" +
			"            <span style='display:inline-block;padding:10px 20px;font-size:28px;font-weight:bold;letter-spacing:4px;color:#4A90E2;background:#ffffff;border:1px dashed #4A90E2;border-radius:4px;'>" + code + "</span>" +
			"          </div>" +
			"        </div>" +
			"      </div>" +
			"      <div style='background:#f0f4f8;color:#999999;padding:20px;font-size:12px;'>© 2025 Netand. All rights reserved.</div>" +
			"    </div>" +
			"  </div>" +
			"</body>" +
			"</html>";
	}
}
