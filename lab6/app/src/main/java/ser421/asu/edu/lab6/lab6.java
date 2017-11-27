package ser421.asu.edu.lab6;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class lab6 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab6);

        WebView myWebView = findViewById(R.id.lab5WebView);
        myWebView.setWebViewClient(new WebViewClient());

        WebSettings ws = myWebView.getSettings();
        ws.setJavaScriptEnabled(true);
        ws.setAllowUniversalAccessFromFileURLs(true);
        ws.setDomStorageEnabled(true);

        myWebView.loadUrl("file:///android_asset/www/lab5_solution.html");
    }
}
