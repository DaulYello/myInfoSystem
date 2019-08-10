package com.personal.info.myInfoSystem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Test {

    public static void main(String [] arg){

        List<String> testList = new ArrayList<>();
        testList.add("1杨");
        testList.add("1李");
        testList.add("1王");
        testList.add("1张");
        testList.add("2杨");
        /*testList.add("2孙");*/
        /*testList.add("2赵");*/
        /*List<String> temAddList = new ArrayList<>();
        for(String test : testList)
        {
            if(test.startsWith("1"))
            {
                temAddList.add(test);
            }
        }
        testList.removeAll(temAddList);*/
        /*testList.removeIf(test->test.startsWith("1"));
        System.out.println(JSON.toJSONString(testList));*/

        /*new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("Before Java8, too much code for too little to do");
            }
        }).start();

        //Java 8方式：
        new Thread( () -> System.out.println("In Java8, Lambda expression rocks !!") ).start();*/

        // Java 8之前：
        List features = Arrays.asList("Lambdas", "Default Method", "Stream API", "Date and Time API");
        for (Object feature : features) {
            System.out.println(feature);
        }

        // Java 8之后：
        List features1 = Arrays.asList("Lambdas", "Default Method", "Stream API", "Date and Time API");
        features1.forEach(n -> System.out.println(n));

        // 使用Java 8的方法引用更方便，方法引用由::双冒号操作符标示，
        // 看起来像C++的作用域解析运算符
        features.forEach(System.out::println);
    }
}
