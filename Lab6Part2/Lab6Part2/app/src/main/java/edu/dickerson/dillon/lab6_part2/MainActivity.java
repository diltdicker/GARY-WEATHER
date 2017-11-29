package edu.dickerson.dillon.lab6_part2;

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

public class MainActivity extends AppCompatActivity {

    private WebView firstview;
    private Button buttonChange;
    private Button buttonUpdateWeather;
    static String selectionChoice="FINISH";
    static JSONArray selectedCities;

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
        firstview.loadUrl(getString(R.string.choiceURL));
        firstview.addJavascriptInterface(new JavaScriptInterface(this.getApplicationContext()), "Android");

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
                        try {
                            String test = citiesJSON;//"{\"cities\":[]}";
                            JSONObject cityObj = new JSONObject(citiesJSON);
                            //JSONArray tmpArray = cityObj.getJSONArray("cities");

                        } catch (org.json.JSONException e) {
                            Log.d("LogName", "JSON Error");
                            e.printStackTrace();
                        }
                        Log.d("LogName", citiesJSON);

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
    public void test (String text) {
        Log.d("LogName", text);
    }

    public void updateCities (String citiesJSON) {
        try {
            JSONObject cityObj = new JSONObject(citiesJSON);
            MainActivity.selectedCities = cityObj.getJSONArray("cities");

        } catch (org.json.JSONException e) {
            Log.d("LogName", "JSON Error");
        }
    }

    public void validateSelection(int val) {
        if (val != 5) {
            // toastmessage
        } else {
            // send the all clear to go to the webpage
        }
    }
}