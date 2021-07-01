package cn.littleBird;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Prac2 {

    public static void main(String[] args) throws IOException {
        int z = 0;
        z=z++;
        System.out.println(z);
        z=++z;
        System.out.println(z);
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        String[] str1 = bf.readLine().split(" ");
        String[] str2 = bf.readLine().split(" ");
        int n = Integer.parseInt(bf.readLine());
        int[] n1 = new int[Integer.parseInt(str1[0])];
        int[] n2 = new int[Integer.parseInt(str2[0])];
        for (int i = 0; i < n1.length; i++)
            n1[i] = Integer.parseInt(str1[i + 1]);
        for (int i = 0; i < n2.length; i++)
            n2[i] = Integer.parseInt(str2[i + 1]);

        int a = 0;
        int b = 0;
        int min = 0;
        int tempa=999;
        int tempb=999;
        for (int i = 0; i < n - 1; i++) {
            if(i!=0){
                if(tempa>a){
                    tempa=a;
                    a=0;
                }
            }
            for (int j = 0; j < n; j++) {
                a += n1[i] + n2[j];
            }
        }
        for (int i = 0; i < n - 1; i++) {
            if(i!=0){
                if(tempb>b){
                    tempb=b;
                    b=0;
                }
            }
            for (int j = n - 1; j >= 0; j--) {
                b += n1[j] + n2[i];
            }
        }
        if(tempa<tempb){
            min=tempa;
        }else {
            min=tempb;
        }
        System.out.println(min);
    }
}
