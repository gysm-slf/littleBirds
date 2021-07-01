package cn.littleBird.proxy;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Random;

public class ProcessTrack {

	/*
	 * public static long processTrackStart(Class clazz,String method) { long time
	 * =System.currentTimeMillis(); System.out.println(getFormatDate(new
	 * Date(time))+" - "+clazz.toString()+"_"+method+"_(start ..."); //method start
	 * time :
	 * 
	 * return time; }
	 * 
	 * public static void processTrackEnd(Class clazz,String method,long time) {
	 * System.out.println(getFormatDate(new
	 * Date(time))+" - "+clazz.toString()+"_"+method+"_end ...)");
	 * System.out.print("Method run end, total time consumption: ");
	 * System.out.println(System.currentTimeMillis()-time+"ms."); }
	 */

	/**
	 * @version:v1.0
	 * @Description: JDK1.8
	 * @author:sulongfei
	 * @date: 2020-9-18
	 * @param clazz
	 * @param method
	 * @return
	 */
	public static long processTrackStart(Class clazz, String method, String TransLogCode) {
		System.out.println(LocalDate.now().toString() + " " + LocalTime.now().toString() + "-" + TransLogCode + " "
				+ clazz.toString() + "." + method + "()_(start ...");
		return System.currentTimeMillis();
	}

	/**
	 * @version:v1.0
	 * @Description: JDK1.8
	 * @author:sulongfei
	 * @date: 2020-9-18
	 * @param clazz
	 * @param method
	 * @param time
	 */
	public static void processTrackEnd(Class clazz, String method, long start, String TransLogCode) {
		System.out.println("Method_" + method + " run end, total time consumption: "
				+ (System.currentTimeMillis() - start) + "ms.");
		System.out.println(LocalDate.now().toString() + " " + LocalTime.now().toString() + "-" + TransLogCode + " "
				+ clazz.toString() + "." + method + "()_end ...)");
	}

	public static String getTransLogCode() {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < 8; i++) {
			Random random = new Random();
			if (random.nextInt(2) % 2 == 0) {
				builder.append(random.nextInt(10));
			} else {
				builder.append((char) (random.nextInt(26) + 65));
			}
		}
		return "[" + builder.append("]").toString();
	}

	public static void main(String[] args) {
		for (int i = 0; i < 27; i++) {
			System.out.println(new ProcessTrack().getTransLogCode());
		}
	}

}
