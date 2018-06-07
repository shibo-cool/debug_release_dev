package com.example.administrator.myapplication.fragment;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.app.Instrumentation;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.myapplication.BuildConfig;
import com.example.administrator.myapplication.MainActivity;
import com.example.administrator.myapplication.R;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class FragmentWebView extends BaseFragment {

    private WebView webview;
    private View view;
    private ProgressBar progressbar;
    private EditText et;
    private ImageButton back;
    private LinearLayout error;

    private void url(){//打包时选择debug或者release包
        if(BuildConfig.DEBUG) {
            webview.loadUrl("http://www.baidu.com/");
        }else{
            webview.loadUrl("http://www.qq.com/");
        }
    }

    @Override
    public View initView() {
        if (view == null) {
            view = View.inflate(mActivity, R.layout.fragment_webview, null);
            webview = (WebView) view.findViewById(R.id.webview);
            error = (LinearLayout)view.findViewById(R.id.error);
            url();//加载网页

            webview.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {//手机返回键回退功能实现
                    if(event.getAction() == KeyEvent.ACTION_DOWN){
                        if(keyCode == KeyEvent.KEYCODE_BACK){
                            //这里处理返回事件
                            if(webview.canGoBack()){
                                webview.goBack();
                                return true;
                            }
                        }
                    }
                    return false;
                }
            });

            back = (ImageButton)view.findViewById(R.id.back);
            back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    webview.goBack();
                }
            });//UI返回键返回功能实现

            webview.getSettings().setJavaScriptEnabled(true);// 首先设置支持JS脚本
            webview.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);//不要缓存
            webview.getSettings().setLoadWithOverviewMode(true);//缩放至屏幕大小
            progressbar = (ProgressBar)view.findViewById(R.id.progress1);
            WebChromeClient wvcc = new WebChromeClient() {
                @Override
                public void onProgressChanged(WebView view, int newProgress) {//设置进度条
                    super.onProgressChanged(view, newProgress);
                    if (newProgress == 100) {
                        progressbar.setVisibility(GONE);
                    } else {
                        if (progressbar.getVisibility() == GONE)
                            progressbar.setVisibility(VISIBLE);
                        progressbar.setProgress(newProgress);
                    }
                    super.onProgressChanged(view, newProgress);
                }
            };
            webview.setWebChromeClient(wvcc);//进度条的实现
            webview.setWebViewClient(new WebViewClient() {//使接下来的所有网页都在这个窗口中打开
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    return false;// 返回false
                }

                @Override
                public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error1) {
                    super.onReceivedError(view, request, error1);
                    webview.setVisibility(GONE);
                    error.setVisibility(VISIBLE);
                }
            });// 设置webview的客户端

            et = (EditText)view.findViewById(R.id.edit);
            et.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {//根据用户输入的网址加载网页
                    if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                        final String url_1 = et.getText().toString();
                        webview.loadUrl("http://"+url_1);
                    }
                    return false;
                }
            });
        }
        return view;
    }
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if ((keyCode == KeyEvent.KEYCODE_BACK)&&webview.canGoBack()) {
//            if(webview.getOriginalUrl() == getOriginalUrl()){
//                webview.goBack();//返回上一页面
//            }
//            else {
//                webview.loadUrl(getOriginalUrl());
//            }
//            return true;
//        }
//        return super.onKeyDown(keyCode, event);
//    }//手机返回键

    public void onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && webview.canGoBack()) {
            if(webview.getOriginalUrl() == getOriginalUrl()) {
                webview.goBack(); //goBack()表示返回WebView的上一页面
            }
            else{
                webview.loadUrl(getOriginalUrl());
            }
        }
    }//手机返回键

    private String getOriginalUrl(){
        String ret = "";
        if(BuildConfig.DEBUG ){
            ret = "http://www.baidu.com/";
        }
        else{
            ret = "http://www.qq.com/";
        }
        return ret;
    }
}
