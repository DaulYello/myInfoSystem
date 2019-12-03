package com.personal.info.myInfoSystem;

import cn.stylefeng.roses.core.config.WebAutoConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
@SpringBootApplication(exclude = {WebAutoConfiguration.class})
@EnableScheduling
public class MyInfoSystemApplication {

	private final static Logger LOGGER = LoggerFactory.getLogger(MyInfoSystemApplication.class);
	public static void main(String[] args) {
		SpringApplication.run(MyInfoSystemApplication.class, args);
		LOGGER.debug(MyInfoSystemApplication.class.getSimpleName()+" is success!");
	}

}
