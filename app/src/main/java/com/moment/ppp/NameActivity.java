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
    FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
    DatabaseReference ref=firebaseDatabase.getReference();
    DatabaseReference userRef=ref.child("User").push();
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
        loadData();
        dataSave();
    }


    public void profileSave(View view) {// 저장 버튼
        saveData();
        dataSave();
        savdImg();

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
                // 이미지 업로드가 성공되었으므로 곧 바로 FireBaseStorage 의 이미지 파일 다운로드 URL 얻어오기
                imgRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        // 파라미터로 FireBase 저장소에 저장되어있는 이미지에 대한 다운로드 주소[URL]를 문자열로 얻어오기
                        H.profileUrl = uri.toString();
                        Toast.makeText(NameActivity.this, "프로필 저장완료", Toast.LENGTH_SHORT).show();

//                        ******//1. FireBase DataBase 에 nickName,profileUrl 저장
                        //FireBase DB 관리자 객체 소환
                        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                        //'profiles' 라는 이름의 자식노드 참조객체 얻어오기
                        DatabaseReference profileRef = firebaseDatabase.getReference("profiles");

                        //nickName 을 Key 식별자로, profileImage 주소를 값으로 저장
                        profileRef.child(H.name).setValue(H.profileUrl);


//                        *****//2. 내 phone 에 nickName,profileUrl 저장

                        SharedPreferences preferences = getSharedPreferences("account", MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();

                        editor.putString("ame", H.name);
                        editor.putString("profileUrl", H.profileUrl);
                        editor.commit();

                        //저장 완료, ChatActivity 전환
                        Intent intent = new Intent(NameActivity.this, ChatActivity.class);
                        startActivity(intent);
                        finish();


                    }
                });
            }
        });

    }

    //내 phone 에 저장되어 있는 프로필정보 읽어오기
    void loadData() {
        SharedPreferences preferences = getSharedPreferences("account", MODE_PRIVATE);
        H.name = preferences.getString("name", null);
        H.profileUrl = preferences.getString("profileUrl", null);
    }
    //이름,전화번호 등 정보 DataBase 에 넣기
    void dataSave(){
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User setUser= new User(H.name,H.num);
                userRef.setValue(setUser);



            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}