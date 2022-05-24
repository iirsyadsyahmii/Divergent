package com.example.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class VoucherDetails extends AppCompatActivity {
    ImageView vimage;
    TextView resname,offer,desc;
    Button redeem;
    VoucherClass voucherclass = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ImageButton back,cross ;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voucher_details);
        vimage = findViewById(R.id.imageView4);
        resname = findViewById(R.id.textView20);
        offer = findViewById(R.id.vname1);
        desc = findViewById(R.id.vdesc1);
        back = findViewById(R.id.imageButton4);
        redeem = findViewById(R.id.button2);


        final Object object =getIntent().getSerializableExtra("detail");
        if (object instanceof VoucherClass){
            voucherclass=(VoucherClass)object;

        }

        if(voucherclass!=null){
            Glide.with(getApplicationContext()).load(voucherclass.getImage()).into(vimage);
            resname.setText(voucherclass.getRestaurant());
            offer.setText(voucherclass.getOffer());
            desc.setText(voucherclass.getDescription());

        }
        redeem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String restaurant = resname.getText().toString();
                String roffer = offer.getText().toString();
                String image = voucherclass.getImage().toString();
                String voucherid = voucherclass.getVoucherID();
                Intent intent = new Intent (getApplicationContext(),VoucherQR.class);
                intent.putExtra("resname",restaurant);
                intent.putExtra("roffer",roffer);
                intent.putExtra("image",image);
                intent.putExtra("vid",voucherid);
                startActivity(intent);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (getApplicationContext(),Your_Rewards.class);
                startActivity(intent);
            }
        });
    }

}