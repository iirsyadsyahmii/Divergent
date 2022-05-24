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

public class company_details extends AppCompatActivity {
    ImageView compimage;
    TextView compname,compdesc,complocation;
    //Button redeem;
    RecycleCompany companyclass = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ImageButton back,cross ;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.company_details);
        compimage = findViewById(R.id.compimage);
        compname = findViewById(R.id.compname);
        compdesc = findViewById(R.id.compdesc);
        complocation = findViewById(R.id.complocation);
        back = findViewById(R.id.back);
        //redeem = findViewById(R.id.button2);


        final Object object =getIntent().getSerializableExtra("compdetail");
        if (object instanceof RecycleCompany){
            companyclass=(RecycleCompany) object;

        }

        if(companyclass!=null){
            Glide.with(getApplicationContext()).load(companyclass.getImage()).into(compimage);
            compname.setText(companyclass.getName());
            compdesc.setText(companyclass.getDescription());
            complocation.setText(companyclass.getLocation());

        }
//        redeem.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String company = compname.getText().toString();
//                String location = complocation.getText().toString();
//                String image = companyclass.getImage().toString();
//                String companyid = companyclass.getCompanyID();
//                Intent intent = new Intent (getApplicationContext(),VoucherQR.class);
//                intent.putExtra("resname",restaurant);
//                intent.putExtra("roffer",roffer);
//                intent.putExtra("image",image);
//                intent.putExtra("vid",voucherid);
//                startActivity(intent);
//            }
//        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (getApplicationContext(),Time_to_Recycle.class);
                startActivity(intent);
            }
        });
    }

}