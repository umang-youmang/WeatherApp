package com.audiocabs.myweatherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    TextView textView;
    TextView textView2;
    EditText editText;
    RequestQueue requestQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText=findViewById(R.id.city);
        textView=findViewById(R.id.result);
        requestQueue= Volley.newRequestQueue(this);
        textView2=findViewById(R.id.result2);

    }

    public void getit(View view)
    {
        String urlbase="https://api.openweathermap.org/data/2.5/weather?q=";
        String cityname=editText.getText().toString();
        String key="&appid=139c4f2882f0aead36efd1d2bcfc9d34";
         String url=urlbase+cityname;
        url=url+key;
        Log.i("hello", url);
        JsonObjectRequest request=new JsonObjectRequest(Request.Method.GET,url , null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray weather= response.getJSONArray("weather");
                    JSONObject obj=weather.getJSONObject(0);
                    JSONObject main=response.getJSONObject("main");
                    double mintemp=main.getDouble("temp_min");
                    double maxtemp=main.getDouble("temp_max");
                    double humidity=main.getDouble("humidity");

                    String des=obj.getString("description");
                    String ans=obj.getString("main");
                    textView2.setText("Min Temp:"+String.valueOf(mintemp)+"\n"+"Max Temp:"+String.valueOf(maxtemp)+"\n Humidity:"+ String.valueOf(humidity));
                    textView.setText("\n"+ ans);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
                , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                textView.setText("go1");
            }
        });
        requestQueue.add(request);
    }
}
