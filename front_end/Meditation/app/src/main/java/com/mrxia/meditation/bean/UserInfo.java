package com.mrxia.meditation.bean;

public class UserInfo {

    private String password;

    private String userId;

    private String userName;

    private String favorite;

    private String imgUrl;


    public String getFavorite() {
        return favorite;
    }
    public void setFavorite(String favorite) {
        this.favorite = favorite;
    }
    public String getImgUrl() {
        return imgUrl;
    }
    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public UserInfo(String password, String userId, String userName) {
        super();
        this.password = password;
        this.userId = userId;
        this.userName = userName;
    }
    public UserInfo(String password, String userId, String userName, String imgUrl, String favorite) {
        super();
        this.password = password;
        this.userId = userId;
        this.userName = userName;
        this.imgUrl = imgUrl;
        this.favorite = favorite;
    }

    public UserInfo(){

    }
}