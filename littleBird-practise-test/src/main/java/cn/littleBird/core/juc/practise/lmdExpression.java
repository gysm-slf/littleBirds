package cn.littleBird.core.juc.practise;

public class lmdExpression {
//    函数式编程
//    拉姆达表达式复习

}

@FunctionalInterface
interface Foo{
    public void demo();
    default void demo2(){

    }
    default void demo3(){

    }
    static void demo4(){

    }
    static void demo5(){
        System.out.println("什么情况");
    }

}