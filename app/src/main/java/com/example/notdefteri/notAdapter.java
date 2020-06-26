package com.example.notdefteri;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.Layout;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;


import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

public class notAdapter extends BaseAdapter {
    Context ctx;
    List<notTable> liste;
    Activity activity;
    Realm realm;


    public notAdapter(Context ctx, List<notTable> liste, Activity activity) {
        this.ctx = ctx;
        this.liste = liste;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return liste.size();
    }

    @Override
    public Object getItem(int position) {
        return liste.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(ctx).inflate(R.layout.liste_gorunum, parent, false);
        try {
            TextView txtBaslik, txtIcerik, txtTarih;
            LinearLayout notNesne;
            Button btnSil;
            txtBaslik = (TextView) convertView.findViewById(R.id.txtNotBaslik);
            txtIcerik = (TextView) convertView.findViewById(R.id.txtNotIcerik);
            txtTarih = (TextView) convertView.findViewById(R.id.txtNotTarih);
            notNesne = (LinearLayout) convertView.findViewById(R.id.notNesne);
            btnSil = (Button) convertView.findViewById(R.id.btnSil);

            txtBaslik.setText(liste.get(position).getNotBaslik());
            txtIcerik.setText(liste.get(position).getNotİcerik());
            txtTarih.setText(liste.get(position).getNotTarih().toString());


            notNesne.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(activity, not.class);
                    intent.putExtra("id", position);
                    activity.startActivity(intent);
                }
            });
            btnSil.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sil(position);
                    MainActivity.listView1.invalidateViews();
                }
            });
        } catch (Exception err) {
            Log.i("Hata", "" + err);
        }



        return convertView;
    }

    public void sil(final int position) {
        try {
            Realm realm = Realm.getDefaultInstance();
            final RealmResults<notTable> liste = realm.where(notTable.class).findAll();

            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    notTable not = liste.get(position);
                    not.deleteFromRealm();
                }
            });

        } catch (Exception err) {
            Log.i("Hata", "" + err);
        }

    }

}
