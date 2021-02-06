package com.raynold20180810078.projekprakbap.admin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.raynold20180810078.projekprakbap.R;
import com.raynold20180810078.projekprakbap.customer.Dasboard;
import com.raynold20180810078.projekprakbap.helperconfig.RequestHandler;
import com.raynold20180810078.projekprakbap.helperconfig.konfigurasi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class TampilPulsaAdmin extends AppCompatActivity implements ListView.OnItemClickListener, View.OnClickListener{

    private  ListView listView;
    private String JSON_STRING;
    private Button btntampil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tampil_pulsa_admin);

        listView = (ListView) findViewById(R.id.listPulsaAdmin);
        listView.setOnItemClickListener(this);

        btntampil = (Button) findViewById(R.id.btn_tambah);
        btntampil.setOnClickListener(this);

        getJSON();
    }

    private void showEmployee(){

        JSONObject jsonObject;
        ArrayList<HashMap<String,String>> list = new ArrayList<>();
        try {
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray result = jsonObject.getJSONArray(konfigurasi.TAG_JSON_ARRAY);

            for (int i = 0; i < result.length();i++){

                JSONObject object = result.getJSONObject(i);
                String id = object.getString(konfigurasi.TAG_ID_PULSA);
                String nominal = object.getString(konfigurasi.TAG_NOMINAL);
                String harga = object.getString(konfigurasi.TAG_HARGA);

                HashMap<String,String> employees = new HashMap<>();
                employees.put(konfigurasi.TAG_ID_PULSA,id);
                employees.put(konfigurasi.TAG_NOMINAL,nominal);
                employees.put(konfigurasi.TAG_HARGA,harga);
                list.add(employees);
            }
        } catch (JSONException e) {
            new AlertDialog.Builder(TampilPulsaAdmin.this)
                    .setMessage(e.getMessage())
                    .show();
        }catch (NullPointerException e){
            new AlertDialog.Builder(TampilPulsaAdmin.this)
                    .setMessage(e.getMessage())
                    .show();
        }

        ListAdapter adapter = new SimpleAdapter(
                TampilPulsaAdmin.this,list,R.layout.list_item_pulsa,
                new String[] {konfigurasi.TAG_ID_PULSA,konfigurasi.TAG_NOMINAL,konfigurasi.TAG_HARGA},
                new int[] {R.id.id,R.id.nominal,R.id.harga});
        listView.setAdapter(adapter);
    }

    private void getJSON(){

        class GetJSON extends AsyncTask<Void,Void,String>{
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(
                        TampilPulsaAdmin.this,
                        "Mengambil Data",
                        "Mohon tunggu...",
                        false,true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                JSON_STRING = s;
                showEmployee();
            }

            @Override
            protected String doInBackground(Void... voids) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequest(konfigurasi.URL_GETALL_PULSA, TampilPulsaAdmin.this);
                return s;
            }
        }

        GetJSON js = new GetJSON();
        js.execute();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent intent = new Intent(this, UpdatePulsa.class);
        HashMap<String,String> map = (HashMap)adapterView.getItemAtPosition(i);
        intent.putExtra(konfigurasi.ID,map.get(konfigurasi.TAG_ID_PULSA).toString());
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        startActivity(new Intent(TampilPulsaAdmin.this, InputPulsa.class));
    }


    /** menu **/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater influter = getMenuInflater();
        influter.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.aboutus:
                Intent intent = new Intent(TampilPulsaAdmin.this, com.raynold20180810078.projekprakbap.AboutUs.class);
                startActivity(intent);
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
