package com.example.login.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.login.MainActivity;
import com.example.login.MainMenu;
import com.example.login.R;

import java.util.List;

public class MenuRecyclerViewAdapter extends RecyclerView.Adapter<MenuRecyclerViewAdapter.MenuViewHolder> {

    public List<MainMenu> menuList;
    private Context context;

    public MenuRecyclerViewAdapter(Context context, List<MainMenu> menuList) {
        this.context = context;
        this.menuList = menuList;
    }

    @NonNull
    @Override
    public MenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View menu_row = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_row, null);
        MenuViewHolder menuVH = new MenuViewHolder(menu_row);

        return menuVH;
    }

    @Override
    public void onBindViewHolder(@NonNull MenuViewHolder holder, int position) {
        holder.tvMenuName.setText(menuList.get(position).getName());
        holder.imgViewMenuName.setImageResource(menuList.get(position).getImage());
        //holder.cardView.setBackground(menuList.get(position).getImage());

        holder.imgViewMenuName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = null;
//                try {
//                    intent = new Intent(v.getContext(),Class.forName(menuList.get(position).getName()));
//                } catch (ClassNotFoundException e) {
//                    e.printStackTrace();
//                }
                Intent intent = new Intent(v.getContext(), menuList.get(position).getActivityName());
//                try {
//                    System.out.println(Class.forName(menuList.get(position).getName()));
//                } catch (ClassNotFoundException e) {
//                    e.printStackTrace();
//                }
               // Toast.makeText(v.getContext(),menuList.get(position).getName(), Toast.LENGTH_SHORT).show();
                v.getContext().startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return menuList.size();
    }

    public class MenuViewHolder extends RecyclerView.ViewHolder {

        public TextView tvMenuName;
        public ImageView imgViewMenuName;
        public CardView cardView;
        public View v;

        public MenuViewHolder(@NonNull View itemView) {
            super(itemView);
            v=itemView;
            tvMenuName = itemView.findViewById(R.id.tv_menu_name);
            imgViewMenuName = itemView.findViewById(R.id.img_menu);
            cardView = itemView.findViewById(R.id.cardview);

//            imgViewMenuName.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
////                    Intent intent = new Intent(itemView.getContext(), MainMenu.class);
////
////                    itemView.getContext().startActivity(new Intent(itemView.getContext(), MainMenu.class));
//                }
//            });
        }

    }




}
