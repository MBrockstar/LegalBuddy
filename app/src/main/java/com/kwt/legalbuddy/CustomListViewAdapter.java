package com.kwt.legalbuddy;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import java.util.List;


/**
 * Created by MB on 8/8/2015.
 */
public class CustomListViewAdapter extends ArrayAdapter<Questions> {

    Context context;

    public CustomListViewAdapter(Context context, int resourceId,
                                 List<Questions> items) {
        super(context, resourceId, items);
        this.context = context;
    }

    /*private view holder class*/
    private class ViewHolder {

        TextView QTitle;
        EditText QAns;
    }

    public View getView(final int position, View convertView, ViewGroup parent)  {
         final ViewHolder holder;
        Questions questions = getItem(position);


        LayoutInflater mInflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.list_v, null);
            holder = new ViewHolder();

            holder.QTitle = (TextView) convertView.findViewById(R.id.textView3);
            holder.QAns = (EditText) convertView.findViewById(R.id.editText);
            //holder.QAns.requestFocusFromTouch();
            convertView.setTag(holder);
        } else
            holder = (ViewHolder) convertView.getTag();

        holder.QTitle.setText(questions.getQuest());
        //holder.QAns.setText(questions.getQuest());
        holder.QAns.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                // TODO Auto-generated method stub
                if (!hasFocus){

                    EditText etxt = (EditText) v;
                    holder.QAns.setText(etxt.getText().toString());

                    Toast t=Toast.makeText(getContext(),etxt.getText(),Toast.LENGTH_SHORT);
                    t.show();


                }


            }
        });

        questions.setAns(holder.QAns.getText().toString());

        return convertView;
    }
}