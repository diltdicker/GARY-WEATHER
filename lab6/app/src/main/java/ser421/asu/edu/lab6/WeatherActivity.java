package ser421.asu.edu.lab6;


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
    static String[] cityList = new String[5];

    @Override
    protected void onCreate(Bundle savedInstanceSate) {
        super.onCreate(savedInstanceSate);
        setContentView(R.layout.activity_weather);
        cityList[0] = "1";
        cityList[1] = "2";
        cityList[2] = "3";
        cityList[3] = "4";
        cityList[4] = "5";
        String listOfCities = getIntent().getStringExtra("selection");
        cityList = listOfCities.split(",");
        secondView = (WebView) findViewById(R.id.weatherView);

        secondView.setWebViewClient(new WebViewClient());
        secondView.setWebChromeClient(new WebChromeClient());
        secondView.setWebContentsDebuggingEnabled(true);
        WebSettings webSettings = secondView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setAllowUniversalAccessFromFileURLs(true);
        secondView.addJavascriptInterface(new JavaScriptInterface(this.getApplicationContext()), "Android");
        secondView.loadUrl(getString(R.string.weather2URL));

        Intent intent = new Intent();
        // These last 2 are to send values back to the original activity
        intent.putExtra("textToMain", "value_here");
        setResult(RESULT_OK, intent);

        Log.d("LogName", "Race condition Weather");
        /*if (listOfCities != null) {
            // closes this activity out before it shows up on the user's screen
            // evaluate JS

            finish();
        }*/

        //So we're able to use the JS info buttons.  Once button is clicked
        // create an intent and new webview to send data to city-data
        secondView.addJavascriptInterface(new Object() {
            @JavascriptInterface
            public void performClick(String cityName) {
                //Toast.makeText(lab6.this, cityName, Toast.LENGTH_LONG).show();
                Intent cityDataIntent = new Intent(WeatherActivity.this, cityDataActivity.class);
                cityDataIntent.putExtra("cityInfoStr", cityName);
                startActivity(cityDataIntent);

            }
        }, "androidInfo");

    }
}
