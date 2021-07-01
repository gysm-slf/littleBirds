//package cn.littleBird.demo.old;
//
//import java.text.ParseException;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.HashSet;
//import java.util.Set;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
//import com.su.entity.Email;
//import com.su.utils.ProcessTrack;
//
//public class test {
////	@SuppressWarnings("deprecation")
//	 int a = 10;
//
//	public int errMe() {
//		try {
//			a = 10;
//			int i = 5 / 0;
//			System.out.println("执行了try");
//			return 1;
//		} catch (Exception e) {
//			a = 20;
//			System.out.println("输出异常信息");
//			e.printStackTrace();
//			return 2;
//		} finally {
//			a = 30;
//			System.out.println("执行了finally");
//			return 3;
//		}
//	}
//
//	public int getA() {
//		return this.a;
//	}
//
//	public int errMe2() {
//		try {
//			a = 10;
//			int i = 5 / 0;
//			System.out.println("执行了try");
//			return a;
//		} catch (Exception e) {
//			a = 20;
//			System.out.println("输出异常信息");
//			e.printStackTrace();
//			return a;
//		} finally {
//			a = 30;
//			System.out.println("执行了finally");
//		}
//	}
//
//	public static void main(String[] args) throws ClassNotFoundException {
////		long start = ProcessTrack.processTrackStart(new test().getClass(), "main");
////		Class clazz = Class.forName("demo");
//		System.out.println("After is excute");
//		System.out.println(1/0);
//		String aq = "hel";
//		String b = "你好";
//		System.out.println(aq.length());
//		System.out.println(b.length());
//		PojoTest pojo = new PojoTest();
//		PojoTest pojo2 = new PojoTest();
//
//		System.out.println(pojo.getName());
//		pojo.setName("asdasdasd");
//		pojo2.setName("asdasd432122222");
//		System.out.println(pojo.getName());
//
//
//		String regex;
//		//17为数字+1位数字或字母X组合
////		regex = "\\d{17}[\\dX]{1}";
//		//第一位固定为1 第二位为3/5/6/7 后九位任意数字组合
////		regex = "1[3567]{1}[\\d]{9}";
//		regex = "[\\d]{1,32}[.]{1}\\d{4}";
////		* 等效于 {0,}  + 等效于 {1,} ? 等效于 {0,1} 	\\d|X 等效于 任意数字或字母X
////		[a-z4]{3} 等效于a-z或4组成的三位字符串  \d数字 \D非数字 \n换行符  \r回车符
////		{n,m} 至少出现n次,至多出现m次,m省略代表多次 ^代表非
////		\s匹配任何空白字符，包括空格、制表符、换页符等。与 [ \f\n\r\t\v] 等效  \S相反
////		任何字符放到[]中都表示从这个范围中取值,     .放到外面表示匹配除"\r\n"之外的任何单个字符
//		String a = "1123X1211";
//
////		Pattern p = Pattern.compile(regex);
//		System.err.println(Pattern.matches(regex, a));;
////		p.matches(regex, a);
//
////		for (int i = 0; i < 1000; i++) {
////			System.out.println(i);
////		}
//		HashMap map = new HashMap();
//		map.get("abc");
////		ArrayList a = new ArrayList();
////		a.add(5);
////		a.add("123");
////		Email p = new Email();
////		Email p2 = new Email();
////		Email p3 = new Email();
////		p.setUserName("a");p2.setUserName("ab");p3.setUserName("abc");
////		Set s = new HashSet();
////		s.add(p);s.add(p2);		s.add(p3);
////		p2.setUserName("a");
////		System.out.println(s);
//
////		test t = new test();
////		System.out.println(t.errMe());
////		System.out.println(t.getA());
////		System.out.println(t.errMe2());
////		System.out.println(t.getA());
//		// ThreadTest1 tt1 = new ThreadTest1();
////		Thread t1 = new Thread(tt1);
////		Thread t2 = new Thread(tt1);
////		t1.setName("thread1__");
////		t2.setName("thread2__");
////		t2.start();
////		t1.start();
////
////
////		TheadExtendType te = new TheadExtendType();
////		Thread t3 = new Thread(te);
////		t3.setName("三号售票机卖了");
////		t3.start();
////
////		System.err.println(new cat().name);
//		/*
//		 * Person slf = new Person(); slf.setName(""); IdCard idCard = new IdCard();
//		 * idCard.setNumber("130681199605234717"); idCard.setBirthday("1996-05-23");
//		 * slf.setIdCard(idCard); slf.setSex("鐢�"); slf.setBirthday("1996-05-23");
//		 * Map<String, Qq> qq = new HashMap<String, Qq>(); Qq mainQq = new Qq();
//		 * mainQq.setAccounts("1281688655"); qq.put("涓诲彿", mainQq); Comic onepiece = new
//		 * Comic(); onepiece.setName("娴疯醇鐜�"); Comic naruto = new Comic();
//		 * naruto.setName("鐏奖蹇嶈��"); Map<String,Comic> comics = new HashMap<String,
//		 * Comic>();
//		 * slf.setPersonalProfile(com.su.constant.Constant.GYSMSLFPERSONALPROFILE);
//		 * Person p2 = new Person();
//		 * System.out.println(com.su.utils.CommonUtil.getMinutesAndSeconds(1000 * 60 *
//		 * 60 * 24 + 1000 * 60 * 60 + 1000));
//		 * System.out.println(com.su.utils.CommonUtil.getMinutesAndSeconds(System.
//		 * currentTimeMillis())); System.out.println(new
//		 * Timestamp(System.currentTimeMillis()) ); Timetest t = new Timetest();
//		 * t.setTimestamp(new Timestamp(System.currentTimeMillis()));
//		 * System.out.println(t.toString()); System.out.println("+++++++++++++++"+new
//		 * Timestamp(System.currentTimeMillis())); Date date = new Date();//鑾峰緱绯荤粺鏃堕棿.
//		 *
//		 * String nowTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);//
//		 * 灏嗘椂闂存牸寮忚浆鎹㈡垚绗﹀悎Timestamp瑕佹眰鐨勬牸寮�.
//		 *
//		 * Timestamp goodsC_date =Timestamp.valueOf(nowTime);//鎶婃椂闂磋浆鎹�
//		 *
//		 * System.out.println(goodsC_date);
//		 */
////		System.err.println(i);
////		System.out.println(" "+i+j);
////		int i = 1;
////		 i =  i++;
////		int j = i++;
////		int k = i + ++i * i++;
////		System.out.println("  " + i + "  " + j + "  " + k);
////		Integer q = new Integer(0);
////		System.out.println(q == null);
////		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 璁剧疆鏃ユ湡鏍煎紡
////		String currentTime = "2019-04-07 AM 09:30:00 - 12:00:00";
////		currentTime = currentTime.substring(0,10)+currentTime.substring(24);
////		Date currentDate = df.parse(currentTime);
//////		Date reverDate = df.parse(df.format(new Date()));
////		System.out.println(currentTime);
////		System.out.println(df.format(new Date()));
////		System.out.println(currentDate.getTime()-reverDate.getTime());
////		System.out.println(1000*60*60*12);
//		/*
//		 * SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//
//		 * 璁剧疆鏃ユ湡鏍煎紡 Date startDate = df.parse(df.format(new Date()));
//		 * System.out.println(date); Date endDate = new add ;
//		 */
//		// 褰撳墠 鏃ユ湡 灏忔椂 鍒嗛挓
////		int currentDate = date.getDate();
////		int currentHours = date.getHours();
////		int currentMinutes = date.getMinutes();
////		int endDate = date.getDate()+10;
////		String validDateTime = ""+date.getYear()+"-";
////		if(String.valueOf(date.getMonth()).length()==1) {
////			validDateTime += "0" + date.getMonth()+"-";
////		}else if(String.valueOf(date.getMonth()).length()==2) {
////			validDateTime += date.getMonth()+"-";
////		}
////		for (int startDate = date.getDate(); startDate < date.getDate() + 10; startDate++) {
////			for()
////		}
//////		CommonUtil.everyCharTimes("asdfasf5wqeqwe");
////		String str = "asdf asf5 wq eq we ".replace(" ", "%20");
////		System.out.println(str);
////		StringBuffer sb = new StringBuffer("abcdef");
////		String s = sb.toString();
////
////		// -128~127
////		byte b1 = -128;
////		byte b2 = 127;
////		byte b3 = 50;
////		byte b4 = (byte) (b2 + b3);
////		System.out.println(b4);
////		// 00000000 00000000 00000000 10000000
//
////		ProcessTrack.processTrackEnd(new test().getClass(), "main", start);
//	}
//	// 求1+2+3+...+n
//	// 求1+2+3+...+n，要求不能使用乘除法、for、while、if、else、switch、case等关键字及条件判断语句（A?B:C）。
////		List<String> list = new ArrayList<String>();
////		for(int i=0;i<str.length();i++) {
////			list.add(str.substring(i,i+1));
////		}
////		Map<String,Integer> map = new LinkedHashMap<String,Integer>();
////		for(int j =0;j<list.size();j++) {
////			if(map.containsKey(list.get(j))) {
////				map.put(list.get(j), map.get(list.get(j))+1);
////			}else {
////				map.put(list.get(j), 1);
////			}
////		}
////
////		Set<String> keySet = map.keySet();
////		for(String st:keySet) {
////			System.out.println(st+"出现"+map.get(st)+"次");
////		}
////	}
//}
