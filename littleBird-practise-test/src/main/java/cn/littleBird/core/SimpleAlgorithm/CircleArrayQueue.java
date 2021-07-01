package cn.littleBird.core.SimpleAlgorithm;

import java.util.Map;
import java.util.Scanner;

public class CircleArrayQueue {

    int front=0;
    int rear=0;
    int maxSize;
    int[] arr;

    public CircleArrayQueue(int maxSize){
        this.maxSize=maxSize;
        arr = new int[maxSize];
    }

    public boolean isEmpty(){
        return (front)%maxSize==rear;
    }

    public boolean isFull(){
        if((rear+1)%maxSize==front)
        return true;
        return false;
    }

    public void addQueue(int v){
        if(this.isFull()){
            throw new RuntimeException("queue is full, can't insert.");
        }
        arr[rear]=v;
        rear = ((rear+1)%maxSize);
    }

    public int getQueue(){
        if(this.isEmpty()){
            throw new RuntimeException("queue is empty, can't remove.");
        }
        int temp = arr[front];
        front=(++front)%maxSize;
        return temp;
//        front = (front%maxSize)+1;
//        return arr[front-1];
    }

    public void show(){
        if(this.isEmpty()){
            throw new RuntimeException("queue is empty, can't show.");
        }
        for (int i = front; i < front+this.size(); i++) {
            System.out.println(arr[i%maxSize]);
        }
//        SortsUtilsImpl.printArrays(this.arr)a;
    }

    public int size(){
        return (rear-front+maxSize)%maxSize;
    }

    public static void main(String[] args) {

        int v;
        String o;
        CircleArrayQueue circleArrayQueue = new CircleArrayQueue(4);
        Scanner scanner = new Scanner(System.in);

        boolean flag = true;
            System.out.println("数组模拟环形队列已启动:");
        while (flag){
            System.out.println("a(add):添加数到队列");
            System.out.println("r(remove):添加数到队列");
            System.out.println("l(list):添加数到队列");
            System.out.println("e(exit):添加数到队列");
            o=scanner.next();
            switch (o){
                case "a":
                    System.out.println("请输入添加值:");
                    v=Integer.valueOf(scanner.next());
                    try {
                        circleArrayQueue.addQueue(v);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case "r":
                    try {
                        System.out.println(circleArrayQueue.getQueue());
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case "l":
                    try {
                        circleArrayQueue.show();
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case "e":
                    flag=false;
                    System.out.println("program is exit ...");
                    break;
            }
        }
    }
}
