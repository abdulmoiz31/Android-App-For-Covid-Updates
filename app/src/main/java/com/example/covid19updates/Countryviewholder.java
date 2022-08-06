package com.example.covid19updates;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class Countryviewholder extends RecyclerView.ViewHolder{
    public TextView cname,ctotal, crec, cdeath;
    public LinearLayout layout;
    //Constructor
    public Countryviewholder(@NonNull View itemView) {
        super(itemView);

        cname =(TextView)itemView.findViewById(R.id.countryname);
        crec=(TextView)itemView.findViewById(R.id.countryrec);
        cdeath =(TextView)itemView.findViewById(R.id.countrydea);
        ctotal =(TextView)itemView.findViewById(R.id.countrytotal);
        layout=(LinearLayout)itemView.findViewById(R.id.furtherinfo);


    }
}
