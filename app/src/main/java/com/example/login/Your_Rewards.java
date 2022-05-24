package com.example.login;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static android.content.ContentValues.TAG;

public class Your_Rewards extends AppCompatActivity {
    RecyclerView recyclerView;
    List<VoucherClass> voucherlist;
    YourRewardAdapter adapter;
    ImageButton back, home, profile;
    private FirebaseAuth auth;
    private FirebaseFirestore fStore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.your_rewards);
        back = findViewById(R.id.imageButton4);
        home = findViewById(R.id.imageButton5);
        profile = findViewById(R.id.imageButton6);
        fStore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        recyclerView = findViewById(R.id.recycler_view_1);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        voucherlist = new ArrayList<>();
        adapter = new YourRewardAdapter(this,voucherlist);
        recyclerView.setAdapter(adapter);

        fStore.collection("Users").document(auth.getCurrentUser().getUid()).collection("Voucher").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(DocumentSnapshot doc :task.getResult().getDocuments()){
                        VoucherClass voucher= doc.toObject(VoucherClass.class);
                                            voucher.setVoucherID(doc.getId());

                                            String vid = voucher.getVoucherID();
                        voucherlist.add(voucher);
                        adapter.notifyDataSetChanged();
                    }
                }
            }
        });


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
                startActivity(intent);
            }
        });
    }

    /*private void initData() {
        voucherlist = new ArrayList<>();

        fStore.collection("Users").document(auth.getCurrentUser().getUid()).collection("Voucher").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                for(DocumentSnapshot doc :task.getResult().getDocuments()){
                    VoucherClass voucher= doc.toObject(VoucherClass.class);
                    voucherlist.add(voucher);
                    adapter.notifyDataSetChanged();
                }
                }
            }
        });


    }*/

    //voucherlist.add(new VoucherClass(R.drawable.voucher_2,"Italian Delight","Free meal & coffee",VoucherDetails2.class));
    //voucherlist.add(new VoucherClass(R.drawable.voucher_3,"Eco Bistro","Discount Voucher",VoucherDetails3.class));
    //voucherlist.add(new VoucherClass(R.drawable.voucher_3,"Eco Bistro","Discount Voucher",VoucherDetails3.class));
    //voucherlist.add(new VoucherClass(R.drawable.voucher_3,"Eco Bistro","Discount Voucher",VoucherDetails3.class));



    /*private void initRecyclerView() {
        recyclerView = findViewById(R.id.recycler_view_1);
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        voucherlist = new ArrayList<>();
        adapter = new YourRewardAdapter(this,voucherlist);
        recyclerView.setAdapter(adapter);

    }*/




}

//import android.os.Bundle;
//import android.widget.Button;
//
//import androidx.annotation.Nullable;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import java.util.ArrayList;
//
//public class Time_to_Recycle extends AppCompatActivity {
//    Button checkin;
//    RecyclerView company;
//    RecyclerView.Adapter adapter;
//
//
//
//    @Override
//    protected void onCreate( Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.time_to_recycle);
//
//        RecyclerView recyclerView = findViewById(R.id.recycler_view_1);
//        recyclerView.setHasFixedSize(true);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        card();
//
////        RecycleCompany [] CompanyData = new RecycleCompany[] {
////              new RecycleCompany("Denton Plastics","Selangor,Malaysia",R.drawable.denton_company),
////                new RecycleCompany("KRS Recycling Technologies","Pahang,Malaysia",R.drawable.krs_company),
////                new RecycleCompany("ReMatter","Penang,Malaysia",R.drawable.rematter_company),
////        };
////
////        RecycleCompanyAdapter recycleCompanyAdapter = new RecycleCompanyAdapter(CompanyData,Time_to_Recycle.this);
////        recyclerView.setAdapter(recycleCompanyAdapter);
//
//
//    }
//    public void card(){
//        company.setHasFixedSize(true);
//        company.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));
//        ArrayList<RecycleCompany> CompanyData=new ArrayList<RecycleCompany>();
//        CompanyData.add( new RecycleCompany("Denton Plastics","Selangor,Malaysia",R.drawable.denton_company));
//        CompanyData.add(new RecycleCompany("KRS Recycling Technologies","Pahang,Malaysia",R.drawable.krs_company));
//        CompanyData.add(new RecycleCompany("ReMatter","Penang,Malaysia",R.drawable.rematter_company));
//        adapter =new RecycleCompanyAdapter(CompanyData);
//        company.setAdapter(adapter);
//    }
//}
