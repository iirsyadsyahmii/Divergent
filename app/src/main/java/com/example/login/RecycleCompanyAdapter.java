package com.example.login;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class RecycleCompanyAdapter extends RecyclerView.Adapter<RecycleCompanyAdapter.ViewHolder> {
    private Context context;
    private List<RecycleCompany> companylist;

    //this will hold the Data
    public RecycleCompanyAdapter(Context context, List<RecycleCompany> companylist) {

        this.context=context;
        this.companylist=companylist;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.time_to_recycle_cardview,parent,false));

    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {

        holder.compname.setText(companylist.get(i).getName());
        holder.comploc.setText(companylist.get(i).getLocation());
        Glide.with(context).load(companylist.get(i).getImage()).into(holder.compimage);
        holder.compimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, company_details.class);
                intent.putExtra("compdetail",companylist.get(i));
                context.startActivity(intent);
            }
        });

    }

    public int getItemCount() {
        return companylist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView compimage;
        TextView compname;
        TextView comploc;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            compimage = itemView.findViewById(R.id.company_image);
            compname = itemView.findViewById(R.id.tw_reward_name);
            comploc = itemView.findViewById(R.id.tw_desc);


        }

//    public int getItemCount() {
//        return CardviewDatas.size();
//    }
//
//    //this will hold the View Design
//    public static class DesignViewHolder extends RecyclerView.ViewHolder{
//        ImageView compimage;
//        TextView compname;
//        TextView compdesc;
//        TextView complocation;
//
//        public DesignViewHolder(@NonNull View itemView) {
//            super(itemView);
//
//            //Hooks
//            compimage=itemView.findViewById(R.id.compimage);
//            compname=itemView.findViewById(R.id.compname);
//            compdesc=itemView.findViewById(R.id.compdesc);
//            complocation=itemView.findViewById(R.id.complocation);
//
//
//        }
//    }

    }
}
