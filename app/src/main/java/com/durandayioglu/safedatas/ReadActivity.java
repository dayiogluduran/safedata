package com.durandayioglu.safedatas;

import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

public class ReadActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final ListView listele=(ListView)findViewById(R.id.lstListele);

        Database db=new Database(ReadActivity.this);
        List<String> gelenListe=db.veriListele();
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(ReadActivity.this,android.R.layout.simple_list_item_1,android.R.id.text1,gelenListe);
        listele.setAdapter(adapter);

    }

    //Geri Butonuna basıldığında main activitye döndüren metod
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                Intent i=new Intent(getApplicationContext(),MainActivity.class);
                NavUtils.navigateUpTo(this,i);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
