package com.example.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class VoucherDetails3 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ImageButton back ;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voucher_details3);

        back = findViewById(R.id.imageButton4);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (getApplicationContext(),Your_Rewards.class);
                startActivity(intent);
            }
        });
    }
}