package com.maturano.alexis.alexismaturanotest.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.widget.ResourceCursorAdapter;
import android.widget.TextView;

import com.maturano.alexis.alexismaturanotest.R;

/**
 * Created by alexismaturano on 5/28/17.
 */

public class CursorMoviesAdapter  extends ResourceCursorAdapter{
    final String column_name = "Titulo";
    final String colum_idMovie = "_id";
    Context context;

    public CursorMoviesAdapter(Context context, int layout, Cursor c, int flags) {
        super(context, layout, c, flags);
        this.context = context;
    }


    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView movieName = (TextView)view.findViewById(R.id.lbl_moview_name);

        String data_column_name = cursor.getString(cursor.getColumnIndexOrThrow(column_name));
        String data_column_idMovie = cursor.getString((cursor.getColumnIndexOrThrow(colum_idMovie)));

        movieName.setText(data_column_name);
        view.setTag(data_column_idMovie);
    }
}
