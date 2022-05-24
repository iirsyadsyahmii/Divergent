package com.example.login;

import androidx.annotation.NonNull;
        import androidx.annotation.Nullable;
        import androidx.appcompat.app.AppCompatActivity;

        import android.content.Intent;
        import android.net.Uri;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
        import android.widget.Toast;

        import com.bumptech.glide.Glide;
        import com.google.android.gms.tasks.OnCompleteListener;
        import com.google.android.gms.tasks.OnSuccessListener;
        import com.google.android.gms.tasks.Task;
        import com.google.firebase.auth.FirebaseAuth;
        import com.google.firebase.firestore.DocumentReference;
        import com.google.firebase.firestore.DocumentSnapshot;
        import com.google.firebase.firestore.FirebaseFirestore;
        import com.google.firebase.storage.FirebaseStorage;
        import com.google.firebase.storage.StorageReference;
        import com.google.firebase.storage.UploadTask;

        import java.net.URL;
        import java.text.SimpleDateFormat;
        import java.util.Calendar;
        import java.util.HashMap;

public class Upload_Voucher extends AppCompatActivity {
    EditText res,offer,des,point;
    Button upload;
    ImageView vimage;
    FirebaseStorage storage;
    FirebaseAuth auth;
    FirebaseFirestore fstore;
    String vimageUri,resname;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_voucher);

        res = findViewById(R.id.et_restaurant);
        offer = findViewById(R.id.et_name);
        des = findViewById(R.id.et_description);
        point = findViewById(R.id.et_minpoints);
        upload=findViewById(R.id.btn_upload);
        vimage=findViewById(R.id.imageViewVoucher);
        progressBar=findViewById(R.id.progressBar3);
        storage =FirebaseStorage.getInstance();
        auth=FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();
        fstore.collection("Users").document(auth.getCurrentUser().getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.getResult().exists()) {
                    resname = task.getResult().getString("Nickname");
                    res.setText(resname);
                    Toast.makeText(Upload_Voucher.this,resname,Toast.LENGTH_SHORT);
                }
            }
        });

        vimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent,33);
            }
        });
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadVoucher();
            }
        });
    }
    private void uploadVoucher() {
        String saveCurrentTime, saveCurrentDate;

        Calendar calForDate = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat ("MM dd yyyy");
        saveCurrentDate = currentDate.format(calForDate.getTime());

        if(vimageUri==null){
            Toast.makeText(Upload_Voucher.this,"Please upload an image.", Toast.LENGTH_SHORT).show();
            return;
            //vimageUri="https://firebasestorage.googleapis.com/v0/b/login-10810.appspot.com/o/voucherImages%2FNo-Image-Placeholder.svg.png?alt=media&token=e7ff8bee-1cc7-4051-b7dd-7b473cd43d4b";
        }
        final HashMap<String,Object> cartMap = new HashMap<>();

        cartMap.put("Image",vimageUri);
        cartMap.put("Offer",offer.getText().toString());
        cartMap.put("Restaurant",resname);
        cartMap.put("Description",des.getText().toString());
        cartMap.put("Points",point.getText().toString());
        cartMap.put("Current Date",saveCurrentDate);

        fstore.collection("Uvoucher").add(cartMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {

            }
        });
        //Add to User Voucher
        fstore.collection("Users").document(auth.getCurrentUser().getUid())
                .collection("UploadedVoucher").add(cartMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {
                Toast.makeText(Upload_Voucher.this,"Upload Successfully!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent (Upload_Voucher.this, Your_Voucher.class);
                startActivity(intent);
                //finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(data.getData()!=null){
            progressBar.setVisibility(View.VISIBLE);
            Uri voucherUri = data.getData();
            vimage.setImageURI(voucherUri);

            final StorageReference reference = storage.getReference().child("voucherImages")
                    .child(auth.getUid());
            reference.putFile(voucherUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(Upload_Voucher.this,"Uploaded Image Successfully",Toast.LENGTH_SHORT).show();

                    reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            vimageUri=uri.toString();

                        }
                    });
                }
            });
        }
    }
}