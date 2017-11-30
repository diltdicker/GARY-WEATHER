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
import android.widget.Toast;

import org.json.*;

import java.util.Arrays;


/*
    Note Weird Bug found when you tilt the phone on its side: it resets the app
 */
public class MainActivity extends AppCompatActivity {

    private WebView firstview;
    private Button buttonChange;
    private Button buttonUpdateWeather;
    static String current = "Mesa";
    //var list = ["Mesa", "Tuscon", "Miami", "Cheyenne", "Nome", "Dallas", "Anaheim", "Tallahassee", "Austin", "Juneau"];
    static String selectionChoice="Mesa,Tuscon,Miami,Cheyenne,Nome";

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
                            // empty string array
                            Toast.makeText(MainActivity.this, (String) "Empty Selection\nYou need to Select 5 cities", Toast.LENGTH_SHORT).show();
                        } else if (list.length > 5 || list.length < 5) {
                            // too many selected
                            Toast.makeText(MainActivity.this, (String) "You need to Select 5 cities", Toast.LENGTH_SHORT).show();
                        } else {
                            // it all right
                            boolean flag = false;
                            for (int i = 0; i < list.length; i++) {
                                if (list[i].compareTo(current) == 0) {
                                    Log.d("LogCompare", list[i] + " vs " + current);
                                    flag = true;
                                }
                            }
                            if (flag) {
                                // update the selection
                                selectionChoice = citiesJSON;
                            } else {
                                // didn't contain selected
                                Toast.makeText(MainActivity.this, (String) ("One of your choices needs to be: " + current), Toast.LENGTH_SHORT).show();
                            }
                        }

                    }
                });
            }
        });

        buttonUpdateWeather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // open second activity webview for user

                Intent WeatherIntent = new Intent(MainActivity.this, WeatherActivity.class);
                WeatherIntent.putExtra("selection", selectionChoice);
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
    public void updateCurrent(String newCurrent) {
        MainActivity.current = newCurrent;
        Log.d("LogName", MainActivity.current);
    }

    @JavascriptInterface
    public String grabCityList(String tmp) {
        tmp = "";
        for (int i =0 ; i <  WeatherActivity.cityList.length; i++) {
            tmp += "" + WeatherActivity.cityList[i];
            if (i != WeatherActivity.cityList.length - 1 ) {
                tmp += ",";
            }
        }

        Log.d("LogName", tmp);
        return tmp;
    }
}