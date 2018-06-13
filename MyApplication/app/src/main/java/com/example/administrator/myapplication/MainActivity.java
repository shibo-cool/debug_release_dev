package com.example.administrator.myapplication;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.myapplication.adapter.TabFragmentPagerAdapter;
import com.example.administrator.myapplication.fragment.FragmentMy;
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
    private FragmentMy myFragment;
    private FragmentManager fm;
    private FragmentTransaction ft;
    private Fragment mCurrentFragment;
    private TabFragmentPagerAdapter adapter;

//    private void url(){//打包时选择debug或者release包
//        if(BuildConfig.DEBUG) {
//            webview.loadUrl("http://www.baidu.com/");
//        }else{
//            webview.loadUrl("http://www.qq.com/");
//        }
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        init();
        init_1();
//        url();
        if (getSupportActionBar() != null){//一般在设置了requestWindowFeature(Window.FEATURE_NO_TITLE);的时候是可以成功去掉标题栏的，
            getSupportActionBar().hide();//但是avtivity在继承了AppCompatActivity之后就不行了，在onCreate（）方法中加载hide（）方法之后就行了
        }
    }

//    private void title() {
//        title1 = (TextView)findViewById(R.id.title);
//        progressbar = (ProgressBar)findViewById(R.id.progress1);
//        WebChromeClient wvcc = new WebChromeClient() {
//            @Override
//            public void onReceivedTitle(WebView view, String title) {
//                super.onReceivedTitle(view, title);
//                Log.d("ANDROID_LAB", "TITLE=" + title);
//               // title1.setText(title);
//            }
//            @Override
//            public void onProgressChanged(WebView view, int newProgress) {
//                super.onProgressChanged(view, newProgress);
//                if (newProgress == 100) {
//                    progressbar.setVisibility(GONE);
//                } else {
//                    if (progressbar.getVisibility() == GONE)
//                        progressbar.setVisibility(VISIBLE);
//                    progressbar.setProgress(newProgress);
//                }
//                super.onProgressChanged(view, newProgress);
//            }
//        };
//        // 设置setWebChromeClient对象
//        webview.setWebChromeClient(wvcc);
//    }

    private void init(){

        mViewPager = (NoScrollViewPager)findViewById(R.id.viewPager);
        mViewPager.setisCanScroll(false);
        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();
        webviewFragment = new FragmentWebView();
        myFragment = new FragmentMy();
        mList = new ArrayList<Fragment>();
        mList.add(webviewFragment);
        mList.add(myFragment);
        adapter = new TabFragmentPagerAdapter(getSupportFragmentManager(),mList);
        mViewPager.setAdapter(adapter);
        mViewPager.setCurrentItem(0);

//        a.setWebViewClient(new WebViewClient() {
//            @Override
//            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                // 使用自己的WebView组件来响应Url加载事件，而不是使用默认浏览器器加载页面
//                a.loadUrl(url);
//                // 消耗掉这个事件。Android中返回True的即到此为止吧,事件就会不会冒泡传递了，我们称之为消耗掉
//                return true;
//            }
//            @Override
//            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
//                super.onReceivedError(view, errorCode, description, failingUrl);
//
//                // 断网或者网络连接超时
////                if (errorCode == ERROR_HOST_LOOKUP || errorCode == ERROR_CONNECT || errorCode == ERROR_TIMEOUT) {
////                    view.loadUrl("about:blank"); // 避免出现默认的错误界面
////                    setContentView(R.layout.fragment_my);
////                }
//                a.setVisibility(View.GONE);
//                b.setVisibility(View.VISIBLE);
//            }
//        });
    }

//    public void switchFragment(String fromTag, String toTag) {
//        Fragment from = fm.findFragmentByTag(fromTag);
//        Fragment to = fm.findFragmentByTag(toTag);
//        if (mCurrentFragment != to) {
//            mCurrentFragment = to;
//            FragmentTransaction transaction = fm.beginTransaction();
//            if (!to.isAdded()) {//判断是否被添加到了Activity里面去了
//                transaction.hide(from).add(R.id.framlayout, to).commit();
//            } else {
//                transaction.hide(from).show(to).commit();
//            }
//        }
//    }

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
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        // TODO Auto-generated method stub
//        if(mCurrentFragment instanceof FragmentWebView){
//            ((FragmentWebView)mCurrentFragment).onKeyDown(keyCode, event);
//            return true;
//        }
//        return false;
//    }
}
