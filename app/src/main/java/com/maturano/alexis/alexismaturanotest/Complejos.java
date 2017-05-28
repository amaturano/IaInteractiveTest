package com.maturano.alexis.alexismaturanotest;

import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.SQLException;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.maturano.alexis.alexismaturanotest.adapter.CustomCursorAdapter;
import com.maturano.alexis.alexismaturanotest.utils.DataBaseHelper;

public class Complejos extends AppCompatActivity {
    final String TAG = "[Complejos.java] :";
    private ListView complejos_list;
    FloatingActionButton btn;
    static String cityName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complejos);
        final Intent intent = getIntent();
        cityName = intent.getStringExtra("CITY_NAME");
        final Cursor result;

        complejos_list = (ListView)findViewById(R.id.listView_complejos);

        btn = (FloatingActionButton)this.findViewById(R.id.floatingActionButton2);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, " OPENING MAPS...");
                //Uri gmmIntentUri = Uri.parse("google.streetview:cbll=46.414382,10.013988");
                Intent mapintent = new Intent(Complejos.this, MapActivity.class);
                //mapintent.setPackage("com.google.android.apps.maps");
                //intent.putExtra("CITY_NAME", db_city_name);
                startActivity(mapintent);
            }
        });

        Log.e(TAG, "You do tab in --->" + cityName);

        DataBaseHelper db = new DataBaseHelper(getApplicationContext(), (cityName + ".db"));

        try{
            db.openDataBase();

            result = db.execute_query("SELECT c.*,c.id as _id FROM complejo c ");

            Log.e(TAG, DatabaseUtils.dumpCursorToString(result));

            final CustomCursorAdapter adapter = new CustomCursorAdapter(getApplicationContext(), R.layout.row_layout_complejo_template,result, 0);
            complejos_list.setAdapter(adapter);
            complejos_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    String position=(String)view.getTag();
                    Log.i(TAG, "idComplejo = " + position);
                    Intent movies = new Intent(Complejos.this, Movies.class);
                    movies.putExtra("ID_COMPLEJO",position);
                    movies.putExtra("CITY_NAME", cityName);
                    startActivity(movies);
                }
            });

        }
        catch(SQLException e){
            throw  e;
        }
    }




}
