package com.raynold20180810078.projekprakbap.customer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.raynold20180810078.projekprakbap.AboutUs;
import com.raynold20180810078.projekprakbap.MainActivity;
import com.raynold20180810078.projekprakbap.R;
import com.raynold20180810078.projekprakbap.helperconfig.konfigurasi;

public class Dasboard extends AppCompatActivity {

    TextView tvpulsa, tvnotelp;
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dasboard2);

        sp = getSharedPreferences(konfigurasi.PREF_NAME, MODE_PRIVATE);
        String pulsa = sp.getString(konfigurasi.KEY_PULSA, null);
        String telp = sp.getString(konfigurasi.KEY_TELP, null);

        tvpulsa = (TextView) findViewById(R.id.tvpulsa);
        tvpulsa.setText("Rp. "+pulsa);

        tvnotelp = (TextView) findViewById(R.id.notelp);
        tvnotelp.setText("Nomor Anda : "+telp);

        Button btn_belipulsa = (Button) findViewById(R.id.btn_transpulsa);
        btn_belipulsa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Dasboard.this, com.raynold20180810078.projekprakbap.customer.TampilPulsaCustomer.class);
                startActivity(intent);
            }
        });
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
                Intent intent = new Intent(Dasboard.this, com.raynold20180810078.projekprakbap.AboutUs.class);
                startActivity(intent);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

}