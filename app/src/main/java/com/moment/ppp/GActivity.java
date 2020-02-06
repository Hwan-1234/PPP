package com.moment.ppp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class GActivity extends AppCompatActivity {
    TextView tvTitle,tvDate,tvMsg;
    ImageView iv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_g);
//
        tvTitle=findViewById(R.id.tv_title);
        tvDate=findViewById(R.id.tv_date);
        tvMsg=findViewById(R.id.tv_msg);
        iv=findViewById(R.id.iv);

    }

    public void clickImg(View view) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_PICK);
        startActivityForResult(intent, H.REQUEST_CODE);
        int permissionCheck = ContextCompat.checkSelfPermission(GActivity.this, Manifest.permission.WRITE_CALENDAR);

        if (permissionCheck == PackageManager.PERMISSION_DENIED) {

            // 권한 없음
        } else {
            // 권한 있음
        }

    }

    public void clickSave(View view) {

    }
}
