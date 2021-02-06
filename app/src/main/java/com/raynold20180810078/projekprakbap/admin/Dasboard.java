package com.raynold20180810078.projekprakbap.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.raynold20180810078.projekprakbap.R;

public class Dasboard extends AppCompatActivity implements View.OnClickListener {

    private Button blihat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dasboard);

        blihat  = (Button) findViewById(R.id.buttonView);
        blihat.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        if (view == blihat){
            startActivity(new Intent(Dasboard.this, TampilPulsaAdmin.class));
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
                Intent intent = new Intent(com.raynold20180810078.projekprakbap.admin.Dasboard.this, com.raynold20180810078.projekprakbap.AboutUs.class);
                startActivity(intent);
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}