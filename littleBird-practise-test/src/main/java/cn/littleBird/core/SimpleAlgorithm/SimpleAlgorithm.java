package cn.littleBird.core.SimpleAlgorithm;

/**
 * 简单算法demo
 */
public class SimpleAlgorithm {
    public static void main(String[] args) {
        System.out.println(jump(5));
    }
//
//1. 第一题（引子）：输出菲波那切数列的第N项。
//    斐波那契数列含义（百度百科）：
//    指的是这样一个数列：1、1、2、3、5、8、13、21、34、……在数学上，斐波纳契数列以如下被以递归的方法定义：F(0)=0，F(1)=1, F(n)=F(n-1)+F(n-2)（n>=2，n∈N*）
//
//    递归方式：
//    public static int fibnacci(int n){
//        if (n==0){
//            return 0;
//        }
//        if (n==1){
//            return 1;
//        }
//        return fibnacci(n-1)+fibnacci(n-2);
//    }
//    f 4 + f3
//        f 4 = f3 + f2
//            f3= 1 + 1
//            f2= 1
//        f 3 = 1 + 1
//            f2= 1
//            f1= 1

//    我们计算n为4的情况：那么我们需要做如下的计算：
//    Fibonacci(4) = Fibonacci(3) + Fibonacci(2);
//    = Fibonacci(2) + Fibonacci(1) + Fibonacci(1) + Fibonacci(0);
//    = Fibonacci(1) + Fibonacci(0) + Fibonacci(1) + Fibonacci(1) + Fibonacci(0);
//    看看，多做了多少计算。2 计算了 2次，1 计算了5次，0计算了3次。正常来说我们计算4次就可以了吧。这样相当于多做了4次。
//
//    非递归方式：
//    public static int fibnacci2(int n){
//        if (n==0){
//            return 0;
//        }
//        if (n==1 || n==2){
//            return  1;
//        }
//        int f1=1;
//        int f2=1;
//        int count=3;
//        while (count++<=n){
//            int temp=f1;
//            f1=f2;
//            f2=temp+f2;
//        }
//        return f2;
//    }
//    延伸到青蛙跳台阶问题：
//            2. 一只青蛙一次可以跳上1级台阶，也可以跳上2级。求该青蛙跳上一个n级的台阶总共有多少种跳法（先后次序不同算不同的结果）。
//    如果n=1，只有一种跳法，  [1]
//    如果n=2，那么有两种跳法，[1,1]，[2]
//    如果n=3，那么有三种跳法，[1,1,1],,[1,2],[2,1]
//    如果n=4，那么有五种跳法，[1,1,1,1],[1,1,2],[1,2,1],[2,1,1],[2,2]
//    如果n=5，那么有八种跳法，[1,1,1,1,1],[1,1,1,2],[1,1,2,1],[1,2,1,1],[2,1,1,1],[2,2,1],[2,1,2],[1,2,2]
//    结果为1，2，3，5，8  这不特么是斐波那切数列嘛
//    递归做法：
    public static int jump(int n){
        if (n==0)
            return 0;
        if (n==1)
            return 1;
        if (n==2)
            return 2;
        return jump(n-1)+jump(n-2);
    }
    /*
        jump(5)=jump(4)   +   jump(3)
               =jump(3)+jump(2)   +   jump(2)+jump(1)
               =jump(2)+jump(1)+2   +   2+1
        j4 + j3
            j4=j3+j2
                j3=2 + 1
                j2=2
            j3=j2+j1
                j2=2
                j1=1
    *
    * */
//    非递归做法：
//    public static int jump2(int n){
//        if (n==0)
//            return 0;
//        if (n==1)
//            return 1;
//        if (n==2)
//            return 2;
//        int n1=1;
//        int n2=2;
//        int count=2;
//        while (count++<=n){
//            int tmp=n1;
//            n1=n2;
//            n2=tmp+n2;
//        }
//        return n2;
//    }
//    //一只青蛙一次可以跳上1级台阶，也可以跳上2级……它也可以跳上n级。求该青蛙跳上一个n级的台阶总共有多少种跳法。
//    //f(n) = f(n-1) + f(n-2) + f(n-3) + ... + f(n-(n-1)) + f(n-n)
//3. 一只青蛙一次可以跳上1级台阶，也可以跳上2级……它也可以跳上n级。求该青蛙跳上一个n级的台阶总共有多少种跳法。
//
//    f(n) = f(n-1) + f(n-2) + f(n-3) + ... + f(n-(n-1)) + f(n-n)= f(0) + f(1) + f(2) + f(3) + ... + f(n-2)+f(n-1)
//    f(n-1) = f(0) + f(1)+f(2)+f(3) + ... + f((n-1)-1) = f(0) + f(1) + f(2) + f(3) + ... + f(n-2)
//    so  f(n)=2*f(n-1)
//    public int Jump3(int n) {
//        if (n <= 1) {
//            return 1;
//        } else {
//            return 2 * Jump3(n - 1);
//        }
//    }
// 4. 一只青蛙一次可以跳上1级台阶，也可以跳上2级……它也可以跳上n级。求该青蛙跳上一个m级的台阶总共有多少种跳法。
//    先列多项式：
//    f(n) =  f(n-1) + f(n-2) + f(n-3) + ... + f(n-m)
//    f(n-1) =   f(n-2) + f(n-3) + ... + f(n-m) + f(n-m-1)
//    化简得：f(n) = 2f(n-1) - f(n-m-1)
//
//    public static int Jump4(int n,int m ) {
//        //当大于m的时候是上面的公式
//        if(n > m){
//            return 2*Jump4(n-1, m)-Jump4(n-1-m, m);
//        }
//        //当小于等于m的时候就是和n级的相同了
//        if (n <= 1) {
//            return 1;
//        } else {
//            return 2 * Jump4(n - 1,n);
//        }
//    }
}
