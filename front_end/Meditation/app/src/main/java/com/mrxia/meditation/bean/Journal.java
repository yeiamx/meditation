package com.mrxia.meditation.bean;

public class Journal {
    private String id;
    private String userName;
    private String content;
    private String time;
    private int userHead;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getUserHead() {
        return userHead;
    }

    public void setUserHead(int userHead) {
        this.userHead = userHead;
    }

    public Journal(String id, String userName, String content, String time, int userHead){
        super();
        this.id = id;
        this.userName = userName;
        this.content = content;
        this.time = time;
        this.userHead = userHead;
    }
    public  Journal(){

    }
}
