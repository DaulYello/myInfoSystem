package com.personal.info.myInfoSystem;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * web 程序启动类
 * @author huangshuang
 * @date 2019-8-10
 */
public class MyInfoSystemServletInitializer extends SpringBootServletInitializer {

    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder){
        return builder.sources(MyInfoSystemApplication.class);
    }

}
