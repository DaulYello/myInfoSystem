package com.personal.info.myInfoSystem.core.aop;

import com.personal.info.myInfoSystem.core.common.annotation.Permission;
import com.personal.info.myInfoSystem.core.shiro.service.PermissionCheckService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.naming.NoPermissionException;
import java.lang.reflect.Method;

@Aspect
@Component
@Order(200)
public class PermissionAop {

    @Autowired
    private PermissionCheckService check;
    /**
     * @within：用于匹配所以持有指定注解类型内的方法；
     *
     * @target：用于匹配当前目标对象类型的执行方法，其中目标对象持有指定的注解；
     *
     * @args：用于匹配当前执行的方法传入的参数持有指定注解的执行；
     *
     * @annotation：用于匹配当前执行方法持有指定注解的方法；
     */
    @Pointcut(value = "@annotation(com.personal.info.myInfoSystem.core.common.annotation.Permission)")
    private void cutPermission(){

    }

    /**
     * ProceedingJoinPoint获取当前方法和参数
     * @param point
     * @return
     */
    @Around("cutPermission()")
    public Object doPremission(ProceedingJoinPoint point) throws Throwable {
        MethodSignature ms = (MethodSignature)point.getSignature();
        Method method = ms.getMethod();
        Permission permission = method.getAnnotation(Permission.class);
        Object[] permissions = permission.value();
        if (permissions.length == 0){

            boolean result = check.checkAll();
            if (result){
                /**
                 * ProceedingJoinPoint 执行proceed方法的作用是让目标方法执行，
                 * 这也是环绕通知和前置、后置通知方法的一个最大区别。简单理解，
                 * 环绕通知=前置+目标方法执行+后置通知，proceed方法就是用于启动目标方法执行的
                 */
                return point.proceed();
            }else{
                throw new NoPermissionException();
            }
        }else{
            boolean result = check.check(permissions);
            if (result){
                return point.proceed();
            }else{
                throw new NoPermissionException();
            }
        }
    }
}
