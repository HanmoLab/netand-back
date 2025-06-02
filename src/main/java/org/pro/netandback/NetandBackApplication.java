package org.pro.netandback;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class NetandBackApplication {

	public static void main(String[] args) {
		SpringApplication.run(NetandBackApplication.class, args);
	}

}
