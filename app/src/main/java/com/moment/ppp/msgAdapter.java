//package com.moment.ppp;
//
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.BaseAdapter;
//import android.widget.TextView;
//
//import com.bumptech.glide.Glide;
//
//import java.util.ArrayList;
//
//import de.hdodenhof.circleimageview.CircleImageView;
//
//public class msgAdapter extends BaseAdapter {
//    ArrayList<msgItem> msgItems;
//    LayoutInflater layoutInflater;
//
//    public msgAdapter() {
//    }
//
//    public msgAdapter(ArrayList<msgItem> msgItems, LayoutInflater layoutInflater) {
//        this.msgItems = msgItems;
//        this.layoutInflater = layoutInflater;
//    }
//
//    @Override
//    public int getCount() {
//        return msgItems.size();
//    }
//
//    @Override
//    public Object getItem(int position) {
//        return msgItems.get(position);
//    }
//
//    @Override
//    public long getItemId(int position) {
//        return position;
//    }
//
//    @Override
//    public View getView(int position, View view, ViewGroup viewGroup) {
//
//        msgItem item=msgItems.get(position);
//        View itemView=null;
//        if (item.getName().equals(H.name)){
//            itemView=layoutInflater.inflate(R.layout.my_msgbox,viewGroup,false);
//
//        }else{
//            itemView=layoutInflater.inflate(R.layout.a_msgbox,viewGroup,false);
//
//        }
//        CircleImageView iv=itemView.findViewById(R.id.iv);
//        TextView tvName=iv.findViewById(R.id.tv_name);
//        TextView tvMsg=iv.findViewById(R.id.tv_msg);
//        TextView tvTime=iv.findViewById(R.id.tv_time);
//
//        Glide.with(itemView).load(item.profileImg).into(iv);
//
//
//        return null;
//    }
//}
