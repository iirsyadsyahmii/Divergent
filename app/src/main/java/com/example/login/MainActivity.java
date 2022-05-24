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

import com.example.login.adapter.MenuRecyclerViewAdapter;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity{

    LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);

        linearLayoutManager = new LinearLayoutManager(MainActivity.this);
        recyclerView.setLayoutManager(linearLayoutManager);

        List<MainMenu> allMenuInfo = getAllMenuInfo();

        MenuRecyclerViewAdapter menuRecyclerViewAdapter = new MenuRecyclerViewAdapter(MainActivity.this,allMenuInfo);
        recyclerView.setAdapter(menuRecyclerViewAdapter);

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

        switch (item.getItemId()){
            case R.id.menu_logout:
                Toast.makeText(MainActivity.this,"Log Out", Toast.LENGTH_SHORT).show();
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(),Login.class));
                finish();
                break;

            case R.id.menu_profile:
                Toast.makeText(MainActivity.this,"Profile", Toast.LENGTH_SHORT).show();
                intent = new Intent(MainActivity.this, ProfileActivity.class);
                startActivity(intent);
                break;
//            case R.drawable.recycle:
//                Toast.makeText(MainActivity.this,"Recycle", Toast.LENGTH_SHORT).show();
////                intent = new Intent(MainActivity.this, ProfileActivity.class);
////                startActivity(intent);
//                break;
//            case R.drawable.donate:
//                Toast.makeText(MainActivity.this,"Donate", Toast.LENGTH_SHORT).show();
////                intent = new Intent(MainActivity.this, ProfileActivity.class);
////                startActivity(intent);
//                break;
//            case R.drawable.rewards:
//                Toast.makeText(MainActivity.this,"Rewards", Toast.LENGTH_SHORT).show();
////                intent = new Intent(MainActivity.this, ProfileActivity.class);
////                startActivity(intent);
//                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private List<MainMenu> getAllMenuInfo(){
        List<MainMenu> allMenuInfo = new ArrayList<>();

        allMenuInfo.add(new MainMenu("Time to Recycle",R.drawable.recycle,Time_to_Recycle.class));
        allMenuInfo.add(new MainMenu("Time to Donate",R.drawable.donate,Time_to_Donate.class));
        allMenuInfo.add(new MainMenu("Rewards For You",R.drawable.rewards,RewardsForYou.class));
        allMenuInfo.add(new MainMenu("Your Rewards",R.drawable.yourrewards,Your_Rewards.class));
        allMenuInfo.add(new MainMenu("Your Points",R.drawable.points,YourPoints.class));

        return allMenuInfo;
    }
}