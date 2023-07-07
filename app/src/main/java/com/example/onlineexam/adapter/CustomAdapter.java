package com.example.onlineexam.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.onlineexam.Activityuserinterface;
import com.example.onlineexam.MainActivity3;
import com.example.onlineexam.R;

import java.util.ArrayList;
import java.util.HashSet;

public class CustomAdapter extends ArrayAdapter<String> {
    private final Context mContext;
    private final ArrayList<String> mTestNumber;
    private final ArrayList<String> score;
    private final ArrayList<String> accuresy;
    private final Activity activity;
    private final HashSet<Integer> mclickableItems; // set of unclickable item positions
    private int subseq;
    String id_value;

    public CustomAdapter(Context context,Activity activity, ArrayList<String> data,ArrayList<String> score,ArrayList<String> accuresy, HashSet<Integer> unclickableItems,String id,int subseq) {
        super(context, 0, data);
        mContext = context;
        this.activity=activity;
        this.subseq=subseq;
        mTestNumber = data;
        this.accuresy=accuresy;
        this.score=score;
        this.id_value=id;
        mclickableItems = unclickableItems;
        System.out.println("lund "+unclickableItems);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.adaptervirw, parent, false);
        }

        TextView textView1 = convertView.findViewById(R.id.text_view_1);
        TextView textView2 = convertView.findViewById(R.id.text_view_2);
        TextView textView3 = convertView.findViewById(R.id.text_view_3);
        textView1.setText(mTestNumber.get(position));
        textView2.setText(score.get(position));
        textView3.setText(accuresy.get(position));
        System.out.println(mTestNumber.get(position));
        if (mclickableItems.contains(position)) {
            convertView.setClickable(true);
            convertView.setEnabled(true);
            convertView.setBackgroundResource(R.color.white);
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, Activityuserinterface.class);
                    intent.putExtra("vaalueofid", position+1);
                    System.out.println("in adaptor id "+id_value);
                    intent.putExtra("id_values",id_value);
                    intent.putExtra("subseq",subseq);
                    mContext.startActivity(intent);
                    activity.overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
                    ((Activity)mContext).finish();

                }
            });
        }

         else {
            convertView.setClickable(false);
            convertView.setEnabled(false);
            convertView.setBackgroundResource(R.color.light_gray);
            convertView.setOnClickListener(null);
        }

        return convertView;
    }
}
