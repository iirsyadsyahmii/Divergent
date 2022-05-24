package com.example.login;

import static android.service.controls.ControlsProviderService.TAG;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.WriteBatch;

import java.util.List;

public class UploadedAdapter extends RecyclerView.Adapter<UploadedAdapter.ViewHolder>{

    FirebaseAuth auth = FirebaseAuth.getInstance();;
    FirebaseFirestore fstore = FirebaseFirestore.getInstance();
    Context context;
    List<Uploaded> allUploadedInfor;

    public UploadedAdapter (Context context,List<Uploaded>allUploadedInfor){
        this.context=context;
        this.allUploadedInfor=allUploadedInfor;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.uploaded_row, viewGroup,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.textview1.setText(allUploadedInfor.get(position).getRestaurant());
        holder.textview2.setText(allUploadedInfor.get(position).getOffer());
        holder.textview3.setText(allUploadedInfor.get(position).getDescription());
        Glide.with(context).load(allUploadedInfor.get(position).getImage()).into(holder.imageview1);

        holder.btndetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Delete").setMessage("Are you sure you want to delete?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FirebaseFirestore.getInstance().collection("Uvoucher").whereEqualTo("Image",allUploadedInfor.get(position).getImage()).get()
                                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                    @Override
                                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                        WriteBatch batch =  FirebaseFirestore.getInstance().batch();
                                        List<DocumentSnapshot> snapshotList = queryDocumentSnapshots.getDocuments();
                                        for(DocumentSnapshot snapshot: snapshotList){
                                            batch.delete(snapshot.getReference());
                                        }
                                        batch.commit()
                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {
                                                        Log.d(TAG,"OnSuccess:Deleted!");
                                                    }
                                                })
                                                .addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        Log.e(TAG,"OnFailure:"+e);
                                                    }
                                                });
                                    }
                                });

                        fstore.collection("Users").document(auth.getCurrentUser().getUid()).collection("UploadedVoucher").document(allUploadedInfor.get(position).getVoucherID()).delete()
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        //Toast.makeText(context,res,Toast.LENGTH_SHORT).show();
                                        if(task.isSuccessful()){
                                            allUploadedInfor.remove(allUploadedInfor.get(position));
                                            notifyDataSetChanged();
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
    }

    public int getItemCount() {
        return allUploadedInfor.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageview1, imgviewdelete;
        TextView textview1, textview2, textview3;
        Button btndetails;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageview1 = itemView.findViewById(R.id.imageViewUploaded);
            textview1 = itemView.findViewById(R.id.tvremark);
            textview2 = itemView.findViewById(R.id.tvweight);
            textview3 = itemView.findViewById(R.id.tvpoint);
            btndetails = itemView.findViewById(R.id.btn_details);
        }


    }
}
