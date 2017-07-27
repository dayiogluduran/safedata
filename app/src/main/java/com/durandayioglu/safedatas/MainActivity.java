package com.durandayioglu.safedatas;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button readButton,writeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        readButton=(Button)findViewById(R.id.btnRead);
        writeButton=(Button)findViewById(R.id.btnWrite);

    }

    public void yonlendirme(View view){
        if (view.getId()==readButton.getId()){
            Intent i=new Intent(this,ReadActivity.class);
            startActivity(i);
        } else if (view.getId()==writeButton.getId()){
            Intent i=new Intent(this,WriteActivity.class);
            startActivity(i);
        }
    }
}
