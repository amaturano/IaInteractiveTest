package com.maturano.alexis.alexismaturanotest.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.maturano.alexis.alexismaturanotest.R;
import com.maturano.alexis.alexismaturanotest.model.MyDataModel;

import java.util.List;

/**
 * Created by alexismaturano on 5/26/17.
 */

public class MyArrayAdapter extends ArrayAdapter<MyDataModel>{
    List<MyDataModel> modelList;
    Context context;
    private LayoutInflater miInflater;

    public MyArrayAdapter(Context context, List<MyDataModel> objects){
        super (context, 0, objects);
        this.context = context;
        this.miInflater = LayoutInflater.from(context);
        modelList = objects;
    }

    @Override
    public MyDataModel getItem(int position){
        return  modelList.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        final ViewHolder vh;
        if(convertView == null){
            View view = miInflater.inflate(R.layout.row_layout_item, parent, false);
            vh = ViewHolder.create((RelativeLayout) view);
            view.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }

        MyDataModel item = getItem(position);
        vh.cityName.setText(item.getName_city());

        return vh.rootView;
    }

    private static class ViewHolder {
        public final RelativeLayout rootView;
        public final TextView cityName;

        private ViewHolder(RelativeLayout rootView, TextView cityName){
            this.rootView = rootView;
            this.cityName = cityName;
        }

        public static ViewHolder create(RelativeLayout rootView) {
            TextView cityName = (TextView) rootView.findViewById(R.id.txt_item);
            return new ViewHolder(rootView, cityName);
        }
    }

}
