package com.moment.ppp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Date;

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
    }

    public void profileSave(View view) {
        myiv=H.Profileiv;
        saveData();
        loadData();
        start();


    }

public void loadData(){

    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");
    String fileName = sdf.format(new Date()) + ".png";
    FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
    imgRef = firebaseStorage.getReference("profileImages/" + fileName);

}

public void start(){
    Intent intent=new Intent(this,StartActivity.class);
    startActivityForResult(intent,10);

}
public void saveData(){
    H.name=et_name.getText().toString();
    H.ProfileMsg=et_profileMsg.getText().toString();
    H.yyhhmm=et_yyhhmm.getText().toString();
    Toast.makeText(this, "저장완료", Toast.LENGTH_SHORT).show();

    UploadTask uploadTask = imgRef.putFile(imgUri);
    uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
        @Override
        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
            // 이미지 업로드가 성공되었으므로 곧 바로 FireBaseStorage 의 이미지 파일 다운로드 URL 얻어오기
            imgRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    // 파라미터로 FireBase 저장소에 저장되어있는 이미지에 대한 다운로드 주소[URL]를 문자열로 얻어오기
                    H.profileUrl = uri.toString();

//                        ******//1. FireBase DataBase 에 Name,profileUrl 저장
                    //FireBase DB 관리자 객체 소환
                    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                    //'profiles' 라는 이름의 자식노드 참조객체 얻어오기
                    DatabaseReference profileRef = firebaseDatabase.getReference("profiles");

                    //Name 을 Key 식별자로, profileImage 주소를 값으로 저장
                    profileRef.child(H.name).setValue(H.profileUrl);


//                        *****//2. 내 phone 에 Name,profileUrl 저장

                    SharedPreferences preferences = getSharedPreferences("account", MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();

                    editor.putString("name", H.name);
                    editor.putString("profileUrl", H.profileUrl);
                    editor.commit();


                }
            });
        }
    });

}

    public void Img(View view) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_PICK);

        startActivityForResult(intent, H.REQUEST_CODE);
        int permissionCheck = ContextCompat.checkSelfPermission(NameActivity.this, Manifest.permission.WRITE_CALENDAR);

        if(permissionCheck== PackageManager.PERMISSION_DENIED){

            // 권한 없음
        }else{
            // 권한 있음
        }
    ///TODO
        //이미지 설정
    }


}