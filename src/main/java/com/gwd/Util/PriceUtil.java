package com.gwd.Util;

/**
 * @Description: OnlinePrint
 * @Param:
 * @return:
 * @Author: ChenYu
 * @Date: 2019/5/4
 */
public class PriceUtil {

    /*(int)((PriceUtil.CalculatePrice(property.getPrice(),orderInfo.getPageNum(),orderInfo.getNum())+0.005)*100)*/
    public static int CalculatePrice(double price, int pagerNum, int num,int priority){
        return (int)((price*pagerNum*num + (priority - 1) +0.005)*100);
    }
}
