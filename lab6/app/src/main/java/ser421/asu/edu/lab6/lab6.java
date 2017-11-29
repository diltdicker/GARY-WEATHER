package ser421.asu.edu.lab6;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

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


        //So we're able to use the JS info buttons.  Once button is clicked
        // create an intent and new webview to send data to city-data
        myWebView.addJavascriptInterface(new Object() {
            @JavascriptInterface
            public void performClick(String cityName) {
                //Toast.makeText(lab6.this, cityName, Toast.LENGTH_LONG).show();
                Intent cityDataIntent = new Intent(lab6.this, cityDataActivity.class);
                cityDataIntent.putExtra("cityInfoStr", cityName);
                startActivity(cityDataIntent);

            }
        }, "androidInfo");

    }
}
