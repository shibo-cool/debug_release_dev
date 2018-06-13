package com.example.administrator.myapplication.my_item;

public class item_photo  extends item_text{
    private  String photo_left;
    private  int photo_right;
    private String path;

    public item_photo(){}

    public item_photo(String photo_left,int photo_right){
        this.photo_left = photo_left;
        this.photo_right = photo_right;
    }
    public  String getphoto_left(){
        return photo_left;
    }

    public void setphoto_left(String photo_left) {
        this.photo_left = photo_left;
    }

    public int getphoto_right(){
        return photo_right;
    }

    public void setphoto_right(int photo_right) {
        this.photo_right = photo_right;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
