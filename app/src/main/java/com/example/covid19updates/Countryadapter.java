package com.example.covid19updates;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


//class to display pantry items in Recyclerview
public class Countryadapter extends RecyclerView.Adapter<Countryviewholder> implements Filterable {
    //list of pantry items
    private List<Country> items;
    private Context context;
    List<Country> filtered;




    //constructor
    public Countryadapter(List<Country> items, Context context) {
        this.items = items;
        this.context = context;
        filtered=new ArrayList<>(items);
    }

    @NonNull
    @Override
    public Countryviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.recyleritem,parent,false);
        return new Countryviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Countryviewholder holder, final int position) {
        Country country=items.get(position);
        holder.cname.setText(country.getName());
        holder.cdeath.setText("Deaths: "+country.getDeaths());
        holder.crec.setText("Recovered: "+country.getRecovered());
        holder.ctotal.setText("Total cases: "+country.getTotal());
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context,Main3Activity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                Country country=items.get(position);
                intent.putExtra("name",country.getName());
                intent.putExtra("Total",country.getTotal());
                intent.putExtra("recovered",country.getRecovered());
                intent.putExtra("Deaths",country.getDeaths());
                intent.putExtra("total",country.getTconfirm());
                intent.putExtra("deaths",country.getTdeath());
                intent.putExtra("critical",country.getCritical());
                /*intent.putExtra("drate",country.getDeathrate());
                intent.putExtra("rrate",country.getRecoveryrate());
                intent.putExtra("rvsd",country.getRecovervsdeath());*/
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }
    Filter filter=new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<Country> filterelist=new ArrayList<>();
            if (charSequence.toString().isEmpty()){
                filterelist.addAll(filtered);
            }else {
                for (Country movie : filtered){
                    if (movie.getName().toLowerCase().contains(charSequence.toString().toLowerCase())){
                        filterelist.add(movie);
                    }
                }
            }
            FilterResults results=new FilterResults();
            results.values=filterelist;

            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            items.clear();
            items.addAll((Collection<? extends Country>) filterResults.values);
            notifyDataSetChanged();
        }
    };
    //returns whole view



}
