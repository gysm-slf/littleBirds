package cn.littleBird.core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class Main {

//    public static void main(String[] args) {
//        Scanner scanner = new Scanner(System.in);
//        String str = scanner.nextLine();
//        System.out.println(lastLength(str));
//    }
//
//    public static int lastLength(String str){
//        str=str.trim();
//        int index=-1;
//        for (int i = str.length()-1; i >0; i--) {
//            if(String.valueOf(str.charAt(i)).equals(" ")){
//                index=i;
//                break;
//            }
//        }
//        return str.substring(index+1).length();
//    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String input;
        StringBuilder sb = new StringBuilder();
        while ((input = br.readLine()) != null) {
            int num = Integer.parseInt(input);
            for (int i = 2; i <= Math.sqrt(num); i++) {
                if (num % i == 0) {
                    sb.append(i).append(" ");
                    num = num / i;
                    i--;
                }
            }
            System.out.println(sb.append(num).append(" ").toString());
            sb.delete(0, sb.length());
        }

//        String fat = scanner.nextLine();
//        String son = scanner.nextLine();

//        System.out.println(str2InStr1Times(fat, son));
    }

    public static int str2InStr1Times(String str1, String str2) {
//        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//        char[] fat = br.readLine().toLowerCase().toCharArray();
//        char[] son = br.readLine().toLowerCase().toCharArray();
//        int count = 0;
//        for (int i = 0; i < fat.length; i++) {
//            if (fat[i] == son[0]) {
//                count++;
//            }
//        }
//        System.out.println(count);
        str1 = str1.toLowerCase();
        str2 = str2.toLowerCase();
        int count = 0;
        char[] char1 = str1.toCharArray();
        char[] char2 = str2.toCharArray();
        if (str1.contains(str2)) {
            for (int i = 0; i < char1.length; i++) {
                if (char1[i] == char2[0]) {
                    count++;
                }
            }
            return count;
        } else {
            return 0;
        }
    }

    public static void stu() {
    /*        BufferedReader bf=new BufferedReader(new InputStreamReader(System.in));
        String str;

        while((str=bf.readLine())!=null)
        {
            StringBuilder sb=new StringBuilder();
            boolean[] stu=new boolean[1001];

            int n=Integer.parseInt(str);
            for(int i=0;i<n;i++)
                stu[Integer.parseInt(bf.readLine())]=true;
            for(int i=0;i<1001;i++)
                if(stu[i])
                    sb.append(i).append("\n");
            sb.deleteCharAt(sb.length()-1);
            System.out.println(sb.toString());*/
    }

    /*\
    * 16进制
    *        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String input;
        while ((input=br.readLine())!=null){
            String num = input.substring(2);
            System.out.println(Long.parseLong(num,16));
        }
    * */

}
