package com.lzf.test;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.util.Patterns;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import java.util.Locale;

/**
 * Created by MJCoder on 2017-10-09.
 */

public class WebViewActivity extends Activity {

    private WebView webView;
    private TextView titleTv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        titleTv = findViewById(R.id.title);
        String url = getIntent().getStringExtra("url");

        webView = (WebView) findViewById(R.id.webView);
        webView.setWebChromeClient(new WebChromeClient() {
            //这里设置获取到的网站title
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                titleTv.setText(title + "");
            }
        });
        webView.setWebViewClient(new WebViewClient() {
            //设置在webView点击打开的新网页在当前界面显示,而不跳转到新的浏览器中
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.startsWith("newtab:")) {
                    String realUrl = url.substring(7, url.length());
                    Intent it = new Intent(Intent.ACTION_VIEW);
                    it.setData(Uri.parse(realUrl));
                    startActivity(it);
                } else {
                    view.loadUrl(url);
                }
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                view.loadUrl("javascript: var allLinks = document.getElementsByTagName('a'); if (allLinks) {var i;for (i=0; i<allLinks.length; i++) {var link = allLinks[i];var target = link.getAttribute('target'); if (target && target == '_blank') {link.setAttribute('target','_self');link.href = 'newtab:'+link.href;}}}");
            }
        });
        webView.getSettings().setJavaScriptEnabled(true);  //设置WebView属性,运行执行js脚本
        if (url != null && Patterns.WEB_URL.matcher(url).matches()) {
            webView.loadUrl(url);
        } else {
            webView.loadUrl("http://rocketship.com.au/404");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            finish();
            //===========国际化开始========================
            SharedPreferences sp = this.getSharedPreferences("basicInfoSet", MODE_PRIVATE);
            if ("".equals(sp.getString("language", "")) || sp.getString("language", "") == null) {
                if ("zh".equals(Locale.getDefault().getLanguage())) {
                    Resources resources = getResources();// 获得res资源对象
                    Configuration config = resources.getConfiguration();// 获得设置对象
                    DisplayMetrics dm = resources.getDisplayMetrics();// 获得屏幕参数：主要是分辨率，像素等。
                    config.locale = Locale.CHINESE; // 简体中文
                    resources.updateConfiguration(config, dm);
                } else {
                    Resources resources = getResources();// 获得res资源对象
                    Configuration config = resources.getConfiguration();// 获得设置对象
                    DisplayMetrics dm = resources.getDisplayMetrics();// 获得屏幕参数：主要是分辨率，像素等。
                    config.locale = Locale.ENGLISH; // 英文
                    resources.updateConfiguration(config, dm);
                }
            } else {
                if ("中文".equals(sp.getString("language", ""))) {
                    Resources resources = getResources();// 获得res资源对象
                    Configuration config = resources.getConfiguration();// 获得设置对象
                    DisplayMetrics dm = resources.getDisplayMetrics();// 获得屏幕参数：主要是分辨率，像素等。
                    config.locale = Locale.CHINESE; // 简体中文
                    resources.updateConfiguration(config, dm);
                } else {
                    Resources resources = getResources();// 获得res资源对象
                    Configuration config = resources.getConfiguration();// 获得设置对象
                    DisplayMetrics dm = resources.getDisplayMetrics();// 获得屏幕参数：主要是分辨率，像素等。
                    config.locale = Locale.ENGLISH; // 英文
                    resources.updateConfiguration(config, dm);
                }
            }
            //===========国际化结束========================
        }
    }
}
