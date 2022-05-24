package com.example.login;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.ServerTimestamp;
import com.google.firebase.firestore.Source;
import com.google.zxing.Result;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class Scanner extends AppCompatActivity {
    private CodeScanner mCodeScanner;
    TextView tv_scanner, tv_scanner2, tv_scanner3;
    String data;
    String weight;
    String remark;
    String companyid,username;

    Integer points, currentpoints;
    FirebaseFirestore fStore;
    FirebaseAuth fAuth;
    String[] seperate;
    @ServerTimestamp Date timestamp;

//    private static final int CAMERA_PERMISSION_CODE = 100;
//    private static final int LOCATION_PERMISSION_CODE = 101;
//    private static final int LOCATION_COURSE_CODE = 102;
//    private static final int WIFI_PERMISSION_CODE = 103;
//    private static final String TAG = "Scanner";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.qr_scanner);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        getData();
//        tv_scanner = findViewById(R.id.tv_textView);
//        tv_scanner2 = findViewById(R.id.tv_textView2);
//        tv_scanner3 = findViewById(R.id.tv_textView3);

        //date = new SimpleDateFormat("\"yyyy.MMMM.dd GGG hh:mm aaa\"", Locale.getDefault()).format(new Date());

        CodeScannerView scannerView = findViewById(R.id.scanner_view);
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
                        final String userid = user.getUid();

                        DocumentReference  historydf = db.collection("History").document();
                        DocumentReference pointsdf = db.collection("Users").document(userid);
                        CollectionReference usercf = db.collection("Users");
//                        Query query = usercf.whereEqualTo("state", "CA");
//                            DocumentReference docRef = db.collection("cities").document("SF");
//                        username = pointsdf.get(Source.valueOf("Name"));


                        Toast.makeText(Scanner.this, result.getText(), Toast.LENGTH_SHORT).show();
//                        tv_scanner.setText(seperate[0]);
//                        tv_scanner2.setText(seperate[1]);
//                        tv_scanner3.setText(seperate[2]);

                        weight = seperate[0];
                        remark = seperate[1];
                        companyid = seperate[2];

                        switch (seperate[0]) {
                            case "Below 1kg":
                                points = 10;
                                Toast.makeText(Scanner.this, points.toString()+" points added!", Toast.LENGTH_SHORT).show();
                                break;

                            case "1kg - 3kg":
                                points = 20;
                                Toast.makeText(Scanner.this, points.toString()+" points added!", Toast.LENGTH_SHORT).show();
                                break;

                            case "3kg - 5kg":
                                points = 30;
                                Toast.makeText(Scanner.this, points.toString()+" points added!", Toast.LENGTH_SHORT).show();
                                break;

                            case "5kg and above":
                                points = 40;
                                Toast.makeText(Scanner.this, points.toString()+" points added!", Toast.LENGTH_SHORT).show();
                                break;

                            default:
                                Toast.makeText(Scanner.this, "Error!", Toast.LENGTH_SHORT).show();

                        }

                        //currentpoints = currentpoints + points;
                        Map<String,String> historyMap = new HashMap<>();
                        Map<String, Integer> pointsMap = new HashMap<>();

                        String saveCurrentTime, saveCurrentDate;
                        Calendar calForDate = Calendar.getInstance();
                        SimpleDateFormat currentDate = new SimpleDateFormat ("dd-MM-yyyy");
                        saveCurrentDate = currentDate.format(calForDate.getTime());

                        SimpleDateFormat currentTime = new SimpleDateFormat ("HH:mm:ss a");
                        saveCurrentTime = currentTime.format(calForDate.getTime());

                        historyMap.put("CompanyID",companyid);
                        historyMap.put("Remark",remark);
                        historyMap.put("UserID",pointsdf.getId());
                        historyMap.put("Weight",weight);
                        historyMap.put("Points",points.toString());
                        historyMap.put("Username",username.toString());
                        historyMap.put("Date",saveCurrentDate);
                        historyMap.put("Time",saveCurrentTime);
                        pointsMap.put("Pointsaccu", points);

                        fStore.collection("History").add(historyMap);
                        fStore.collection("Users").document(userid).update("Pointsaccu", FieldValue.increment(points));
                        fStore.collection("Users").document(userid)
                                .collection("History").add(historyMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentReference> task) {

                                finish();
                            }
                        });



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

//    public void PermissionHandler(String permission, int requestCode) {
//
//        Log.i(TAG, "sdk < 28 Q");
//        if (ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_DENIED) {
//
//            // Requesting the permission
//            ActivityCompat.requestPermissions(this, new String[]{permission}, requestCode);
//
//        } else {
//            Toast.makeText(this, "Permission already granted", Toast.LENGTH_SHORT).show();
//        }
//    }
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        // This function is called when user accept or decline the permission.
//        // Request Code is used to check which permission called this function.
//        // This request code is provided when user is prompt for permission.
//        if (requestCode == CAMERA_PERMISSION_CODE) {
//
//            // Checking whether user granted the permission or not.
//            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//
//                // Showing the toast message
//                Toast.makeText(this, "Camera Permission Granted", Toast.LENGTH_SHORT).show();
//            } else {
//                Toast.makeText(this, "Camera Permission Denied", Toast.LENGTH_SHORT).show();
//            }
//        } else if (requestCode == LOCATION_PERMISSION_CODE) {
//            if (grantResults.length > 0
//                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                Toast.makeText(this, "Location Permission Granted", Toast.LENGTH_SHORT).show();
//            } else {
//                Toast.makeText(this, "Location Permission Denied", Toast.LENGTH_SHORT).show();
//            }
//        } else if (requestCode == LOCATION_COURSE_CODE) {
//            if (grantResults.length > 0
//                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                Toast.makeText(this, "Location Permission Granted", Toast.LENGTH_SHORT).show();
//            } else {
//                Toast.makeText(this, "Location Permission Denied", Toast.LENGTH_SHORT).show();
//            }
//        }   else if (requestCode == WIFI_PERMISSION_CODE) {
//            if (grantResults.length > 0
//                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                Toast.makeText(this, "Wifi Permission Granted", Toast.LENGTH_SHORT).show();
//            } else {
//                Toast.makeText(this, "Wifi Permission Denied", Toast.LENGTH_SHORT).show();
//            }
//        }
//    }






