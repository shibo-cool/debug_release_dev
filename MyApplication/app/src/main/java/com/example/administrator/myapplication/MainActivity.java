package com.example.administrator.myapplication;

import android.app.Instrumentation;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class MainActivity extends AppCompatActivity {

    private WebView webview;
    private TextView title1;
    private ImageButton back;
    private View mErrorView;
    private ProgressBar progressbar;
    boolean mIsErrorPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        init();
        title();
        url();
        if (getSupportActionBar() != null){//一般在设置了requestWindowFeature(Window.FEATURE_NO_TITLE);的时候是可以成功去掉标题栏的，
            getSupportActionBar().hide();//但是avtivity在继承了AppCompatActivity之后就不行了，在onCreate（）方法中加载hide（）方法之后就行了
        }
    }

    private void url(){//打包时选择debug或者release包
        if(BuildConfig.DEBUG) {
            webview.loadUrl("http://www.baidu.com/");
        }else{
            webview.loadUrl("http://www.qq.com/");
        }
    }

    private void title() {
        title1 = (TextView)findViewById(R.id.title);
        progressbar = (ProgressBar)findViewById(R.id.progress1);
        WebChromeClient wvcc = new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                Log.d("ANDROID_LAB", "TITLE=" + title);
                title1.setText(title);
            }
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
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
        // 设置setWebChromeClient对象
        webview.setWebChromeClient(wvcc);
        WebViewClient wvc = new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // 使用自己的WebView组件来响应Url加载事件，而不是使用默认浏览器器加载页面
                webview.loadUrl(url);
                // 消耗掉这个事件。Android中返回True的即到此为止吧,事件就会不会冒泡传递了，我们称之为消耗掉
                return true;
            }//webview.setWebViewClient(wvc);
            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
                // 断网或者网络连接超时
                if (errorCode == ERROR_HOST_LOOKUP || errorCode == ERROR_CONNECT || errorCode == ERROR_TIMEOUT) {
//                    setContentView(R.layout.activity_error);
                    view.loadUrl("about:blank"); // 避免出现默认的错误界面
//                    view.loadUrl(url);
                    setContentView(R.layout.activity_error);

                }
            }
        };
        webview.setWebViewClient(wvc);
    }

    private void init(){
        webview = (WebView)findViewById(R.id.webview);
        back = (ImageButton)findViewById(R.id.back);

        back.setOnClickListener(new View.OnClickListener() {
            public void actionKey(final int keyCode) {
                new Thread () {
                    public void run () {
                        try {
                            Instrumentation inst=new Instrumentation();
                            inst.sendKeyDownUpSync(keyCode);
                        } catch(Exception e) {
                            e.printStackTrace();
                        }
                    }
                }.start();
            }
            @Override
            public void onClick(View v) {
                actionKey(KeyEvent.KEYCODE_BACK);//返回上一页面
            }
        });//UI返回键
        // 首先设置支持JS脚本
        webview.getSettings().setJavaScriptEnabled(true);

        // 设置webview的客户端
        webview.setWebViewClient(new WebViewClient(){//使接下来的所有网页都在这个窗口中打开
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;// 返回false
            }
        });
    }
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)&&webview.canGoBack()) {
                webview.goBack();//返回上一页面
                return true;
        }
        return super.onKeyDown(keyCode, event);
    }//手机返回键
}
