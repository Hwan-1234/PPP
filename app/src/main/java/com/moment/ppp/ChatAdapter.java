package com.moment.ppp;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatAdapter extends BaseAdapter {

    ArrayList<msgItem> messageItems;
    LayoutInflater layoutInflater;

    public ChatAdapter() {
    }

    public ChatAdapter(ArrayList<msgItem> messageItems, LayoutInflater layoutInflater) {
        this.messageItems = messageItems;
        this.layoutInflater = layoutInflater;
    }

    @Override
    public int getCount() {
        return messageItems.size();
    }

    @Override
    public Object getItem(int position) {
        return messageItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        //현재 보여줄 번째의(position) 의 데이터로 뷰 생성
        msgItem item = messageItems.get(position);

        View itemView=null;//재활용할 뷰는 사용하지 않음.

        //메세지가 내 메세지인지?


        if ((item.getName()).equals(H.name)){
            itemView=layoutInflater.inflate(R.layout.my_msgbox,viewGroup,false);
        }else{
            itemView=layoutInflater.inflate(R.layout.a_msgbox,viewGroup,false);
        }

        //만들어진 itemView 에 값 설정
        CircleImageView iv=itemView.findViewById(R.id.iv);
        TextView tvName=itemView.findViewById(R.id.tv_name);
        TextView tvMsg=itemView.findViewById(R.id.tv_msg);
        TextView tvTime=itemView.findViewById(R.id.tv_time);

        tvName.setText(item.getName());
        tvMsg.setText(item.getMsg());
        tvTime.setText(item.getTime());
//        Log.e("Tag",item.getName());
//        Log.e("Tag",item.getMsg());
//        Log.e("Tag",item.getTime());






        Glide.with(itemView).load(item.getProfileUrl()).into(iv);

        return itemView;
    }
}
