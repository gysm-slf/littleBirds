package gysm.slf.develop.standard.constant;

import cn.hutool.core.io.IoUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 正确做法是单独抽出一个maven包 (纯公共方法 与项目毫无关系)
 */
//@Slf4j
public class Utils {

  public static String SESSION_KEY = "userId";// sessionKey
  public static String FILEHOST;// 文件路径前缀
  public static String URL;// 根目录
  public static String httphost;

  public static void main(String[] args) {
    System.err.println(getMinutesAndSeconds(21321231321l));
    Map<Object, String> map = new HashMap<>();
    map.put(1, "hello");
    map.put(3, "hello");
    map.put("1231", "hello");
    map.put(17, "hello");
    map.put(122, "hello");

    printMap(map);

    Collection<Object> col = new ArrayList<>();
    col.add("123123");
    col.add("12143123");
    col.add("125673123");
    col.add("12583123");
    col.add("12319923");
    col.add(123123);

    printCollection(col);
    System.err.println();

  }

  public static ArrayList<LinkedHashMap<String, Object>> areaList = new ArrayList<LinkedHashMap<String, Object>>();

  /**
   * @Title: getRequestHelpInfos
   * @Description:输出request对象常用方法信息
   * @author sulongfei
   * @date 2019年7月19日
   * @param request
   */
/*	public static void getRequestHelpInfos(HttpServletRequest request) {
		System.out.println("当前request对象以及对象的方法信息如下：");
		System.out.println("RequestURL:" + request.getRequestURL());
		System.out.println("RequestURI:" + request.getRequestURI());
		System.out.println("Method:" + request.getMethod());
		;
		System.out.println("Cookies:" + request.getCookies());
		System.out.println("Scheme:" + request.getScheme());
		System.out.println("ServerPort:" + request.getServerPort());
		System.out.println("ContextPath:" + request.getContextPath());
	}*/

  /**
   * @version:v1.0
   * @Description:print map element
   * @author:sulongfei
   * @param <E>
   * @param <V>
   * @date: 2020-9-15
   * @param map
   */
  public static <E, V> void printMap(Map<E, V> map) {
    Object key;
    Set<E> keySet = map.keySet();
    Iterator<E> iterator = keySet.iterator();
    while (iterator.hasNext())
      System.out.println("key = " + (key = iterator.next()) + " , " + "value = " + map.get(key));
    // 遍历所有key Set keySet = map.keySet(); iterator.next()
    // 遍历所有value Collection col = map.values();
  }

  /**
   * @version:v1.0
   * @Description:print collection element
   * @author:sulongfei
   * @date: 2020-9-15
   * @param <E>
   * @param collection
   */
  public static <E> void printCollection(Collection<E> collection) {
    Iterator<E> iterator = collection.iterator();
    while (iterator.hasNext())
      System.out.println(iterator.next());
  }

  /**
   * @Title: str1InStr2Times
   * @Description:统计字符串1在字符串2中出现的次数
   * @author sulongfei
   * @date 2019年7月10日
   * @param str1
   * @param str2
   * @return
   */
  public static int str1InStr2Times(String str1, String str2) {
    if (str1 == null || str2 == null) {
      int no = -1;
      return no;
    }
    String copy = str2;
    int sum = 0;
    while (copy.contains(str1)) {
      sum++;
      copy = copy.replaceFirst(str1, "");
    }
    return sum;
  }

  /**
   * @Title: yangHuiTriangle
   * @Description:输出弱居中对齐的由数字组成的参数行数的杨辉三角形
   * @author sulongfei
   * @date 2019年7月19日
   * @param len
   */
  public static void yangHuiTriangle(int len) {
//		System.out.println("请输入杨辉三角形行数：");
//		Scanner reader = new Scanner(System.in);
//		int len = reader.nextInt();
    int[][] arr1 = new int[len][len];
    for (int i = 0; i < len; i++) {// 1 1 23 35 47
      for (int k = 0; k < len - i; k++)
        System.out.print(" ");
      for (int j = 0; j < i + 1; j++) {
        if (j == 0 || j == i) {
          arr1[i][j] = 1;
          System.out.print(1 + " ");
        } else {
          arr1[i][j] = arr1[i - 1][j - 1] + arr1[i - 1][j];
          System.out.print(arr1[i][j] + " ");
        }
      }
      System.out.println();
    }
  }

  /**
   * @Title: removeDuplicateWithOrder
   * @Description:删除ArrayList中重复元素，保持顺序
   * @author sulongfei
   * @date 2019年7月19日
   * @param list
   */
  public static void removeDuplicateWithOrder(List list) {
    Set set = new HashSet();
    List newList = new ArrayList();
    for (Iterator iter = list.iterator(); iter.hasNext();) {
      Object element = iter.next();
      if (set.add(element))
        newList.add(element);
    }
    list.clear();
    list.addAll(newList);
    System.out.println(" remove duplicate " + list);
  }

  /**
   * @Title: bubbleSort
   * @Description:返回使用冒泡排序法进行排序的数组
   * @author sulongfei
   * @date 2019年7月19日
   * @param arr
   */
  public static void bubbleSort(int arr[], String sorting) {
    if (sorting != null && arr != null) {
      int length = arr.length;
      if ((sorting.equals("desc") || sorting.equals("DESC"))) {
        for (int i = 1; i < length; i++) {
          for (int j = 0; j < length - i; j++) {
            if (arr[j] < arr[j + 1]) {
              int t = arr[j];
              arr[j] = arr[j + 1];
              arr[j + 1] = t;
            }
          }
        }
      } else if ((sorting.equals("") || sorting.equals("asce") || sorting.equals("ASCE"))) {
        for (int i = 1; i < length; i++) {
          for (int j = 0; j < length - i; j++) {
            if (arr[j] > arr[j + 1]) {
              int t = arr[j];
              arr[j] = arr[j + 1];
              arr[j + 1] = t;
            }
          }
        }
      }
      for (int i = 0; i < length; i++) {
        System.out.print(arr[i] + " ");
      }
    } else {
      System.out.println("无效参数值");
    }
  }

  /**
   * @Title: SelectionSort
   * @Description:选择排序法，第一次查找最小的值的下标号，若就是0则不换，如果相等证明第一个位置就是最小的，以此类推
   * @author sulongfei
   * @date 2019年6月25日
   * @param arr
   */
  public static void SelectionSort(int arr[]) {
    int length = arr.length;
    for (int i = 0; i < length; i++) {
      int index = i;
      for (int j = i + 1; j < length; j++) {
        if (arr[j] < arr[index]) {
          index = j;
        }
      }
      if (index == i)
        continue;
      else {
        int temp;
        temp = arr[index];
        arr[index] = arr[i];
        arr[i] = temp;
      }
    }
  }

  /**
   * @Title: everyCharTimes
   * @Description:统计输入的字符串中每个字符的出现次数
   * @author sulongfei
   * @date 2019年6月25日
   * @param str
   * @return
   */
  public static int everyCharTimes(String str) {
    /*
     * Scanner reader = new Scanner(System.in); String str = reader.nextLine();
     */
    Map<Character, Integer> map = new HashMap<Character, Integer>();
    for (int i = 0; i < str.length(); i++) {
      char c = str.charAt(i);
      if (map.containsKey(c)) {
        int count = map.get(c) + 1;
        map.put(c, count);
      } else {
        map.put(c, 1);
      }
    }

    Iterator<Map.Entry<Character, Integer>> ite = map.entrySet().iterator();
    while (ite.hasNext()) {
      Map.Entry<Character, Integer> entry = ite.next();
      System.out.print(entry.getKey());
      System.out.print(entry.getValue() + "次 ");
    }
    return 1;
  }

  /**
   * 根据毫秒数返回 时 分 秒
   *
   * @param millisecond
   * @return
   */
  public static String getMinutesAndSeconds(long millisecond) {
    millisecond = millisecond / 1000;
    Integer day = Integer.valueOf(Long.toString(millisecond / (60 * 60 * 24)));
    Integer hour = Integer.valueOf(Long.toString((millisecond - (day * 24 * 60 * 60)) / 60 / 60));
    Integer seconds = Integer.valueOf(Long.toString((millisecond - (day * 24 * 60 * 60) - (hour * 60 * 60)) / 60));
    Integer millis = Integer
        .valueOf(Long.toString((millisecond - (day * 24 * 60 * 60) - (hour * 60 * 60) - (seconds * 60))));
    return day + "天" + hour + "时" + seconds + "分" + millis + "秒";
  }



  /**
   * 获得一个UUID
   *
   * @return String UUID
   */
  public static String getUUID() {
    String s = UUID.randomUUID().toString();
    // 去掉“-”符号
    return s.substring(0, 8) + s.substring(9, 13) + s.substring(14, 18) + s.substring(19, 23) + s.substring(24);
  }

  /**
   * 生成随机数
   *
   * @return
   */
  public static String getRandom() {
    String d = new Random().nextInt(9)
        + new SimpleDateFormat("yyMMddHHmmssSS").format(Calendar.getInstance().getTime())
        + new Random().nextInt(9);
    return d;
  }

  /**
   * 创建指定数量的随机字符串
   *
   * @param numberFlag 是否是数字
   * @param length
   * @return
   */
  public static String createRandom(boolean numberFlag, int length) {
    String retStr = "";
    String strTable = numberFlag ? "1234567890" : "1234567890abcdefghijkmnpqrstuvwxyz";
    int len = strTable.length();
    boolean bDone = true;
    do {
      retStr = "";
      int count = 0;
      for (int i = 0; i < length; i++) {
        double dblR = Math.random() * len;
        int intR = (int) Math.floor(dblR);
        char c = strTable.charAt(intR);
        if (('0' <= c) && (c <= '9')) {
          count++;
        }
        retStr += strTable.charAt(intR);
      }
      if (count >= 2) {
        bDone = false;
      }
    } while (bDone);

    return retStr;
  }

  /**
   * object 转BigDecimal
   *
   * @param value
   * @return
   */
  public static BigDecimal objectConvertBigDecimal(Object value) {
    BigDecimal ret = null;
    if (value != null) {
      if (value instanceof BigDecimal) {
        ret = (BigDecimal) value;
      } else if (value instanceof String) {
        ret = new BigDecimal((String) value);
      } else if (value instanceof BigInteger) {
        ret = new BigDecimal((BigInteger) value);
      } else if (value instanceof Number) {
        ret = new BigDecimal(((Number) value).doubleValue());
      } else {
        throw new ClassCastException("Not possible to coerce [" + value + "] from class " + value.getClass()
            + " into a BigDecimal.");
      }
    }
    return ret;
  }

  /**
   * @Title: encodeMD
   * @Description:加密函数
   * @author sulongfei
   * @date 2019年7月19日
   * @param userName
   * @return
   */
  public static String encodeMD(String userName) {
    Format format = new SimpleDateFormat("yyyyMMddHHmmss");
    String tokenName = format.format(new Date()) + userName;
    try {
      MessageDigest digest = MessageDigest.getInstance("MD5");
      byte[] bytes = digest.digest(tokenName.getBytes());
      StringBuffer sb = new StringBuffer();
      for (int i = 0; i < bytes.length; i++) {
        String s = Integer.toHexString(0xff & bytes[i]);

        if (s.length() == 1) {
          sb.append("0" + s);
        } else {
          sb.append(s);
        }
      }

      return sb.toString();
    } catch (Exception e) {
      e.printStackTrace();
      throw new RuntimeException("NoSuchAlgorith");
    }
  }

  public static String encodePwd(String userPassword) {
    try {
      MessageDigest digest = MessageDigest.getInstance("MD5");
      byte[] bytes = digest.digest(userPassword.getBytes());
      StringBuffer sb = new StringBuffer();
      for (int i = 0; i < bytes.length; i++) {
        String s = Integer.toHexString(0xff & bytes[i]);

        if (s.length() == 1) {
          sb.append("0" + s);
        } else {
          sb.append(s);
        }
      }
      return sb.toString();
    } catch (Exception e) {
      e.printStackTrace();
      throw new RuntimeException("NoSuchAlgorith");
    }
  }

  /**
   * @Title: sumOfPrimeNumber
   * @Description:返回num1~num2之间的质数和,并输出范围内的质数num1必须小于num2
   * @author sulongfei
   * @date 2019年7月1日
   */
  public static int sumOfPrimeNumber(int num1, int num2) {
    if (num1 - num2 < 0) {
      System.out.println("输入参数不正确,参数规则是num1<num2");
    }
    boolean flag;
    int sum = 0;
    for (int i = num1; i <= num2; i++) {
      flag = true;
      for (int j = 2; j < i; j++) {
        if (i % j == 0) {
          flag = false;
          break;
        }
      }
      if (flag) {
        System.out.print(i + " ");
        sum += i;
      }
    }
    return sum;
//		System.out.println("从" + num1 + "~" + num2 + "之间的质数和为:" + sum);
  }

  /**
   * @Title: getFormatDate
   * @Description:返回日期格式 2018-06-06
   * @author sulongfei
   * @date 2019年7月19日
   * @param date
   * @return
   */
  public static String getFormatDate(Date date) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
//		System.out.println(calendar.get(calendar.YEAR));
//		System.out.println(calendar.get(calendar.MONTH) + 1);
//		System.out.println(calendar.get(calendar.DATE));
//		System.out.println( calendar.get(Calendar.DAY_OF_MONTH));
    if ((calendar.get(calendar.MONTH) + 1) > 0 && (calendar.get(calendar.MONTH) + 1) < 10
        && calendar.get(calendar.DATE) > 9) {
      return "" + calendar.get(calendar.YEAR) + "-0" + (calendar.get(calendar.MONTH) + 1) + "-"
          + calendar.get(calendar.DATE);
    }
    if (calendar.get(calendar.MONTH) + 1 > 9 && calendar.get(calendar.DATE) > 0
        && calendar.get(calendar.DATE) < 10) {
      return "" + calendar.get(calendar.YEAR) + "-" + (calendar.get(calendar.MONTH) + 1) + "-0"
          + calendar.get(calendar.DATE);
    }
    if (calendar.get(calendar.MONTH) + 1 > 0 && calendar.get(calendar.MONTH) + 1 < 10
        && calendar.get(calendar.DATE) > 0 && calendar.get(calendar.DATE) < 10) {
      return "" + calendar.get(calendar.YEAR) + "-0" + (calendar.get(calendar.MONTH) + 1) + "-0"
          + calendar.get(calendar.DATE);
    }
    return "" + calendar.get(calendar.YEAR) + "-" + (calendar.get(calendar.MONTH) + 1) + "-"
        + calendar.get(calendar.DATE);
  }

  /**
   * @Title: getName
   * @Description:随机生成男女姓名
   * @author sulongfei
   * @date 2019年7月19日
   * @param sexPrefix
   * @return
   */
  public static String getName(Boolean sexPrefix) {
    Random random = new Random();
    String[] Surname = { "赵", "钱", "孙", "李", "周", "吴", "郑", "王", "冯", "陈", "褚", "卫", "蒋", "沈", "韩", "杨", "朱", "秦",
        "尤", "许", "何", "吕", "施", "张", "孔", "曹", "严", "华", "金", "魏", "陶", "姜", "戚", "谢", "邹", "喻", "柏", "水", "窦",
        "章", "云", "苏", "潘", "葛", "奚", "范", "彭", "郎", "鲁", "韦", "昌", "马", "苗", "凤", "花", "方", "俞", "任", "袁", "柳",
        "酆", "鲍", "史", "唐", "费", "廉", "岑", "薛", "雷", "贺", "倪", "汤", "滕", "殷", "罗", "毕", "郝", "邬", "安", "常", "乐",
        "于", "时", "傅", "皮", "卞", "齐", "康", "伍", "余", "元", "卜", "顾", "孟", "平", "黄", "和", "穆", "萧", "尹", "姚", "邵",
        "湛", "汪", "祁", "毛", "禹", "狄", "米", "贝", "明", "臧", "计", "伏", "成", "戴", "谈", "宋", "茅", "庞", "熊", "纪", "舒",
        "屈", "项", "祝", "董", "梁", "杜", "阮", "蓝", "闵", "席", "季" };
    String girl = "秀娟英华慧巧美娜静淑惠珠翠雅芝玉萍红娥玲芬芳燕彩春菊兰凤洁梅琳素云莲真环雪荣爱妹霞香月莺媛艳瑞凡佳嘉琼勤珍贞莉桂娣叶璧璐娅琦晶妍茜秋珊莎锦黛青倩婷姣婉娴瑾颖露瑶怡婵雁蓓纨仪荷丹蓉眉君琴蕊薇菁梦岚苑婕馨瑗琰韵融园艺咏卿聪澜纯毓悦昭冰爽琬茗羽希宁欣飘育滢馥筠柔竹霭凝晓欢霄枫芸菲寒伊亚宜可姬舒影荔枝思丽 ";
    String boy = "伟刚勇毅俊峰强军平保东文辉力明永健世广志义兴良海山仁波宁贵福生龙元全国胜学祥才发武新利清飞彬富顺信子杰涛昌成康星光天达安岩中茂进林有坚和彪博诚先敬震振壮会思群豪心邦承乐绍功松善厚庆磊民友裕河哲江超浩亮政谦亨奇固之轮翰朗伯宏言若鸣朋斌梁栋维启克伦翔旭鹏泽晨辰士以建家致树炎德行时泰盛雄琛钧冠策腾楠榕风航弘";
    int index = random.nextInt(Surname.length - 1);
    String name = Surname[index]; // 获得一个随机的姓氏
    int i = random.nextInt(3);// 可以根据这个数设置产生的男女比例
    if (i == 2) {
      if (sexPrefix) {
        int j = random.nextInt(girl.length() - 2);
        if (j % 2 == 0) {
          name = "女-" + name + girl.substring(j, j + 2);
        } else {
          name = "女-" + name + girl.substring(j, j + 1);
        }
      } else {
        int j = random.nextInt(girl.length() - 2);
        if (j % 2 == 0) {
          name = name + girl.substring(j, j + 2);
        } else {
          name = name + girl.substring(j, j + 1);
        }
      }
    } else {
      if (sexPrefix) {
        int j = random.nextInt(girl.length() - 2);
        if (j % 2 == 0) {
          name = "男-" + name + boy.substring(j, j + 2);
        } else {
          name = "男-" + name + boy.substring(j, j + 1);
        }
      } else {
        int j = random.nextInt(girl.length() - 2);
        if (j % 2 == 0) {
          name = name + girl.substring(j, j + 2);
        } else {
          name = name + girl.substring(j, j + 1);
        }
      }
    }
    return name;
  }

  /**
   * @Title: addDate
   * @Description:返回添加指定天数的日期
   * @author sulongfei
   * @date 2019年7月19日
   * @param date
   * @param day
   * @return
   * @throws ParseException
   */
  public static Date addDate(Date date, long day) throws ParseException {
    long time = date.getTime(); // 得到指定日期的毫秒数
    day = day * 24 * 60 * 60 * 1000; // 要加上的天数转换成毫秒数
    time += day; // 相加得到新的毫秒数
    return new Date(time); // 将毫秒数转换成日期
  }

  public static <E> boolean export(String name, List<E> data, String suffix, HttpServletResponse response) throws IOException {
    if (data == null || data.size() == 0)
      throw new IllegalArgumentException("暂无需要导出的数据");
    boolean flag = true;
    switch (suffix) {
      case "xls": case "xlsx":
        exportExcel(name, data, response);
        break;
      case "doc": case "docx":
//                exportWord();
        break;
      default:
        flag = false;
//        log.error("暂不支持导出的格式,当前仅支持xls、xlsx");

    }
    return flag;
  }

  public static <E> void exportExcel(String name, List<E> rows, HttpServletResponse response) throws IOException {
    ExcelWriter writer = ExcelUtil.getWriter();
    writer.write(rows, true);
//        writer.merge(row1.size() - 1, "测试标题");

    response.setContentType("application/vnd.ms-excel;charset=utf-8");
    response.setHeader("Content-Disposition", "attachment;filename=" + name + ".xls");

    ServletOutputStream out = response.getOutputStream();
    //close
    writer.flush(out, true);
    writer.close();
    IoUtil.close(out);
  }

  /**
   * 将size最大的map放在第一位,为了避免数据列数不一致导致数据丢失的问题
   *
   * @param list
   * @return
   */
  public static LinkedList maxLengthSort(LinkedList<LinkedHashMap> list) {
    if (list == null && list.size() == 0)
      return null;
    int max = 0, mapLen = 0, listMax = 0;
    int len = list.size();
    for (int i = 0; i < len; i++) {
      if (max < (mapLen = list.get(i).size())) {
        max = mapLen;
        listMax = i;
      }
    }
    LinkedHashMap temp = list.get(0);
    list.set(0, list.get(listMax));
    list.set(listMax, temp);
    return list;
  }

  /**
   * 存储上传文件
   *
   * @param filePath
   * @param fileName
   * @param multipartFile
   * @return
   */
  public static boolean uploadFile(String filePath, String fileName, MultipartFile multipartFile) {
    File file = new File(filePath);
    if (!file.exists()) {
      file.mkdirs();
    }
    try {
      multipartFile.transferTo(new File(filePath, fileName));
    } catch (IOException e) {
//      log.error("上传文件失败");
      e.printStackTrace();
      return false;
    }
    return true;
  }

  public static boolean copyFile(String sourcePath, String sourceName, String targetPath, String targetName) throws IOException {
    boolean flag = true;
    FileInputStream fis;
    FileOutputStream fos;
    File source = new File(sourcePath + sourceName);
    if (!source.exists()) {
      flag = false;
//      log.error("复制出错,源文件不存在");
    }else {
      File tmpPath = new File(targetPath);
      if (!tmpPath.exists() && !tmpPath.isDirectory())
        tmpPath.mkdirs();
      File target = new File(targetPath + targetName);
      fis = new FileInputStream(source);
      fos = new FileOutputStream(target);
      byte[] tmp = new byte[32];
//          copy start
      long start = System.currentTimeMillis();
      while (fis.read(tmp) != -1) {
        fos.write(tmp);
      }
      long end = System.currentTimeMillis();
      System.out.println(targetPath + targetName + "copy success. cost time:" + (end - start) + "ms");

      fis.close();
      fos.close();
    }
    return flag;
  }

  public static boolean copyFile(String source, String target) throws IOException {
    String sPath, sName, tPath, tName;
//        if (System.getProperty("os.name").toLowerCase().contains("windows")) {
//            sName = source.split("\\\\")[source.split("\\\\").length - 1];
//            sPath = source.substring(0, source.length() - sName.length());
//            tName = target.split("\\\\")[target.split("\\\\").length - 1];
//            tPath = target.substring(0, target.length() - tName.length());
//        } else {
    sName = source.split("/")[source.split("/").length - 1];
    sPath = source.substring(0, source.length() - sName.length());
    tName = target.split("/")[target.split("/").length - 1];
    tPath = target.substring(0, target.length() - tName.length());
//        }
    return copyFile(sPath, sName, tPath, tName);
  }
//    public static boolean copyFile(String source, String target) throws IOException {
//        return copyFile(source.split("/")[source.split("/").length - 1],
//                        source.substring(0, source.length() - source.split("\\\\")[source.split("\\\\").length - 1].length()),
//                        target.split("/")[target.split("/").length - 1],
//                        target.substring(0, target.length() - target.split("\\\\")[target.split("\\\\").length - 1].length()));
//    }

}
