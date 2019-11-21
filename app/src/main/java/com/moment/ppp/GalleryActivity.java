package com.moment.ppp;

import androidx.appcompat.app.AppCompatActivity;

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
            list_itemArrayList.add(
                    new List_Item(R.mipmap.ic_launcher,"보라돌이","제목1",new Date(System.currentTimeMillis()),"내용1"));
            list_itemArrayList.add(
                    new List_Item(R.mipmap.ic_launcher,"뚜비","제목2",new Date(System.currentTimeMillis()),"내용2"));
            list_itemArrayList.add(
                    new List_Item(R.mipmap.ic_launcher,"나나","제목3",new Date(System.currentTimeMillis()),"내용3"));
            list_itemArrayList.add(
                    new List_Item(R.mipmap.ic_launcher,"뽀","제목4",new Date(System.currentTimeMillis()),"내용4"));
            list_itemArrayList.add(
                    new List_Item(R.mipmap.ic_launcher,"햇님","제목5",new Date(System.currentTimeMillis()),"내용5"));
            listViewAdapter=new ListViewAdapter(GalleryActivity.this,list_itemArrayList);
            lv.setAdapter(listViewAdapter);
        }
}
