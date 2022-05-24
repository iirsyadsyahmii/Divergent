package com.example.login.RestaurantAdapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.login.R;
import com.example.login.RestaurantMainMenu;

import java.util.List;

public class RestaurantRecyclerViewAdapter extends RecyclerView.Adapter<RestaurantRecyclerViewAdapter.RestaurantViewHolder  > {
    List<RestaurantMainMenu> restaurantList;
    private Context context;

    public RestaurantRecyclerViewAdapter(Context context, List<RestaurantMainMenu> restaurantList) {
        this.context=context;
        this.restaurantList=restaurantList;
    }

    @NonNull
    @Override
    public RestaurantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View restaurant_row = LayoutInflater.from(parent.getContext()).inflate(R.layout.restaurant_row, null);
        RestaurantViewHolder restaurantVH = new RestaurantViewHolder(restaurant_row);
        return restaurantVH;
    }

    @Override
    public void onBindViewHolder(@NonNull RestaurantViewHolder holder, int position) {
        holder.tvRestaurantName.setText(restaurantList.get(position).getName());
        holder.imgViewRestaurantImage.setImageResource(restaurantList.get(position).getImage());

        holder.imgViewRestaurantImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = null;
//                try {
//                    intent = new Intent(v.getContext(),Class.forName(menuList.get(position).getName()));
//                } catch (ClassNotFoundException e) {
//                    e.printStackTrace();
//                }
                Intent intent = new Intent(v.getContext(), restaurantList.get(position).getActivityName());
                try {
                    System.out.println(Class.forName(restaurantList.get(position).getName()));
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                 Toast.makeText(v.getContext(),restaurantList.get(position).getName(), Toast.LENGTH_SHORT).show();
                v.getContext().startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return restaurantList.size();
    }

    public class RestaurantViewHolder extends RecyclerView.ViewHolder{

        public TextView tvRestaurantName;
        public ImageView imgViewRestaurantImage;

        public RestaurantViewHolder(@NonNull View itemView) {
            super(itemView);
            tvRestaurantName = itemView.findViewById(R.id.tv_restaurant_main);
            imgViewRestaurantImage = itemView.findViewById(R.id.img_restaurant);
        }
    }
}
