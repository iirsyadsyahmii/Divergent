package com.example.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class RewardsForYou extends AppCompatActivity {


    RecyclerView recyclerView;
    List<RewardClass> voucher4ulist;
    RewardForYouAdapter adapter;
    ImageButton back, home,profile;
    private FirebaseAuth auth;
    private FirebaseFirestore fStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rewards_for_you);
        back=findViewById(R.id.imageButton4);
        home=findViewById(R.id.imageButton5);
        profile=findViewById(R.id.imageButton6);
        fStore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        recyclerView = findViewById(R.id.recycler_view_uploaded);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        voucher4ulist = new ArrayList<>();
        adapter = new RewardForYouAdapter(this,voucher4ulist);
        recyclerView.setAdapter(adapter);

        fStore.collection("Uvoucher").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(DocumentSnapshot document : task.getResult()){
                        RewardClass rewardClass = document.toObject(RewardClass.class);
                        voucher4ulist.add(rewardClass);
                        adapter.notifyDataSetChanged();
                    }
                }else{
                    Toast.makeText(getApplicationContext(),"Error"+task.getException(),Toast.LENGTH_SHORT).show();
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        });

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (getApplicationContext(),ProfileActivity.class);
                startActivity(intent);
            }
        });
    }

//    private void initData() {
//        voucher4ulist = new ArrayList<>();
//
//        voucher4ulist.add(new RewardClass(R.drawable.voucher_1,"Lantaw Restaurant","Food Voucher","50",Voucher4uDetails.class));
//        voucher4ulist.add(new RewardClass(R.drawable.voucher_2,"Italian Delight","Free meal & coffee","50",VoucherDetails2.class));
//        voucher4ulist.add(new RewardClass(R.drawable.voucher_3,"Eco Bistro","Discount Voucher","50",VoucherDetails3.class));
//        voucher4ulist.add(new RewardClass(R.drawable.voucher_3,"Eco Bistro","Discount Voucher","100",VoucherDetails3.class));
//
//    }
//
//    private void initRecyclerView() {
//        recyclerView = findViewById(R.id.recycler_view_2);
//        layoutManager = new LinearLayoutManager(this);
//        layoutManager.setOrientation(RecyclerView.VERTICAL);
//        recyclerView.setLayoutManager(layoutManager);
//        adapter = new RewardForYouAdapter(voucher4ulist);
//        recyclerView.setAdapter(adapter);
//        adapter.notifyDataSetChanged();
//    }
}