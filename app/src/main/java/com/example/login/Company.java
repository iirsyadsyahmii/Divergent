package com.example.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.login.Companyadapter.CompanyRecyclerViewAdapter;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class Company extends AppCompatActivity{
    LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company);

        Toolbar toolbar = findViewById(R.id.toolbar_main2);
        setSupportActionBar(toolbar);

        RecyclerView adminrecyclerView =findViewById(R.id.admin_recycler_view);

        linearLayoutManager = new LinearLayoutManager(Company.this);
        adminrecyclerView.setLayoutManager(linearLayoutManager);
        List<CompanyMainMenu> allCompanyMenuInfo  =  getAllCompanyMenuInfo();

        CompanyRecyclerViewAdapter companyRecyclerViewAdapter = new CompanyRecyclerViewAdapter(Company.this, allCompanyMenuInfo);
        adminrecyclerView.setAdapter(companyRecyclerViewAdapter);

    }

    private List<CompanyMainMenu> getAllCompanyMenuInfo(){
        List<CompanyMainMenu> allCMenu = new ArrayList<CompanyMainMenu>();
        allCMenu.add(new CompanyMainMenu("Your Page", R.drawable.your_page, YourPage.class ));
        allCMenu.add(new CompanyMainMenu("Your QR Code", R.drawable.qrcode, QR_Generator.class));
        allCMenu.add(new CompanyMainMenu("Your Analysis", R.drawable.analysis, TransactionHistory.class  ));
        return allCMenu;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.menu_logout:
                Toast.makeText(Company.this, "Log Out", Toast.LENGTH_SHORT).show();
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(), Login.class));
                finish();
                break;

            case R.id.menu_profile:
                Toast.makeText(Company.this, "Profile", Toast.LENGTH_SHORT).show();
                intent = new Intent(Company.this, ProfileAdmin.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}