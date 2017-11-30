package ser421.asu.edu.lab6;


import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.ValueCallback;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import org.json.*;

import java.util.Arrays;


/*
    Note Weird Bug found when you tilt the phone on its side: it resets the app
 */
public class MainActivity extends AppCompatActivity {

    private WebView firstview;
    private Button buttonChange;
    private Button buttonUpdateWeather;
    static String current;
    static String selectionChoice="FINISH";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonChange = (Button) findViewById(R.id.buttonChange);
        buttonUpdateWeather = (Button) findViewById(R.id.buttonShowWeather);
        firstview = (WebView) findViewById(R.id.webView);
        firstview.setWebContentsDebuggingEnabled(true);
        WebSettings webSettings = firstview.getSettings();
        webSettings.setJavaScriptEnabled(true);

        firstview.addJavascriptInterface(new JavaScriptInterface(this.getApplicationContext()), "Android");
        firstview.loadUrl(getString(R.string.choiceURL));
        selectionChoice = "NewYork";
        buttonChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //validates changes and sends them to second activity
                //Intent WeatherIntent = new Intent(MainActivity.this, WeatherActivity.class);
                //WeatherIntent.putExtra("selection", selectionChoice);
                //startActivity(WeatherIntent);
                firstview.evaluateJavascript("grabSelected();", new ValueCallback<String>() {

                    @Override
                    public void onReceiveValue(String citiesJSON) {
                        citiesJSON = citiesJSON.substring(1, citiesJSON.length()-1);
                        String[] list = citiesJSON.split(",");
                        if (list[0].compareTo("") == 0) {
                            // empty string
                        } else  {
                            selectionChoice = "Phoenix";
                        }
                        Log.d("LogName","Dallas = " + list[0]);

                    }
                });
                Log.d("LogName", "Race condition Main");
            }
        });

        buttonUpdateWeather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // open second activity webview for user

                Intent WeatherIntent = new Intent(MainActivity.this, WeatherActivity.class);
                startActivityForResult(WeatherIntent, 1);
            }
        });
    }

    @Override
    public void onActivityResult(int mainCode, int returnCode, Intent intent) {
        super.onActivityResult(mainCode, returnCode, intent);
        if (mainCode == 1) {
            if(returnCode == RESULT_OK) {
                String returnText = intent.getStringExtra("textToMain");
                // Logs data that came from second activity (after you press the back button)
                Log.d("LogName", returnText);
                Log.d("LogName", selectionChoice);
                // the important thing we need to get here is the selected city on the second activity
            }
        }
    }
}

class JavaScriptInterface {
    private Context mainContext;
    private JSONArray citiesList;

    JavaScriptInterface(Context context) {
        mainContext = context;
    }

    @JavascriptInterface
    public void test(String text) {
        Log.d("LogName", text);
    }

    @JavascriptInterface
    public String grabCityList(String tmp) {
        return WeatherActivity.cityList.toString();
    }
}