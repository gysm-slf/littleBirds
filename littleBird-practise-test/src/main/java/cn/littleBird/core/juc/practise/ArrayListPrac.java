package cn.littleBird.core.juc.practise;

import cn.littleBird.core.Father;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.UUID;

public class ArrayListPrac {
//    ArrayList为什么不安全
//  请举例 1.故障现象 多个线程调用add方法
//        2.导致原因
//        3.解决方法
//        4.优化建议
    public static void main(String[] args) throws InterruptedException {
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < 3; i++) {
            new Thread(()->{arrayList.add(UUID.randomUUID().toString().substring(0,8));
                Iterator iterator = arrayList.iterator();
                while (iterator.hasNext()){
                    System.out.println(iterator.next());
                }
            }).start();
        }
        Thread.sleep(2000);
        System.out.println(arrayList.size());
        System.out.println(arrayList);

    }
}
