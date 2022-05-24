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

import com.example.login.RestaurantAdapter.RestaurantRecyclerViewAdapter;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class Restaurant extends AppCompatActivity{
    LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant);

        Toolbar toolbar = findViewById(R.id.toolbar_main3);
        setSupportActionBar(toolbar);

        RecyclerView restaurantrecyclerview = findViewById(R.id.restaurant_recycler_view);
        linearLayoutManager= new LinearLayoutManager(Restaurant.this);
        restaurantrecyclerview.setLayoutManager(linearLayoutManager);

        List<RestaurantMainMenu> allRestaurantMenuInfo = getAllRestaurantMenuInfo();

        RestaurantRecyclerViewAdapter restaurantRecyclerViewAdapter = new RestaurantRecyclerViewAdapter(Restaurant.this,allRestaurantMenuInfo);
        restaurantrecyclerview.setAdapter(restaurantRecyclerViewAdapter);

    }

    private List<RestaurantMainMenu> getAllRestaurantMenuInfo(){
        List<RestaurantMainMenu> allRMenu = new ArrayList<RestaurantMainMenu>();
        allRMenu.add(new RestaurantMainMenu("Your Page", R.drawable.your_page, YourPage.class));
        allRMenu.add(new RestaurantMainMenu("QR Code Scanner", R.drawable.qrcodescanner, RestaurantScanner.class));
        allRMenu.add(new RestaurantMainMenu("Your Voucher", R.drawable.vouchermenu, Your_Voucher.class));
        allRMenu.add(new RestaurantMainMenu("Your Analysis", R.drawable.analysis, RestaurantHistory.class));
        return allRMenu;
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
                Toast.makeText(Restaurant.this, "Log Out", Toast.LENGTH_SHORT).show();
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(), Login.class));
                finish();
                break;

            case R.id.menu_profile:
                Toast.makeText(Restaurant.this, "Profile", Toast.LENGTH_SHORT).show();
                intent = new Intent(Restaurant.this, ProfileAdmin.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}