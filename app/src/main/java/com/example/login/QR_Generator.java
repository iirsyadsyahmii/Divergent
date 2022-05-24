package com.example.login;

import android.graphics.Point;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;
import androidmads.library.qrgenearator.QRGEncoder;
import android.graphics.Bitmap;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.zxing.WriterException;

import java.util.ArrayList;
import java.util.List;

import androidmads.library.qrgenearator.QRGContents;



public class QR_Generator extends AppCompatActivity {

Spinner spinner_1;
EditText et_remark;
Button qr_button;
Bitmap bitmap;
ImageView QRimgview;
QRGEncoder qrgEncoder;
DatabaseReference database;
FirebaseFirestore fStore;
FirebaseAuth fAuth;
String username,userID;



    int choice=0;
    List<String> list = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.qr_generator);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        et_remark=findViewById(R.id.et_remark);
        spinner_1=findViewById(R.id.spinner_1);
        qr_button=findViewById(R.id.btn_qrcode);
        QRimgview=findViewById(R.id.qrCodeIV);
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        getData();
        //setContentView(R.layout.qr_generator);

        list.add("Below 1kg");
        list.add("1kg - 3kg");
        list.add("3kg - 5kg");
        list.add("5kg and above");

        //create an ArrayAdapter from the String Array


        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(QR_Generator.this,
                android.R.layout.simple_spinner_item, list);
        //set the view for the Drop down list
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        //Toast.makeText(QR_Generator.this,dataAdapter.getItem(0),Toast.LENGTH_SHORT).show();
        //set the ArrayAdapter to the spinner
        spinner_1.setAdapter(dataAdapter);
        spinner_1.setPrompt("Weight");
        spinner_1.setOnItemSelectedListener(new MyOnItemSelectedListener());



        qr_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(et_remark.getText().toString())) {

                    // if the edittext inputs are empty then execute
                    // this method showing a toast message.
                    Toast.makeText(QR_Generator.this, "Enter some text to generate QR Code", Toast.LENGTH_SHORT).show();
                } else {


                    // below line is for getting
                    // the windowmanager service.
                    WindowManager manager = (WindowManager) getSystemService(WINDOW_SERVICE);

                    // initializing a variable for default display.
                    Display display = manager.getDefaultDisplay();

                    // creating a variable for point which
                    // is to be displayed in QR Code.
                    Point point = new Point();
                    display.getSize(point);

                    // getting width and
                    // height of a point
                    int width = point.x;
                    int height = point.y;

                    // generating dimension from width and height.
                    int dimen = width < height ? width : height;
                    dimen = dimen * 3 / 4;

                    // setting this dimensions inside our qr code
                    // encoder to generate our qr code.
                    String text = spinner_1.getSelectedItem().toString();
                    userID = fAuth.getCurrentUser().getUid();
                    DocumentReference documentReference= fStore.collection("Users").document(userID);
//                    Toast.makeText(QR_Generator.this,userID,Toast.LENGTH_SHORT).show();
                    String qrcontent = text +"_"+ et_remark.getText().toString()+"_"+userID;
                    qrgEncoder = new QRGEncoder(qrcontent, null, QRGContents.Type.TEXT, dimen);
                    try {
                        // getting our qrcode in the form of bitmap.
                        bitmap = qrgEncoder.encodeAsBitmap();
                        // the bitmap is set inside our image
                        // view using .setimagebitmap method.
                        QRimgview.setImageBitmap(bitmap);
                    } catch (WriterException e) {
                        // this method is called for
                        // exception handling.
                        Log.e("Tag", e.toString());
                    }
                }
            }
        });


    }

    private void getData(){
        fStore = FirebaseFirestore.getInstance();
        FirebaseUser user = fAuth.getCurrentUser();
        final String userid = user.getUid();

        DocumentReference reference = reference = fStore.collection("Users").document(userid);
        reference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.getResult().exists()){
                    username = task.getResult().getString("Name");

//                        tvusername.setText(usernameresult);
//                        tvnickname.setText(nicknameresult);
//                        tvnickname1.setText(nicknameresult);
//                        tvemail.setText(emailresult);
//                        tvpassword.setText(passwordresult);
//                        tvpointsaccu.setText(pointsaccuresult);
                }
            }
        });
    }

    public class MyOnItemSelectedListener implements AdapterView.OnItemSelectedListener {

        public void onItemSelected(@NonNull AdapterView<?> parent, View view, int pos, long id) {

            String selectedItem = parent.getItemAtPosition(pos).toString();

            choice = list.indexOf(selectedItem);
            //Toast.makeText(QR_Generator.this,choice,Toast.LENGTH_SHORT).show();

        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }

     




}
