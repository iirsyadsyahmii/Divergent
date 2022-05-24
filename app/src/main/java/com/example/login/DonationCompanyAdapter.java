package com.example.login;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class DonationCompanyAdapter extends RecyclerView.Adapter<DonationCompanyAdapter.DesignViewHolder> {
    ArrayList<DonationCompany> CardviewDatas;


    //this will hold the Data
    public DonationCompanyAdapter(ArrayList<DonationCompany> CardviewDatas) {
        this.CardviewDatas = CardviewDatas;
    }

    @NonNull
    @Override
    public DesignViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.time_to_donation_cardview,parent,false);
        DesignViewHolder DesignViewHolder=new DesignViewHolder(view);

        return DesignViewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull DesignViewHolder holder, int position) {
        //main function to bind the design
        //pass down the position
        DonationCompany CardviewData= CardviewDatas.get(position);
        //set the image
        holder.iv_card.setImageResource(CardviewData.getCompanyImage());
        holder.tv_name.setText(CardviewData.getCompanyName());
        holder.tv_location.setText(CardviewData.getCompanyLocation());

    }

    @Override
    public int getItemCount() {
        return CardviewDatas.size();
    }

    //this will hold the View Design
    public static class DesignViewHolder extends RecyclerView.ViewHolder{
        ImageView iv_card;
        TextView tv_name;
        TextView tv_location;

        public DesignViewHolder(@NonNull View itemView) {
            super(itemView);

            //Hooks
            iv_card=itemView.findViewById(R.id.company_image);
            tv_name=itemView.findViewById(R.id.tw_reward_name);
            tv_location=itemView.findViewById(R.id.tw_desc);


        }
    }


}

