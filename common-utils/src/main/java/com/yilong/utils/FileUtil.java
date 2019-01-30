package com.yilong.utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {

    public static List<String> ReadLineTxtFile(String path) {
       List<String> newList=new ArrayList<String>();
        //打开文件
        File file = new File(path);
        //如果path是传递过来的参数，可以做一个非目录的判断
//        if (file.isDirectory())
//        {
//            Log.d("TestFile", "The File doesn't not exist.");
//        }
//        else
//        {
            try {
                InputStream instream = new FileInputStream(file);
                if (instream != null)
                {
                    InputStreamReader inputreader = new InputStreamReader(instream);
                    BufferedReader buffreader = new BufferedReader(inputreader);
                    String line;
                    //分行读取
                    while (( line = buffreader.readLine()) != null) {
                        newList.add(line+"\n");
                    }
                    instream.close();
                }
            }
            catch (java.io.FileNotFoundException e)
            {
                System.out.println("文件未找到");
            }
            catch (IOException e)
            {
                System.out.println("文件流读取错误");
            }
//        }
        return  newList;
    }

    /**
     * 按照分隔符将地址字符串切割开,然后拼装成FIle数组
     * @param files 字符串
     * @param split 分隔符,可以为正则
     * @return
     */
    public File[] getFileArrayByString(String files, String split){
        String[] aa = files.split(split);
        File[] attachments = new File[aa.length];
        for(int i=0;i<aa.length;i++){
            attachments[i] = new File(aa[i]);
        }
        return attachments;
    }

    public String getFilePrefix(MultipartFile file){
        String fileName=file.getOriginalFilename();
        String prefix=fileName.substring(fileName.lastIndexOf(".")+1);
        return prefix;
    }

}
