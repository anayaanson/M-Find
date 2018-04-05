package com.example.anaya.shopkeeper;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;


import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    TextView login;
    EditText email;
    EditText password;
    Button button;
    TextView newshop;
Typeface typeface;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        login = (TextView)findViewById(R.id.login);
        newshop= (TextView)findViewById(R.id.newshop);
        email=(EditText)findViewById(R.id.email);
        password=(EditText)findViewById(R.id.pass);
        button= (Button)findViewById(R.id.button);
        typeface = Typeface.createFromAsset(getAssets(),"fonts/font2.ttf");

        login.setTypeface(typeface);
        newshop.setTypeface(typeface);
        email.setTypeface(typeface);
        password.setTypeface(typeface);
        button.setTypeface(typeface);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(email.getText().toString().equals(""))
                {
                    Toast.makeText(getApplicationContext(),"Enter an Email ID",Toast.LENGTH_SHORT).show();
                }
                else if(password.getText().toString().equals(""))
                {
                    Toast.makeText(getApplicationContext(),"Enter the Password",Toast.LENGTH_SHORT).show();
                }
                else
                    new fetchOperation().execute(email.getText().toString(),password.getText().toString());

            }
        });
        newshop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent registration = new Intent(MainActivity.this,Registration.class);
                startActivity(registration);
            }
        });
    }
    class fetchOperation extends AsyncTask<String,String,String>
    {

        String result;
        String URL = new getUrl().setUrl("login.php");
        JSONParserArray jParser = new JSONParserArray();
        JSONArray jArray;
        String username,password;
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        @Override
        protected String doInBackground(String... values) {
            // values[0]=email;
            //values[1]=password;
             username = values[0];
             password = values[1];
            params.add(new BasicNameValuePair("userid",username));
            params.add(new BasicNameValuePair("password",password));

            jArray = jParser.makeHttpRequest(URL, "GET", params);
            try {
                int count=jArray.getJSONArray(0).length();
                Log.d("Response",jArray.getJSONArray(0).getString(0));
                result = jArray.getJSONArray(0).getString(0);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return result;

        }
        @Override
        protected void onPostExecute(String result) {
            if(result.equals("No Record"))
                Toast.makeText(getApplicationContext(),result,Toast.LENGTH_SHORT).show();
            else if (result.equals("Error Response"))
                Toast.makeText(getApplicationContext(),result,Toast.LENGTH_SHORT).show();
            else
            {
                Intent next = new Intent(MainActivity.this,Updation.class);
                next.putExtra("username",username);
                startActivity(next);
            }
        }
    }

}
