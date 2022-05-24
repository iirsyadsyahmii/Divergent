package com.example.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.login.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class TransactionHistory extends AppCompatActivity {
    FirebaseFirestore fStore;
    TextView date, detail, weight, remark, users, name,userName,points;
    String username;
    FirebaseAuth fAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_history);
        TableLayout ll = (TableLayout) findViewById(R.id.table);
        fAuth = FirebaseAuth.getInstance();
        users = findViewById(R.id.tv_users);
        name = findViewById(R.id.tv_name);
        getData();

        fStore = FirebaseFirestore.getInstance();
        String UID = FirebaseAuth.getInstance().getUid();
        FirebaseUser user = fAuth.getCurrentUser();




        Task<QuerySnapshot> citiesRef = fStore.collection("History").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            int i=0;
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                if (document.getData().get("CompanyID").equals(UID)){
                                    TableRow row= new TableRow(getApplicationContext());
                                    TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
                                    row.setLayoutParams(lp);
                                    date=new TextView(getApplicationContext());
                                    userName=new TextView(getApplicationContext());
                                    weight=new TextView(getApplicationContext());
                                    remark=new TextView(getApplicationContext());
                                    points=new TextView(getApplicationContext());

                                    date.setText(document.getData().get("Date").toString());
                                    userName.setText(document.getData().get("Username").toString());
                                    weight.setText(document.getData().get("Weight").toString());
                                    remark.setText(document.getData().get("Remark").toString());
                                    points.setText(document.getData().get("Points").toString());
                                    row.addView(date);
                                    row.addView(userName);
                                    row.addView(weight);
                                    row.addView(remark);
                                    row.addView(points);
                                    ll.addView(row);
                                    i++;
                                    users.setText(String.valueOf(i));
                                }


                            }

                        } else {
//                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    private void getData() {
        fStore = FirebaseFirestore.getInstance();
        FirebaseUser user = fAuth.getCurrentUser();
        final String userid = user.getUid();

        DocumentReference reference = fStore.collection("Users").document(userid);
        reference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.getResult().exists()) {
                    username = task.getResult().getString("Name");
                    // currentpoints = Integer.parseInt(task.getResult().getString("pointsaaccu"));


                        name.setText(username);
//                        tvnickname.setText(nicknameresult);
//                        tvnickname1.setText(nicknameresult);
//                        tvemail.setText(emailresult);
//                        tvpassword.setText(passwordresult);
//                        tvpointsaccu.setText(pointsaccuresult);
                }
            }
        });

//        DocumentReference pointsUpdate = fStore.collection("")
    }
}