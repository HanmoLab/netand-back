package org.pro.netandback.domain.s3.util;

import java.time.Instant;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;


@Component
public class S3KeyGenerator {
	public String generateProfileImageKey(Long userId, String originalFilename) {
		String cleanName = StringUtils.cleanPath(originalFilename);
		long timestamp = Instant.now().toEpochMilli();
		return String.format("profiles/%d/%d_%s", userId, timestamp, cleanName);
	}
}

