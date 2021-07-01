package cn.littleBird.demo;

public class Test {

    public static void main(String[] args) {
        sellTickets s1 = new sellTickets();
        sellTickets s2 = new sellTickets();
        Thread t1 = new Thread(s1);
        Thread t2 = new Thread(s1);
        Thread t3 = new Thread(s1);
        Thread t4 = new Thread(s2);
        t1.start();
        t2.start();
        t3.start();
//        t4.start();
    }

}

class sellTickets implements Runnable{

    int totalTickets = 100;

    @Override
    public void run() {
        while (totalTickets > 0){
            totalTickets--;
            if(totalTickets<=0){
                System.err.println("简直头皮发麻,totalTickets:"+totalTickets);
            }
            System.out.println(Thread.currentThread().getName()+"正在售出第"+(100-totalTickets)+"张票，剩余"+totalTickets+"张票");
        }
    }
}