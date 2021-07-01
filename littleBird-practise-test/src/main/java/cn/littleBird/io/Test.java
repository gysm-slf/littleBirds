package cn.littleBird.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Properties;

public class Test {

//    public static boolean copyFile(String sourcePath, String sourceName, String targetPath, String targetName) throws IOException {
//        boolean flag = true;int len = 0;
//        FileInputStream fis = null;
//        FileOutputStream fos = null;
//        File source = new File(sourcePath + sourceName);
//        File target = new File(targetPath + targetName);
//        if (!source.exists()) {
//            flag = false;
////            log.error("复制出错,源文件不存在");
//        }
//        fis = new FileInputStream(source);
//        fos = new FileOutputStream(target);
//        byte[] tmp = new byte[32];
////        开始复制
//        long start = System.currentTimeMillis();
//        while ((len = fis.read(tmp))!=-1){
//            fos.write(tmp);
//        }
//        long end = System.currentTimeMillis();
//        System.out.println("copy succe!");
//        System.out.println("复制该文件共用了" + (end - start) + "ms");
//
//        fos.close();
//        fis.close();
//        return flag;
//    }

    public static void main(String[] args) throws IOException {
//        System.out.println(copyFile("E:\\gysm\\test\\A\\","preview.gif","E:\\gysm\\test\\B\\C\\D","yes..gif"));
        String sName ,sPath = "";
        String source = "E:\\gysm\\test\\A\\preview.gif";
        sName = source.split("\\\\")[source.split("\\\\").length - 1];
        sPath = source.substring(0,source.length() - sName.length());
        String property = System.getProperty("os.name");
        System.out.println(System.getProperty("os.name"));
//        System.out.println(os.name);
//        tName = target.split("/")[target.split("/").length - 1];
//        tPath = target.substring(0,target.length() - tName.length());
    }
}
