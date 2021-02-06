package com.raynold20180810078.projekprakbap.admin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.raynold20180810078.projekprakbap.R;
import com.raynold20180810078.projekprakbap.helperconfig.RequestHandler;
import com.raynold20180810078.projekprakbap.helperconfig.konfigurasi;

import java.util.HashMap;

public class InputPulsa extends AppCompatActivity implements View.OnClickListener{

    private EditText enama,epos;
    private Button bsimpan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_pulsa);


        enama   = (EditText) findViewById(R.id.enominal);
        epos    = (EditText) findViewById(R.id.eharga);

        bsimpan = (Button) findViewById(R.id.buttonAdd);

        bsimpan.setOnClickListener(this);

    }


    private void reset(){

    }
    private void AddEmployee(){
        final String nominal = enama.getText().toString().trim();
        final String harga = epos.getText().toString().trim();
        class AddEmployee extends AsyncTask<Void,Void,String >{

            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(
                        InputPulsa.this,
                        "Menambahkan...",
                        "Tunggu Sebentar...",
                        false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                reset();
                Toast.makeText(InputPulsa.this, s, Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Void... voids) {
                HashMap<String,String> params = new HashMap<>();
                params.put(konfigurasi.KEY_NOMINAL_PULSA,nominal);
                params.put(konfigurasi.KEY_HARGA_PULSA,harga);
                RequestHandler requestHandler = new RequestHandler();
                return requestHandler.sendPostRequest(konfigurasi.URL_ADD_PULSA,params);
            }
        }

        new AddEmployee().execute();
    }

    @Override
    public void onClick(View view) {

        if (view == bsimpan){
            AddEmployee();

            Intent intent = new Intent(getApplicationContext(), Dasboard.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

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
                Intent intent = new Intent(com.raynold20180810078.projekprakbap.admin.InputPulsa.this, com.raynold20180810078.projekprakbap.AboutUs.class);
                startActivity(intent);
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
