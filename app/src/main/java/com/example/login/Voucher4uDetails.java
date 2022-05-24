package com.example.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class Voucher4uDetails extends AppCompatActivity {

    ImageView vimage;
    RewardClass rewardClass =null;
    TextView resname, offer, desc,pointo;
    FirebaseAuth auth;
    ArrayList<List<String>> Resname = new ArrayList<>();
    ArrayList<List<String>> Resname2 = new ArrayList<>();
    private FirebaseFirestore fStore;
    public String pointsaccuresult;
    public int ownpoint,vpoint;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voucher4u_details);

        ImageButton back;
        Button redeem,claim;


        back = findViewById(R.id.imageButton4);
        redeem = findViewById(R.id.button2);
        claim = findViewById(R.id.button3);
        vimage = findViewById(R.id.imageView4);
        resname= findViewById(R.id.textView20);
        offer = findViewById(R.id.vname1);
        desc = findViewById(R.id.vdesc1);
        pointo=findViewById(R.id.point);
        fStore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
//import data from RewardClass
        final Object object =getIntent().getSerializableExtra("vdetails");
        if (object instanceof RewardClass){
            rewardClass=(RewardClass)object;

        }

        if(rewardClass!=null){
            Glide.with(getApplicationContext()).load(rewardClass.getImage()).into(vimage);
            resname.setText(rewardClass.getRestaurant());
            offer.setText(rewardClass.getOffer());
            pointo.setText(rewardClass.getPoints());
            desc.setText(rewardClass.getDescription());

        }

        claim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fStore.collection("Users").document(auth.getCurrentUser().getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.getResult().exists()){
                            pointsaccuresult = task.getResult().getLong("Pointsaccu").toString();
                            ownpoint= Integer.parseInt(pointsaccuresult);
                            vpoint=Integer.parseInt(pointo.getText().toString());

                            fStore.collection("Users").document(auth.getCurrentUser().getUid()).collection("Voucher").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {

                                @Override

                                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                                    List<DocumentSnapshot>snapshotList = queryDocumentSnapshots.getDocuments();


                                    for(DocumentSnapshot snapshot : snapshotList){

                                        Resname.add(Arrays.asList(snapshot.getString("Restaurant"), snapshot.getString("Offer")));
                                        Log.d(TAG,"on Create: detect1: "+Resname);


                                    }
                                    int count =0;

                                    Resname2.add(Arrays.asList(resname.getText().toString(),offer.getText().toString()));

                                    Log.d(TAG,"on Create: detect: "+Resname2.get(0));
                                    for(int i=0; i<Resname.size();i++){
                                        if(Resname2.get(0).equals(Resname.get(i))){
                                            count++;
                                        }
                                        Log.d(TAG,"on Create: resname: "+Resname.get(i));
                                        Log.d(TAG,"on Create: count: "+count);
                                    }


                                    if(count>0){

                                        Toast.makeText(Voucher4uDetails.this,"You can't claimed because you already claimed once.Please check in Your Reward",Toast.LENGTH_SHORT).show();
                                    }
                                    else{
                                        //Deduct points
                                        if(ownpoint>=vpoint) {
                                            ownpoint = ownpoint - vpoint;
                                            Toast.makeText(Voucher4uDetails.this, "Claim Successfully!", Toast.LENGTH_SHORT).show();
                                            claimVoucher();
                                            UpdatePoint(ownpoint);
                                        }
                                        else{
                                            Toast.makeText(Voucher4uDetails.this,"Your points is not enough to claim this voucher.",Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                }
                            });




                        }



                    }

                });

            }
        });

        redeem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (getApplicationContext(),R4uQR.class);
                startActivity(intent);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (getApplicationContext(),RewardsForYou.class);
                startActivity(intent);
            }
        });
    }

    public void UpdatePoint(Integer opoint){
        Map<String,Object> userPoints = new HashMap<>();
        userPoints.put("Pointsaccu",opoint);

        fStore.collection("Users").document(auth.getCurrentUser().getUid()).update(userPoints).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d(TAG,"onSuccess: update the doc");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e(TAG,"onFailure",e);
            }
        });
    }
    public void claimVoucher(){
        String saveCurrentTime, saveCurrentDate;

        Calendar calForDate = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat ("MM dd yyyy");
        saveCurrentDate = currentDate.format(calForDate.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat ("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calForDate.getTime());

        final HashMap<String,Object> cartMap = new HashMap<>();

        cartMap.put("Image",rewardClass.getImage());
        cartMap.put("Offer",offer.getText().toString());
        cartMap.put("Restaurant",resname.getText().toString());
        cartMap.put("Description",desc.getText().toString());
        cartMap.put("Current Date",saveCurrentDate);


//Add to Uploaded Voucher
//    fStore.collection("Uvoucher").add(cartMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
//        @Override
//        public void onComplete(@NonNull Task<DocumentReference> task) {
//
//        }
//    });

        //Add to User Voucher
        fStore.collection("Users").document(auth.getCurrentUser().getUid())
                .collection("Voucher").add(cartMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {
                Toast.makeText(Voucher4uDetails.this,"Claimed Successfully!", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}