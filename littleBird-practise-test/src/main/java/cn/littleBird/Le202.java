package cn.littleBird;

import java.util.ArrayList;
import java.util.List;

/**
 * @author fans
 * @version 1.0
 * @description: TODO
 * @date 2022-08-25 上午 9:23
 */
public class Le202 {


//	https://leetcode.cn/problems/happy-number/

//	202. 快乐数
//	编写一个算法来判断一个数 n 是不是快乐数。
//
//			「快乐数」 定义为：
//
//	对于一个正整数，每一次将该数替换为它每个位置上的数字的平方和。
//	然后重复这个过程直到这个数变为 1，也可能是 无限循环 但始终变不到 1。
//	如果这个过程 结果为 1，那么这个数就是快乐数。
//	如果 n 是 快乐数 就返回 true ；不是，则返回 false 。
//
//
//	示例 1：
//
//	输入：n = 19
//	输出：true
//	解释：²
//			1²  + 9²  = 82
//			8²  + 2²  = 68
//			6²  + 8²  = 100
//			1²  + 0²  + 0²  = 1
//	示例 2：
//
//	输入：n = 2
//	输出：false
//
//
//	提示：
//
//			1 <= n <= 231 - 1
//	通过次数290,636提交次数460,317

	//   二〇二二年八月二十六日 14:49:18
	public static void main(String[] args) {

		;
		System.out.println(Solution.isHappy(19,new ArrayList<>()));
	}
}

class Solution {

	public static boolean isHappy(int n, List<Integer> ll) {
		System.out.println(n);
		if (ll.contains(n)) {
			return false;
		}

		int ge;
		int shi;
		int bai;
		if (n >= 100) {  //得先求出来他们的每个位置需要计算的数
			bai = n / 100;
			shi = n % 100 / 10;
			ge = n % 10;

			ll.add(n);
			n = bai * bai + shi * shi + ge * ge;

			if (n == 1) {
				return true;
			} else {
				isHappy(n,ll);
			}
		} else if (n >= 10) {  //得先求出来他们的每个位置需要计算的数
			shi = n % 100 / 10;
			ge = n % 10;

			ll.add(n);
			n = shi * shi + ge * ge;
			if (n == 1) {

				return true;
			} else {

				isHappy(n,ll);
			}

		} else if (n < 10) {  //得先求出来他们的每个位置需要计算的数
			ge = n % 10;

			ll.add(n);
			n = ge * ge;
			if (n == 1) return true;
			else {

				isHappy(n,ll);
			}
		}

		return false;

	}
}
