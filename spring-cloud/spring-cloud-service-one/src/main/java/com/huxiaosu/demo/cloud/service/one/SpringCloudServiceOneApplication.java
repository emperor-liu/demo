package com.huxiaosu.demo.cloud.service.one;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;

@SpringBootApplication
@RefreshScope
public class SpringCloudServiceOneApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringCloudServiceOneApplication.class, args);
	}

}
