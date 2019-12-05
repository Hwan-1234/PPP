package com.moment.ppp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;

public class ChatActivity extends AppCompatActivity {
    ListView chatView;
    EditText et;
    ChatAdapter adapter;
    ArrayList<msgItem> msgItems = new ArrayList<>();
    private FirebaseDatabase firebasedatabase = null;
    private DatabaseReference chatRef = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        firebasedatabase = FirebaseDatabase.getInstance();
        chatRef = firebasedatabase.getReference().child("chat");

        et = findViewById(R.id.et);
        chatView = findViewById(R.id.listview);
        adapter = new ChatAdapter(msgItems, getLayoutInflater());
        chatView.setAdapter(adapter);
//        loadUser();
        chatRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                msgItem msgItem = dataSnapshot.getValue(com.moment.ppp.msgItem.class);
                msgItems.add(msgItem);
                adapter.notifyDataSetChanged();
                chatView.setSelection(msgItems.size() - 1);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    public void clickSend(View view) {
        String name = H.name;// 프로필 불러와서 채팅창 표시
        String message = et.getText().toString();
        String profileUrl = H.profileUrl;

        Calendar calendar = Calendar.getInstance();//시간 설정
        String time = calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE);

        msgItem msgItem = new msgItem(name, message, time, profileUrl);
        chatRef.push().setValue(msgItem);
        et.setText("");


        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);


    }
//private void loadUser(){
//    SharedPreferences preferences= getSharedPreferences("account", MODE_PRIVATE);
//    H.name= preferences.getString("name", null);
//    H.profileUrl= preferences.getString("profileUrl", null);
//}

}