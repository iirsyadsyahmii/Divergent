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
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class YourPoints extends AppCompatActivity  {
    RecyclerView recycler;
    List<YourPointClass> historylist;
    YourPointsAdapter adapter;
    ImageButton back, home,profile,delete;
    Button more;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firestore;
    TextView username, points;
    String usernameresult;
    String pointsaccuresult;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Toolbar toolbar = findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_your_points);
        firebaseAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        back=findViewById(R.id.imageButton);
        home=findViewById(R.id.imageButton2);
        profile=findViewById(R.id.imageButton3);
        delete=findViewById(R.id.delete);
        more=findViewById(R.id.check_in);
        username=findViewById(R.id.username);
        points=findViewById(R.id.accupoints);
        recycler=(RecyclerView) findViewById(R.id.recycler_view_1);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        historylist = new ArrayList<>();
        adapter = new YourPointsAdapter(this,historylist);
        recycler.setAdapter(adapter);

        firestore.collection("Users").document(firebaseAuth.getCurrentUser().getUid()).collection("History").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(DocumentSnapshot doc :task.getResult().getDocuments()){
                        YourPointClass voucher= doc.toObject(YourPointClass.class);
                        String documentId = doc.getId();
                        voucher.setDocumentId(documentId);
                        historylist.add(voucher);
                        adapter.notifyDataSetChanged();
                    }
                }
            }
        });

        getData();
//        delete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                openDialog();
//            }
//        });
        more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (getApplicationContext(),RewardsForYou.class);
                startActivity(intent);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        });

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (getApplicationContext(),ProfileActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

//    public void card() {
//        recycler.setHasFixedSize(true);
//        recycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false));
//        ArrayList<YourPointClass> card=new ArrayList<YourPointClass>();
//        card.add(new YourPointClass("Lantaw Restaurant","50% OFF", R.drawable.voucher_1,ClaimVoucherActivity.class));
//        card.add(new YourPointClass("Italian Restaurant","Buy 1 Free 1", R.drawable.voucher_2, VoucherDetails2.class));
//        card.add(new YourPointClass("Eco Bistro","Free meal", R.drawable.voucher_3, VoucherDetails3.class));
//        adapter=new YourPointsAdapter(card);
//        recycler.setAdapter(adapter);
//    }

//    public void openDialog(){
//        DeleteDialog deleteDialog = new DeleteDialog();
//        deleteDialog.show(getSupportFragmentManager(),"delete dialog");
//    }
//
//    public void deleteData(int index){
//
//    }
    private void getData(){
        firestore = FirebaseFirestore.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        final String userid = user.getUid();

        DocumentReference reference = reference = firestore.collection("Users").document(userid);
        reference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.getResult().exists()){
                    usernameresult = task.getResult().getString("Name");
                    pointsaccuresult = task.getResult().getLong("Pointsaccu").toString();

                    username.setText("Hi, " +usernameresult);
                    points.setText(pointsaccuresult);

                }
            }
        });
    }

//    public void deleteRecord(){
//
//        firestore.collection("Users").document(firebaseAuth.getCurrentUser().getUid()).collection("History").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                if(task.isSuccessful()){
//                    for(DocumentSnapshot doc :task.getResult().getDocuments()){
//                        YourPointClass voucher= doc.toObject(YourPointClass.class);
//
//                        String documentId = doc.getId();
//                        voucher.setDocumentId(documentId);
//
//                    }
//                }
//            }
//        });
//    }
}