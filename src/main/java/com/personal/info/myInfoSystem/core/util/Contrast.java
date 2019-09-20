package com.personal.info.myInfoSystem.core.util;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.personal.info.myInfoSystem.core.common.constant.dictmap.base.AbstractDictMap;
import com.personal.info.myInfoSystem.core.common.constant.dictmap.factory.DictFieldWarpperFactory;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.Map;

/**
 *比较两个对象变化的工具
 */
public class Contrast {

    //记录每个修改字段的分隔符
    public static final String separator = ";;;";

    /**
     * 比较两个对象pojo1和pojo2,并输出不一致信息
     *
     */
    public static String contrastObj(Class dictClass, String key, Object pojo1, Map<String, String> pojo2) throws IllegalAccessException, InstantiationException {
        AbstractDictMap dictMap = (AbstractDictMap) dictClass.newInstance();
        String str = parseMutiKey(dictMap, key, pojo2) + separator;
        try {
            Class clazz = pojo1.getClass();
            Field[] fields = pojo1.getClass().getDeclaredFields();
            int i = 1;
            for (Field field : fields) {
                if ("serialVersionUID".equals(field.getName())) {
                    continue;
                }
                // 获取bean的某个属性的描述符
                PropertyDescriptor pd = new PropertyDescriptor(field.getName(), clazz);
                Method getMethod = pd.getReadMethod(); //获得用于读取属性值的方法；
                Object o1 = getMethod.invoke(pojo1); // 读取属性值
                Object o2 = pojo2.get(StrUtil.lowerFirst(getMethod.getName().substring(3)));
                if (o1 == null || o2 == null) {
                    continue;
                }
                if (o1 instanceof Date) {
                    o1 = DateUtil.formatDate((Date) o1);
                } else if (o1 instanceof Integer) {
                    o2 = Integer.parseInt(o2.toString());
                }
                if (!o1.toString().equals(o2.toString())) {
                    if (i != 1) {
                        str += separator;
                    }
                    String fieldName = dictMap.get(field.getName());
                    String fieldWarpperMethodName = dictMap.getFieldWarpperMethodName(field.getName());
                    if (fieldWarpperMethodName != null) {
                        Object o1Warpper = DictFieldWarpperFactory.createFieldWarpper(o1, fieldWarpperMethodName);
                        Object o2Warpper = DictFieldWarpperFactory.createFieldWarpper(o2, fieldWarpperMethodName);
                        str += "字段名称:" + fieldName + ",旧值:" + o1Warpper + ",新值:" + o2Warpper;
                    } else {
                        str += "字段名称:" + fieldName + ",旧值:" + o1 + ",新值:" + o2;
                    }
                    i++;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }


    /**
     * 解析多个key(逗号隔开的)
     *
     * @author stylefeng
     * @Date 2017/5/16 22:19
     */
    public static String parseMutiKey(AbstractDictMap dictMap, String key, Map<String, String> requests) {
        StringBuilder sb = new StringBuilder();
        if (key.contains(",")) {
            String[] keys = key.split(",");
            for (String item : keys) {
                String fieldWarpperMethodName = dictMap.getFieldWarpperMethodName(item);
                String value = requests.get(item);
                if (fieldWarpperMethodName != null) {
                    Object valueWarpper = DictFieldWarpperFactory.createFieldWarpper(value, fieldWarpperMethodName);
                    sb.append(dictMap.get(item)).append("=").append(valueWarpper).append(",");
                } else {
                    sb.append(dictMap.get(item)).append("=").append(value).append(",");
                }
            }
            return StrUtil.removeSuffix(sb.toString(), ",");
        } else {
            String fieldWarpperMethodName = dictMap.getFieldWarpperMethodName(key);
            String value = requests.get(key);
            if (fieldWarpperMethodName != null) {
                Object valueWarpper = DictFieldWarpperFactory.createFieldWarpper(value, fieldWarpperMethodName);
                sb.append(dictMap.get(key)).append("=").append(valueWarpper);
            } else {
                sb.append(dictMap.get(key)).append("=").append(value);
            }
            return sb.toString();
        }
    }
}
