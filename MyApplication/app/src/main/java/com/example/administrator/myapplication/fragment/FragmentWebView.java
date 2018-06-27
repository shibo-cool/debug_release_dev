package com.example.administrator.myapplication.fragment;

import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
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

import com.example.administrator.myapplication.BuildConfig;
import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.refreshWebview.Refresh;
import com.example.administrator.myapplication.refreshWebview.RefreshLayout;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class FragmentWebView extends BaseFragment {

    private View view;
    private ProgressBar progressbar;
    private EditText et;
    private ImageButton back;
    private LinearLayout error;

//    private PtrClassicFrameLayout mPtrFrame;
    private WebView mWebView;

    /**
     * 若是需要设置一下功能 可在DavidWebView中进行设置
     */

    private void url(){//打包时选择debug或者release包
        if(BuildConfig.DEBUG) {
            mWebView.loadUrl("http://www.baidu.com/");
        }else{
            mWebView.loadUrl("http://www.qq.com/");
        }
    }

    @Override
    public View initView() {
        if (view == null) {
            view = View.inflate(mActivity, R.layout.fragment_webview, null);
            mWebView = (WebView)view.findViewById(R.id.webview);
//            mPtrFrame = (PtrClassicFrameLayout) view.findViewById(R.id.rotate_header_web_view_frame);

            error = (LinearLayout)view.findViewById(R.id.error);
            error.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {//断网后重新联网点击之后回到正常界面重新加载网页
                    mWebView.setVisibility(VISIBLE);
                    error.setVisibility(GONE);
                    mWebView.reload();
                }
            });

            url();//加载网页

            mWebView.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {//手机返回键回退功能实现
                    if(event.getAction() == KeyEvent.ACTION_DOWN){
                        if(keyCode == KeyEvent.KEYCODE_BACK){
                            //这里处理返回事件
                            if(mWebView.canGoBack()){
                                mWebView.goBack();
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
                    mWebView.goBack();
                }
            });//UI返回键返回功能实现

            mWebView.getSettings().setJavaScriptEnabled(true);// 首先设置支持JS脚本
            mWebView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);//不要缓存
            mWebView.getSettings().setLoadWithOverviewMode(true);//缩放至屏幕大小
            progressbar = (ProgressBar)view.findViewById(R.id.progress1);
            WebChromeClient wvcc = new WebChromeClient() {
                @Override
                public void onProgressChanged(WebView view, int newProgress) {//设置加载网页进度条
                    super.onProgressChanged(view, newProgress);
                    if (newProgress == 100) {
                        progressbar.setVisibility(GONE);//加载完毕时进度条隐藏
                    } else {
                        if (progressbar.getVisibility() == GONE)
                            progressbar.setVisibility(VISIBLE);
                        progressbar.setProgress(newProgress);
                    }
                    super.onProgressChanged(view, newProgress);
                }
            };
            mWebView.setWebChromeClient(wvcc);//进度条的实现
            mWebView.setWebViewClient(new WebViewClient() {//使接下来的所有网页都在这个窗口中打开
                @Override
                public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error1) {
                    super.onReceivedError(view, request, error1);
                    mWebView.setVisibility(GONE);
                    error.setVisibility(VISIBLE);
                }
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    if(url.toLowerCase().startsWith("http://") || url.toLowerCase().startsWith("https://"))
                    {
                        return false;
                    }
                    return true;
                }
//                @Override
//                public void onPageFinished(WebView view, String url) {
//                    new Handler().postDelayed(new Runnable() {
//
//                        @Override
//                        public void run() {
//                            mPtrFrame.refreshComplete();
//                        }
//                    }, 1000);
//                }
            });// 设置webview的客户端

            et = (EditText)view.findViewById(R.id.edit);
            et.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {//根据用户输入的网址加载网页
                    if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                        final String url_1 = et.getText().toString();
                        mWebView.loadUrl("http://"+url_1);
                    }
                    return false;
                }
            });
        }

        final RefreshLayout refreshLayout = (RefreshLayout) view.findViewById(R.id.refreshLayout);
        if (refreshLayout != null) {
            // 刷新状态的回调
            refreshLayout.setRefreshListener(new RefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    // 延迟3秒后刷新成功
                    refreshLayout.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            refreshLayout.refreshComplete();
                            mWebView.reload();
                        }
                    }, 3000);
                }
            });
        }
        Refresh header  = new Refresh(getActivity());
        refreshLayout.setRefreshHeader(header);
        refreshLayout.autoRefresh();
//        mPtrFrame.setLastUpdateTimeRelateObject(this);
//        mPtrFrame.setPtrHandler(new PtrHandler() {
//            @Override
//            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
//                return PtrDefaultHandler.checkContentCanBePulledDown(frame, mWebView, header);
//            }
//            @Override
//            public void onRefreshBegin(PtrFrameLayout frame) {
//                updateData();
//            }
//        });
//        mPtrFrame.setResistance(1.7f);
//        mPtrFrame.setRatioOfHeaderHeightToRefresh(1.2f);
//        mPtrFrame.setDurationToClose(200);
//        mPtrFrame.setDurationToCloseHeader(1000);
//        mPtrFrame.setPullToRefresh(false);
//        mPtrFrame.setKeepHeaderWhenRefresh(true);
//
//        mPtrFrame.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                mPtrFrame.autoRefresh();
//            }
//        }, 1000);

        return view;
    }

    public void onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && mWebView.canGoBack()) {
            if(mWebView.getOriginalUrl() == getOriginalUrl()) {
                mWebView.goBack(); //goBack()表示返回WebView的上一页面
            }
            else{
                mWebView.loadUrl(getOriginalUrl());
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
    private void updateData() {
        mWebView.reload();
    }
}
