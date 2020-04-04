package com.example.teamcure_4;
 
import com.google.gson.annotations.SerializedName;
 
public class Post {

    private int userId;

    private int id;

    private String title;



    @SerializedName("uid")
    private int uid;
    @SerializedName("user")
    private int user;
    @SerializedName("body")
    private String body;
    @SerializedName("img")
    private String img;
    @SerializedName("feeling")
    private int feeling;
    @SerializedName("support")
    private int support;
    @SerializedName("img_identifier")
    private String img_identifier;





    public int getUserId() {
        return userId;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }



    public int getUID(){
        return uid;
    }

    public int getUser(){
        return user;
    }
    public String getBody(){
        return body;
    }
    public String getImg(){
        return img;
    }
    public int getFeeling(){
        return feeling;
    }
    public int getSupport(){
        return support;
    }
    public String getImg_identifier(){
        return img_identifier;
    }


}