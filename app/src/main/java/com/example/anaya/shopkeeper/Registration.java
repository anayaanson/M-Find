package com.example.anaya.shopkeeper;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;

import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
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
import android.widget.TimePicker;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.text.SimpleDateFormat;


import java.util.Date;

public class Registration extends AppCompatActivity {
    Spinner spinner, spinner_inside;
    String[] districts = {"Select District", "Trivandrum", "Kollam", "Alappuzha", "Ernakulam"}, places;
    HashMap<String, ArrayList<String>> hashMap;
    ArrayAdapter setAdapter, setInsideAdapter;
    ArrayList<String> arrayList;
    Context context;
    EditText shopname, shopid, password, reenter, mobile;
    TextView check, settime;
    Button proceed;
    int global = 0,timeflag = 0;
    int districtselect = 0;
    String _open, _close;
    View exView;
    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        spinner = (Spinner) findViewById(R.id.district_spinner);
        spinner_inside = (Spinner) findViewById(R.id.place);
        shopname = findViewById(R.id.email);
        shopid = findViewById(R.id.shopid);
        password = findViewById(R.id.password);
        reenter = findViewById(R.id.reenter);
        mobile = findViewById(R.id.mobile);
        check = findViewById(R.id.check);
        proceed = findViewById(R.id.go);
        settime = findViewById(R.id.settime);
        settime.setTextColor(Color.RED);

        exView = getLayoutInflater().inflate(R.layout.time_set, null);
        builder = new AlertDialog.Builder(this);

        settime.setPaintFlags(settime.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        settime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final TextView opentime = exView.findViewById(R.id.textView3);
                final TextView closetime = exView.findViewById(R.id.textView4);
                TimePicker open = exView.findViewById(R.id.timePicker);
                TimePicker close = exView.findViewById(R.id.timePicker2);
                Button set = exView.findViewById(R.id.button2);
                builder.setView(exView);
                final AlertDialog showBuilder = builder.create();
                showBuilder.setCancelable(false);
                showBuilder.show();
                open.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
                    @Override
                    public void onTimeChanged(TimePicker timePicker, int hour, int minute) {
                        SimpleDateFormat hr = new SimpleDateFormat("HH");
                        SimpleDateFormat min = new SimpleDateFormat("mm");
                        SimpleDateFormat _24HourSDF = new SimpleDateFormat("HH:mm");
                        SimpleDateFormat _12HourSDF = new SimpleDateFormat("hh:mm a");
                        String setTime = String.valueOf(hour) + ":" + String.valueOf(minute);
                        try {
                            Date _24HourDt;

                            _24HourDt = _24HourSDF.parse(setTime);
                            _open = setTime;

                            opentime.setTextColor(Color.parseColor("#50535c"));
                            opentime.setText("Open" + " Time - " + _12HourSDF.format(_24HourDt));
                            timeflag = 1;

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });

                close.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
                    @Override
                    public void onTimeChanged(TimePicker timePicker, int hour, int minute) {
                        SimpleDateFormat hr = new SimpleDateFormat("HH");
                        SimpleDateFormat min = new SimpleDateFormat("mm");
                        SimpleDateFormat _24HourSDF = new SimpleDateFormat("HH:mm");
                        SimpleDateFormat _12HourSDF = new SimpleDateFormat("hh:mm a");
                        String setTime = String.valueOf(hour) + ":" + String.valueOf(minute);
                        try {
                            Date _24HourDt;
                            _24HourDt = _24HourSDF.parse(setTime);
                            _close = setTime;
                            closetime.setTextColor(Color.parseColor("#50535c"));
                            closetime.setText("Close" + " Time - " + _12HourSDF.format(_24HourDt));
                            timeflag = 2;

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });

                set.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(timeflag == 1)
                            Toast.makeText(getApplicationContext(),"Enter the Closing Time",Toast.LENGTH_SHORT).show();
                        else  if(timeflag == 0)
                            Toast.makeText(getApplicationContext(),"Enter the Opening Time",Toast.LENGTH_SHORT).show();
                        else
                        {
                            Toast.makeText(getApplicationContext(),"Time set",Toast.LENGTH_SHORT).show();
                            showBuilder.cancel();

                        }

                    }
                });
            }
        });



        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!shopid.getText().toString().equals(""))
                new Check().execute(shopid.getText().toString());
                else
                    Toast.makeText(getApplicationContext(),"Enter a shopid",Toast.LENGTH_SHORT).show();
            }
        });


        spinner_inside.setVisibility(View.INVISIBLE);
        context = this;
        setAdapter = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,districts);

        hashMap = new HashMap<>();

        arrayList = new ArrayList<>();

        arrayList.add("Karamana");
        arrayList.add("Nalanchira");
        arrayList.add("Kowdiar");
        arrayList.add("Valiyavila");
        Collections.sort(arrayList);
        arrayList.add(0,"Select Place");
        hashMap.put("Trivandrum",arrayList);

        arrayList =new ArrayList<>();

        arrayList.add("Karunagapally");
        arrayList.add("Sasthamkotta");
        arrayList.add("Kottarakara");
        arrayList.add("Punalur");
        Collections.sort(arrayList);
        arrayList.add(0,"Select Place");
        hashMap.put("Kollam",arrayList);

        arrayList =new ArrayList<>();

        arrayList.add("Ramakari");
        arrayList.add("Edathva");
        arrayList.add("Vembanad");
        arrayList.add("Kuttanad");
        Collections.sort(arrayList);
        arrayList.add(0,"Select Place");
        hashMap.put("Alappuzha",arrayList);

        arrayList =new ArrayList<>();


        arrayList.add("Kakkand");
        arrayList.add("Fort Kochi");
        arrayList.add("Mattancheri");
        Collections.sort(arrayList);
        arrayList.add(0,"Select Place");
        hashMap.put("Ernakulam",arrayList);


       spinner.setAdapter(setAdapter);

       spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
           @Override
           public void onItemSelected(AdapterView<?> adapterView, View view, final int position, long l) {
               if (!districts[position].equals("Select District")) {
                   spinner_inside.setVisibility(View.VISIBLE);
                   setInsideAdapter = new ArrayAdapter<String>
                           (context, R.layout.support_simple_spinner_dropdown_item, hashMap.get(districts[position]));
                   spinner_inside.setAdapter(setInsideAdapter);

                   districtselect = 1;
                   spinner_inside.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                       @Override
                       public void onItemSelected(AdapterView<?> adapterView, View view, int subpos, long l) {

                           if (!adapterView.getSelectedItem().equals("Select Place"))
                               districtselect = 2;
                           else
                               districtselect = 1;
                       }

                       @Override
                       public void onNothingSelected(AdapterView<?> adapterView) {

                       }
                   });
               } else {
                   spinner_inside.setVisibility(View.INVISIBLE);
                   districtselect = 0;
               }
           }
           @Override
           public void onNothingSelected(AdapterView<?> adapterView) {

           }
       });
    proceed.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if(global !=1 )
                Toast.makeText(getApplicationContext(),"Enter a available Shop-ID",Toast.LENGTH_SHORT).show();
           else if(shopname.getText().toString().equals("")||shopid.getText().toString().equals("")
                    ||password.getText().toString().equals("")||reenter.getText().toString().equals("")
                    ||mobile.getText().toString().equals(""))
                Toast.makeText(getApplicationContext(),"All fields are Mandatory",Toast.LENGTH_SHORT).show();
           else if(!password.getText().toString().equals(reenter.getText().toString()))
                Toast.makeText(getApplicationContext(),"Password doesn't match",Toast.LENGTH_SHORT).show();
           else if(password.getText().toString().length()<4)
                Toast.makeText(getApplicationContext(),"Enter password more than length 4",Toast.LENGTH_SHORT).show();
           else if(mobile.getText().toString().length()!=10)
                Toast.makeText(getApplicationContext(),"Enter a Valid Mobile number",Toast.LENGTH_SHORT).show();
           else if(timeflag == 0)
                Toast.makeText(getApplicationContext(),"Select the Time",Toast.LENGTH_SHORT).show();
           else  if(districtselect == 0)
                Toast.makeText(getApplicationContext(),"Select a District",Toast.LENGTH_SHORT).show();
           else if(districtselect == 1)
                Toast.makeText(getApplicationContext(),"Select a place",Toast.LENGTH_SHORT).show();
           else if( districtselect == 2)
                new UserUpdate().execute(shopname.getText().toString(),shopid.getText().toString(),
                                password.getText().toString(),mobile.getText().toString(),spinner.getSelectedItem().toString(),spinner_inside.getSelectedItem().toString(),_open,_close);
        }
    });

    }

    public class UserUpdate extends AsyncTask<String,String,String>{
        String result;
        String URL = new getUrl().setUrl("userReg.php"); //GET
        JSONParserArray jParser = new JSONParserArray();
        JSONArray jArray;
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        @Override
        protected String doInBackground(String... values) {
            String shopname = values[0];
            String shopid = values[1];
            String shoppass = values[2];
            String mobile = values[3];
            String district = values[4];
            String place = values[5];


            params.add(new BasicNameValuePair("shopid",shopid));
            params.add(new BasicNameValuePair("shopname",shopname));
            params.add(new BasicNameValuePair("shoppass",shoppass));
            params.add(new BasicNameValuePair("district",district));
            params.add(new BasicNameValuePair("place",place));
            params.add(new BasicNameValuePair("mobile",mobile));
            params.add(new BasicNameValuePair("opentime",values[6]));
            params.add(new BasicNameValuePair("closetime",values[7]));

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
           if(s.equals("User Registration Success"))
           {
               Toast.makeText(getApplicationContext(),"Registration Success",Toast.LENGTH_SHORT).show();
               startActivity(new Intent(Registration.this,MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));


           }
        }
    }


    public class Check extends AsyncTask<String,String,String>{
        String result;
        String URL = "http://192.168.43.101/S-MFind/checkShopid.php"; //GET
        JSONParserArray jParser = new JSONParserArray();
        JSONArray jArray;
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        @Override
        protected String doInBackground(String... values) {
            String shopid = values[0];

            params.add(new BasicNameValuePair("shopid",shopid));
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
            if(s.equals("false")){
                check.setText("User-id Available");
                check.setTextColor(Color.GREEN);
                global = 1;
            }

            else{
                check.setText("User-id not available");
                check.setTextColor(Color.RED);
                global = 2;
            }

        }
    }


}
