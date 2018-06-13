package com.example.administrator.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.my_item.item_photo;

import java.util.List;

public class MyPhotoAdapter extends BaseAdapter{
    private Context context;
    private List<item_photo> list;

    public MyPhotoAdapter(List<item_photo> list,Context context){
        this.context = context;
        this.list = list;
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
        ItemPhotoHolder util = null;
        if(view == null){
            util = new ItemPhotoHolder();
            view = LayoutInflater.from(context).inflate(R.layout.item_photo,parent,false);
            util.photo_left = (TextView)view.findViewById(R.id.photo_left);
            util.photo_right = (ImageView)view.findViewById(R.id.photo_right);
            view.setTag(util);
        }else {
            util = (ItemPhotoHolder) view.getTag();
        }
        util.photo_left.setText(list.get(position).getphoto_left());
        util.photo_right.setImageResource(list.get(position).getphoto_right());
        return view;
    }
}
class ItemPhotoHolder {
    TextView photo_left;
    ImageView photo_right;
}