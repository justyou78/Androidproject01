package org.techtown.linktest01;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.*;
import java.util.ArrayList;
import java.util.List;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import static org.techtown.linktest01.R.drawable.person;

/**
 * Created by 안동규 on 2018-01-09.
 */

public class MylistAdapter  extends ArrayAdapter<String> {

    MylistAdapter(Context context, List<String> items)
    {
        super(context, R.layout.list_view, items);

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater imageInflater = LayoutInflater.from(getContext());
        View view = imageInflater.inflate(R.layout.list_view, parent,false);
        String item = getItem(position);
        TextView textView = (TextView) view.findViewById(R.id.textView3);
        ImageView imageView = (ImageView) view.findViewById(R.id.imageView);
        TextView textView2 = (TextView) view.findViewById(R.id.textView4);
        Random r= new Random();
        int selnum = r.nextInt(4);
        ImageView imageView2 = (ImageView) view.findViewById(R.id.imageView2);

        String Test02[] = new String[2];
        Test02 =item.split("!");

        switch(selnum){
            case 0:
                imageView.setImageResource(R.drawable.person1);
                break;
            case 1:
                imageView.setImageResource(R.drawable.person2);
                break;
            case 2:
                imageView.setImageResource(R.drawable.person3);
                break;
            case 3:
                imageView.setImageResource(R.drawable.person4);
                break;


        }
        textView.setText(Test02[0]);
        int time_dev = Integer.parseInt(Test02[1]);
        if(time_dev>=30){
            imageView2.setImageResource(R.drawable.redlight);
        }
        else if(time_dev>=7){
            imageView2.setImageResource(R.drawable.yellowlight);
        }
        else{
            imageView2.setImageResource(R.drawable.greenlight);
        }

        textView2.setText(Test02[1]+"일");

        return view;
    }
}

class CalDate{
    long Days;
    public void doDiffOfDate(String start){


        String inDate   = new java.text.SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date());

        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date beginDate = formatter.parse(start);
            Date endDate = formatter.parse(inDate);

            // 시간차이를 시간,분,초를 곱한 값으로 나누면 하루 단위가 나옴
            long diff = endDate.getTime() - beginDate.getTime();
            long diffDays = diff / (24 * 60 * 60 * 1000);
            new java.text.SimpleDateFormat("yyyyMMdd").format(new java.util.Date());



            Days = diffDays;

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
    public long SendDate(){
        return Days;
    }
}
