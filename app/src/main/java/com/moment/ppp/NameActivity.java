package com.moment.ppp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.loader.content.CursorLoader;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.android.volley.request.SimpleMultiPartRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.moment.ppp.H.REQUEST_CODE;
import static com.moment.ppp.H.profileUrl;

public class NameActivity extends AppCompatActivity {
    public ImageView iv;
    public EditText et_name, et_profileMsg, et_num;
    Uri img;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference ref = firebaseDatabase.getReference();
    DatabaseReference userRef = ref.child("User").child("Player");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name);
        iv = findViewById(R.id.iv);
        et_name = findViewById(R.id.et_name);
        et_profileMsg = findViewById(R.id.et_ProfileMsg);
        et_num = findViewById(R.id.et_num);


        //동적퍼미션 작업
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int permissionResult = checkSelfPermission(Manifest.permission.ACCESS_MEDIA_LOCATION);
            if (permissionResult == PackageManager.PERMISSION_DENIED) {
                String[] permissions = new String[]{Manifest.permission.ACCESS_MEDIA_LOCATION, Manifest.permission.WRITE_EXTERNAL_STORAGE};
                requestPermissions(permissions, 10);
            } else {
                iv.setVisibility(View.VISIBLE);
            }
        }


        iv.setOnClickListener(new View.OnClickListener() { // 이미지 뷰 눌러서 이미지 가져오기-107
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });
    }


    public void profileSave(View view) {// 저장 버튼
        loadData();
        saveData();
        dataSave();
        savdImg();
        Toast.makeText(NameActivity.this, "프로필 저장완료", Toast.LENGTH_SHORT).show();
        start();
    }


    public void start() {//StartActivity 넘어가기
        Intent intent = new Intent(this, StartActivity.class);
        startActivity(intent);
    }

    public void saveData() { // 프로필 정보 H 에 저장.

        H.name = et_name.getText().toString();
        H.ProfileMsg = et_profileMsg.getText().toString();
        H.num = et_num.getText().toString();

    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_CODE:
                if (resultCode == RESULT_OK) {
                    img = data.getData();
                    Glide.with(NameActivity.this).load(img).into(iv);
                }
                break;
        }

    }

    public void savdImg() {
        if (profileUrl!=null){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");
        String fileName = sdf.format(new Date()) + ".png";

        //FireBaseStorage 에 저장하기
        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
        final StorageReference imgRef = firebaseStorage.getReference("profileImages/" + fileName);

        //파일 업로드

          UploadTask uploadTask = imgRef.putFile(img);
          uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                imgRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        H.profileUrl = uri.toString();
                        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                        DatabaseReference profileRef = firebaseDatabase.getReference("profiles");
                        profileRef.child(H.name).setValue(H.profileUrl);
                        SharedPreferences preferences = getSharedPreferences("account", MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString("ame", H.name);
                        editor.putString("profileUrl", H.profileUrl);
                        editor.commit();
                        finish();
                    }
                });
            }
        });}

    }

    void loadData() {
        SharedPreferences preferences = getSharedPreferences("account", MODE_PRIVATE);
        H.name = preferences.getString("name", null);
        H.profileUrl = preferences.getString("profileUrl", null);
    }

    void dataSave() {
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User setUser = new User(H.name,H.num,H.ProfileMsg);
                userRef.setValue(setUser);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}