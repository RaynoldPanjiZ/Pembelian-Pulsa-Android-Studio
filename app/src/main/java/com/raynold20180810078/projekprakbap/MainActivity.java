package com.raynold20180810078.projekprakbap;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.raynold20180810078.projekprakbap.helperconfig.RequestHandler;
import com.raynold20180810078.projekprakbap.helperconfig.konfigurasi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    EditText euname, epass;
    Button btnlogin;

    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        euname   = (EditText) findViewById(R.id.uname);
        epass    = (EditText) findViewById(R.id.pass);

        btnlogin  = (Button) findViewById(R.id.btn_login);

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v == btnlogin) {
                    proseslogin();
                }
            }
        });
    }

    private void ambildata(String res){
        try {
            JSONObject jsonObject = new JSONObject(res);
            JSONArray json_result = jsonObject.getJSONArray(konfigurasi.TAG_JSON_ARRAY);
            JSONObject c = json_result.getJSONObject(0);

            sp = getSharedPreferences(konfigurasi.PREF_NAME, MODE_PRIVATE);

            Toast.makeText(MainActivity.this, c.getString(konfigurasi.TAG_AKSES), Toast.LENGTH_LONG).show();
            if(c.getString(konfigurasi.TAG_AKSES).equals("Customer")){
                SharedPreferences.Editor editor = sp.edit();
                // Storing data to pref editor
                editor.putString(konfigurasi.KEY_ID_USER, c.getString(konfigurasi.TAG_ID_USER));
                editor.putString(konfigurasi.KEY_PULSA, c.getString(konfigurasi.TAG_PULSA));
                editor.putString(konfigurasi.KEY_TELP, c.getString(konfigurasi.TAG_TELP));
                // commit changes
                editor.commit();
                // go to activity
                Intent intent = new Intent(MainActivity.this, com.raynold20180810078.projekprakbap.customer.Dasboard.class);
                startActivity(intent);

            } else if(c.getString(konfigurasi.TAG_AKSES).equals("Admin")){
                startActivity(new Intent(MainActivity.this, com.raynold20180810078.projekprakbap.admin.Dasboard.class));
            } else {
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void reset(){ }

    private void proseslogin(){
        final String uname = euname.getText().toString().trim();
        final String pass = epass.getText().toString().trim();
        class Login extends AsyncTask<Void,Void,String > {

            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(
                        MainActivity.this,
                        "Menambahkan...",
                        "Tunggu Sebentar...",
                        false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                reset();
                ambildata(s);
            }

            @Override
            protected String doInBackground(Void... voids) {
                HashMap<String,String> params = new HashMap<>();
                params.put(konfigurasi.KEY_USERNAME,uname);
                params.put(konfigurasi.KEY_PASSWORD,pass);
                RequestHandler requestHandler = new RequestHandler();
                return requestHandler.sendPostRequest(konfigurasi.URL_LOGIN,params);
            }
        }

        new Login().execute();
    }
}