package com.moment.ppp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ApiActivity extends AppCompatActivity {
    String apiKey="e9480143a4328b3336e2371538625e63";

    ListView listView;
    ArrayAdapter adapter;

    ArrayList<String> items= new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_api);

        listView= findViewById(R.id.listview);
        adapter= new ArrayAdapter(this, android.R.layout.simple_list_item_1, items);
        listView.setAdapter(adapter);
    }

    public void clickBtn(View view) {
        new Thread(){
            @Override
            public void run() {
                items.clear();

                Date date= new Date();
                date.setTime( date.getTime()-(1000*60*60*24));
                SimpleDateFormat sdf= new SimpleDateFormat("yyyyMMdd");
                String dateStr= sdf.format(date);

                String address="http://www.kobis.or.kr/kobisopenapi/webservice/rest/boxoffice/searchDailyBoxOfficeList.xml"
                        +"?key="+apiKey
                        +"&targetDt="+dateStr
                        +"&itemPerPage=10";

                try {
                    URL url= new URL(address);

                    InputStream is= url.openStream();
                    InputStreamReader isr= new InputStreamReader(is);

                    XmlPullParserFactory factory= XmlPullParserFactory.newInstance();
                    XmlPullParser xpp= factory.newPullParser();
                    xpp.setInput(isr);

                    int eventType= xpp.getEventType();

                    String tagName;
                    StringBuffer buffer= null;

                    while(eventType!=XmlPullParser.END_DOCUMENT){

                        switch (eventType){
                            case XmlPullParser.START_DOCUMENT:

                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(ApiActivity.this, "예비", Toast.LENGTH_SHORT).show();
                                    }
                                });

                                break;

                            case XmlPullParser.START_TAG:
                                tagName= xpp.getName();

                                if(tagName.equals("dailyBoxOffice")){
                                    buffer= new StringBuffer();
                                }else if(tagName.equals("rank")){
                                    buffer.append("순위:");
                                    xpp.next();
                                    buffer.append(xpp.getText()+"\n");

                                }else if(tagName.equals("movieNm")){
                                    buffer.append("제목:");
                                    xpp.next();
                                    buffer.append(xpp.getText()+"\n");

                                }else if(tagName.equals("openDt")){
                                    buffer.append("개봉일:");
                                    xpp.next();
                                    buffer.append(xpp.getText()+"\n");

                                }else if(tagName.equals("audiAcc")){
                                    buffer.append("누적관객수:");
                                    xpp.next();
                                    buffer.append(xpp.getText()+"\n");
                                }

                                break;

                            case XmlPullParser.TEXT:
                                break;

                            case XmlPullParser.END_TAG:
                                tagName= xpp.getName();
                                if( tagName.equals("dailyBoxOffice")){

                                    items.add(buffer.toString());

                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            adapter.notifyDataSetChanged();
                                        }
                                    });
                                }
                                break;
                        }

                        eventType= xpp.next();
                    }//while..

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(ApiActivity.this, "END", Toast.LENGTH_SHORT).show();
                        }
                    });


                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (XmlPullParserException e) {
                    e.printStackTrace();
                }


            }//run method...
        }.start();
    }
}
