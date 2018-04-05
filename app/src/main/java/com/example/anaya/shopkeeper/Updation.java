package com.example.anaya.shopkeeper;

import android.graphics.Typeface;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

public class Updation extends AppCompatActivity {
Button go;
EditText medicine_id,update;
TextView title,availability;
ArrayAdapter setAdapter;
Spinner status;
Typeface typeface;
int con = 0;

    String[] statuses = {"Available","Not Available"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updation);
        go = findViewById(R.id.submit);
        medicine_id = findViewById(R.id.medid);
        update = findViewById(R.id.stockno);
        title = findViewById(R.id.update);
        availability = findViewById(R.id.avail);
        status = findViewById(R.id.spinner);
        typeface = Typeface.createFromAsset(getAssets(),"fonts/font2.ttf");

        medicine_id.setTypeface(typeface);
        update.setTypeface(typeface);

        availability.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/font1.ttf"));

        setAdapter = new ArrayAdapter<String>
                (this, R.layout.support_simple_spinner_dropdown_item,statuses);
        status.setAdapter(setAdapter);
       go.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
              if(medicine_id.getText().toString().equals("") )
                   Toast.makeText(getApplicationContext(),"Enter Medicine ID",Toast.LENGTH_SHORT).show();
              else if(!medicine_id.getText().toString().startsWith("M-"))
                   Toast.makeText(getApplicationContext(),"Medicine-ID Starts with 'M-' ",Toast.LENGTH_SHORT).show();
              else if(update.getText().toString().equals("") && con == 0)
                   Toast.makeText(getApplicationContext(),"Enter Quantity",Toast.LENGTH_SHORT).show();
              else
                    new Update().execute(medicine_id.getText().toString(),update.getText().toString(),status.getSelectedItem().toString());
           }
       });

       status.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
           @Override
           public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               if(position == 1) {
                   update.setVisibility(View.INVISIBLE);
                   con = 0;
               } else {
                   update.setVisibility(View.VISIBLE);
                   con = 1;
               }
           }

           @Override
           public void onNothingSelected(AdapterView<?> parent) {

           }
       });


    }
    class Update extends AsyncTask<String,String,String>{
        String result;
        String URL = new getUrl().setUrl("update.php"); //GET
        JSONParserArray jParser = new JSONParserArray();
        JSONArray jArray;
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        @Override
        protected String doInBackground(String... val) {

            params.add(new BasicNameValuePair("medid",val[0]));

            params.add(new BasicNameValuePair("status",val[2]));
            if(con == 0) {
                params.add(new BasicNameValuePair("av", "NA"));
            }     else {
                params.add(new BasicNameValuePair("av", "AV"));
                params.add(new BasicNameValuePair("qty",val[1]));
            }
            params.add(new BasicNameValuePair("shopid",getIntent().getExtras().getString("username")));


            jArray = jParser.makeHttpRequest(URL, "GET", params);
            try {

                Log.d("Response",jArray.getJSONArray(0).getString(0));
                result = jArray.getJSONArray(0).getString(0);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(result.equals("updated")) {
                Toast.makeText(getApplicationContext(), "Update Success", Toast.LENGTH_SHORT).show();
                medicine_id.setText("");
                update.setText("");
            }
            else
                Toast.makeText(getApplicationContext(), "Invalid Medicine ID/(Server Error)", Toast.LENGTH_SHORT).show();

            }
    }
}
