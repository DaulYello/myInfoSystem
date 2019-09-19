package com.personal.info.myInfoSystem.core.common.annotation;

import com.personal.info.myInfoSystem.core.common.constant.dictmap.base.AbstractDictMap;
import com.personal.info.myInfoSystem.core.common.constant.dictmap.base.SystemDict;

import java.lang.annotation.*;

@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface BussinessLog {

    /**
     * 业务名称。例如“修改菜单”
     * @return
     */
    String value() default "";

    /**
     * 被修改的实体的唯一标识,例如:菜单实体的唯一标识为"id"
     */
    String key() default "id";

    /**
     * 字典(用于查找key的中文名称和字段的中文名称)
     */
    Class<? extends AbstractDictMap> dict() default SystemDict.class;

}
