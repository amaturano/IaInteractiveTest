package com.maturano.alexis.alexismaturanotest.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ResourceCursorAdapter;
import android.widget.TextView;

import com.maturano.alexis.alexismaturanotest.R;

/**
 * Created by alexismaturano on 5/27/17.
 */

public class CustomCursorAdapter extends ResourceCursorAdapter {

    final String column_name = "Nombre";
    final String column_address = "Direccion";
    final String colum_idComplejo = "Id";
    Context context;

    public CustomCursorAdapter(Context context, int layout, Cursor c, int flags) {
        super(context, layout, c, flags);
        this.context = context;
    }


    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView complejoName = (TextView)view.findViewById(R.id.complejo_name);
        TextView complejoAddress = (TextView)view.findViewById(R.id.complejo_address);

        String data_column_name = cursor.getString(cursor.getColumnIndexOrThrow(column_name));
        String data_column_address = cursor.getString(cursor.getColumnIndexOrThrow(column_address));
        String data_column_idComplejo = cursor.getString((cursor.getColumnIndexOrThrow(colum_idComplejo)));

        complejoName.setText(data_column_name);
        complejoAddress.setText(data_column_address);
        view.setTag(data_column_idComplejo);


    }
}
