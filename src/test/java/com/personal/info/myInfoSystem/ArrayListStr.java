package com.personal.info.myInfoSystem;

import com.personal.info.myInfoSystem.core.common.controller.SystemErrorView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ArrayListStr {
    public static void main(String [] arg){
        String classIdStr ="1,2,-2";
        String[] classIds = classIdStr.split(",");
        List<String> li = Arrays.asList(classIds);
        List<String> target = new ArrayList<String>();
        target.add("-2");
        target.addAll(li);
        for (int i=0;i<target.size();i++){
            System.out.println(target.get(i));
        }
    }
}
