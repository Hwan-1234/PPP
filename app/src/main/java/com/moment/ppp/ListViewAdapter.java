package com.moment.ppp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ListViewAdapter extends BaseAdapter {
    Context context;
    ArrayList<List_Item> list_itemArrayList;
    TextView name_textView;
    TextView title_textView;
    TextView date_textView;
    ImageView profile_imageView;

    public ListViewAdapter(Context context, ArrayList<List_Item> list_itemArrayList) {
        this.context = context;
        this.list_itemArrayList = list_itemArrayList;
    }

    @Override
    public int getCount() {
        return list_itemArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return list_itemArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
      if (convertView==null){
          convertView= LayoutInflater.from(context).inflate(R.layout.listviewitem,null);
          name_textView=(TextView)convertView.findViewById(R.id.tv_item_name);
          title_textView=(TextView)convertView.findViewById(R.id.tv_item_title);
          profile_imageView=convertView.findViewById(R.id.iv_imageview);
          date_textView=(TextView)convertView.findViewById(R.id.tv_date);
      }
      name_textView.setText(list_itemArrayList.get(position).getName());
      title_textView.setText(list_itemArrayList.get(position).getTitle());
      date_textView.setText(list_itemArrayList.get(position).getWrite_date().toString());
      profile_imageView.setImageResource(list_itemArrayList.get(position).getProfile_image());

        return convertView;
    }
}
