package com.durandayioglu.safedatas;

import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class WriteActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final EditText ad = (EditText) findViewById(R.id.txtAd);
        final EditText soyad = (EditText) findViewById(R.id.txtSoyad);
        final EditText email = (EditText) findViewById(R.id.txtMail);
        final EditText sifre = (EditText) findViewById(R.id.txtSifre);
        Button kaydet = (Button) findViewById(R.id.btnKaydet);
        Button listele = (Button) findViewById(R.id.btnGoruntule);


        kaydet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Database db = new Database(WriteActivity.this);
                db.veriEkle(ad.getText().toString(), soyad.getText().toString(), email.getText().toString(), sifre.getText().toString());
                ad.setText("");
                soyad.setText("");
                email.setText("");
                sifre.setText("");

            }
        });

        listele.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(WriteActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }

    //Geri Butonuna basıldığında main activitye döndüren metod
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                NavUtils.navigateUpTo(this, i);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
