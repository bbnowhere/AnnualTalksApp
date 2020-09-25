package in.res.ncbs.at;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
    Toolbar toolbar;
    RelativeLayout rlTab;
    WebView webView;

    ViewPagerAdapter viewPagerAdapter;
    ViewPager viewPager;
    TabLayout tabLayout;
    ImageView imgNoInternet;
BottomNavigationView bottomNavigationView;
ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        progressBar = (ProgressBar) findViewById(R.id.pbar);
        progressBar.setMax(100);
        webView = (WebView) findViewById(R.id.webView);
        rlTab = (RelativeLayout) findViewById(R.id.rlTab);
        imgNoInternet = (ImageView) findViewById(R.id.imgNoInternet);

       bottomNavigationView = (BottomNavigationView) findViewById(R.id.nav_view);


        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (!isNetworkConnected()) {
                    imgNoInternet.setVisibility(View.VISIBLE);
                    webView.setVisibility(View.GONE);
                    Toast.makeText(MainActivity.this, "No Internet Connection Available", Toast.LENGTH_LONG).show();
                    if (id == R.id.nav_schdeule) {
                        rlTab.setVisibility(View.VISIBLE);
                    }
                } else {
                    if (id == R.id.nav_schdeule) {
                        rlTab.setVisibility(View.VISIBLE);
                        webView.setVisibility(View.GONE);
                        MainActivity.this.progressBar.setProgress(0);
                    } else if (id == R.id.nav_live) {
                        webView.setVisibility(View.VISIBLE);
                        rlTab.setVisibility(View.GONE);
                        webView.loadUrl("https://www.ncbs.res.in/at2020/");
                        MainActivity.this.progressBar.setProgress(0);
                    } else if (id == R.id.nav_posters) {
                        webView.setVisibility(View.VISIBLE);
                        rlTab.setVisibility(View.GONE);
                      //  webView.loadUrl("https://www.ncbs.res.in/at2020/appposters");
                        webView.loadUrl("file:///android_asset/posters.html");

                        MainActivity.this.progressBar.setProgress(0);
                    }
                    else if (id == R.id.nav_userfullinks) {
                        webView.setVisibility(View.VISIBLE);
                        rlTab.setVisibility(View.GONE);
                       // webView.loadUrl("https://www.ncbs.res.in/at2020/node/49");
                        webView.loadUrl("file:///android_asset/usefullinks.html");
                        MainActivity.this.progressBar.setProgress(0);
                    }
                }

                return true;            }
        });

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

        imgNoInternet.setVisibility(View.GONE);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new MyBrowser());
         // webView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT); //Live Page can be loaded.
        // webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK); //Live Page will not load
    }

    public class ViewPagerAdapter extends FragmentPagerAdapter {

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;
            if (position == 0) {
                fragment = new day1("file:///android_asset/14.html");
            } else if (position == 1) {
                fragment = new day2("file:///android_asset/15.html");
            } else if (position == 2) {
                fragment = new day3("file:///android_asset/16.html");
            }
            else if (position == 3) {
                fragment = new day3("file:///android_asset/17.html");
            }
            return fragment;
        }

        @Override
        public int getCount() {
            return 4;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            String title = null;
            if (position == 0) {
                title = "14 JAN";
            } else if (position == 1) {
                title = "15 JAN";
            } else if (position == 2) {
                title = "16 JAN";
            }
            else if (position == 3) {
                title = "17 JAN";
            }
            return title;
        }
    }

    private class MyBrowser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {

            if (url.contains("tel:")) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse(url));
                startActivity(intent);
                return true;
            } else {
                view.loadUrl(url);
                return true;
            }
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            webView.setVisibility(View.INVISIBLE);
        }

        public void onPageFinished(WebView view, String url) {
            // do your stuff here
            webView.setVisibility(View.VISIBLE);
            MainActivity.this.progressBar.setProgress(100);
            progressBar.setVisibility(view.GONE);
        }
        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            super.onReceivedError(view, errorCode, description, failingUrl);
            view.loadUrl("about:blank");
            webView.loadUrl("file:///android_asset/error.html");        }
    }

    @Override
    public void onBackPressed() {

            super.onBackPressed();

    }


    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            switch (keyCode) {
                case KeyEvent.KEYCODE_BACK:
                    if (webView.canGoBack()) {
                        webView.goBack();
                    } else {
                        finish();
                    }
                    return true;
            }

        }
        return super.onKeyDown(keyCode, event);
    }

}
