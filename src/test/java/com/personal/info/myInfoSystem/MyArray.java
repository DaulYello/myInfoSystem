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

    /*冒泡排序*/
    public void bubbleSort(){

        long temp = 0;
        for (int i=0;i<elements;i++){
            for (int j = 0;j<elements-1-i;j++){
                if (arr[j] > arr[j+1]){
                    temp = arr[j];
                    arr[j] = arr[j+1];
                    arr[j+1] = temp;
                }
            }

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
