//package cn.littleBird.demo.old;
//
//import java.io.BufferedInputStream;
//import java.io.BufferedOutputStream;
//import java.io.BufferedReader;
//import java.io.BufferedWriter;
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileNotFoundException;
//import java.io.FileOutputStream;
//import java.io.FileReader;
//import java.io.FileWriter;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.io.OutputStreamWriter;
//
//import javax.xml.transform.stream.StreamResult;
//
//import org.junit.Test;
//
//import com.su.entity.Person;
//
//public class testBuffered {
//	@Test
//	public void testBuffered() {
//	BufferedInputStream bis = null;
//	BufferedOutputStream bos =null;
//	try {
//		File f1 = new File("G:\\IOTestCopy.txt");
//		File f2  = new File("G:\\IOBufferedTestCopy.txt");
//		FileInputStream fis = new FileInputStream(f1);
//		FileOutputStream fos = new FileOutputStream(f2);
//		bis = new BufferedInputStream(fis);
//		bos = new BufferedOutputStream(fos);
//		byte[] b = new byte[3];
//		int len =0;int i = 0;
//		while((len=bis.read(b))!=-1) {
//			bos.write(b);
//			System.out.println(++i);
//
//		}
//	} catch (Exception e) {
//		e.printStackTrace();
//	}finally {
//		try {
//			if(bis!=null) {
//				bis.close();
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		try {
//			if(bos!=null) {
//				bos.close();
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//}
//
//	@Test
//	public void testBufferedReadLine() {
//	BufferedReader br = null;
//	BufferedWriter bw =null;
//	try {
//		File f1 = new File("G:\\IOTestCopy.txt");
//		File f2  = new File("G:\\IOBufferedTestCopyReadLine.txt");
//		FileReader fis = new FileReader(f1);
//		FileWriter fos = new FileWriter(f2);
//		br = new BufferedReader(fis);
//		bw = new BufferedWriter(fos);
//		String str;
//		int i = 0;
//		while((str=br.readLine())!=null) {
//			bw.write(str);
//			bw.newLine();//或者  直接 bw.write(str+"\n")
//			bw.flush();
//			System.out.print(++i);
//
//		}
//	} catch (Exception e) {
//		e.printStackTrace();
//	}finally {
//		try {
//			if(br!=null) {
//				br.close();
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		try {
//			if(bw!=null) {
//				bw.close();
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//}
//
//	@Test
//	public void practice() throws IOException {
//		File f = new File("G:\\test.txt");
//		if(!f.exists()) {
//			f.createNewFile();
//		}
//		FileInputStream fis = new FileInputStream(f);
//
//		FileOutputStream fos = new FileOutputStream(f);
//
//		String str= "本人学识渊博、经验丰富，代码风骚、效率恐怖，c/c++、java、JS、php无不精通，熟练掌握各种框架，深山苦练20余年，一天只睡4小时，千里之外定位问题，瞬息之间修复上线。身体强壮、健步如飞，可连续编程100小时不休息，讨论技术方案5小时不喝水，上至带项目、出方案，下至盗账号、威胁pm，啥都能干。泡面矿泉水已备好，学校不支持编程已辍学，家人不支持编程已断绝关系，老婆不支持编程已离婚，小孩不支持编程已送孤儿院，备用电源万兆光纤永不断电断网，门口已埋雷无人打扰";
//		fos.write(str.getBytes());
//
//		byte[] c = new byte[1024];
//		int len ;
//		while((len=fis.read(c))!=-1) {
//			System.out.println(new String(c,0,len));
//		}
//
//		fis.close();
//		fos.close();
//
//	}
//	@Test
//	/**
//	 * @Title: testStreamReader
//	 * @Description:转换流
//	 * @author sulongfei
//	 * @date 2019年7月1日
//	 */
//	public void testStreamReader() throws Exception {
//		//解码
//		File f = new File("G:\\test.txt");
//		FileInputStream fis = new FileInputStream(f);
//		InputStreamReader isr = new InputStreamReader(fis,"GBK");
//		BufferedReader br = new BufferedReader(isr);
//		//编码
//		File f2=new File("G:\\testSwitch.txt");
//		FileOutputStream fos = new FileOutputStream(f2);
//		OutputStreamWriter osw = new OutputStreamWriter(fos,"GBK");
//		BufferedWriter bw = new BufferedWriter(osw);
//
//	}
//}
