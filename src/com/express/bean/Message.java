package com.express.bean;

public class Message {//回复的格式为：{result:"字符串",data:{object对象}}
    private String result;//给客户端回复的消息的内容
    private Object data;//消息所携带的一组数据
    private int status;//状态码

    public Message(Object data,int status) {
        this.data = data;
        this.status = status;
    }

    public Message(int status) {
        this.status = status;
    }

    public Message(String result) {
        this.result = result;
    }

    public Message(int status, String result) {
        this.status = status;
        this.result = result;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Message() {
    }

    public Message(String result, Object data, int status) {
        this.result = result;
        this.data = data;
        this.status = status;
    }
}
