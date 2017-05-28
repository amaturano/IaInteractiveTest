package com.maturano.alexis.alexismaturanotest;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;

import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
//import android.support.design.widget.Snackbar;
import android.widget.ListView;
import android.widget.Toast;

import com.maturano.alexis.alexismaturanotest.adapter.MyArrayAdapter;
import com.maturano.alexis.alexismaturanotest.model.MyDataModel;
import com.maturano.alexis.alexismaturanotest.parser.JSONParser;
import com.maturano.alexis.alexismaturanotest.utils.Keys;
import com.maturano.alexis.alexismaturanotest.utils.InternetConnection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    private  final String TAG = "[MainActivity.java]";
    private ListView listView;
    private ArrayList<MyDataModel> list;
    private MyArrayAdapter adapter;
    public String db_city_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        list = new ArrayList<>();
        adapter = new MyArrayAdapter(this, list);
        listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                db_city_name = list.get(position).getName_city();
                String id_city = list.get(position).getId();

                Log.i(TAG, db_city_name);
                Log.i(TAG, id_city);

                if(doesDatabaseExist(getApplicationContext(), db_city_name)){
                    Log.i(TAG, "This database previusly was installed");
                }
                else{
                    getRomoteDatabase getDB = new getRomoteDatabase(getApplicationContext());
                    getDB.execute(id_city, db_city_name);


                    //copyServerDatabase(db_city_name+".db");

                    /* DataBaseHelper db = new DataBaseHelper(getApplicationContext(), (db_city_name + ".db"));
                    try{
                        db.createDataBase();
                    }
                    catch (IOException ioe){
                        throw new Error("Unable to create database");
                    }

                    try {

                        db.openDataBase();

                    }catch(SQLException sqle){

                        throw sqle;

                    }*/
                }
            }
        });

        if (InternetConnection.checkInternetConnection(getApplicationContext())) {
            new GetDataTask().execute();
        } else {

            Toast msg = Toast.makeText(getApplicationContext(), "Please verify your internet Connection", Toast.LENGTH_LONG);
            msg.setGravity(Gravity.CENTER, 0, 0);
            msg.show();
        }


    }


    class GetDataTask extends AsyncTask<Void, Void, Void> {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.setTitle("Getting data");
            progressDialog.setMessage("Downloading remote data...");
            progressDialog.show();
        }

        @Nullable
        @Override
        protected Void doInBackground(Void... params) {
            String jsonString = JSONParser.getDataFromServer();

            if (jsonString != null && jsonString.length() > 0) {


                try {

                    JSONArray array = new JSONArray(jsonString);
                    int lenArray = array.length();

                    if (lenArray > 0) {
                        for (int jIndex = 0; jIndex < lenArray; jIndex++) {
                            MyDataModel model = new MyDataModel();
                            JSONObject innerObject = array.getJSONObject(jIndex);

                            String id = innerObject.getString(Keys.KEY_ID);
                            String id_state = innerObject.getString(Keys.KEY_ID_STATE);
                            String id_country = innerObject.getString(Keys.KEY_ID_COUNTRY);
                            String latitude = innerObject.getString(Keys.KEY_LATITUDE);
                            String longitude = innerObject.getString(Keys.KEY_LONGITUDE);
                            String name_state = innerObject.getString(Keys.KEY_NAME_STATE);
                            String name_country = innerObject.getString(Keys.KEY_NAME_COUNTRY);
                            String city_name = innerObject.getString(Keys.KEY_NAME_CITY);

                            model.setId(id);
                            model.setId_estate(id_state);
                            model.setId_country(id_country);
                            model.setLatitude(latitude);
                            model.setLongitude(longitude);
                            model.setName_estate(name_state);
                            model.setName_estate(name_country);
                            model.setName_city(city_name);

                            list.add(model);

                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progressDialog.dismiss();
            if (list.size() > 0) {
                adapter.notifyDataSetChanged();
            } else {
                Toast msg = Toast.makeText(getApplicationContext(), "No data found", Toast.LENGTH_LONG);
            }
        }

    }

    private class getRomoteDatabase extends AsyncTask<String, Void, String>{

        private Context context;
        ProgressDialog activityIndicator ;

        public getRomoteDatabase(Context contex){
            this.context = contex;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            activityIndicator = new ProgressDialog(MainActivity.this);
            activityIndicator.setTitle("Getting data");
            activityIndicator.setMessage("Downloading database...");
            activityIndicator.show();

        }

        @Override
        protected String doInBackground(String... strings) {

            String id = strings[0];
            String cityName = strings[1];
            String current_db_name = cityName + ".db" ;

            Log.i(TAG, " getRemoteDatabase -> doInBackground = id recived " + id);
            Log.i(TAG, " getRemoteDatabase -> doInBackground = city name recived " + cityName);

            try {
                URL url = new URL("http://api.cinepolis.com.mx/sqlite.aspx?idCiudad=" + id);
                URLConnection urlConnection = url.openConnection();

                InputStream  input_stream = urlConnection.getInputStream();
                BufferedInputStream bufer_InputStream = new BufferedInputStream(input_stream);

                ByteArrayOutputStream byte_array_buffer = new ByteArrayOutputStream(50);
                byte [] data = new byte[50];
                int current = 0;
                while((current = bufer_InputStream.read(data, 0, data.length)) != -1){
                    byte_array_buffer.write(data, 0, current);
                }


                FileOutputStream fos = null;
                fos = context.openFileOutput(current_db_name, Context.MODE_PRIVATE);
                fos.write(byte_array_buffer.toByteArray());


                fos.close();


            } catch (IOException e) {
                Log.i(TAG, "Download database error IOException : " + e);
                return "fail";

            } catch (NullPointerException e){
                Log.i(TAG, "Download database error NullPointerException : " + e);
                return "fail";

            }catch (Exception e){
                Log.i(TAG, "Download database error Exception : " + e);
                return "fail";
            }

            Log.i(TAG, "DATABASE WAS INSTALLED SUCCESSFUL");
            return "ok";
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            activityIndicator.dismiss();
            Log.e(TAG, "Valor de S = "+ s);

            Intent intent = new Intent(MainActivity.this, Complejos.class);
            intent.putExtra("CITY_NAME", db_city_name);
            startActivity(intent);

            if (s == "ok") {
                Toast msg = Toast.makeText(getApplicationContext(), "Database was installed successful", Toast.LENGTH_LONG);
                msg.show();
            } else {
                Toast msg = Toast.makeText(getApplicationContext(), "Something was wrong", Toast.LENGTH_LONG);
                msg.show();
            }
        }
    }


    private  boolean doesDatabaseExist(Context context, String dbName) {

        File dbFile = context.getDatabasePath(dbName + ".db");
        Log.i(TAG, "DATA BASE NAME FULL PATH = " + dbFile);

        return dbFile.exists();
    }

    public void copyServerDatabase(String db_name) {


       Context mContext = getApplicationContext();
        //SQLiteDatabase db = getReadableDatabase() ;
       // db.close();


        OutputStream os = null;
        InputStream is = null;
        try {
            // Log.d(TAG, "Copying DB from server version into app");
            is = mContext.openFileInput(db_name);
            os = new FileOutputStream("/data/data/com.maturano.alexis.alexismaturanotest/databases/");

            copyFile(os, is);
        } catch (Exception e) {
            Log.e(TAG, "Server Database was not found - did it download correctly?", e);
        } finally {
            try {
                //Close the streams
                if(os != null){
                    os.close();
                }
                if(is != null){
                    is.close();
                }
            } catch (IOException e) {
                Log.e(TAG, "failed to close databases");
            }
        }
        Log.d(TAG, "Done Copying DB from server");
    }




    private void copyFile(OutputStream os, InputStream is) throws IOException {
        byte[] buffer = new byte[1024];
        int length;
        while((length = is.read(buffer))>0){
            os.write(buffer, 0, length);
        }
        os.flush();
    }


}


