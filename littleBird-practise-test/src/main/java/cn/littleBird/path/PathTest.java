package cn.littleBird.path;

import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class PathTest {

    public static void main(String[] args) throws IOException {

String size = "56KB";

        System.out.println(size.substring(0, size.length() - 2));
        System.out.println(size.substring(size.length() - 2).toLowerCase());
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
//        System.out.println(simpleDateFormat.format(new Date()));
//
//        System.out.println(ResourceUtils.getURL("classpath:").getPath() + "static/upload");
//        System.out.println(new PathTest().getClass().getResource("/").getPath());
                new PathTest().getPath();
//        System.out.println("测试".length());          //2
//        System.out.println("测试123".length());       //5
//        System.out.println("测试abc".length());       //5
//        System.out.println("测试--（）".length());     //6

    }


    public void getPath() throws IOException {
        //当前项目下路径
        File file = new File("");
        String filePath = file.getCanonicalPath();
        System.out.println(filePath);

        //当前项目下xml文件夹
        File file1 = new File("");
        String filePath1 = file1.getCanonicalPath()+File.separator+"xml\\";
        System.out.println(filePath1);

        //获取类加载的根路径
        File file3 = new File(this.getClass().getResource("/").getPath());
        System.out.println(file3);

        //获取当前类的所在工程路径
        File file4 = new File(this.getClass().getResource("").getPath());
        System.out.println(file4);

        //获取所有的类路径 包括jar包的路径
        System.out.println(System.getProperty("java.class.path"));
    }
}
