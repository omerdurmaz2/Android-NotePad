package com.example.notdefteri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Date;

import androidx.appcompat.widget.Toolbar;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import io.realm.Realm;
import io.realm.RealmResults;

public class not extends AppCompatActivity {

    EditText txtBaslik, txtIcerik;
    Realm realm;
    String baslik, icerik, tarih;
    RealmResults<notTable> liste;
    Integer id = -1;
    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_not);


        toolbar = (Toolbar) findViewById(R.id.notHeader);
        toolbar.setTitleTextColor(Color.parseColor("#faf9f9"));
        setSupportActionBar(toolbar);


        //Toolbar a geri butonu eklendiği alan
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final Drawable upArrow = getResources().getDrawable(R.drawable.arrow_left);
        upArrow.setColorFilter(getResources().getColor(R.color.text_white), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //Toolbar a geri butonu eklendiği alan




        realmTanimla();
        tanimla();
        veriBas();
    }


    //Eğer toolbardaki bitti butonuna basılırsa yapılacaklar


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.btnBitti:
                verileriAl();
                kaydet();
                finish();

        }
        return true;
    }


    @Override
    protected void onPause() {
        super.onPause();
        //sonra burada da kaydetme yapılacak
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.not_header, menu);
        return true;
    }

    public void realmTanimla() {

        try {
            realm = Realm.getDefaultInstance();
        } catch (Exception err) {
            Log.i("Hata:", "" + err.toString());
        }
    }

    public void listeAl() {
        try {
            liste = realm.where(notTable.class).findAll();
        } catch (Exception err) {
            Log.i("Hata:", "" + err.toString());
        }
    }

    public void tanimla() {
        txtBaslik = (EditText) findViewById(R.id.txtBaslik);
        txtIcerik = (EditText) findViewById(R.id.txtIcerik);
    }

    void kaydet() {

        try {
            if (getIntent() != null && getIntent().getExtras() != null) {
                final notTable duzenlenenNot = liste.get(id);

                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        duzenlenenNot.setNotBaslik(baslik);
                        duzenlenenNot.setNotİcerik(icerik);
                        duzenlenenNot.setNotTarih(tarih);
                    }
                });

            } else {

                realm.executeTransactionAsync(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        notTable tablo = realm.createObject(notTable.class);
                        tablo.setNotBaslik(baslik);
                        tablo.setNotİcerik(icerik);
                        tablo.setNotTarih(tarih);
                    }
                }, new Realm.Transaction.OnSuccess() {
                    @Override
                    public void onSuccess() {
                        Toast.makeText(getApplicationContext(), "Kaydedildi", Toast.LENGTH_SHORT).show();
                    }
                }, new Realm.Transaction.OnError() {
                    @Override
                    public void onError(Throwable error) {
                        Toast.makeText(getApplicationContext(), "Hata", Toast.LENGTH_SHORT).show();
                        Log.i("Hata", "" + error.toString());

                    }
                });
            }


        } catch (Exception err) {
            Log.i("Hata", "" + err.toString());
        }


    }

    public void verileriAl() {
        baslik = txtBaslik.getText().toString();
        icerik = txtIcerik.getText().toString();
        Date simdikiTarih = new Date();
        DateFormat tarihFormati = new SimpleDateFormat("dd/MM/yyyy");
        tarih = tarihFormati.format(simdikiTarih);
    }

    public void veriBas() {
        if (getIntent() != null && getIntent().getExtras() != null) {
            Bundle bundle = getIntent().getExtras();
            id = bundle.getInt("id");
            if (id != -1) {
                listeAl();
                try {
                    toolbar.setTitle("Düzenle");
                    txtBaslik.setText(liste.get(id).getNotBaslik());
                    txtIcerik.setText(liste.get(id).getNotİcerik());
                } catch (Exception err) {
                    Log.i("Hata:", "" + err.toString());
                }
            }
        }

    }

}
