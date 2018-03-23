package com.example.anaya.shopkeeper;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class Updation extends AppCompatActivity {
Button go;
EditText medicine_id,update;
TextView title,availability;
ArrayAdapter setAdapter;
Spinner status;
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
        setAdapter = new ArrayAdapter<String>
                (this, R.layout.support_simple_spinner_dropdown_item,statuses);
        status.setAdapter(setAdapter);
       go.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               if(medicine_id.getText().toString().equals(""))
                   Toast.makeText(getApplicationContext(),"Enter Medicine ID",Toast.LENGTH_SHORT).show();
               else if(update.getText().toString().equals(""))
                   Toast.makeText(getApplicationContext(),"Enter Quantity",Toast.LENGTH_SHORT).show();
                else
                    new Update().execute(medicine_id.getText().toString(),update.getText().toString());
           }
       });

    }
    class Update extends AsyncTask<String,String,String>{

        @Override
        protected String doInBackground(String... strings) {
            return null;
        }
    }
}
