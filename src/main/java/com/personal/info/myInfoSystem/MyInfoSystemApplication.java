package com.personal.info.myInfoSystem;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MyInfoSystemApplication {

	private final static Logger LOGGER = LoggerFactory.getLogger(MyInfoSystemApplication.class);
	public static void main(String[] args) {
		SpringApplication.run(MyInfoSystemApplication.class, args);
		LOGGER.info(MyInfoSystemApplication.class.getSimpleName()+" is success!");
	}

}
