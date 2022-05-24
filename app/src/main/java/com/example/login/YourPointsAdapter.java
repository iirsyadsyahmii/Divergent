package com.example.login;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class YourPointsAdapter extends RecyclerView.Adapter<YourPointsAdapter.ViewHolder>   {
    private Context context;
    private List<YourPointClass> historylist;
    FirebaseFirestore fstore;
    FirebaseAuth auth;
    public YourPointsAdapter(Context context,List<YourPointClass> historylist) {
        this.context=context;
        this.historylist=historylist;
        fstore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        return new YourPointsAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.user_history,parent,false));
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {

        holder.textview1.setText(historylist.get(i).getRemark());
        holder.textview2.setText(historylist.get(i).getWeight());
        holder.textview3.setText(historylist.get(i).getPoints());
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Are you sure you want to delete this record?")
                    .setMessage("Deletion is permanent...");

            builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    fstore.collection("Users").document(auth.getCurrentUser().getUid()).collection("History").document(historylist.get(i).getDocumentId()).delete()
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    if(task.isSuccessful()){
                                        historylist.remove(historylist.get(i));
                                        notifyDataSetChanged();
                                        Toast.makeText(context,"Record Deleted",Toast.LENGTH_SHORT).show();
                                    }
                                    else {
                                        Toast.makeText(context,"Error" +task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
            builder.create().show();

            }
        });
//        holder.iv_card.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(v.getContext(), CardviewDatas.get(i).getActivityName());
//                v.getContext().startActivity(intent);
//            }
//        });
    }

    public int getItemCount() {
        return historylist.size();
    }

    //this will hold the View Design
    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView textview1;
        TextView textview2,textview3;
        ImageView delete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            //Hooks
            textview1=itemView.findViewById(R.id.tvremark);
            textview2=itemView.findViewById(R.id.tvweight);
            textview3=itemView.findViewById(R.id.tvpoint);
            delete = itemView.findViewById(R.id.delete);

        }

//        public void setData (int companyImage, String companyName, String companyLocation){
//            iv_card.setImageResource(companyImage);
//            tv_name.setText(companyName);
//            tv_location.setText(companyLocation);
//        }

    }
}
