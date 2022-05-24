package com.example.login;

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

import java.util.List;

import android.content.Context;

public class RewardForYouAdapter extends RecyclerView.Adapter<RewardForYouAdapter.ViewHolder> {
    private Context vcontext;
    private List<RewardClass> voucher4ulist;

    public RewardForYouAdapter (Context vcontext, List<RewardClass>voucher4ulist){
        this.vcontext = vcontext;
        this.voucher4ulist=voucher4ulist;}


    @NonNull
    @Override
    public  RewardForYouAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.reward4u,viewGroup,false));

    }



    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {

        holder.textview1.setText(voucher4ulist.get(i).getRestaurant());
        holder.textview2.setText(voucher4ulist.get(i).getOffer());
        holder.textview3.setText(voucher4ulist.get(i).getPoints());
        Glide.with(vcontext).load(voucher4ulist.get(i).getImage()).into(holder.imageview1);

        holder.btndetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(vcontext, Voucher4uDetails.class);
                intent.putExtra("vdetails",voucher4ulist.get(i));

                vcontext.startActivity(intent);
            }
        });

    }

    public int getItemCount() {
        return voucher4ulist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imageview1;
        TextView textview1;
        TextView textview2;
        TextView textview3;
        Button btndetails;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageview1 =itemView.findViewById(R.id.imageViewUploaded);
            textview1 =itemView.findViewById(R.id.tvremark);
            textview2 =itemView.findViewById(R.id.tvweight);
            textview3 =itemView.findViewById(R.id.points);
            btndetails=itemView.findViewById(R.id.btn_details);

        }

//        public void setData(int resource, String vouchername, String voucherdesc,String voucherpoint) {
//            imageview1.setImageResource(resource);
//            textview1.setText(vouchername);
//            textview2.setText(voucherdesc);
//            textview3.setText(voucherpoint);
//
//
//        }
    }
}
