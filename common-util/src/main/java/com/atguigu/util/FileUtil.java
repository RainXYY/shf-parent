package com.atguigu.util;

import java.util.Calendar;
import java.util.Random;
import java.util.UUID;

/**
 * 包名:com.atguigu.utils
 *
 * @author Leevi
 * 日期2022-03-26  14:07
 */
public class FileUtil {
    /**
     * 随机生成唯一的文件名
     * @param originalFileName
     * @return
     */
    public static String getUUIDName(String originalFileName){
        //1. 获取文件名的后缀
        String suffix = originalFileName.substring(originalFileName.lastIndexOf("."));
        //2. 使用UUID生成一个唯一的字符串
        String str = UUID.randomUUID().toString();

        //返回唯一的文件名
        return str + suffix;
    }

    /**
     * 生成随机目录的方法
     * @param dirPathLevel
     * @param dirPathNameSize
     * @return
     */
    public static String getRandomDirPath(int dirPathLevel,int dirPathNameSize){
        StringBuilder dirPath = new StringBuilder();
        //我们随机生成的目录就从这个字符串中随机取俩字母
        String source = "abcde0123456789";
        Random random = new Random();
        for (int j=0;j < dirPathLevel;j++) {
            for (int i = 0; i < dirPathNameSize; i++) {
                //循环两次,每次取出一个字符作为目录名
                int index = random.nextInt(source.length());
                dirPath.append(source.charAt(index));
            }
            dirPath.append("/");
        }
        return dirPath.toString();
    }

    /**
     * 按照日期生成随机目录:我们每天上传的内容放到一个目录中
     * @return
     */
    public static String getDateDirPath(){
        StringBuilder dirPath = new StringBuilder();
        //获取当前时间:获取当前的年、月、日就可以生成目录路径
        Calendar calendar = Calendar.getInstance();

        int year = calendar.get(Calendar.YEAR);
        dirPath.append(year+"/");

        //获取到的月是0-11,而真正的月是1-12
        int month = calendar.get(Calendar.MONTH) + 1;
        if (month < 10){
            dirPath.append("0"+month+"/");
        }else {
            dirPath.append(month+"/");
        }

        //获取日
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        if(day < 10){
            dirPath.append("0" + day + "/");
        }else {
            dirPath.append(day + "/");
        }
        return dirPath.toString();
    }

    public static void main(String[] args) {
        System.out.println(getDateDirPath());
    }
}
