package com.maturano.alexis.alexismaturanotest;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.maturano.alexis.alexismaturanotest.adapter.CursorMoviesAdapter;
import com.maturano.alexis.alexismaturanotest.utils.DataBaseHelper;

public class Movies extends AppCompatActivity {
    private final String TAG = "[Movies.java]";
    private ListView movies_listview;
    private String cityName;
    private String idComplejo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies);
        final Intent intent = getIntent();
        idComplejo = intent.getStringExtra("ID_COMPLEJO");
        cityName = intent.getStringExtra("CITY_NAME");

        Log.e(TAG, "id Complejo = " + idComplejo);
        movies_listview = (ListView)findViewById(R.id.listview_moview);

        DataBaseHelper db = new DataBaseHelper(getApplicationContext(), (cityName + ".db"));

        try {
            db.openDataBase();
            final Cursor result = db.execute_query("select distinct c.idPelicula as _id, p.titulo, c.idComplejo from Cartelera c inner join Pelicula p on p.Id = c.IdPelicula AND c.IdComplejo = " + idComplejo);
            CursorMoviesAdapter adapter = new CursorMoviesAdapter(getApplicationContext(), R.layout.row_layout_movies, result, 0);
            movies_listview.setAdapter(adapter);
            movies_listview.setOnItemClickListener(new AdapterView.OnItemClickListener(){
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    String position = (String)view.getTag();
                    Log.i(TAG, "idPelicula = " + position);
                    Toast msg = Toast.makeText(getApplicationContext(), ("The id from this Movie is :" + position), Toast.LENGTH_LONG);
                    msg.show();
                }
            });
        }
        catch (SQLException e){
            throw  e;
        }


    }
}
