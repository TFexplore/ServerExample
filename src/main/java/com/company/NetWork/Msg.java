package com.company.NetWork;

public class Msg {

    private Integer type;
    private Integer msgNo;
    private String data;

    public Msg(Integer type, String data) {
        this.data = data;
    }
    public Msg() {

    }



    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getMsgNo() {
        return msgNo;
    }

    public void setMsgNo(Integer msgNo) {
        this.msgNo = msgNo;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}