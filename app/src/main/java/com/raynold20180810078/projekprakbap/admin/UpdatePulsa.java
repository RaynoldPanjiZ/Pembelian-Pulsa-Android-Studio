package com.raynold20180810078.projekprakbap.admin;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AlertDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.raynold20180810078.projekprakbap.R;
import com.raynold20180810078.projekprakbap.admin.Dasboard;
import com.raynold20180810078.projekprakbap.helperconfig.RequestHandler;
import com.raynold20180810078.projekprakbap.helperconfig.konfigurasi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class UpdatePulsa extends AppCompatActivity implements View.OnClickListener{

    private EditText eid, enominal, eharga;
    private Button bedit,bhapus;

    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_pulsa);

        eid = (EditText) findViewById(R.id.editTextId);
        enominal = (EditText) findViewById(R.id.enominal);
        eharga = (EditText) findViewById(R.id.eharga);

        bedit = (Button) findViewById(R.id.buttonUpdate);
        bhapus = (Button) findViewById(R.id.buttonDelete);

        bedit.setOnClickListener(this);
        bhapus.setOnClickListener(this);

        id = getIntent().getStringExtra(konfigurasi.ID);
        eid.setText(id);
        getEmployee();
    }

    private void getEmployee(){
        class GetEmployee extends AsyncTask<Void,Void,String>{

            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(
                        UpdatePulsa.this,"Mengambil Data",
                        "Tunggu Sebentar...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                showEmployee(s);
            }

            @Override
            protected String doInBackground(Void... voids) {
                RequestHandler rh = new RequestHandler();
                return rh.sendGetRequestParam(konfigurasi.URL_GET_PULSA,id);
            }
        }
        new GetEmployee().execute();
    }

    private void showEmployee(String json){
        try {

            JSONObject jsonObject = new JSONObject(json);
            JSONArray result = jsonObject.getJSONArray(konfigurasi.TAG_JSON_ARRAY);
            JSONObject c = result.getJSONObject(0);
            enominal.setText(c.getString(konfigurasi.TAG_NOMINAL));
            eharga.setText(c.getString(konfigurasi.TAG_HARGA));

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void updateEmployee(){
        final String nominal = enominal.getText().toString().trim();
        final String harga = this.eharga.getText().toString().trim();

        class UpdateEmployee extends AsyncTask<Void,Void,String>{

            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(
                        UpdatePulsa.this,"Mengubah",
                        "Tunggu Sebentar...",false,false
                );

            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(UpdatePulsa.this, s, Toast.LENGTH_SHORT).show();
            }

            @Override
            protected String doInBackground(Void... voids) {

                HashMap<String,String> map = new HashMap<>();
                map.put(konfigurasi.KEY_ID_PULSA,id);
                map.put(konfigurasi.KEY_NOMINAL_PULSA,nominal);
                map.put(konfigurasi.KEY_HARGA_PULSA,harga);
                RequestHandler rh = new RequestHandler();
                return rh.sendPostRequest(konfigurasi.URL_UPDATE_PULSA,map);

            }
        }

        new UpdateEmployee().execute();
    }

    private void deleteEmployee(){
        class DeleteEmpolyee extends AsyncTask<Void,Void,String>{

            ProgressDialog progressDialog;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog = ProgressDialog.show(
                        UpdatePulsa.this,"Menghapus Data",
                        "Tunggu Sebentar",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                progressDialog.dismiss();
                Toast.makeText(UpdatePulsa.this, s, Toast.LENGTH_SHORT).show();
            }

            @Override
            protected String doInBackground(Void... voids) {
                RequestHandler rh = new RequestHandler();
                return rh.sendGetRequestParam(konfigurasi.URL_DELETE_PULSA,id);
            }
        }

        new DeleteEmpolyee().execute();
    }

    private void confirmDelete(){
        new AlertDialog.Builder(UpdatePulsa.this)
                .setMessage("Hapus data pegawai ini ?")
                .setTitle("KONFIRMASI")
                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        deleteEmployee();

                        Intent intent = new Intent(getApplicationContext(), Dasboard.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("Tidak",null)
                .show();
    }

    @Override
    public void onClick(View view) {
        if (view == bedit){
            updateEmployee();

            Intent intent = new Intent(getApplicationContext(), Dasboard.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }else if (view == bhapus){
            confirmDelete();
        }
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
                Intent intent = new Intent(com.raynold20180810078.projekprakbap.admin.UpdatePulsa.this, com.raynold20180810078.projekprakbap.AboutUs.class);
                startActivity(intent);
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
