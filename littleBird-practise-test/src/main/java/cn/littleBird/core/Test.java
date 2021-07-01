package cn.littleBird.core;

import cn.littleBird.config.AppConfig;
import cn.littleBird.core.SimpleAlgorithm.SortsUtilsImpl;
import cn.littleBird.core.controller.UserController;
import cn.littleBird.core.service.UserService;
import cn.littleBird.core.service.impl.UserServiceImpl;
import cn.littleBird.utils.SingleLinkedList;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.*;

/*
*   拦截器
*   过滤器
*   处理excel、word、xml等
*   spring底层
*   简介
*   离职原因
*   精度问题
*   mysql搜索引擎
*   mysql与oracle区别
* */
public class Test {
    int a[];
    static int b[];
    Father wh = new Father();

    public static void main(String[] args) {
        new ArrayList().add(null);
//        switch (5){
//            case 5:
//                System.out.println("1");
//                break;
//            case 4:
//                System.out.println("5");
//            case 3:
//                System.out.println("3");
//            default:
//                System.out.println("defau");
//        }
//        SingleLinkedList<String> singleLinkedList = new SingleLinkedList<String>();
////        singleLinkedList.reverse(0);
////        for (int i = 0; i < 400; i++) {
//            singleLinkedList.add("123");
//            singleLinkedList.add("789");
//            singleLinkedList.add("444");
//            singleLinkedList.add("zzz");
//            singleLinkedList.add("zazz");
////        }
//        System.out.println(singleLinkedList.size());
//////        System.out.println(singleLinkedList.isOpenLinked());
//        singleLinkedList.show();
//        singleLinkedList.orderReverse();
//        singleLinkedList.show();
////        singleLinkedList.reverse(0);
////        singleLinkedList.reverse(1);
//        String a = new String();
//        Object s = singleLinkedList;
//        System.out.println(SingleLinkedList.isOpenLinked(singleLinkedList));
//        singleLinkedList.isOpenLinked();
//        singleLinkedList.add("213333333333");
//        singleLinkedList.isOpenLinked();
//        singleLinkedList.show();
//        SingleLinkedList.isOpenLinked(singleLinkedList);
//        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
//        System.out.println(ac.getBean(Son.class));
////    1    System.out.println(ac.getBean(UserServiceImpl.class));
//        System.out.println(ac.getBean(UserController.class));
//        System.out.println();
////    1    ((UserServiceImpl)ac.getBean("userServiceImpl")).sayHello();
//        ac.getBean(UserServiceImpl.class).sayHello();
//        void linkLast(E e) {
//            final LinkedList.Node<E> l = last;
//            final LinkedList.Node<E> newNode = new LinkedList.Node<>(l, e, null);
//            last = newNode;
//            if (l == null)
//                first = newNode;
//            else
//                这里的l.next是上一次的node   注意引用之间的转移
//                l.next = newNode;
//            size++;
//            modCount++;
//        }
//        Father f = new Father();
//        Father q = f;
//        System.out.println(q.name);
//        f.name="asdsd";
//        System.out.println(q.name);

//        System.out.println(b==null);
//        new Test().test();
//        Father f = new Son();
//        System.out.println(f.name);
//        f.say();

//        new Object();
//        Son q = (Son)f;
//        System.out.println(q.age);
//        q.cry();
//        int n = 0;
//        int hash= 8;
////        System.out.println((n - 1) & hash);
////        Son s = new Son();
////        System.out.println(s.name);
////        System.out.println(s.age);
//        HashSet set = new HashSet();
//        set.add("123");
//        set.add(new Object());
//        set.add("fuckingggggggggggggggg");
//        set.add(new Father());
//        set.add("slf");
//        Iterator iterator = set.iterator();
//        while (iterator.hasNext())
//            System.out.println("set1"+iterator.next());
//
//        System.out.println();
//        LinkedHashMap m = new LinkedHashMap();
//        LinkedHashSet set2 = new LinkedHashSet();
//        set2.add("123");
//        set2.add(new Object());
//        set2.add("fuckingggggggggggggggg");
//        set2.add(new Father());
//        set2.add("slf");
//        Iterator iterator2 = set2.iterator();
//        while (iterator2.hasNext())
//            System.out.println("set2"+iterator2.next());
//        LinkedList<Integer> a  = new LinkedList<Integer>();
//        for (int i = 0; i < 12; i++) {
//            a.add(3);
//            a.add(3,4);
//            a.contains("a");
//        }
//        System.out.println(a.size());
//        int[] n = new int[0];
//        System.out.println(n);
//        if(n==null){
//
//        }
//        System.out.println();
//        HashMap map = new HashMap();
//        map.put("a","1");
//        map.put("2","b");
//        map.put("dd","fuck");
//        String s;
//        Set set = map.entrySet();
////        Set set = map.keySet();
//        Iterator iterator = set.iterator();
//        while (iterator.hasNext()){
//            s=(String) iterator.next();
//            System.out.println(map.get(s));
//            System.out.println(iterator.next());
//            System.out.println(((Map.Entry)iterator.next()).getValue());
//        }
        }

//    public void test(){
//        Father f = wh;
//        Father q = new Father();
//        wh=q;
//        q.name="qn";
//        wh.name="whn";
//        f.name="fname";
//        System.out.println(f);
//        System.out.println(q);
//        System.out.println(wh);
//        f.name="asdsd";
//        System.out.println(f);
//        System.out.println(q);
//        System.out.println(wh);
//    }
    }
