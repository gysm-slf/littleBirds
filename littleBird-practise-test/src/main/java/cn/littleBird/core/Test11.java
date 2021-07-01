package cn.littleBird.core;

public class Test11 {


    public static void main(String[] args) {


        int n[] = new int[10];
        for (int i = 0; i < 10; i++) {
            n[i] = i*3;
        }
        System.out.println(new Test11().find(n, 9));


    }


    public int find(int[] n, int num) {
        int mix = 0, max = n.length;
        int flag = -1;

        for (int i = 0; i < n.length / 2; i++) {
            flag = (mix + max) / 2;
            if (num == n[flag]) {
                return flag;
            } else if (num > n[flag]) {
                mix = flag + 1;
            } else if(num<n[flag]){
                max=flag-1;
            }
            //else (num < n[flag]) {
//                max = flag - 1;
//            }
        }
        return flag;
    }
}
