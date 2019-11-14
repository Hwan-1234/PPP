package com.moment.ppp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
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

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.moment.ppp.H.REQUEST_CODE;

public class NameActivity extends AppCompatActivity {
    public ImageView iv;
    public EditText et_name,et_profileMsg,et_yyhhmm;
    public StorageReference imgRef;
    public Uri imgUri;
    TextView tvmy;
    ImageView myiv;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name);
        tvmy=findViewById(R.id.tvmy);
        myiv=findViewById(R.id.myiv);
        iv=findViewById(R.id.iv);
        et_name=findViewById(R.id.et_name);
        et_profileMsg=findViewById(R.id.et_ProfileMsg);
        et_yyhhmm=findViewById(R.id.et_yyhhmm);


        //동적퍼미션 작업
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            int permissionResult= checkSelfPermission(Manifest.permission.ACCESS_MEDIA_LOCATION);
            if(permissionResult== PackageManager.PERMISSION_DENIED){
                String[] permissions= new String[]{Manifest.permission.ACCESS_MEDIA_LOCATION, Manifest.permission.WRITE_EXTERNAL_STORAGE};
                requestPermissions(permissions, 10);
            }else{
                iv.setVisibility(View.VISIBLE);
            }
        }


        iv.setOnClickListener(new View.OnClickListener(){ // 이미지 뷰 눌러서 이미지 가져오기-107
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                try {
                    InputStream in = getContentResolver().openInputStream(data.getData());

                    Bitmap img = BitmapFactory.decodeStream(in);
                    in.close();

                    iv.setImageBitmap(img);
                } catch (Exception e) {

                }
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "사진 선택 취소", Toast.LENGTH_LONG).show();
            }
        }

    }//이미지

    public void profileSave(View view) {// 저장 버튼
        saveData();
        start();

    }



public void start(){//StartActivity 넘어가기
        Intent intent= new Intent(this,StartActivity.class);
        startActivity(intent);

}
public void saveData(){ // 저장 버튼 클릭 시 이미지, 이름 등 FireBase 에 저장하기


}









}