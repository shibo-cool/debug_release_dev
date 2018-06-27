package com.example.administrator.myapplication;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.myapplication.adapter.TabFragmentPagerAdapter;
import com.example.administrator.myapplication.fragment.FragmentList;
import com.example.administrator.myapplication.fragment.FragmentWebView;
import com.example.administrator.myapplication.viewPager.NoScrollViewPager;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private LinearLayout menu_1;
    private LinearLayout menu_2;
    private ImageView menu_line_up;
    private ImageView menu_my;
    private TextView menu_line_up_txt;
    private TextView menu_my_txt;
    private NoScrollViewPager mViewPager;
    private List<Fragment> mList;
    private FragmentWebView webviewFragment;
    private FragmentList myFragment;
    private FragmentManager fm;
    private FragmentTransaction ft;
    private Fragment mCurrentFragment;
    private TabFragmentPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        init();
        init_1();
        if (getSupportActionBar() != null){//一般在设置了requestWindowFeature(Window.FEATURE_NO_TITLE);的时候是可以成功去掉标题栏的，
            getSupportActionBar().hide();//但是avtivity在继承了AppCompatActivity之后就不行了，在onCreate（）方法中加载hide（）方法之后就行了
        }
    }

    private void init(){
        mViewPager = (NoScrollViewPager)findViewById(R.id.viewPager);
        mViewPager.setisCanScroll(false);
        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();
        webviewFragment = new FragmentWebView();
        myFragment = new FragmentList();
        mList = new ArrayList<Fragment>();
        mList.add(webviewFragment);
        mList.add(myFragment);
        adapter = new TabFragmentPagerAdapter(getSupportFragmentManager(),mList);
        mViewPager.setAdapter(adapter);
        mViewPager.setCurrentItem(0);
    }

    private void init_1(){
        menu_1 = (LinearLayout)findViewById(R.id.menu_1);
        menu_2 = (LinearLayout)findViewById(R.id.menu_2);
        menu_line_up = (ImageView)findViewById(R.id.menu_line_up);
        menu_my = (ImageView)findViewById(R.id.menu_my);
        menu_line_up_txt = (TextView)findViewById(R.id.menu_line_up_txt);
        menu_my_txt = (TextView)findViewById(R.id.menu_my_txt);

        menu_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menu_line_up_txt.setTextColor(getResources().getColor(R.color.menu_2));
                menu_my_txt.setTextColor(getResources().getColor(R.color.menu_1));
                menu_line_up.setImageDrawable(getResources().getDrawable(R.mipmap.icon_queuing_selected));
                menu_my.setImageDrawable(getResources().getDrawable(R.mipmap.icon_queuing_my_default));
                mViewPager.setCurrentItem(0);
            }
        });
        menu_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menu_line_up_txt.setTextColor(getResources().getColor(R.color.menu_1));
                menu_my_txt.setTextColor(getResources().getColor(R.color.menu_2));
                menu_line_up.setImageDrawable(getResources().getDrawable(R.mipmap.icon_queuing_default));
                menu_my.setImageDrawable(getResources().getDrawable(R.mipmap.icon_queuing_my_selected));
                //Toast.makeText(MainActivity.this, "数字是:"+menu_line_up_txt.getTextColors(), Toast.LENGTH_LONG).show();
                mViewPager.setCurrentItem(1);
            }
        });
    }//菜单选择以及按钮颜色改变
}
