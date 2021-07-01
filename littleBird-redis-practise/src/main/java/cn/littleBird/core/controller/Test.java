package cn.littleBird.core.controller;

import redis.clients.jedis.Jedis;

import java.util.Iterator;
import java.util.Set;

public class Test {

    public static void main(String[] args) {
        Jedis jedis = new Jedis("127.0.0.1",6379);
        jedis.set("test1","value1");
        jedis.set("test2","value22");
        Set set = jedis.keys("*");
        System.out.println(set.size());
        Iterator ite = set.iterator();
        while (ite.hasNext()){
            System.out.println(ite.next());
        }
        System.out.println(jedis.ping());
    }
}
