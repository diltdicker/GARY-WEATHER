package edu.dickerson.dillon.lab6_part2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by diltd on 11/27/2017.
 */

public class WeatherActivity extends AppCompatActivity {

    private WebView secondView;

    @Override
    protected void onCreate(Bundle savedInstanceSate) {
        super.onCreate(savedInstanceSate);
        setContentView(R.layout.activity_weather);

        String name = getIntent().getStringExtra("selection");

        secondView = (WebView) findViewById(R.id.weatherView);
        secondView.setWebViewClient(new WebViewClient());
        secondView.setWebChromeClient(new WebChromeClient());
        secondView.setWebContentsDebuggingEnabled(true);
        WebSettings webSettings = secondView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setAllowUniversalAccessFromFileURLs(true);
        secondView.loadUrl(getString(R.string.weather2URL));
        secondView.addJavascriptInterface(new JavaScriptInterface(this.getApplicationContext()), "Android");
        Intent intent = new Intent();
        // These last 2 are to send values back to the original activity
        intent.putExtra("textToMain", "value_here");
        setResult(RESULT_OK, intent);

        Log.d("LogName", "Race condition Weather");
        if (name != null) {
            // closes this activity out before it shows up on the user's screen
            // evaluate JS

            finish();
        }
    }

    protected void insertCityValues (WebView view, String JSONList) {

    }
}
