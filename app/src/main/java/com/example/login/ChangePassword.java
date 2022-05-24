package com.example.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.core.view.Change;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Exclude;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;

public class ChangePassword extends AppCompatActivity {

    private EditText etoldpw, etnewpw, etconfirmpw;
    Button btnchange;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firestore;
    String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        etoldpw = findViewById(R.id.et_oldpw);
        firebaseAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        etnewpw = findViewById(R.id.et_newpw);
        etconfirmpw = findViewById(R.id.et_confirmpw);
        btnchange = findViewById(R.id.btn_change);

        Intent intent = getIntent();
        password = intent.getStringExtra("password");
        etoldpw.setText(password);

        btnchange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UpdatePassword();
            }
        });
    }

    //private boolean hasValidationErrors(String etnewpw, String etconfirmpw){
    //return
    //}

    private void UpdatePassword() {
        String newpw = etnewpw.getText().toString();
        String confirmpw = etconfirmpw.getText().toString();
        if (TextUtils.isEmpty(newpw)) {
            etnewpw.setError("Password is required.");
            return;
        } else if (TextUtils.isEmpty(confirmpw)) {
            etconfirmpw.setError("Please re-enter your password.");
            return;
        } else if (!newpw.equals(confirmpw)) {
            etconfirmpw.setError("Wrong password.");
            return;
        } else if (newpw.length() < 6) {
            etnewpw.setError("Password must greater or equal to 6 characters.");
            return;
        } else {
            firestore = FirebaseFirestore.getInstance();
            FirebaseUser user = firebaseAuth.getCurrentUser();
            final String userid = user.getUid();
            final DocumentReference reference = firestore.collection("Users").document(userid);
            reference
                    .update("Password", newpw)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(ChangePassword.this, "Password Updated", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(ChangePassword.this, ProfileActivity.class);
                            startActivity(intent);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(ChangePassword.this, "Updating Failed", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(ChangePassword.this, ProfileActivity.class);
                            startActivity(intent);
                        }
                    });
        }
    }
}
