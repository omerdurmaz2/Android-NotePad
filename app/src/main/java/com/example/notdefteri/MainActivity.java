package com.example.notdefteri;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity {

    Realm realm;
    RelativeLayout bosListe;
    RealmResults<notTable> liste;
    public static ListView listView1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Toolbar myToolbar = (Toolbar) findViewById(R.id.headerMenu);
        myToolbar.setTitleTextColor(Color.parseColor("#faf9f9"));
        setSupportActionBar(myToolbar);

        realmTanimla();
        tanimla();
        listeBas();
    }

    @Override
    protected void onResume() {
        super.onResume();
        listeBas();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.header, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.btnYeni:
                Intent intent = new Intent(this, not.class);
                startActivity(intent);
        }
        return true;
    }

    public void realmTanimla(){
        realm=Realm.getDefaultInstance();
    }
    public void tanimla() {
        listView1 = (ListView) findViewById(R.id.liste1);
        bosListe = (RelativeLayout) findViewById(R.id.bosListe);
    }

    public void listeGetir() {
        liste = realm.where(notTable.class).findAll();
    }


    public void listeBas() {
        try {
            listeGetir();
            if (liste.size() > 0) {
                bosListe.setVisibility(View.INVISIBLE);

                notAdapter adp = new notAdapter(getApplicationContext(), liste,this);
                listView1.setAdapter(adp);
            }else{
                bosListe.setVisibility(View.VISIBLE);
            }
        } catch (Exception err) {
            Toast.makeText(getApplicationContext(), "Liste Yüklenirken Hata", Toast.LENGTH_SHORT);
        }
    }

}
