package com.moment.ppp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.InputStream;


public class StartActivity extends AppCompatActivity {

    Button chat,gallery,api,user;
    ImageButton id;
    TextView tvmy,tva,tvday;
    ImageView myiv,aiv;
    boolean isFirst = true;//앱 처음 실행?
    boolean isChanged = false;//프로필 업데이트 한 적 있?

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        id=findViewById(R.id.id);
        user=findViewById(R.id.user);
        loadData();
        id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, H.REQUEST_CODE);
                int permissionCheck = ContextCompat.checkSelfPermission(StartActivity.this, Manifest.permission.WRITE_CALENDAR);

                if(permissionCheck== PackageManager.PERMISSION_DENIED){

                    // 권한 없음
                }else{
                    // 권한 있음
                }
            }
        });
        loadData();


    }    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == H.REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                try {
                    InputStream in = getContentResolver().openInputStream(data.getData());

                    Bitmap img = BitmapFactory.decodeStream(in);
                    in.close();

                    id.setImageBitmap(img);
                } catch (Exception e) {

                }
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "사진 선택 취소", Toast.LENGTH_LONG).show();
            }
        }
    }


    public void Chat(View view) {
        Intent intent=new Intent(this,ChatActivity.class);
        startActivityForResult(intent,10);

    }

    public void Gallery(View view) {

    }

    public void API(View view) {
        Intent intent=new Intent(this,ApiActivity.class);
        startActivityForResult(intent,10);

    }

    public void Img(View view) {





    }



    public void User(View view) {
        Intent intent = new Intent(this,NameActivity.class);
        startActivityForResult(intent,10);


    }
    //내 phone 에 저장되어 있는 프로필정보 읽어오기
    public void loadData() {
        SharedPreferences preferences = getSharedPreferences("account", MODE_PRIVATE);
        H.name = preferences.getString("name", null);
        H.profileUrl = preferences.getString("profileUrl", null);

    }


}


