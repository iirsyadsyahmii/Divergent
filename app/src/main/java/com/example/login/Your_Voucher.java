package com.example.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class Your_Voucher extends AppCompatActivity {

    RecyclerView recyclerView;
    List<Uploaded> allUploadedInfor;
    UploadedAdapter adapter;
    ImageButton back, home, profile;
    Button btnnew;
    FirebaseAuth auth;
    FirebaseFirestore fStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_your_voucher);
        fStore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        btnnew = findViewById(R.id.btn_new);
        //back = findViewById(R.id.imageButton7);
        //home = findViewById(R.id.imageButton8);
        //profile = findViewById(R.id.imageButton9);

        recyclerView = findViewById(R.id.recycler_view_uploaded);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        allUploadedInfor = new ArrayList<>();
        adapter = new UploadedAdapter(this,allUploadedInfor);
        recyclerView.setAdapter(adapter);

        fStore.collection("Users").document(auth.getCurrentUser().getUid()).collection("UploadedVoucher").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(DocumentSnapshot doc :task.getResult().getDocuments()){
                        Uploaded voucher= doc.toObject(Uploaded.class);
                            voucher.setVoucherID(doc.getId());
                            String vid = voucher.getVoucherID();
                        allUploadedInfor.add(voucher);
                        adapter.notifyDataSetChanged();
                    }
                }
            }
        });

        btnnew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Your_Voucher.this, Upload_Voucher.class);
                startActivity(intent);
            }
        });

        /*back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Restaurant.class);
                startActivity(intent);
            }
        });

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Restaurant.class);
                startActivity(intent);
            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ProfileAdmin.class);
                startActivity(intent);
            }
        });*/
    }
}