package com.raynold20180810078.projekprakbap;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class AboutUs extends AppCompatActivity implements View.OnClickListener {

    private Button linkedin, github, instagram;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        linkedin = (Button) findViewById(R.id.btnlinkedin);
        github = (Button) findViewById(R.id.btngithub);
        instagram = (Button) findViewById(R.id.btnig);

        linkedin.setOnClickListener(this);
        github.setOnClickListener(this);
        instagram.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v == linkedin){
            String url = "https://www.linkedin.com/in/raynold-panji/";
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            startActivity(i);
        }
        if(v == github){
            String url = "https://github.com/RaynoldPanjiZ";
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            startActivity(i);
        }
        if(v == instagram){
            String url = "https://www.instagram.com/raynold_panji/";
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            startActivity(i);
        }
    }
}