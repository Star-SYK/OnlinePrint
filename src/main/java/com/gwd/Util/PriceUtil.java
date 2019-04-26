package com.gwd.Util;


public class PriceUtil {

    /*(int)((PriceUtil.CalculatePrice(property.getPrice(),orderInfo.getPageNum(),orderInfo.getNum())+0.005)*100)*/
    public static int CalculatePrice(double price, int pagerNum, int num){
        return (int)((price*pagerNum*num+0.005)*100);
    }
}
