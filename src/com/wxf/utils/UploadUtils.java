package com.wxf.utils;

import java.io.File;
import java.util.UUID;

public class UploadUtils {
    public static String getNewName(String oldName){
        String uuidName =UUID.randomUUID().toString().replace("-","");
        return uuidName+"_"+oldName;
    }
    //xxx/WEB-INF/upload   a.jpg
    public static String getNewPath(String bashPath,String oldName){
        int hashCode = oldName.hashCode();  //根据文件名得到hash值  a.jpg   a.jpg
        int path1 = hashCode & 15;  //0 1
        int path2 = hashCode>>4 & 15;  //0 1 2
        String path = bashPath+"/"+path1+"/"+path2;  //xxx/WEB-INF/upload/1/3
        File file = new File(path);
        if(!file.exists()){   //判断目录是否存在，如果不存在，则创建
            file.mkdirs();
        }
        return path;
    }

    public static void main(String[] args) {
        System.out.println(getNewName("a.jpg"));
    }
}
