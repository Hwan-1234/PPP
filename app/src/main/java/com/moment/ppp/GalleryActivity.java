package com.moment.ppp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Date;

public class GalleryActivity extends AppCompatActivity {
    ListView lv;
    ListViewAdapter listViewAdapter;
    ArrayList<List_Item> list_itemArrayList=new ArrayList<List_Item>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        lv=findViewById(R.id.lv);


    }

        public void clickBtn(View view) {
            Intent intent=new Intent(this,GActivity.class);
            startActivityForResult(intent,10);

        }
}
