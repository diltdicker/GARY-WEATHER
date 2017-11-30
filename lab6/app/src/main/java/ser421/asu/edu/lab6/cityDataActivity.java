package ser421.asu.edu.lab6;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;


public class cityDataActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_data);

        /*Find the cityDataActivity ID and create an instance of WebViewClientCity
          to create "custom" web settings and make sure links are disabled*/
        WebView myWebView = (WebView) findViewById(R.id.wikiWebView);
        myWebView.setWebViewClient(new WebViewClientCity());
        //myWebView.setWebChromeClient(new WebChromeClient());

        WebSettings ws = myWebView.getSettings();
        ws.setJavaScriptEnabled(true);
        ws.setAllowUniversalAccessFromFileURLs(true);
        ws.setDomStorageEnabled(true);
        String cityName = getIntent().getExtras().getString("cityInfoStr");
        myWebView.loadUrl("http://www.city-data.com/cityw/" + cityName + ".html");
    }


    //Prevent any links from changing pages in the WebView for city-data.org
    private class WebViewClientCity extends WebViewClient {
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            //view.loadUrl(url);
            return true;
        }
    }
}