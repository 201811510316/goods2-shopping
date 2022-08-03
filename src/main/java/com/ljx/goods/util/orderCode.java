package com.ljx.goods.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class orderCode {
    private static String getDateTime() {
        DateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        return sdf.format(new Date());
    }

    /**
     * 生成一个5位的随机数；（当然可以生成3、4、6、7、8……位；只是，这儿为了便于管理、程序明确，这儿我们统一生成5位）
     * @param n
     * @return
     */
    private static int getRandom(Integer n) {
        Random random = new Random();
        return (int)(random.nextDouble() * (90000)) + 10000;
    }

    /**
     * 生成订单号
     * @param userId
     * @return
     */
    public static String getOrderCode(Integer userId) {
        return getDateTime() + getRandom(userId);
    }

}
