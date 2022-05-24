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

import java.util.List;

public class YourRewardAdapter extends RecyclerView.Adapter<YourRewardAdapter.ViewHolder> {
    private Context context;
    private List<VoucherClass> voucherlist;

    public YourRewardAdapter (Context context,List<VoucherClass>voucherlist){
        this.context=context;
        this.voucherlist=voucherlist;

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.yourreward_cardview,viewGroup,false));

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {

        holder.textview1.setText(voucherlist.get(i).getRestaurant());
        holder.textview2.setText(voucherlist.get(i).getOffer());
        holder.textview3.setText(voucherlist.get(i).getDescription());
        Glide.with(context).load(voucherlist.get(i).getImage()).into(holder.imageview1);
        // Picasso.get().load(voucherlist.get(i). getImageview1()).into(holder.imageview1);
        holder.btndetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, VoucherDetails.class);
                Intent intent2 = new Intent (context,VoucherQR.class);

                intent.putExtra("detail",voucherlist.get(i));
                intent2.putExtra("detail2",voucherlist.get(i));
                context.startActivity(intent);
            }
        });


    }

    public int getItemCount() {
        return voucherlist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imageview1;
        TextView textview1;
        TextView textview2,textview3;
        Button btndetails;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageview1 =itemView.findViewById(R.id.imageViewUploaded);
            textview1 =itemView.findViewById(R.id.tvremark);
            textview2 =itemView.findViewById(R.id.tvweight);
            textview3 =itemView.findViewById(R.id.tvpoint);
            btndetails=itemView.findViewById(R.id.btn_details);

        }

        /*public void setData(int resource, String vouchername, String voucherdesc) {
        imageview1.setImageResource(resource);
        textview1.setText(vouchername);
            textview2.setText(voucherdesc);


        }*/
    }
}
