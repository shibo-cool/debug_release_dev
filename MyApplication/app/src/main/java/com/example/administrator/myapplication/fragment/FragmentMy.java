package com.example.administrator.myapplication.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.adapter.MyTextAdapter;
import com.example.administrator.myapplication.my_item.item_photo;
import com.example.administrator.myapplication.my_item.item_text;

import java.util.ArrayList;
import java.util.List;

public class FragmentMy extends BaseFragment{
    private View view;
    private ListView textlistview;
    private List<item_text> list1 = new ArrayList<item_text>();
    private MyTextAdapter myTextAdapter;
    private ImageButton add;
    private View mPopupRootView,tPopupRootView;
    private String TAG = "FragmentMy";
    private ImageView item_photo_right;
    private String path;
//    private photoPopupwindow photo_popupWindow;

    @Override
    protected View initView() {
        if (view == null) {
            view = View.inflate(mActivity, R.layout.fragment_my, null);
            textlistview = (ListView)view.findViewById(R.id.my_listview_text);
            item_photo_right = (ImageView)view.findViewById(R.id.photo_right);
            init();
            //装载R.layout.popup对应的界面布局

            initadd();
//            initPopupWindow();
            myTextAdapter = new MyTextAdapter(getActivity(),list1);
            textlistview.setAdapter(myTextAdapter);
        }
        return view;
    }

    private void initadd() {
        mPopupRootView = this.getLayoutInflater().inflate(R.layout.photo_dialog, null);
        tPopupRootView = this.getLayoutInflater().inflate(R.layout.text_dialog, null);
        add = (ImageButton) view.findViewById(R.id.my_add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final PopupWindow popup = new PopupWindow(mPopupRootView,ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
                popup.setFocusable(true);//使这个弹窗可以调用软键盘
                final PopupWindow popup_1 = new PopupWindow(tPopupRootView,ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
                popup_1.setFocusable(true);//同上
                final Dialog mWindowDialog = new Dialog(getActivity(), R.style.BottomDialog);
                RelativeLayout root_1 = (RelativeLayout) LayoutInflater.from(getActivity()).inflate(
                        R.layout.add_menu, null);
                //初始化视图
                root_1.findViewById(R.id.choose_photo).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {//点击图片选项
                        mWindowDialog.dismiss();
                        popup.showAsDropDown(view.findViewById(R.id.fragment_my_back), 200, 200);

                    }
                });
                mPopupRootView.findViewById(R.id.photo_dialog_pickture).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {//点击选择图片
                        Toast.makeText(getActivity(),"请选择图片",Toast.LENGTH_SHORT).show();
                        // 打开本地相册
                        Intent i = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        // 设定结果返回
                        startActivityForResult(i, 2);
                    }
                });
                mPopupRootView.findViewById(R.id.photo_dialog_ok).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {//点击确认键
                        // TODO Auto-generated method stub
                        popup.dismiss();
                        item_photo a = new item_photo();
                        TextView et_txt = (TextView)(mPopupRootView != null ? mPopupRootView.findViewById(R.id.photo_dialog_text) : null);
                        String txt = et_txt != null ? et_txt.getText().toString() : "标题为空";
                        a.setphoto_left(txt);
                        a.setType(1);
                        a.setPath(path);
                        list1.add(a);
                        if(myTextAdapter != null){
                            myTextAdapter.notifyDataSetChanged();

                        }
                    }
                });

                root_1.findViewById(R.id.choose_text).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {//点击文字选项
//                        Toast.makeText(getActivity(),"您选择了文字",Toast.LENGTH_SHORT).show();
                        mWindowDialog.dismiss();
                        popup_1.showAsDropDown(view.findViewById(R.id.fragment_my_back), 200, 200);
                    }
                });
                tPopupRootView.findViewById(R.id.text_dialog_ok).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {//点击text弹窗的确认键
                        popup_1.dismiss();
                        item_text text_txt = new item_text();
                        TextView et_left =(TextView)(tPopupRootView != null ? tPopupRootView.findViewById(R.id.text_dialog_left) : null);
                        String txt_left = et_left != null ? et_left.getText().toString() : "题目为空";
                        TextView et_right =(TextView)(tPopupRootView != null ? tPopupRootView.findViewById(R.id.text_dialog_right) : null);
                        String txt_right = et_right != null ? et_right.getText().toString() : "内容为空";
                        text_txt.settext_left(txt_left);
                        text_txt.settext_right(txt_right);
                        list1.add(text_txt);
                        if(myTextAdapter != null){
                            myTextAdapter.notifyDataSetChanged();

                        }
                    }
                });

                mWindowDialog.setContentView(root_1);
                Window dialogWindow = mWindowDialog.getWindow();
                dialogWindow.setGravity(Gravity.BOTTOM);
//        dialogWindow.setWindowAnimations(R.style.dialogstyle); // 添加动画
                WindowManager.LayoutParams lp = dialogWindow.getAttributes(); // 获取对话框当前的参数值
                lp.x = 0; // 新位置X坐标
                lp.y = 0; // 新位置Y坐标
                lp.width = (int) getResources().getDisplayMetrics().widthPixels; // 宽度
                root_1.measure(0, 0);
                lp.height = root_1.getMeasuredHeight();
                lp.alpha = 9f; // 透明度
                dialogWindow.setAttributes(lp);
                mWindowDialog.show();
            }
        });
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
    // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        //打开相册并选择照片，这个方式选择单张
        // 获取返回的数据，这里是android自定义的Uri地址
        Uri selectedImage = data.getData();
        String[] filePathColumn = { MediaStore.Images.Media.DATA };
        // 获取选择照片的数据视图
        Cursor cursor = getContext().getContentResolver().query(selectedImage,
                filePathColumn, null, null, null);
        cursor.moveToFirst();
        // 从数据视图中获取已选择图片的路径
        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        String picturePath = cursor.getString(columnIndex);
        cursor.close();
        // 将图片显示到界面上
        Log.v(TAG, "mPopupRootView:" +mPopupRootView);
        if(mPopupRootView != null){
            ImageView imageView = (ImageView) mPopupRootView.findViewById(R.id.photo_dialog_pickture);
            Log.v(TAG, "imageView:" +imageView);
            Log.v(TAG, "picturePath:" +picturePath);
            Bitmap bmp = BitmapFactory.decodeFile(picturePath);
            Log.v(TAG, "bmp:" +bmp);
            imageView.setImageBitmap(bmp);
            path = picturePath;
        }
    }

    private void init() {
        item_photo head = new item_photo();
        head.setphoto_left("头像");
        head.setType(1);
        head.setphoto_right(R.mipmap.dc_dj_picking_img_portrait);
        list1.add(head);

        item_text name = new item_text();
        item_text department = new item_text();
        item_text position = new item_text();
        item_text phone = new item_text();
        name.settext_left("姓名");
        name.settext_right("李立昱");
        department.settext_left("部门");
        department.settext_right("股东/天虹商场股份有限公司/君尚3019/超市部");
        position.settext_left("职位");
        position.settext_right("商场营业员");
        phone.settext_left("手机号码");
        phone.settext_right("18822689080");
        list1.add(name);
        list1.add(department);
        list1.add(position);
        list1.add(phone);
    }
}