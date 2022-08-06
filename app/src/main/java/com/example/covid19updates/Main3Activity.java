package com.example.covid19updates;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;

public class Main3Activity extends AppCompatActivity {
    private TextView name,Total,Deaths,recovered,total,deaths,drate,rrate,rvsd,critical;
    private RequestQueue queue;
    JSONArray arm;
    ArrayList<String> x=new ArrayList<>();
    ArrayList<Entry> t=new ArrayList<>();
    ArrayList<Entry> r=new ArrayList<>();
    ArrayList<Entry> d=new ArrayList<>();


    ArrayList<String> x1=new ArrayList<>();
    ArrayList<Entry> t1=new ArrayList<>();
    ArrayList<Entry> r1=new ArrayList<>();
    ArrayList<Entry> d1=new ArrayList<>();
    String str = "";
   public static LineChart chart,chart1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        chart=(LineChart)findViewById(R.id.chart);
        chart1=(LineChart)findViewById(R.id.chart1);
        queue= Volley.newRequestQueue(this);

        Bundle bundle = getIntent().getExtras();
        str=bundle.getString("name");
        Mydownload mydownload = new Mydownload();
        mydownload.execute();


        name=(TextView)findViewById(R.id.textView);
        name.setText("Name: "+str);

        Total=(TextView)findViewById(R.id.textView6);
        recovered=(TextView)findViewById(R.id.textView13);
        Deaths=(TextView)findViewById(R.id.textView5);
        critical=(TextView)findViewById(R.id.textView12);
        rrate=(TextView)findViewById(R.id.textView11);
        drate=(TextView)findViewById(R.id.textView10);
        rvsd=(TextView)findViewById(R.id.textView9);
        total=(TextView)findViewById(R.id.textView7);
        deaths=(TextView)findViewById(R.id.textView14);


        Total.setText("Confirmed: "+bundle.getString("Total"));
        Deaths.setText("Deaths: "+bundle.getString("Deaths"));
        recovered.setText("Recovered: "+bundle.getString("recovered"));
        total.setText("Confirmed: "+bundle.getString("total"));
        deaths.setText("Deaths: "+bundle.getString("deaths"));
        critical.setText("Critical: "+bundle.getString("critical"));

        if (Double.parseDouble(bundle.getString("Total"))>0){
            drate.setText("Death Rate: "+String.format("%.2f",(Double.parseDouble(bundle.getString("Deaths"))/Double.parseDouble(bundle.getString("Total")))*100));
            rrate.setText("Recovery Rate: "+String.format("%.2f",(Double.parseDouble(bundle.getString("recovered"))/Double.parseDouble(bundle.getString("Total")))*100));
            drate.append("%");
            rrate.append("%");
        }
        else {
            drate.setText("Death Rate: 0");
            rrate.setText("Recovery Rate: 0");
        }
        if (Double.parseDouble(bundle.getString("recovered"))>0){
            rvsd.setText("Death vs Recovery: "+String.format("%.2f",(Double.parseDouble(bundle.getString("Deaths"))/(Double.parseDouble(bundle.getString("Deaths"))+Double.parseDouble(bundle.getString("recovered"))))*100));
            rvsd.append("%");
        }else {
            rvsd.setText("Death vs Recovery: 0");
        }





    }

    class Mydownload extends AsyncTask<Void,Void,Void> {
        ArrayList<ILineDataSet> lineDataSets;
        String[] xaxes=new String[0];
        ArrayList<ILineDataSet> lineDataSets1;
        String[] xaxes1=new String[0];
        @Override
        protected Void doInBackground(Void... voids) {

            JSONArray a=Util.odj.names();
            //Toast.makeText(Main3Activity.this,name.getText().toString().substring(6),Toast.LENGTH_SHORT).show();


            for (int i = 0; i <a.length() ; i++) {
                try {
                    if (Util.odj.has(a.get(i).toString())){
                        try {
                            //Toast.makeText(Main3Activity.this,name.getText().toString().substring(6),Toast.LENGTH_SHORT).show();

                            if (a.get(i).toString().equalsIgnoreCase(str)){
                                JSONArray arm=Util.odj.getJSONArray(a.get(i).toString());
                                for (int j = 0; j <arm.length() ; j++) {
                                    JSONObject obj=arm.getJSONObject(j);
                                    int l=obj.getInt("confirmed");
                                    int m=obj.getInt("deaths");
                                    int n=obj.getInt("recovered");

                                    x.add(j,obj.getString("date"));
                                    t.add(new Entry(l,j));
                                    d.add(new Entry(m,j));
                                    r.add(new Entry(n,j));
                                    if (j>0){
                                        JSONObject obj1=arm.getJSONObject(j-1);
                                        l-=obj1.getInt("confirmed");
                                        m-=obj1.getInt("deaths");
                                        n-=obj1.getInt("recovered");
                                    }

                                    x1.add(j,obj.getString("date"));
                                    t1.add(new Entry(l,j));
                                    d1.add(new Entry(m,j));
                                    r1.add(new Entry(n,j));

                                }

                            }


                            // array.add(country);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            if (!x.isEmpty()){
                xaxes=new String[x.size()];
                xaxes1=new String[x1.size()];
                for (int i = 0; i <x.size() ; i++) {
                    xaxes[i]=x.get(i).toString();
                    xaxes1[i]=x1.get(i).toString();
                }
                lineDataSets=new ArrayList<>();
                LineDataSet lcon=new LineDataSet(t,"Confirmed");
                lcon.setDrawCircles(false);
                lcon.setColor(Color.BLACK);

                LineDataSet lrec=new LineDataSet(r,"Recovered");
                lrec.setDrawCircles(false);
                lrec.setColor(Color.GREEN);

                LineDataSet ldea=new LineDataSet(d,"Deaths");
                ldea.setDrawCircles(false);
                ldea.setColor(Color.RED);

                lineDataSets.add(lcon);
                lineDataSets.add(lrec);
                lineDataSets.add(ldea);

                lineDataSets1=new ArrayList<>();
                LineDataSet lcon1=new LineDataSet(t1,"Confirmed");
                lcon1.setDrawCircles(false);
                lcon1.setColor(Color.BLACK);

                LineDataSet lrec1=new LineDataSet(r1,"Recovered");
                lrec1.setDrawCircles(false);
                lrec1.setColor(Color.GREEN);

                LineDataSet ldea1=new LineDataSet(d1,"Deaths");
                ldea1.setDrawCircles(false);
                ldea1.setColor(Color.RED);

                lineDataSets1.add(lcon1);
                lineDataSets1.add(lrec1);
                lineDataSets1.add(ldea1);

            }










            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            Main3Activity.chart.setData(new LineData(xaxes,lineDataSets));
            Main3Activity.chart.setActivated(true);
            Main3Activity.chart1.setData(new LineData(xaxes1,lineDataSets1));
            Main3Activity.chart1.setActivated(true);
            super.onPostExecute(aVoid);
        }
    }

}
