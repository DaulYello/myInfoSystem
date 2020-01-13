package com.personal.info.myInfoSystem;

import cn.stylefeng.roses.core.config.WebAutoConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableScheduling;
@SpringBootApplication(exclude = {WebAutoConfiguration.class})
@EnableScheduling
public class MyInfoSystemApplication extends SpringBootServletInitializer {

	private final static Logger LOGGER = LoggerFactory.getLogger(MyInfoSystemApplication.class);
	public static void main(String[] args) {
		SpringApplication.run(MyInfoSystemApplication.class, args);
		LOGGER.debug(MyInfoSystemApplication.class.getSimpleName()+" is success!");
	}

	/**
	 * 打war包用的
	 */
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(MyInfoSystemApplication.class);
	}

}
