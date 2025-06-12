package org.pro.netandback.core.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

@Configuration
public class S3Config {

	private final S3Properties props;
	public S3Config(S3Properties props) { this.props = props; }


	@Bean
	public AmazonS3Client amazonS3Client() {
		BasicAWSCredentials creds = new BasicAWSCredentials(props.getAccessKey(), props.getSecretKey());
		return (AmazonS3Client) AmazonS3ClientBuilder.standard()
			.withRegion(props.getRegion())
			.withCredentials(new AWSStaticCredentialsProvider(creds))
			.build();
	}
}
