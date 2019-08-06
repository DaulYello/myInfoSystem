package com.personal.info.myInfoSystem.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * myInfoSystem 项目配置
 * @author shuanghuang
 * @date 2019年08月06日 17:15
 * @param @ConfigurationProperties注解可以注入在application.properties配置文件中的属性
 *        使用@ConfigurationProperties注解封装配置文件中的数据
 */
@Component
@ConfigurationProperties(prefix = SystemProperties.PREFIX)
public class SystemProperties {

    public static final String PREFIX = "system";

    /**
     * session 验证失效时间（默认为15分钟 单位：秒）
     */
    private Integer sessionValidationInterval = 15 * 60;

    /**
     * session 失效时间（默认为30分钟 单位：秒）
     */
    private Integer sessionInvalidateTime = 30 * 60;

    public Integer getSessionValidationInterval() {
        return sessionValidationInterval;
    }

    public void setSessionValidationInterval(Integer sessionValidationInterval) {
        this.sessionValidationInterval = sessionValidationInterval;
    }

    public Integer getSessionInvalidateTime() {
        return sessionInvalidateTime;
    }

    public void setSessionInvalidateTime(Integer sessionInvalidateTime) {
        this.sessionInvalidateTime = sessionInvalidateTime;
    }
}
