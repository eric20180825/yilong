package com.yilong.utils;

import java.util.Random;

public class CommonUtil {

    /**
     * 获取整型随机数
     * @param i
     * @return
     */
    public static String getIntRandom(int i){
        Random random = new Random();
        int ran = random.nextInt(i);
        return ran+"";
    }
}
