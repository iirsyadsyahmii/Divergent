package com.example.login;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ServerTimestamp;
import com.google.zxing.Result;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class RestaurantScanner extends AppCompatActivity {
    private CodeScanner mCodeScanner;
    // TextView tv_scanner, tv_scanner2, tv_scanner3;
    String data, username,restaurant,roffer,userId,rvid,resname;

    FirebaseFirestore fStore;
    FirebaseAuth fAuth;
    String[] seperate;
    @ServerTimestamp
    Date timestamp;

    private static final int CAMERA_PERMISSION_CODE = 100;
    private static final int LOCATION_PERMISSION_CODE = 101;
    private static final int LOCATION_COURSE_CODE = 102;
    private static final int WIFI_PERMISSION_CODE = 103;
    private static final String TAG = "Scanner";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.res_scanner);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        getData();
//        tv_scanner = findViewById(R.id.tv_textView);
//        tv_scanner2 = findViewById(R.id.tv_textView2);
//        tv_scanner3 = findViewById(R.id.tv_textView3);

        //date = new SimpleDateFormat("\"yyyy.MMMM.dd GGG hh:mm aaa\"", Locale.getDefault()).format(new Date());
        PermissionHandler(Manifest.permission.CAMERA, CAMERA_PERMISSION_CODE);
        PermissionHandler(Manifest.permission.ACCESS_FINE_LOCATION, LOCATION_PERMISSION_CODE);
        PermissionHandler(Manifest.permission.ACCESS_COARSE_LOCATION, LOCATION_COURSE_CODE);
        PermissionHandler(Manifest.permission.ACCESS_WIFI_STATE, WIFI_PERMISSION_CODE);

        Toast.makeText(RestaurantScanner.this,"Scan the qr Code", Toast.LENGTH_SHORT).show();

        CodeScannerView scannerView = findViewById(R.id.scanner_view2);
        mCodeScanner = new CodeScanner(this, scannerView);
        mCodeScanner.setDecodeCallback(new DecodeCallback() {

            @Override
            public void onDecoded(@NonNull final Result result) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        data = result.getText().toString();

                        //String currentString = "Fruit: they taste good";
                        String[] seperate = data.split("_");
                        fStore = FirebaseFirestore.getInstance();
                        FirebaseUser user = fAuth.getCurrentUser();
                        final String resid = user.getUid();

                        DocumentReference rvoucherdf = db.collection("RedeemedVoucher").document();
                        DocumentReference pointsdf = db.collection("Users").document(resid);


                        // Toast.makeText(RestaurantScanner.this, result.getText(), Toast.LENGTH_SHORT).show();
//                        tv_scanner.setText(seperate[0]);
//                        tv_scanner2.setText(seperate[1]);
//                        tv_scanner3.setText(seperate[2]);

                        restaurant = seperate[0];
                        roffer = seperate[1];
                        userId = seperate[2];
                        rvid = seperate[3];
                        username = seperate[4];

                        if(restaurant==username ) {
                            //currentpoints = currentpoints + points;
                            Map<String, String> historyMap = new HashMap<>();


                            String saveCurrentTime, saveCurrentDate;
                            Calendar calForDate = Calendar.getInstance();
                            SimpleDateFormat currentDate = new SimpleDateFormat("dd-MM-yyyy");
                            saveCurrentDate = currentDate.format(calForDate.getTime());

                            SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
                            saveCurrentTime = currentTime.format(calForDate.getTime());

                            historyMap.put("Restaurant Name", restaurant);
                            historyMap.put("Restaurant ID", pointsdf.getId());
                            historyMap.put("CustomerID", userId);
                            historyMap.put("Customer Name", username);
                            historyMap.put("Offer", roffer);
                            historyMap.put("Voucher ID", rvid);
                            historyMap.put("UserName", username);
                            historyMap.put("Date", saveCurrentDate);
                            historyMap.put("Time", saveCurrentTime);


                            fStore.collection("RedeemedVoucher").add(historyMap);
//

                            fStore.collection("Users").document(resid)
                                    .collection("RedeemedVoucher").add(historyMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentReference> task) {

                                    Toast.makeText(RestaurantScanner.this, " Redeemed Successfully", Toast.LENGTH_SHORT).show();
                                    fStore.collection("Users").document(userId).collection("Voucher").document(rvid).delete()
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {

                                                    if (task.isSuccessful()) {

                                                    } else {

                                                    }
                                                }
                                            });
                                    finish();
                                }
                            });


                        }
                        else{
                            Toast.makeText(RestaurantScanner.this,"This voucher does not belong to your restaurant.",Toast.LENGTH_SHORT).show();
                        }
//                        1kg - 3kg");
//                        list.add("3kg - 5kg");
//                        list.add("5kg and above"



                    }
                });
            }
        });
        scannerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCodeScanner.startPreview();

            }
        });
    }
    public void PermissionHandler(String permission, int requestCode) {

        Log.i(TAG, "sdk < 28 Q");
        if (ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_DENIED) {

            // Requesting the permission
            ActivityCompat.requestPermissions(this, new String[]{permission}, requestCode);

        } else {
            Toast.makeText(this, "Permission already granted", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // This function is called when user accept or decline the permission.
        // Request Code is used to check which permission called this function.
        // This request code is provided when user is prompt for permission.
        if (requestCode == CAMERA_PERMISSION_CODE) {

            // Checking whether user granted the permission or not.
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                // Showing the toast message
                Toast.makeText(this, "Camera Permission Granted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Camera Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
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
                    resname = task.getResult().getString("Name");
                    // currentpoints = Integer.parseInt(task.getResult().getString("pointsaaccu"));


//                        tvusername.setText(usernameresult);
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



    @Override
    protected void onResume() {
        super.onResume();
        mCodeScanner.startPreview();
    }

    @Override
    protected void onPause() {
        mCodeScanner.releaseResources();
        super.onPause();
    }

}