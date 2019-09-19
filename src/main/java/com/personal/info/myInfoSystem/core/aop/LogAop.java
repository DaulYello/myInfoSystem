package com.personal.info.myInfoSystem.core.aop;

import cn.stylefeng.roses.core.util.HttpContext;
import com.personal.info.myInfoSystem.core.common.annotation.BussinessLog;
import com.personal.info.myInfoSystem.core.common.constant.dictmap.base.AbstractDictMap;
import com.personal.info.myInfoSystem.core.log.LogManager;
import com.personal.info.myInfoSystem.core.log.LogObjectHolder;
import com.personal.info.myInfoSystem.core.log.factory.LogTaskFactory;
import com.personal.info.myInfoSystem.core.shiro.ShiroKit;
import com.personal.info.myInfoSystem.core.shiro.ShiroUser;
import com.personal.info.myInfoSystem.core.util.Contrast;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Map;

@Aspect
@Component
public class LogAop {

    private static final Logger LOGGER = LoggerFactory.getLogger(LogAop.class);

    @Pointcut(value = "@annotation(com.personal.info.myInfoSystem.core.common.annotation.BussinessLog)")
    public void cutService() {

    }

    @Around("cutService()")
    public Object recodeSysLog(ProceedingJoinPoint point) throws Throwable {

        //先执行业务(point.proceed() 执行目标方法)
        Object result = point.proceed();

        try {
            handle(point);
        } catch (Exception e) {
            LOGGER.error("日志记录出错!", e);
        }

        return result;
    }

    private void handle(ProceedingJoinPoint point) throws Exception {

        //获取拦截的方法名
        Signature sig = point.getSignature();
        MethodSignature ms = null;

        if (!(sig instanceof MethodSignature)) {
            throw new IllegalArgumentException("该注解只能用于方法");
        }

        ms = (MethodSignature) sig;
        Object target = point.getTarget();
        Method currentMethod = target.getClass().getMethod(ms.getName(), ms.getParameterTypes());
        String methodName = currentMethod.getName();

        //如果用户没有登录，不做日志
        ShiroUser user = ShiroKit.getUserNotNull();
        if (null == user) {
            return;
        }

        //获取拦截方法的参数
        String className = point.getTarget().getClass().getName();
        Object params[] = point.getArgs();

        //获取操作名称
        BussinessLog annotation = currentMethod.getAnnotation(BussinessLog.class);
        String bussinessName = annotation.value();
        String key = annotation.key();
        Class dictClass = annotation.dict();

        /*StringBuilder sb = new StringBuilder();
        for (Object param : params) {
            sb.append(param);
            sb.append(" & ");
        }*/

        //如果设计到修改，比对变化
        String msg = "";
        if (bussinessName.contains("修改") || bussinessName.contains("编辑")) {
            //把前面缓存的对象取出来
            Object objOld = LogObjectHolder.me().get();
            Map<String, String> objNew = HttpContext.getRequestParameters();
            msg = Contrast.contrastObj(dictClass, key, objOld, objNew);
        }else{
            Map<String, String> parameters = HttpContext.getRequestParameters();
            AbstractDictMap dictMap = (AbstractDictMap) dictClass.newInstance();
            msg = Contrast.parseMutiKey(dictMap, key, parameters);
        }

        LogManager.me().executeLog(LogTaskFactory.bussinessLog(user.getId(), bussinessName, className, methodName, msg));
    }
}
