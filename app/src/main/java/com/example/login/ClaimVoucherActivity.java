package com.example.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class ClaimVoucherActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ImageButton back,cross ;
        Button redeem;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voucher_details);

        back = findViewById(R.id.imageButton4);
        redeem = findViewById(R.id.button2);
        redeem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (getApplicationContext(),VoucherQR.class);
                startActivity(intent);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (getApplicationContext(),YourPointClass.class);
                startActivity(intent);
            }
        });
    }
}
