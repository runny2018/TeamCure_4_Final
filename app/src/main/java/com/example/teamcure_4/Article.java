package com.example.teamcure_4;

public class Article {
    String content;
    String image;
    String userName;
    int userFeeling;
    int provideSupport;

    public int getUserFeeling() {
        return userFeeling;
    }

    public void setUserFeeling(int userFeeling) {
        this.userFeeling = userFeeling;
    }

    public int getProvideSupport() {
        return provideSupport;
    }

    public void setProvideSupport(int provideSupport) {
        this.provideSupport = provideSupport;
    }

    public Article(){
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
