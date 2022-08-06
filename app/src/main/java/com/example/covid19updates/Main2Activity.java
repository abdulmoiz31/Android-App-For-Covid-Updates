package com.example.covid19updates;

import android.content.Intent;

import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Main2Activity extends AppCompatActivity {
    private ImageView image;
    private RequestQueue queue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.datasplash);


        // initializing objects for splash screen
        image = (ImageView) findViewById(R.id.splash);
        //creating transition animation for splash
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.transition);
        //to prevent from restarting on orientation change
        if (savedInstanceState == null)
            image.startAnimation(animation);
        final Intent intent = new Intent(Main2Activity.this, MainActivity.class);
        /*Intent intent1=new Intent(Main2Activity.this,Dataservice.class);
        startService(intent1);*/

        //starting animation
        queue = Volley.newRequestQueue(this);
        String url = "https://covid-19-data.p.rapidapi.com/totals?format=undefined";

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {


                try {
                    JSONObject a = response.getJSONObject(0);

                    //Util.value++;
                    Util.confirmd = a.getString("deaths");
                    Util.confirmr = a.getString("recovered");
                    Util.confirm = a.getString("confirmed");
                    Util.confirmc = a.getString("critical");
                    startActivity(intent);
                    Main2Activity.this.finish();

                } catch (JSONException e) {
                    e.printStackTrace();
                }


                    /*MainActivity.t.setText("Confirmed cases: "+total);
                    MainActivity.r.setText("Recovered: "+rec);
                    MainActivity.d.setText("Deaths: "+dea);*/


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();

                Toast.makeText(Main2Activity.this, "Check Internet Connection", Toast.LENGTH_SHORT).show();
                //refresh.setRefreshing(false);
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {

                HashMap<String, String> headers = new HashMap<>();
                headers.put("x-rapidapi-host", "covid-19-data.p.rapidapi.com");
                headers.put("x-rapidapi-key", "aaba10cb3emshc19de4d004614eap156767jsn622918633cd4");

                return headers;
            }
        };

        queue.add(request);
        /*Thread timer=new Thread(){
            public void run(){
                try {
                    sleep(4000);
                } catch (InterruptedException e) {
                    Toast.makeText(Main2Activity.this,"Exception",Toast.LENGTH_SHORT).show();
                }
                finally {
                    startActivity(intent);
                    Intent intent1=new Intent(Main2Activity.this,Dataservice.class);

                    Main2Activity.this.finish();
                }
            }
        };
        timer.start();*/
    }


}
