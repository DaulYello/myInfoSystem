package com.personal.info.myInfoSystem.config.web;


import com.personal.info.myInfoSystem.config.properties.SystemProperties;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.servlet.Cookie;
import org.apache.shiro.web.servlet.ShiroHttpSession;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.apache.shiro.web.session.mgt.ServletContainerSessionManager;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * shiro 权限管理配置
 * @author shuanghuang
 * @date 2019年08月06日 16:00
 */

@Configuration
public class ShiroConfig {

    /**
     * 记住密码cookie
     * cookie设置HttpOnly属性以增强安全，避免一定程度的跨站攻击。
     * 防止脚本攻击,禁止了通过脚本获取cookie信息,浏览器不会将其发送给任何第三方
     * @return
     */
    @Bean
    public SimpleCookie rememberMeCookie(){

        SimpleCookie simpleCookie = new SimpleCookie("rememberMe");

        simpleCookie.setHttpOnly(true);
        /*最大的生存时间7天，参数是秒为单位*/
        simpleCookie.setMaxAge(7*24*60*60);
        /*simpleCookie.setSecure(boolean secure)
        若使用HTTPS安全连接,则需要设置其属性为true
        simpleCookie.setPath(String path)
        设置当前Cookie所处于的相对路径
        simpleCookie.setName(String name)
        修改Session ID的名称，默认为"JSESSIONID"
        simpleCookie.setDomain(String domain)
        设置当前Cookie所处于的域*/

        return simpleCookie;
    }

    /**
     * remeberMe管理器，cipherKey生成见{@code Base64Test.java}
     * @param remeberMeCookie
     * @return
     */
    @Bean
    public CookieRememberMeManager rememberMeManager(SimpleCookie remeberMeCookie){
        CookieRememberMeManager manager = new CookieRememberMeManager();
        manager.setCipherKey(Base64.decode("bXlJbmZvU3lzdGVtAAAAAA=="));
        manager.setCookie(remeberMeCookie);
        return manager;
    }

    /**
     * 缓存管理器，使用Ehcache实现
     * @param ehcache
     * @return
     */
    @Bean
    public CacheManager getCacheShiroManager(EhCacheManagerFactoryBean ehcache) {
        EhCacheManager ehCacheManager = new EhCacheManager();
        ehCacheManager.setCacheManager(ehcache.getObject());
        return ehCacheManager;
    }

    /**
     * spring session管理器（多机环境）
     */
    @Bean
    @ConditionalOnProperty(prefix = "system", name = "spring-session-open", havingValue = "true")
    public ServletContainerSessionManager servletContainerSessionManager() {
        return new ServletContainerSessionManager();
    }
    /**
     * session 管理器（单机环境）
     */
    @Bean
    @ConditionalOnProperty(prefix = "system", name = "spring-session-open", havingValue = "false")
    public DefaultWebSessionManager defaultWebSessionManager(CacheManager cacheManager, SystemProperties systemProperties){
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        sessionManager.setCacheManager(cacheManager);
        sessionManager.setSessionValidationInterval(systemProperties.getSessionValidationInterval() * 1000);
        sessionManager.setGlobalSessionTimeout(systemProperties.getSessionInvalidateTime() * 1000);
        sessionManager.setDeleteInvalidSessions(true);
        sessionManager.setSessionValidationSchedulerEnabled(true);
        Cookie cookie = new SimpleCookie(ShiroHttpSession.DEFAULT_SESSION_ID_NAME);
        cookie.setName("shiroCookie");
        cookie.setHttpOnly(true);
        sessionManager.setSessionIdCookie(cookie);
        return sessionManager;
    }

}
