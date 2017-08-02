package com.durandayioglu.safedatas;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class WriteActivity extends AppCompatActivity {
    Crypto c;
    String keyy;
    SharedPreferences sP;
    SharedPreferences.Editor editor;


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

        sP = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        editor = sP.edit();

        if (sP.getString("oldKey", null) == null) {
            c = new Crypto(16, 7);
            c.createKey(16);
            keyy = c.getKey();
            editor.putString("oldKey", c.getKey());
            editor.commit();
        } else {
            c = new Crypto(sP.getString("oldKey", null), 7);
            keyy = c.getKey();
        }


        kaydet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Database db = new Database(WriteActivity.this);
                db.veriEkle(c.encode(ad.getText().toString()), c.encode(soyad.getText().toString()), c.encode(email.getText().toString()), c.encode(sifre.getText().toString()));
                ad.setText("");
                soyad.setText("");
                email.setText("");
                sifre.setText("");

            }
        });

        listele.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WriteActivity.this, MainActivity.class);
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
