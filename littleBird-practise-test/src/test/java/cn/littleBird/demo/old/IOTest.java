package cn.littleBird.demo.old;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import cn.littleBird.demo.entity.Person;
import org.junit.Test;


public class IOTest {

	/*
	 * 抽象基类 InputStream 处理字节的 OutputStream Reader 处理字符的
	 * 只能处理文本文件(txt,,注意doc文件也不可以，有可能会出现问题，处理doc要用字节流) 处理图片视频等 要用字节流 Writer
	 * 节点流（文件流）WSDSAQQWE FileInputStream 处理字节的 处理字符文件基本不用,效率低，用filereaderr/writer
	 * FileOutputStream FileReader 处理字符的 只能处理文本文件 处理图片视频等 要用字节流 FileWriter 缓冲流
	 * 比较于节点流 相当于进行了加速 BufferedInputStream 处理字节的 BufferedOutputStream BufferedReader
	 * 处理字符的 只能处理文本文件 处理图片视频等 要用字节流 BufferedWriter
	 */

	/**
	 * 绝对路径：包括盘符在内的完整的文件路径 相对路径：在当前文件目录下的文件路径 File中的方法，仅涉及到如何创建、删除、重命名等等。只要涉及文件内
	 * 容的，File是无能为力的，必须由io流来完成
	 * 
	 * @throws IOException
	 *
	 */
	@Test
	public void IOTest1() throws IOException {
		/*
		 * getName getPath getAbsoluateFile getAbsolutePath getParent renameTo(File
		 * newName)
		 */
		File file1 = new File("g:/IOPractice/IOsadasdTest.txt");
		System.out.println(file1.getName());
		System.out.println(file1.getPath());
		System.out.println(file1.getAbsoluteFile());
		System.out.println(file1.getAbsolutePath());
		System.out.println(file1.getParent());
		file1.mkdir();
		file1.mkdirs();
		file1.createNewFile();
	}

	@Test
	public void testFileInputOutputStream() {
		FileInputStream fileInputStream = null;
		try {
			File file1 = new File("G:\\IOPractice\\IOTest.txt");
			fileInputStream = new FileInputStream(file1);
			System.out.println((char) fileInputStream.read());
			int f = fileInputStream.read();
			while (f != -1) {
				System.out.print((char) f + " ");
				f = fileInputStream.read();
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			try {
				fileInputStream.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	@Test
	public void testFileInputStreamArr() {
		FileInputStream fis = null;
		try {
			File file = new File("G:\\IOPractice\\IOTest.txt");
			fis = new FileInputStream(file);
			byte[] b = new byte[10];
			int len;
			while ((len = fis.read(b)) != -1) {
				for (int i = 0; i < len; i++) {
//					System.out.print((char) b[i]);
				}
				String str = new String(b, 0, len);
				System.out.print(str);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (fis != null) {
					fis.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	@Test
	public void testOutPut() {
		FileOutputStream fos = null;
		FileInputStream fis = null;
		try {
			File file = new File("G:\\UpupooResource\\1783830174\\preview.jpg");
			File file2 = new File("G:\\copyGirl.jpg");
			fis = new FileInputStream(file);
			fos = new FileOutputStream(file2);
			int len;
			byte[] b = new byte[1];
			long start = System.currentTimeMillis();
			while ((len = fis.read(b)) != -1) {
				System.out.print(new String(b, 0, len));
				fos.write(b);
			}
			long end = System.currentTimeMillis();
			System.out.println("copy succe!");
			System.out.println("复制该文件共用了" + (end - start) + "秒");
			/*
			 * 
			 * fos.write("你好!".getBytes()); byte[] a = "你好!".getBytes(); for (int i = 0; i <
			 * a.length; i++) { System.out.print((char)a[i]); }
			 */

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (fos != null) {
					fos.close();
				}
				if (fis != null) {
					fis.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}

	@Test
	public void testFileCopy() {
		FileReader fr = null;
		FileWriter fw = null;
		try {
			File f1 = new File("G:\\IOTestCopy.txt");
			File f2 = new File("G:\\Fucking life.txt");
			fr = new FileReader(f1);
			fw = new FileWriter(f2);
			char[] c = new char[24];
			int len;
			while ((len = fr.read(c)) != -1) {
				for (char cs : c) {
					System.out.print(cs);
				}
				fw.write(c);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (fr != null) {
					fr.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
			try {
				if (fw != null) {
					fw.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}

	}

	@Test
	public void testObjectSerializable() throws Exception, IOException {
		Person p1 = new Person();
		p1.setArea("i am p1.area");
		p1.setName("slf");
		System.out.println(p1.getClass());
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File("G:\\testSerializable.txt")));
		oos.writeObject(p1);
		oos.flush();
		oos.close();

		ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File("G:\\testSerializable.txt")));
		System.out.println((Person) ois.readObject());
		ois.close();
	}

	public void listAllFile(String dir) {
		File file = new File(dir);
		File[] files = file.listFiles();
		System.out.println(dir+": 目录下的文件及文件夹列表如下：");
		for (File f : files) {
			if(f.isFile()) {
				System.out.println(f.getName()+" ");
			}
			if (!f.isFile()) {
				if(f.list().length==0) {
					System.out.println(f.getName());
				}else {
					listAllFile(f.getAbsolutePath());
				}
			}
		}
	}
	
	/*
	 * （一） 在电脑D盘下创建一个文件为HelloWorld.txt文件，判断他是
	 * 文件还是目录，在创建一个目录IOTest,之后将HelloWorld.txt移动 到IOTest目录下去；之后遍历IOTest这个目录下的文件
	 */
	@Test
	public void demo1() throws IOException {
		File file1 = new File("G:\\UpupooResource");
		System.out.println(file1.list().length);
		file1.createNewFile();
		File file2 = new File("G:\\IOTest");
		file2.mkdir();
		File file3 = new File(file2.getAbsolutePath() + "\\" + file1.getName());
		file3.createNewFile();
//		listAllFile(file2.getAbsolutePath());
//		file1.delete();
//		FileInputStream fis = new FileInputStream(file1);
//		FileOutputStream fos = new FileOutputStream(file1);
//		int len;
//		byte[] b = new byte[16];
//		while(fis.read(b)!=-1) {
//			
//		}
//		file1.delete();
//		fos.close();
//		fis.close();
	}

	//（二） 递归实现输入任意目录，列出文件以及文件夹，效果看图
	@Test
	public void demo2() {
		File file = new File("G:\\UpupooResource");
		listAllFile(file.getAbsolutePath());
	}

	@Test
	public void demo3() {

	}

	@Test
	public void demo4() {

	}

	@Test
	public void demo5() {

	}

	@Test
	public void demo6() {

	}

	@Test
	public void demo7() {

	}

	@Test
	public void demo8() {

	}

	@Test
	public void demo9() {

	}

	@Test
	public void demo10() {

	}

	@Test
	public void demo11() {

	}

	@Test
	public void demo12() {

	}

	@Test
	public void demo13() {

	}
}
