package cn.littleBird.demo;

import java.util.concurrent.TimeUnit;

//验证volatile不保证原子性
public class VolatileUniformityTest {

    public static void main(String[] args) {

        MyDate date = new MyDate();

        change2 c = new change2(date);

        for (int i=0;i<20;i++){
            Thread t = new Thread(c);
            t.start();
        }

        try {
            TimeUnit.MILLISECONDS.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

//        原子性为最终一致性   如果保证原子性，那么最终值应该为2w 但实际会存在丢数据
        System.out.println(date.num);
//        有原子性的自增  为什么atomicInteger不加synchronize就能保证原子性？
        System.out.println(date.atomicInteger);
    }

}

class change2 implements Runnable{
    public MyDate date;

    public change2(MyDate date){
        this.date=date;
    }

    @Override
    public void run() {
        for (int i=0;i<1000;i++){
            date.addOne();
            date.addPlus();
        }
    }
}

