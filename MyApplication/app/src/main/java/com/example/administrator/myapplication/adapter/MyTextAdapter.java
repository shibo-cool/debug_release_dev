package com.example.administrator.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.my_item.item_photo;
import com.example.administrator.myapplication.my_item.item_text;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MyTextAdapter extends BaseAdapter {
    private Context context;
    private List<item_text> list;

    public MyTextAdapter(Context context, List<item_text> list){
        this.context = context;
        this.list = list;
    }

    public void onDateChange(List<item_text> list) {
        this.list = list;
        this.notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        return list.get(position).getType();
    }

    @Override
    public int getViewTypeCount() {
        return 3;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ItemTextHolder util = null;
        int type = list.get(position).getType();
        if(view == null){
            util = new ItemTextHolder();
            if(type == 0){
                view = LayoutInflater.from(context).inflate(R.layout.item_text,parent,false);
            }
            else{
                view = LayoutInflater.from(context).inflate(R.layout.item_photo,parent,false);
            }

            util.text_left = (TextView)view.findViewById(R.id.text_left);
            util.text_right = (TextView) view.findViewById(R.id.text_right);
            util.photo_left = (TextView)view.findViewById(R.id.photo_left);
            util.photo_right = (ImageView) view.findViewById(R.id.photo_right);
            view.setTag(util);
        }else {
            util = (ItemTextHolder) view.getTag();
        }
        if(type == 0){
            util.text_left.setText(list.get(position).gettext_left());
            util.text_right.setText(list.get(position).gettext_right());
        }
        else{
            util.photo_left.setText(((item_photo)list.get(position)).getphoto_left());
            if(((item_photo)list.get(position)).getphoto_right() != 0){
                util.photo_right.setImageResource(((item_photo)list.get(position)).getphoto_right());
            }
            else{
                Glide.with(util.photo_right).load(((item_photo)list.get(position)).getPath()).into(util.photo_right);
            }
        }
        return view;
    }
}
class ItemTextHolder extends ItemPhotoHolder{
    TextView text_left;
    TextView text_right;
}
