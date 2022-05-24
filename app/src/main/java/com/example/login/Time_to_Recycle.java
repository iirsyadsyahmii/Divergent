package com.example.login;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class Time_to_Recycle extends AppCompatActivity {

    RecyclerView recycler;
    List<RecycleCompany> companylist;
    RecycleCompanyAdapter adapter;
    ImageButton back, home, profile;
    public Button button_scan;
    private static final int CAMERA_PERMISSION_CODE = 100;
    private static final int LOCATION_PERMISSION_CODE = 101;
    private static final int LOCATION_COURSE_CODE = 102;
    private static final int WIFI_PERMISSION_CODE = 103;
    private static final String TAG = "Scanner";

    private FirebaseAuth auth;
    private FirebaseFirestore fStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        Toolbar toolbar = findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
        setContentView(R.layout.time_to_recycle);
        recycler=findViewById(R.id.recycler_view_1);
        LinearLayoutManager layoutManager = new LinearLayoutManager(Time_to_Recycle.this,LinearLayoutManager.HORIZONTAL,false);
        recycler.setLayoutManager(layoutManager);
        companylist = new ArrayList<>();
        adapter = new RecycleCompanyAdapter(this,companylist);
        recycler.setAdapter(adapter);
        //card();
        back = findViewById(R.id.imageButton);
        home = findViewById(R.id.imageButton2);
        profile = findViewById(R.id.imageButton3);
        fStore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        button_scan = findViewById(R.id.check_in);
        button_scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PermissionHandler(Manifest.permission.CAMERA, CAMERA_PERMISSION_CODE);
                PermissionHandler(Manifest.permission.ACCESS_FINE_LOCATION, LOCATION_PERMISSION_CODE);
                PermissionHandler(Manifest.permission.ACCESS_COARSE_LOCATION, LOCATION_COURSE_CODE);
                PermissionHandler(Manifest.permission.ACCESS_WIFI_STATE, WIFI_PERMISSION_CODE);
                Toast.makeText(Time_to_Recycle.this,"Scan the qr Code", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(),Scanner.class));
                finish();
            }
        });

        fStore.collection("RecycleCompanies").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(QueryDocumentSnapshot doc :task.getResult()){
                        RecycleCompany company= doc.toObject(RecycleCompany.class);
                        company.setCompanyID(doc.getId());

                        String cid = company.getCompanyID();
                        companylist.add(company);
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
    public void PermissionHandler(String permission, int requestCode) {

        Log.i(TAG, "sdk < 28 Q");
        if (ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_DENIED) {

            // Requesting the permission
            ActivityCompat.requestPermissions(this, new String[]{permission}, requestCode);

        } else {
            Toast.makeText(this, "Permission already granted", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // This function is called when user accept or decline the permission.
        // Request Code is used to check which permission called this function.
        // This request code is provided when user is prompt for permission.
        if (requestCode == CAMERA_PERMISSION_CODE) {

            // Checking whether user granted the permission or not.
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                // Showing the toast message
                Toast.makeText(this, "Camera Permission Granted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Camera Permission Denied", Toast.LENGTH_SHORT).show();
            }
//        } else if (requestCode == LOCATION_PERMISSION_CODE) {
//            if (grantResults.length > 0
//                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                Toast.makeText(this, "Location Permission Granted", Toast.LENGTH_SHORT).show();
//            } else {
//                Toast.makeText(this, "Location Permission Denied", Toast.LENGTH_SHORT).show();
//            }
//        } else if (requestCode == LOCATION_COURSE_CODE) {
//            if (grantResults.length > 0
//                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                Toast.makeText(this, "Location Permission Granted", Toast.LENGTH_SHORT).show();
//            } else {
//                Toast.makeText(this, "Location Permission Denied", Toast.LENGTH_SHORT).show();
//            }
//        }   else if (requestCode == WIFI_PERMISSION_CODE) {
//            if (grantResults.length > 0
//                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                Toast.makeText(this, "Wifi Permission Granted", Toast.LENGTH_SHORT).show();
//            } else {
//                Toast.makeText(this, "Wifi Permission Denied", Toast.LENGTH_SHORT).show();
            // }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

//    public void card() {
//        recycler.setHasFixedSize(true);
//        recycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false));
//        ArrayList<RecycleCompany> card=new ArrayList<RecycleCompany>();
//        card.add(new RecycleCompany("Denton Plastics","Selangor,Malaysia", R.drawable.denton_company));
//        card.add(new RecycleCompany("KRS Recycling Technologies","Pahang,Malaysia", R.drawable.krs_company));
//        card.add(new RecycleCompany("ReMatter","Penang,Malaysia", R.drawable.rematter_company));
//        adapter=new RecycleCompanyAdapter(card);
//        recycler.setAdapter(adapter);
//    }


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