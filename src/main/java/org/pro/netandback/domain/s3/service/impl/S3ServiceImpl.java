package org.pro.netandback.domain.s3.service.impl;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.DeleteObjectsRequest;
import com.amazonaws.services.s3.model.DeleteObjectsRequest.KeyVersion;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.pro.netandback.core.config.S3Properties;
import org.pro.netandback.domain.s3.service.S3Service;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class S3ServiceImpl implements S3Service {
	private final AmazonS3Client amazonS3Client;
	private final S3Properties properties;

	public S3ServiceImpl(AmazonS3Client amazonS3Client, S3Properties properties) {
		this.amazonS3Client = amazonS3Client;
		this.properties = properties;
	}

	@Override
	public void upload(String key, InputStream data, long contentLength, String contentType) throws IOException {
		ObjectMetadata metadata = new ObjectMetadata();
		metadata.setContentLength(contentLength);
		metadata.setContentType(contentType);

		amazonS3Client.putObject(new PutObjectRequest(properties.getBucket(), key, data, metadata));
	}

	@Override
	public void delete(List<String> keys) {
		if (keys == null || keys.isEmpty()) {
			return;
		}
		List<KeyVersion> versions = keys.stream()
			.map(KeyVersion::new)
			.collect(Collectors.toList());

		DeleteObjectsRequest deleteRequest = new DeleteObjectsRequest(properties.getBucket()).withKeys(versions);
		amazonS3Client.deleteObjects(deleteRequest);
	}
}
