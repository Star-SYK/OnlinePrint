package com.gwd.entity;
/**
 * @Description: OnlinePrint
 * @Param:
 * @return:
 * @Author: ChenYu
 * @Date: 2019/5/4
 */
public class WeChatPay {
    private Integer id;
    private Integer orderId;
    private String request;
    private String response;
    private String payapi;
    private String payResult;
    private String returnCode;


    public WeChatPay() {
    }

    public WeChatPay(Integer orderId, String request, String response, String payapi) {

        this.orderId = orderId;
        this.request = request;
        this.response = response;
        this.payapi = payapi;
    }

    @Override
    public String toString() {
        return "WeChatPay{" +
                "id=" + id +
                ", orderId=" + orderId +
                ", request='" + request + '\'' +
                ", response='" + response + '\'' +
                ", payapi='" + payapi + '\'' +
                ", payResult='" + payResult + '\'' +
                ", returnCode='" + returnCode + '\'' +
                '}';
    }

    public String getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(String returnCode) {
        this.returnCode = returnCode;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getPayapi() {
        return payapi;
    }

    public void setPayapi(String payapi) {
        this.payapi = payapi;
    }

    public String getPayResult() {
        return payResult;
    }

    public void setPayResult(String payResult) {
        this.payResult = payResult;
    }
}
