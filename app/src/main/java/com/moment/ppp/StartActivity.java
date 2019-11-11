package com.moment.ppp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class StartActivity extends AppCompatActivity {
    Button chat,gallery,api,user,a;
    ImageButton id,myid,aid;
    TextView tvmy,tva,tvday;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
    }


    public void Chat(View view) {
        Intent intent=new Intent(this,ChatActivity.class);
        startActivity(intent);
    }

    public void Gallery(View view) {
        Intent intent=new Intent(this,GalleryActivity.class);
        startActivity(intent);
    }

    public void API(View view) {
        Intent intent=new Intent(this,ApiActivity.class);
        startActivity(intent);
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
