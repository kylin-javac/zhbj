package ligang.huse.cn.zhbj;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.onekeyshare.OnekeyShareTheme;

/**
 * 新闻内容详情页
 */
public class NewsDetailActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageButton mBtnMenu;
    private ImageButton mBtnBack;
    private TextView mTvTitle;
    private LinearLayout mLlControl;
    private ImageButton mBtnTextSize;
    private ImageButton mBtnShare;
    private ProgressBar mWebView_loading;
    private WebView mWebView;
    private WebSettings mSettings;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();//这是继承AppCompatActivity类时隐藏标题栏的方法
        //this.requestWindowFeature(Window.FEATURE_NO_TITLE);这种方式是继承Activity类时隐藏标题的栏的方法
        setContentView(R.layout.activity_news_detail);
        initViews();
        String url = getIntent().getStringExtra("url");
        mWebView.loadUrl(url);
        mSettings = mWebView.getSettings();
        mSettings.setJavaScriptEnabled(true);//支持javascript
        mSettings.setUseWideViewPort(true);//支持双击
        mSettings.setBuiltInZoomControls(true);//支持放大或缩小
        //设置webview的监听事件
        mWebView.setWebViewClient(new WebViewClient() {
            //开始加载网页
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                Log.i("--1--->", "开始加载网页....");
                mWebView_loading.setVisibility(View.VISIBLE);
            }

            //加载完成
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                Log.i("--1--->", "网页加载完成....");
                mWebView_loading.setVisibility(View.INVISIBLE);
            }

            //设置跳转过程中的故事
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);//强制新打开的页面在当期activity中
                return true;
            }
        });
        mBtnTextSize.setOnClickListener(this);
        mBtnShare.setOnClickListener(this);
        mBtnBack.setOnClickListener(this);

    }


    /**
     * 初始化布局文件
     */
    private void initViews() {
        mBtnMenu = (ImageButton) findViewById(R.id.btn_menu);
        mBtnBack = (ImageButton) findViewById(R.id.btn_back);
        mTvTitle = (TextView) findViewById(R.id.tv_title);
        mLlControl = (LinearLayout) findViewById(R.id.ll_control);
        mBtnTextSize = (ImageButton) findViewById(R.id.btn_textSize);
        mBtnShare = (ImageButton) findViewById(R.id.btn_share);
        mWebView_loading = (ProgressBar) findViewById(R.id.webView_loading);
        mWebView = (WebView) findViewById(R.id.webView);
        mLlControl.setVisibility(View.VISIBLE);
        mBtnMenu.setVisibility(View.INVISIBLE);
        mBtnBack.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_back:
                finish();
                break;
            case R.id.btn_textSize:
                showChooseDialog();
                break;
            case R.id.btn_share:
                showShare();
                break;

        }

    }

    private int mTmep;
    private int mCurrent = 2;

    /**
     * 设置字体对话框
     */
    private void showChooseDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        String textSize[] = {"超大号字体", "大号字体", "正常字体", "小号字体", "超小号字体"};
        builder.setTitle("设置字体");
        builder.setSingleChoiceItems(textSize, mCurrent, new AlertDialog.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mTmep = which;
            }
        });
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (mTmep) {
                    case 0:
                        mSettings.setTextZoom(180);
                        break;
                    case 1:
                        mSettings.setTextZoom(160);
                        break;
                    case 2:
                        mSettings.setTextZoom(120);
                        break;
                    case 3:
                        mSettings.setTextZoom(100);
                        break;
                    case 4:
                        mSettings.setTextZoom(80);
                        break;
                }
                mCurrent = mTmep;
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();
    }
    private void showShare() {
        ShareSDK.initSDK(this);
        OnekeyShare oks = new OnekeyShare();
        oks.setTheme(OnekeyShareTheme.CLASSIC);
        //关闭sso授权
        oks.disableSSOWhenAuthorize();
       // 分享时Notification的图标和文字  2.5.9以后的版本不调用此方法
        //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        //oks.setTitle(getString(R.string.share));
        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
        oks.setTitleUrl("http://sharesdk.cn");
        // text是分享文本，所有平台都需要这个字段
        oks.setText("我是分享文本，啦啦啦~");
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("http://sharesdk.cn");
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("我是测试评论文本");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite(getString(R.string.app_name));
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl("http://sharesdk.cn");
        // 启动分享GUI
        oks.show(this);
    }
    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
}
