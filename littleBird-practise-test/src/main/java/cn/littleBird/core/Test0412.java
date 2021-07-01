package cn.littleBird.core;

public class Test0412 {

    private String value;
    int i = 0;
    public  void test() {
        Object obj = new Object();
//        System.out.println(testTry());
//        Zi zi = new Zi();
//        Zi zi2 = new Zi("zi2");
//        Test0412 t = new Zi("admin");

        new Thread(() -> {
            synchronized (obj){
                while (true){
                    if(i>=100){
                        obj.notify();
                        try {
                            obj.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }else {
                        i++;
                        System.out.println("生产了一个,当前number:"+i);
                    }
                }
            }
        }).start();
        new Thread(() -> {
            synchronized (obj){
                while (true){
                    if(i<=0){
                        obj.notify();
                        try {
                            obj.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }else {
                        i--;
                        System.out.println("消费了一个,当前number:"+i);
                    }
                }
            }

        }).start();
    }

    public Test0412() {
        System.out.println("Father constr blank");
    }

    public Test0412(String value) {
        System.out.println("Father constr");
        this.value = value;
    }

    static {
        System.out.println("father static");
    }

    public static int testTry() {
        //最终结果为 6
//        try {
//            return 5 / 0;
//        } catch (Exception e) {
//            return 10;
//        } finally {
//            return 6;
//        }
//        //最终结果为 6
//        int a = 0;
//        int b = 0;
//        try {
//            a = 6;
//            b = 5 / 0;
//            return a;
//        } catch (Exception e) {
//            a = 20;
//            return a;
//        } finally {
//            a = 30;
//            return a;
//        }
        //        //最终结果为 30
        int a = 0;
        int b = 0;
        try {
            a = 6;
            b = 5 / 0;
            return a;
        } catch (Exception e) {
            a = 20;
            return a;
        } finally {
            a = 30;
        }
//        最终结果为20
    }

    public static void main(String[] args) {
//        new Test0412().test();
        System.out.println("testTry() = " + testTry());
    }
}


class Zi extends Test0412 {
    private String name;

    public Zi() {
        System.out.println("Zi constru blank");
    }

    public Zi(String name) {
        this.name = name;
        System.out.println("Zi constru");
    }
}
