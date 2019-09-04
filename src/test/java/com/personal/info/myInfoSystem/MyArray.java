package com.personal.info.myInfoSystem;


public class MyArray {

    private long[] arr;

    /*有效的数组元素长度*/
    private int elements;

    public MyArray(){
        arr = new long[50];
    }

    public MyArray(int maxsize){
        arr = new long[maxsize];
    }

    public void addArray(long vaule){
        arr[elements] = vaule;
        elements++;
    }

    public void dispaly(){
        System.out.print("[");
        for (int i=0;i<elements;i++){
            System.out.print(arr[i]+" ");
        }
        System.out.println("]");
    }

    /*根据值查位置*/
    public int search(long value){
        int i =0;
        for (;i<elements;i++){
            if (value == arr[i]){
                break;
            }
        }
        if (i == elements){
            return -1;
        }
        return i;
    }

    /*根据索引查值*/
    public long getValue(int index){
        if (index >elements || index<0){
            throw new ArrayIndexOutOfBoundsException();
        }else{
            return arr[index];
        }
    }

    /*从数组中删除指定数据*/
    public void delete(long value){
        int i=0;
        for (;i<elements;i++){
            if (arr[i] == value){
                for (int j=i;j<elements;j++){
                    arr[j] = arr[j+1];
                }
                elements--;
                break;
            }
        }
        if (i == elements){
            System.out.println("数组中压根没有"+value+"这个数");
        }
    }

    /*从数组中删除指定索引位置的数据*/
    public void deleteByIndex(int index){
        if (index >elements || index<0){
            throw new ArrayIndexOutOfBoundsException();
        }else{
            for (int i=index;i<elements;i++){
                arr[i]=arr[i+1];
            }
            elements--;
        }
    }

    /*跟新数组元素*/
    public void update(int index,long newValue){
        if (index >elements || index<0){
            throw new ArrayIndexOutOfBoundsException();
        }else{
            arr[index] = newValue;
        }
    }

    /*这个思路和选择排序差不多的*/
    public void sortMe(){
        int temp = 0;
        long min = 0;
        for (int i=0;i<elements;i++){
            min = arr[i];
            temp = i;
            for (int j = i+1;j<elements;j++){
                if (min > arr[j]){
                    min = arr[j];
                    temp = j;
                }
            }
            arr[temp] = arr[i];
            arr[i] = min;
        }
    }

    /*选择排序的效率比冒泡排序更好*/
    public void selectSort(){
        long temp = 0;
        int k = 0;//记录最小值的下标，默认第一个
        for (int i = 0;i<elements-1;i++){
            k = i;
            for (int j = i;j<elements;j++){
                if (arr[j] < arr[k]) {
                    k = j;
                }
            }
            temp = arr[i];
            arr[i] = arr[k];
            arr[k] = temp;
        }
    }

    /*冒泡排序*/
    public void bubbleSort(){

        /*每一次冒出最大的数*/
        long temp = 0;
        /*for (int i=0;i<elements;i++){
            for (int j = 0;j<elements-1-i;j++){
                if (arr[j] > arr[j+1]){
                    temp = arr[j];
                    arr[j] = arr[j+1];
                    arr[j+1] = temp;
                }
            }
        }*/
        /*每一次冒出最小的数*/
        for (int i=0;i<elements-1;i++){
            for (int j = elements-1;j>i;j--){
                if (arr[j] < arr[j-1]){
                    temp = arr[j];
                    arr[j] = arr[j-1];
                    arr[j-1] = temp;
                }
            }
        }
    }

    /**
     * 插入排序
     * 思想：它从数组的第二个元素开始i，第二个元素去和第一个元素比较
     * 如果第二个元素小于第一个元素，它们交换位置，i++,往后找下一个
     * 元素，这个元素又和左边的元素进行比较确定插入的位置。从此，i后
     * 面的元素就是有序的,将插入位置开始的数据依次往后移动一位。
     * 下面这个方法有问题呀
     */
    public void InsertSort(){

        long temp = 0;
        for (int i = 1;i<elements;i++){
            temp = arr[i];
            int j = i;
            while(j > 0 && arr[j] >= temp){//比较和移动同时进行
                arr[j] = arr[j-1];
                j--;
            }
            arr[j] = temp;
        }

    }

    /*构建一个有序数组*/
    public void orderlyInsert(long value){
        int i=0;
        for (;i<elements;i++){
            if (value < arr[i]){
                break;
            }
        }
        for (int j=elements;j>i;j--){
            arr[j] = arr[j-1];
        }
        arr[i] = value;
        elements++;
    }

    /*二分法查找*/
    public int binarySelect(long value){

        int middle = 0;
        int low = 0;
        int pow = elements;

        //方法一：
        /*while(true){
            middle = (pow-low)/2+low;
            if (low == middle || pow == middle){
                return -1;
            }
            if (value < arr[middle]){
                pow = middle;
            }else if (value > arr[middle]){
                low = middle;
            }else{
                break;
            }
        }
        return middle;
        */
        //方法二：
        while(true){
            middle = (pow+low)/2;
            if (value == arr[middle]){
                return middle;
            }else if (low > pow){
                return -1;
            }else{
                if (value < arr[middle]){
                    pow = middle-1;
                }else{
                    low = middle+1;
                }
            }
        }
    }
}
