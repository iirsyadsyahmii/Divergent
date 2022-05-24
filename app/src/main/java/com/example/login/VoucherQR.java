package com.example.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.zxing.WriterException;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

public class VoucherQR extends AppCompatActivity {
    ImageView vimage,qrcode;
    TextView resname,offer;
    Button generate;
    Bitmap bitmap;
    QRGEncoder qrgEncoder;
    FirebaseFirestore fStore;
    FirebaseAuth fAuth;
    String username,userID;

    VoucherClass voucherclass = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voucher_q_r);
        Button redeem;
        vimage=findViewById(R.id.imageView5);
        resname=findViewById(R.id.vqname);
        offer=findViewById(R.id.vqdesc);
        fStore = FirebaseFirestore.getInstance();
        fAuth =FirebaseAuth.getInstance();
        qrcode =findViewById(R.id.qrcode);
        generate =findViewById(R.id.qrgenerate);
        //redeem = findViewById(R.id.qrgenerate);

        getData();
        String restaurant = getIntent().getStringExtra("resname");
        String roffer = getIntent().getStringExtra("roffer");
        String rimage = getIntent().getStringExtra("image");
        String rvid = getIntent().getStringExtra("vid");
        resname.setText(restaurant);
        offer.setText(roffer);
        Glide.with(getApplicationContext()).load(rimage).into(vimage);

        generate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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

                userID = fAuth.getCurrentUser().getUid();
                DocumentReference documentReference= fStore.collection("Users").document(userID);
//                    Toast.makeText(QR_Generator.this,userID,Toast.LENGTH_SHORT).show();
                String qrcontent = restaurant +""+ roffer+""+userID+""+rvid+""+username;

                Toast.makeText(VoucherQR.this,qrcontent,Toast.LENGTH_SHORT).show();
                qrgEncoder = new QRGEncoder(qrcontent, null, QRGContents.Type.TEXT, dimen);
                try {
                    // getting our qrcode in the form of bitmap.
                    bitmap = qrgEncoder.encodeAsBitmap();
                    // the bitmap is set inside our image
                    // view using .setimagebitmap method.
                    qrcode.setImageBitmap(bitmap);
                } catch (WriterException e) {
                    // this method is called for
                    // exception handling.
                    Log.e("Tag", e.toString());
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
}