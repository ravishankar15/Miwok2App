package com.example.ravi.Adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ravi.CustomView.Words;
import com.example.ravi.miwok.R;

import java.util.ArrayList;

/**
 * Created by Ravi on 31-07-2016.
 */
public class WordAdapter extends ArrayAdapter<Words> {

    private int Resourseid;

    public WordAdapter(Context context, int Resourseid, ArrayList<Words> numbers) {
        super(context,Resourseid,numbers);
        this.Resourseid = Resourseid;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Words currentnumber = getItem(position);

        View listitemview = convertView;
        if(listitemview == null){
            listitemview = LayoutInflater.from(getContext()).inflate(R.layout.word_listitem_view,parent,false);
        }

        TextView defaultword = (TextView) listitemview.findViewById(R.id.default_text_view);
        defaultword.setText(currentnumber.getDefaultword());

        TextView translateword = (TextView) listitemview.findViewById(R.id.miwok_text_view);
        translateword.setText(currentnumber.getTranslatedword());


        ImageView imageword = (ImageView) listitemview.findViewById(R.id.miwok_image_view);
        if(currentnumber.hasImage()){
            imageword.setImageResource(currentnumber.getImageresourseid());
            imageword.setVisibility(View.VISIBLE);
        }else{
            imageword.setVisibility(View.GONE);
        }

        View textcontainer = listitemview.findViewById(R.id.layout_text_view);

        int color = ContextCompat.getColor(getContext(), Resourseid);

        textcontainer.setBackgroundColor(color);


        return listitemview;

    }
}
