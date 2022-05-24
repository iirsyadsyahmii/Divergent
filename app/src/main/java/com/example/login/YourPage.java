package com.example.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class YourPage extends AppCompatActivity {
    FirebaseAuth auth;
    FirebaseFirestore firestore;
    ImageView compimage;
    TextView compname,compdesc,complocation;
    //Button redeem;



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


        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
       

        DocumentReference reference = reference = firestore.collection("Users").document(auth.getCurrentUser().getUid());
        reference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.getResult().exists()){

                    Glide.with(getApplicationContext()).load(task.getResult().getString("Image")).into(compimage);

                    compname.setText(task.getResult().getString("Name"));
                    compdesc.setText(task.getResult().getString("Description"));
                    complocation.setText(task.getResult().getString("Location"));
                    ;
                }
            }
        });
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
                Intent intent = new Intent (getApplicationContext(),Company.class);
                startActivity(intent);
            }
        });
    }
}
