package com.moment.ppp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;


public class StartActivity extends AppCompatActivity {

    Button chat,gallery,api,user,a;
    ImageButton id,myid,aid;
    TextView tvmy,tva,tvday;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        id=findViewById(R.id.id);

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

    }

    public void A(View view) {

    }

    public void aProfile(View view) {

    }

    public void myProfile(View view) {

    }
}


