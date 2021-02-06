package com.raynold20180810078.projekprakbap.customer;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.raynold20180810078.projekprakbap.R;
import com.raynold20180810078.projekprakbap.helperconfig.RequestHandler;
import com.raynold20180810078.projekprakbap.helperconfig.konfigurasi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class TampilPulsaCustomer extends AppCompatActivity implements ListView.OnItemClickListener{

    private  ListView listView;
    private String JSON_STRING;
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tampil_pulsa_customer);

        listView = (ListView) findViewById(R.id.listPulsaCustomer);
        listView.setOnItemClickListener(this);

        getJSON();
    }

    /**     Tampil Data     **/
    private void showData(){
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

                HashMap<String,String> datapulsa = new HashMap<>();
                datapulsa.put(konfigurasi.TAG_ID_PULSA,id);
                datapulsa.put(konfigurasi.TAG_NOMINAL,nominal);
                datapulsa.put(konfigurasi.TAG_HARGA,harga);
                list.add(datapulsa);
            }
        } catch (JSONException e) {
            new AlertDialog.Builder(TampilPulsaCustomer.this)
                    .setMessage(e.getMessage())
                    .show();
        }catch (NullPointerException e){
            new AlertDialog.Builder(TampilPulsaCustomer.this)
                    .setMessage(e.getMessage())
                    .show();
        }
        ListAdapter adapter = new SimpleAdapter(
                TampilPulsaCustomer.this,list,R.layout.list_item_pulsa,
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
                        TampilPulsaCustomer.this,
                        "Mengambil Data",
                        "Mohon tunggu...",
                        false,true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                JSON_STRING = s;
                showData();
            }

            @Override
            protected String doInBackground(Void... voids) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequest(konfigurasi.URL_GETALL_PULSA, TampilPulsaCustomer.this);
                return s;
            }
        }
        GetJSON js = new GetJSON();
        js.execute();
    }

    /** Input Data **/
    private void reset(){ }
    private void AddData(String pulsa, String id_pulsa, String id_user, String tgl){
        class AddPulsa extends AsyncTask<Void,Void,String >{
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(
                        TampilPulsaCustomer.this,
                        "Menambahkan...",
                        "Tunggu Sebentar...",
                        false,false);
            }
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                reset();
                Toast.makeText(TampilPulsaCustomer.this, s, Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Void... voids) {
                HashMap<String,String> params = new HashMap<>();
                params.put(konfigurasi.KEY_ID_PULSA,id_pulsa);
                params.put(konfigurasi.KEY_ID_USER,id_user);
                params.put(konfigurasi.KEY_TGL_TRANSAKSI,tgl);
                params.put(konfigurasi.KEY_NOMINAL_PULSA,pulsa);
                RequestHandler requestHandler = new RequestHandler();
                return requestHandler.sendPostRequest(konfigurasi.URL_TRANSAKSI_PULSA,params);
            }
        }
        new AddPulsa().execute();
    }

    private void konfirmBeli(String pulsa, String id_pulsa, String id_user, String tgl){
        new AlertDialog.Builder(TampilPulsaCustomer.this)
                .setMessage("Apakah anda Yakin Ingin membeli ini ?")
                .setTitle("KONFIRMASI")
                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        AddData(pulsa, id_pulsa, id_user, tgl);
                        Intent intent = new Intent(getApplicationContext(), Dasboard.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("Tidak",null)
                .show();
    }


    /** On Click ListView **/
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        // mengambil wktu hari ini
        String currentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        String currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
        String tgl = currentDate + " " + currentTime;

        // mengambil id user
        sp = getSharedPreferences(konfigurasi.PREF_NAME, MODE_PRIVATE);
        String user = sp.getString(konfigurasi.KEY_ID_USER, null);

        // mengambil id pulsa dan nominal pulsa
        HashMap<String,String> map = (HashMap)adapterView.getItemAtPosition(i);
        String id_pulsa = map.get(konfigurasi.TAG_ID_PULSA).toString();
        int pulsa = Integer.parseInt(map.get(konfigurasi.TAG_NOMINAL)) + Integer.parseInt(sp.getString(konfigurasi.KEY_PULSA, null));
        Toast.makeText(TampilPulsaCustomer.this, pulsa+" ", Toast.LENGTH_LONG).show();

        konfirmBeli(pulsa+"", id_pulsa, user, tgl);
    }


    /** Menu Bar **/
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
                Intent intent = new Intent(TampilPulsaCustomer.this, com.raynold20180810078.projekprakbap.AboutUs.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
