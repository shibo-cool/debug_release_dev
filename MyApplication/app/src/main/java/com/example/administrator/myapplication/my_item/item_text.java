package com.example.administrator.myapplication.my_item;

public class item_text {
    private  String text_left;
    private  String text_right;
    private int type = 0;
    public item_text(){}

    public item_text(String text_left,String text_right){
        this.text_left = text_left;
        this.text_right = text_right;
    }
    public  String gettext_left(){
        return text_left;
    }
    public String gettext_right(){
        return text_right;
    }
    public void settext_left(String text_left) {
        this.text_left = text_left;
    }

    public void settext_right(String text_right) {
        this.text_right = text_right;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
