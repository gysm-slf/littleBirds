package cn.littleBird;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Prac {

    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        int start = 0;
        int n = Integer.parseInt(bf.readLine());
        String str = bf.readLine();
        String[] context = str.split("-");
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i < context.length; i++) {
            sb.append(context[i]);
        }
        int newLen = sb.length() / n;
        if (sb.length() % n != 0)
            newLen++;
        String[] newStr = new String[newLen];
        for (int i = 0; i < newStr.length; i++) {
            if (i == newStr.length - 1) {
                newStr[i] = convert(sb.substring(start, sb.length()));
            } else {
                newStr[i] = convert(sb.substring(start, start + n));
                start += n;
            }
        }
        for (int i = 0; i < newStr.length; i++) {
            if (i == 0)
                System.out.print(context[0] + "-");
            if (i != newStr.length - 1)
                System.out.print(newStr[i] + "-");
            if (i == newStr.length - 1) {
                System.out.print(newStr[i]);
            }
        }
    }

    public static String convert(String str) {
        int lower = 0;
        int higher = 0;
        String result;
        char[] chr = str.toCharArray();
        for (int i = 0; i < chr.length; i++) {
            if (chr[i] >= 'a' && chr[i] <= 'z') {
                lower++;
            }
            if (chr[i] >= 'A' && chr[i] <= 'Z') {
                higher++;
            }
        }
        if (lower > higher) {
            result = str.toLowerCase();
        } else if (lower == higher) {
            result = str;
        } else {
            result = str.toUpperCase();
        }
        return result;
    }
}
