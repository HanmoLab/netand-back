package org.pro.netandback.domain.s3.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public interface S3Service {
	void upload(String key, InputStream data, long contentLength, String contentType) throws IOException;
	void delete(List<String> keys);
}