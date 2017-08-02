package com.durandayioglu.safedatas;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Button writeButton;
    ListView listele;
    Crypto c;
    String keyy;
    SharedPreferences sP;
    SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        listele = (ListView) findViewById(R.id.lstListele);

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

        kayitListele();

        registerForContextMenu(listele);
        listele.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
            @Override
            public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
                if (v.getId() == R.id.lstListele) {
                    menu.add(0, 1, 0, "Sil");
                    menu.add(0, 2, 0, "Düzenle");
                }
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.yeniKayit:
                Intent i = new Intent(MainActivity.this, WriteActivity.class);
                startActivity(i);
                break;

            default:
                break;
        }

        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }


    public void yonlendirme(View view) {
        if (view.getId() == writeButton.getId()) {
            Intent i = new Intent(this, WriteActivity.class);
            startActivity(i);
        }
    }

    public void kayitListele() {
        Database db = new Database(MainActivity.this);
        ArrayList<String> veriler=new ArrayList<String>();

        String tempID;
        String tempName;
        String tempSurname;
        String tempMail;
        String tempPass;

        Cursor cr=db.veriListele();
        while (cr.moveToNext()) {

            tempID = c.decode(cr.getInt(0) + "");
            tempName = c.decode(cr.getString(1));
            tempSurname = c.decode(cr.getString(2));
            tempMail = c.decode(cr.getString(3));
            tempPass = c.decode(cr.getString(4));


            veriler.add(tempID + " - " + tempName + " - " + tempSurname+ " - " + tempMail+ " - " + tempPass);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, android.R.id.text1, veriler);
        listele.setAdapter(adapter);
    }

    public void VerileriBosalt(ListView lv) {
        List<String> bos = new ArrayList<String>();
        ArrayAdapter<String> bosAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, bos);
        lv.setAdapter(bosAdapter);
    }

    public boolean onContextItemSelected(MenuItem item) {
        boolean donus;
        switch (item.getItemId()) {
            case 1:
                AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
                String secili = listele.getItemAtPosition(info.position).toString();
                String[] dizi = secili.split("-");
                final long id = Long.parseLong(dizi[0].trim());
                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
                alertBuilder.setTitle("Veri Sil");
                alertBuilder.setMessage("\"" + secili + "\" adlı veriyi silmek istediğinize emin misiniz?");
                alertBuilder.setNegativeButton("Evet", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Database veritabani = new Database(MainActivity.this);
                        veritabani.VeriSil(id);
                        kayitListele();
                    }
                });
                alertBuilder.setPositiveButton("Hayır", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                AlertDialog alertDialog = alertBuilder.create();
                alertDialog.show();
                break;
            case 2:
                AdapterView.AdapterContextMenuInfo inf = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
                String seciliVeri = listele.getItemAtPosition(inf.position).toString();
                String[] diziVeri = seciliVeri.split("-");
                final long lid = Long.parseLong(diziVeri[0].trim());
                final String eskiAd = diziVeri[1].trim();
                final String eskiSoyad = diziVeri[2].trim();
                final String eskiMail = diziVeri[3].trim();
                final String eskiSifre = diziVeri[4].trim();
                AlertDialog.Builder bAlert = new AlertDialog.Builder(this);
                bAlert.setTitle("Veri Düzenle");
                LinearLayout lNesne = new LinearLayout(this);
                lNesne.setOrientation(LinearLayout.VERTICAL);
                final EditText yeniAd = new EditText(this);
                yeniAd.setHint("Ad");
                yeniAd.setText(eskiAd);
                final EditText yeniSoyad = new EditText(this);
                yeniSoyad.setHint("Soyad");
                yeniSoyad.setText(eskiSoyad);
                final EditText yeniMail = new EditText(this);
                yeniMail.setHint("E-Mail");
                yeniMail.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
                yeniMail.setText(eskiMail);
                final EditText yeniSifre = new EditText(this);
                yeniSifre.setHint("Şifre");
                yeniSifre.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                yeniSifre.setText(eskiSifre);
                lNesne.addView(yeniAd);
                lNesne.addView(yeniSoyad);
                lNesne.addView(yeniMail);
                lNesne.addView(yeniSifre);
                bAlert.setView(lNesne);
                bAlert.setNegativeButton("Düzenle", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (yeniAd.getText().toString().trim() != "" && yeniSoyad.getText().toString().trim() != "" && yeniMail.getText().toString().trim() != "" && yeniSifre.getText().toString().trim() != "") {
                            Database veritabani = new Database(MainActivity.this);
                            veritabani.VeriDuzenle(lid, yeniAd.getText().toString().trim(), yeniSoyad.getText().toString().trim(), yeniMail.getText().toString().trim(), yeniSifre.getText().toString().trim());
                            kayitListele();
                        }
                    }
                });
                bAlert.setPositiveButton("İptal", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                AlertDialog Alert = bAlert.create();
                Alert.show();
                break;
            default:
                donus = false;
                break;
        }
        donus = true;
        return donus;
    }
}
