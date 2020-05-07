package com.personal.info.myInfoSystem;

import org.junit.Test;

/**
 * 单元测试类
 */
public class UnitTesting {

    /**
     * 随机数或随机字母
     * 'a':表示小写
     * 'A':表示大写
     */
    @Test
    public void random () {
        String str = "";
        int digits = 4; //位数
        for (int i=0;i<digits;i++){
            str = str + (char)(Math.random()*26+'a');
        }

        System.out.println(str);
    }

}
