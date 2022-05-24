package com.example.login.Companyadapter;

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

import com.example.login.CompanyMainMenu;
import com.example.login.R;

import java.util.List;

public class CompanyRecyclerViewAdapter extends RecyclerView.Adapter <CompanyRecyclerViewAdapter.CompanyRecyclerViewHolder> {

    public List<CompanyMainMenu> companyList;
    private Context context;

    public CompanyRecyclerViewAdapter(Context context, List<CompanyMainMenu> companyList) {
        this.context=context;
        this.companyList=companyList;
    }

    @NonNull
    @Override
    public CompanyRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View company_row = LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_row,null);
        CompanyRecyclerViewHolder companyVH=new CompanyRecyclerViewHolder(company_row );
        return companyVH ;
    }

    @Override
    public void onBindViewHolder(@NonNull CompanyRecyclerViewHolder holder, int position) {
        holder.tvMenuName.setText(companyList.get(position).getName());
        holder.imgViewMenuImage.setImageResource(companyList.get(position).getImage());

        holder.imgViewMenuImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = null;
//                try {
//                    intent = new Intent(v.getContext(),Class.forName(menuList.get(position).getName()));
//                } catch (ClassNotFoundException e) {
//                    e.printStackTrace();
//                }
                Intent intent = new Intent(v.getContext(), companyList.get(position).getActivityName());
                try {
                    System.out.println(Class.forName(companyList.get(position).getName()));
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                Toast.makeText(v.getContext(),companyList.get(position).getName(), Toast.LENGTH_SHORT).show();
                v.getContext().startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return companyList.size();
    }

    public class CompanyRecyclerViewHolder extends RecyclerView.ViewHolder{
        public TextView tvMenuName;
        public ImageView imgViewMenuImage;

        public CompanyRecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMenuName=itemView.findViewById(R.id.tv_admin_main);
            imgViewMenuImage=itemView.findViewById(R.id.img_admin);
        }
    }
}
