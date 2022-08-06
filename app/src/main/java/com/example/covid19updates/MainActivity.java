package com.example.covid19updates;


import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.navigation.NavigationView;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import androidx.core.view.GravityCompat;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;



public class MainActivity extends AppCompatActivity {
    private RequestQueue queue;
    private TextView t,r,d,c;
    int t1,r1,d1;
    private ArrayList<Country> array=new ArrayList<>();
    private RecyclerView recycler;
    private Countryadapter adapter;
    private SwipeRefreshLayout refresh;
    private DrawerLayout mlay;
    private ActionBarDrawerToggle mtog;
    private NavigationView nav;
    String total="";
    String rec="";
    String dea="";

     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
String[] names = getAssets().getLocales();
String integrated = "";
         for (int i = 0; i <names.length ; i++) {
             integrated+=names[i];
         }
         Toast.makeText(this,integrated,Toast.LENGTH_SHORT).show();
         queue=Volley.newRequestQueue(this);
         t=(TextView)findViewById(R.id.textView2);
         r=(TextView)findViewById(R.id.textView3);
         d=(TextView)findViewById(R.id.textView4);
         c=(TextView)findViewById(R.id.textView15);

             t.setText("Confirmed cases: " + Util.confirm);
             r.setText("Recovered: " + Util.confirmr);
             d.setText("Deaths: " + Util.confirmd);
             c.setText("Critical: " + Util.confirmc);

         get();
        recycler=(RecyclerView)findViewById(R.id.recyclerview);
        recycler.setHasFixedSize(true);
        recycler.setLayoutManager(new LinearLayoutManager(this));
         refresh=(SwipeRefreshLayout) findViewById(R.id.refresh);
         adapter=new Countryadapter(array,getApplicationContext());

         recycler.setAdapter(adapter);
         r1=0;
         t1=0;
         d1=0;

         nav=(NavigationView)findViewById(R.id.nav);
         nav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
             @Override
             public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                 if (menuItem.getItemId()==R.id.srtc){
                     Collections.sort(array, new Comparator<Country>() {
                         @Override
                         public int compare(Country country, Country t1) {
                             return Integer.valueOf(Integer.parseInt(t1.getTotal())).compareTo(Integer.valueOf(Integer.parseInt(country.getTotal())));
                         }
                     });
                     adapter=new Countryadapter(array,getApplicationContext());
                     recycler.setAdapter(adapter);
                 }
                 else if (menuItem.getItemId()==R.id.srtr){
                     Collections.sort(array, new Comparator<Country>() {
                         @Override
                         public int compare(Country country, Country t1) {
                             return Integer.valueOf(Integer.parseInt(t1.getRecovered())).compareTo(Integer.valueOf(Integer.parseInt(country.getRecovered())));
                         }
                     });
                     adapter=new Countryadapter(array,getApplicationContext());
                     recycler.setAdapter(adapter);
                 }
                 else if (menuItem.getItemId()==R.id.srtd){
                     Collections.sort(array, new Comparator<Country>() {
                         @Override
                         public int compare(Country country, Country t1) {
                             return Integer.valueOf(Integer.parseInt(t1.getDeaths())).compareTo(Integer.valueOf(Integer.parseInt(country.getDeaths())));
                         }
                     });
                     adapter=new Countryadapter(array,getApplicationContext());
                     recycler.setAdapter(adapter);
                 }
                 else if (menuItem.getItemId()==R.id.exit){
                     finishAffinity();
                 }
                 mlay.closeDrawer(GravityCompat.START);
                 return false;
             }
         });
         mlay=(DrawerLayout)findViewById(R.id.drawer);
         mtog=new ActionBarDrawerToggle(this,mlay,R.string.open,R.string.close);
         mlay.addDrawerListener(mtog);
         mtog.syncState();
         getSupportActionBar().setDisplayHomeAsUpEnabled(true);




         refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
             @Override
             public void onRefresh() {
                 fetchdata fetchdata=new fetchdata();
                 fetchdata.execute();
                 get();


             }
         });

parsejson();

    }
    private void get(){
         String url=" https://corona-api.com/countries";

         JsonObjectRequest request=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONArray a = response.getJSONArray("data");

                    array=new ArrayList<>();

                    for (int i = 0; i <a.length() ; i++) {


                        Country country=new Country();


                        JSONObject obj=a.getJSONObject(i);
                        country.setName(a.getJSONObject(i).getString("name"));
                        country.setDeaths(String.valueOf(a.getJSONObject(i).getJSONObject("latest_data").getInt("deaths")));
                        country.setRecovered(String.valueOf(a.getJSONObject(i).getJSONObject("latest_data").getInt("recovered")));
                        country.setTotal(String.valueOf(a.getJSONObject(i).getJSONObject("latest_data").getInt("confirmed")));
                        country.setTdeath(String.valueOf(a.getJSONObject(i).getJSONObject("today").getInt("deaths")));
                        country.setTconfirm(String.valueOf(a.getJSONObject(i).getJSONObject("today").getInt("confirmed")));
                        country.setCritical(String.valueOf(a.getJSONObject(i).getJSONObject("latest_data").getInt("critical")));




                        array.add(country);




                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                adapter=new Countryadapter(array,getApplicationContext());
                recycler.setAdapter(adapter);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                adapter=new Countryadapter(array,getApplicationContext());

                recycler.setAdapter(adapter);
                Toast.makeText(MainActivity.this,"Check Internet Connection",Toast.LENGTH_SHORT).show();
            }
        });

        queue.add(request);

    }



    private void getdat(){
        String url="https://api.covid19api.com/summary";
        JsonObjectRequest request=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONArray a = response.getJSONArray("Countries");
                    for (int i = 1; i <a.length() ; i++) {


                            Country country=new Country();


                            JSONObject obj=a.getJSONObject(i);
                            country.setName(obj.getString("Country"));
                            country.setDeaths(String.valueOf(obj.getInt("TotalDeaths")));
                            country.setRecovered(String.valueOf(obj.getInt("TotalRecovered")));
                            country.setTotal(String.valueOf(obj.getInt("TotalConfirmed")));
                            array.add(country);
                            d1+=obj.getInt("TotalDeaths");
                            r1+=obj.getInt("TotalRecovered");
                            t1+=obj.getInt("TotalConfirmed");



                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


              /*  t.setText("Confirmed cases: "+String.valueOf(t1));
                r.setText("Recovered: "+String.valueOf(r1));
                d.setText("Deaths: "+String.valueOf(d1));*/
                refresh.setRefreshing(false);

                adapter=new Countryadapter(array,getApplicationContext());
                recycler.setAdapter(adapter);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                adapter=new Countryadapter(array,getApplicationContext());
                recycler.setAdapter(adapter);
                Toast.makeText(MainActivity.this,"Check Internet Connection",Toast.LENGTH_SHORT).show();

            }
        });

        queue.add(request);
    }

    private void parsejson(){
        String url="https://pomber.github.io/covid19/timeseries.json";
        JsonObjectRequest request=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Util.odj=response;

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();

            }
        });

        queue.add(request);
    }
    private void geting(){
         String url="https://covid-19-data.p.rapidapi.com/totals?format=undefined";

        JsonArrayRequest request=new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {
                String total="";
                String rec="";
                String dea="";

                try {
                    JSONObject a = response.getJSONObject(0);





                        dea=a.getString("deaths");
                        rec=a.getString("recovered");
                        total=a.getString("confirmed");





                } catch (JSONException e) {
                    e.printStackTrace();
                }

                t.setText("Confirmed cases: "+total);
                r.setText("Recovered: "+rec);
                d.setText("Deaths: "+dea);

                refresh.setRefreshing(false);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                adapter=new Countryadapter(array,getApplicationContext());
                //adapter.setOnItemClickListener(MainActivity.this);
                recycler.setAdapter(adapter);
                Toast.makeText(MainActivity.this,"Check Internet Connection",Toast.LENGTH_SHORT).show();
                refresh.setRefreshing(false);
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers=new HashMap<>();
                headers.put("x-rapidapi-host", "covid-19-data.p.rapidapi.com");
                headers.put("x-rapidapi-key", "aaba10cb3emshc19de4d004614eap156767jsn622918633cd4");

                return headers;
            }
        };

        queue.add(request);




     }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main,menu);

        MenuItem item=menu.findItem(R.id.action_search);
        SearchView searchView=(SearchView)item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });


        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public void onBackPressed() {
        if (mlay.isDrawerOpen(GravityCompat.START)){
            mlay.closeDrawer(GravityCompat.START);
        }else {
            super.onBackPressed();
        }
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (mtog.onOptionsItemSelected(item)){

            return true;

        }
        return super.onOptionsItemSelected(item);

    }
    class fetchdata extends AsyncTask<Void,Void,Void> {

        @Override
        protected Void doInBackground(Void... voids) {

            String url = "https://covid-19-data.p.rapidapi.com/totals?format=undefined";

            JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {

                @Override
                public void onResponse(JSONArray response) {


                    try {
                        JSONObject a = response.getJSONObject(0);


                        dea = a.getString("deaths");
                        rec = a.getString("recovered");
                        total = a.getString("confirmed");

                        t.setText("Confirmed cases: " + total);
                        r.setText("Recovered: " + rec);
                        d.setText("Deaths: " + dea);
                        c.setText("Critical: " + a.getString("critical"));

                        refresh.setRefreshing(false);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }





                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();

                    Toast.makeText(MainActivity.this, "Check Internet Connection", Toast.LENGTH_SHORT).show();
                    refresh.setRefreshing(false);
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

            return null;
        }

        }


    }

