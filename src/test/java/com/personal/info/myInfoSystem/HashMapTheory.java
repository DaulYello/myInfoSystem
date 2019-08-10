package com.personal.info.myInfoSystem;

import java.util.HashMap;

public class HashMapTheory {

    public static void main(String agr[]){
        HashMap<String,Object> map = new HashMap<>();

        String test = "test";

        map.put(test,"riz");
        System.out.println("1test的hashcode值:"+test.hashCode());
        map.put("test","riz");
        System.out.println("2test的hashcode值:"+test.hashCode());

        map.put("test","skjfhsldgje;lk");

        map.put("yes","jllkk");

        System.out.println(map.get("test"));

    }
}
