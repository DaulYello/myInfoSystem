package com.personal.info.myInfoSystem.config.web;


import com.personal.info.myInfoSystem.config.properties.SystemProperties;
import com.personal.info.myInfoSystem.core.interceptor.MyUserFilter;
import com.personal.info.myInfoSystem.core.shiro.ShiroDbRealm;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.Cookie;
import org.apache.shiro.web.servlet.ShiroHttpSession;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.apache.shiro.web.session.mgt.ServletContainerSessionManager;
import org.springframework.beans.factory.config.MethodInvokingFactoryBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import static com.personal.info.myInfoSystem.core.common.constant.Const.NONE_PERMISSION_RES;


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

    /**
     * 项目自定义的Realm
     */
    @Bean
    public ShiroDbRealm shiroDbRealm() {
        return new ShiroDbRealm();
    }


    /**
     * 在方法中 注入 securityManager,进行代理控制
     */
    @Bean
    public MethodInvokingFactoryBean methodInvokingFactoryBean(DefaultWebSecurityManager securityManager) {
        MethodInvokingFactoryBean bean = new MethodInvokingFactoryBean();
        bean.setStaticMethod("org.apache.shiro.SecurityUtils.setSecurityManager");
        bean.setArguments(securityManager);
        return bean;
    }

    /**
     * Shiro的过滤器链
     * 7
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilter(DefaultWebSecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilter = new ShiroFilterFactoryBean();
        shiroFilter.setSecurityManager(securityManager);
        /**
         * 默认的登陆访问url
         */
        shiroFilter.setLoginUrl("/login");
        /**
         * 登陆成功后跳转的url
         */
        shiroFilter.setSuccessUrl("/");
        /**
         * 没有权限跳转的url
         */
        shiroFilter.setUnauthorizedUrl("/global/error");

        /**
         * 覆盖默认的user拦截器(默认拦截器解决不了ajax请求 session超时的问题,若有更好的办法请及时反馈作者)
         */
        HashMap<String, Filter> myFilters = new HashMap<>();
        myFilters.put("user", new MyUserFilter());
        shiroFilter.setFilters(myFilters);

        /**
         * 配置shiro拦截器链
         *
         * anon  不需要认证
         * authc 需要认证
         * user  验证通过或RememberMe登录的都可以
         *
         * 当应用开启了rememberMe时,用户下次访问时可以是一个user,但不会是authc,因为authc是需要重新认证的
         *
         * 顺序从上到下,优先级依次降低
         *
         * api开头的接口，走rest api鉴权，不走shiro鉴权
         *
         */
        Map<String, String> hashMap = new LinkedHashMap<>();
        for (String nonePermissionRe : NONE_PERMISSION_RES) {
            hashMap.put(nonePermissionRe, "anon");
        }
        hashMap.put("/**", "user");
        shiroFilter.setFilterChainDefinitionMap(hashMap);
        return shiroFilter;
    }

    /**
     * Shiro生命周期处理器:
     * 用于在实现了Initializable接口的Shiro bean初始化时调用Initializable接口回调(例如:UserRealm)
     * 在实现了Destroyable接口的Shiro bean销毁时调用 Destroyable接口回调(例如:DefaultSecurityManager)
     */
    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    /**
     * 启用shrio授权注解拦截方式，AOP式方法级权限检查
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(DefaultWebSecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor =
                new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

    /**
     * 安全管理器
     */
    @Bean
    public DefaultWebSecurityManager securityManager(CookieRememberMeManager rememberMeManager, CacheManager cacheShiroManager, SessionManager sessionManager) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(this.shiroDbRealm());
        securityManager.setCacheManager(cacheShiroManager);
        securityManager.setRememberMeManager(rememberMeManager);
        securityManager.setSessionManager(sessionManager);
        return securityManager;
    }
}
