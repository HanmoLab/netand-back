package org.pro.netandback.core.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
			.allowedOrigins(
				"http://localhost:3000",
				"https://localhost:3000",
				"https://netand.store",
				"https://www.netand.store",
				"http://netand.store",
				"http://www.netand.store"
				)
			.allowedMethods("*")
			.allowedHeaders("*")
			.allowCredentials(true)
			.exposedHeaders("Authorization");
	}

}
