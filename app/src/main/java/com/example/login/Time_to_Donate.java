package com.example.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Time_to_Donate extends AppCompatActivity {

    RecyclerView recycler;
    RecyclerView.Adapter adapter;
    public Button button_scan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.time_to_donate);
        recycler=(RecyclerView) findViewById(R.id.recycler_view_1);
        card();

        button_scan = findViewById(R.id.check_in);
        button_scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(Time_to_Donate.this,"Scan the qr Code", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(),Scanner.class));
                finish();
            }
        });




    }

    public void card() {
        recycler.setHasFixedSize(true);
        recycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false));
        ArrayList<DonationCompany> card=new ArrayList<DonationCompany>();
        card.add(new DonationCompany("Elderly Care","Kuala Lumpur,Malaysia",R.drawable.elderly_care));
        card.add(new DonationCompany("Charity Donatiom","Perak,Malaysia",R.drawable.charity_donation));
        card.add(new DonationCompany("The Rotunda Foundation","Perak,Malaysia",R.drawable.the_rotunda_foundation));
        adapter=new DonationCompanyAdapter(card);
        recycler.setAdapter(adapter);
    }


}
