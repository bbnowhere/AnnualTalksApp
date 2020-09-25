package in.res.ncbs.at;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.Toast;

import in.res.ncbs.at.R;

public class day3 extends Fragment {
    WebView webView;
    String url;
    ImageView imgNoInternet;

    public day3() {
    }

    @SuppressLint("ValidFragment")
    public day3(String s) {
        this.url = s;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        webView = (WebView) view.findViewById(R.id.webView);
        imgNoInternet = (ImageView) view.findViewById(R.id.imgNoInternet);

        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new MyBrowser());
        // webView.loadUrl(url);
        webView.setOnKeyListener(new View.OnKeyListener(){
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {
                    webView.goBack();
                    return true;
                }
                return false;
            }
        });
        return view;
    }
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        Log.d("----- VisibleHint :- ", isVisibleToUser + " - " + url);
        if (isVisibleToUser) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (isNetworkConnected()) {
                        webView.loadUrl(url);
                        imgNoInternet.setVisibility(View.GONE);
                        webView.setVisibility(View.VISIBLE);
                    } else {
                        imgNoInternet.setVisibility(View.VISIBLE);
                        webView.setVisibility(View.GONE);
                        Toast.makeText(getActivity(), "No Internet Connection Available", Toast.LENGTH_LONG).show();
                    }
                }
            }, 100);
        }
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }

    private class MyBrowser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            webView.setVisibility(View.INVISIBLE);
        }

        public void onPageFinished(WebView view, String url) {
            // do your stuff here
            webView.setVisibility(View.VISIBLE);
        }
    }
}
