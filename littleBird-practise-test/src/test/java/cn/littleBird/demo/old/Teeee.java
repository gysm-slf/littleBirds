package cn.littleBird.demo.old;

import java.util.Scanner;

public class Teeee {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Scanner reader = new Scanner(System.in);
		System.out.println("input first string");
		String s1 = reader.nextLine();
		System.out.println("input second string");
		String s2 = reader.nextLine();
		String copy = s2;
		int sum = 0;
		while (copy.contains(s1)) {
			sum++;
			copy = copy.replaceFirst(s1, "");
		}
		System.out.println(s1 + "在" + s2 + "中出现的次数为：" + sum);
	}

}
