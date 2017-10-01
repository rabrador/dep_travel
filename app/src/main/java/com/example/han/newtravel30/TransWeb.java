package com.example.han.newtravel30;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class TransWeb extends AppCompatActivity {
    public WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trans_web);

        // 取得XML中的WebView
        mWebView = (WebView) findViewById(R.id.webview);

        Intent i = this.getIntent();
        String a = i.getStringExtra("uri");

        // WebView的設定選項
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);   // Enable Javascript
        webSettings.setDomStorageEnabled(true); // Enable LocalStorage

        //自動調成螢幕大小
        mWebView.getSettings().setUseWideViewPort(true);
        mWebView.getSettings().setLoadWithOverviewMode(true);
        mWebView.getSettings().setBuiltInZoomControls(true); //顯示放大縮小 controler
        mWebView.getSettings().setSupportZoom(true); //可以縮放

        String url = "http://pbike.pthg.gov.tw/Station/Map.aspx";
        // 加這行以避免跳出APP用瀏覽器開啟
        mWebView.setWebViewClient(new WebViewClient());
        // 載入網址
        mWebView.loadUrl(a);
    }

    public static class HotListActivity extends AppCompatActivity {

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_hot_list);
        }
    }
}
