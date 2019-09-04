package com.personal.info.myInfoSystem;

import javax.swing.plaf.synth.SynthOptionPaneUI;

public class TestMyArray {

    public static void main(String[] arg){
        MyArray myArray = new MyArray();
        /*创建一个无序数组*/
        myArray.addArray(34);
        myArray.addArray(23);
        myArray.addArray(1);
        myArray.addArray(2);
        myArray.addArray(-4);
        myArray.addArray(-5);
        myArray.addArray(-11);
        myArray.addArray(-23);
        myArray.dispaly();
        /*创建一个无序数组*/

        /*创建一个有序数组*/
        /*myArray.orderlyInsert(32);
        myArray.orderlyInsert(12);
        myArray.orderlyInsert(1);
        myArray.orderlyInsert(2);
        myArray.orderlyInsert(33);
        myArray.dispaly();*/
        /*创建一个有序数组*/

        /*System.out.println(myArray.search(5));
        System.out.println(myArray.getValue(1));*/
       /* myArray.delete(116);
        System.out.println("delete data return");
        myArray.dispaly();
        myArray.deleteByIndex(4);
        System.out.println("deleteByIndex data return");
        myArray.dispaly();*/
        /*System.out.println("update data method");
        myArray.update(3,8);*/
        //myArray.dispaly();


        //System.out.println(myArray.binarySelect(2));

        //myArray.bubbleSort();
//        myArray.selectSort();
        myArray.InsertSort();
        myArray.dispaly();
    }
}
