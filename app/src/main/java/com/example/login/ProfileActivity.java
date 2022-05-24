package com.example.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class ProfileActivity extends AppCompatActivity{

    Button btnlogout, btnchangepw;
    TextView tvnickname, tvnickname1, tvusername, tvemail, tvpassword, tvpointsaccu;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firestore;
    String usernameresult;
    String nicknameresult;
    String emailresult;
    String passwordresult;
    String pointsaccuresult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        firebaseAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        btnlogout = findViewById(R.id.btn_logout);
        btnchangepw = findViewById(R.id.btn_changepw);
        tvusername = findViewById(R.id.tv_username);
        tvnickname = findViewById(R.id.tv_nickname);
        tvnickname1 = findViewById(R.id.tv_nickname1);
        tvpassword = findViewById(R.id.tv_password);
        tvpointsaccu = findViewById(R.id.tv_pointsaccu);
        tvemail = findViewById(R.id.tv_email);


        getData();

        btnlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(),Login.class));
                finish();

            }
        });

        btnchangepw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=  new Intent(ProfileActivity.this, ChangePassword.class);
                intent.putExtra("password", passwordresult);

                startActivity(intent);
            }
        });
    }

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
                    nicknameresult = task.getResult().getString("Nickname");
                    emailresult = task.getResult().getString("Email");
                    passwordresult = task.getResult().getString("Password");
                    pointsaccuresult = task.getResult().getLong("Pointsaccu").toString();

                    tvusername.setText(usernameresult);
                    tvnickname.setText(nicknameresult);
                    tvnickname1.setText(nicknameresult);
                    tvemail.setText(emailresult);
                    tvpassword.setText(passwordresult);
                    tvpointsaccu.setText(pointsaccuresult);
                }
            }
        });
    }
}