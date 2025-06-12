package org.pro.netandback.domain.s3.util;

public class UrlUtils {
	public static String buildS3Url(String bucket, String region, String key) {
		return String.format("https://%s.s3.%s.amazonaws.com/%s", bucket, region, key);
	}
}
