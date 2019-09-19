/**
 * Copyright 2018-2020 stylefeng & fengshuonan (https://gitee.com/stylefeng)
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.personal.info.myInfoSystem.core.common.annotation;

import java.lang.annotation.*;

/**
 * 权限注解 用于检查权限 规定访问权限
 * @Retention 作用是定义被它所注解的注解保留多久。一共有三种策略，定义在RetentionPolicy枚举中:如下有说明
 * @Param RetentionPolicy.RUNTIME(一直保留到运行是,可以通过反射获取注解信息)、RetentionPolicy.CLASS(默认是该策略,被编译进class文件，
 * 但是不被VM运行时保留)、RetentionPolicy.SOURCE(注解在编译时就被忽略)
 * @Target 指定定义的这个注解@Permission是使用在什么地方，下面是指使用在方法上
 * @example @Permission({role1,role2})
 * @example @Permission
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface Permission {

    /**
     * <p>角色英文名称</p>
     * <p>使用注解时加上这个值表示限制只有某个角色的才可以访问对应的资源</p>
     * <p>常用在某些资源限制只有超级管理员角色才可访问</p>
     */
    String[] value() default {};
}
