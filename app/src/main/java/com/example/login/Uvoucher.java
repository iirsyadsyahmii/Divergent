package com.example.login;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.Exclude;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.Serializable;

public class Uvoucher implements Serializable {
    private FirebaseAuth auth;
    private FirebaseFirestore fStore;
    String uvoucherID;

    public Uvoucher(){
    }

    public String getUVoucherID() {
        return uvoucherID;
    }

    public void setUVoucherID(String voucherID) {
        this.uvoucherID = uvoucherID;
    }
}
