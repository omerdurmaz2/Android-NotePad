package com.example.notdefteri;

import java.util.Date;

import io.realm.RealmObject;

public class notTable extends RealmObject {
    private String notBaslik;
    private String notİcerik;
    private String notTarih;

    public String getNotBaslik() {
        return notBaslik;
    }

    public void setNotBaslik(String notBaslik) {
        this.notBaslik = notBaslik;
    }

    public String getNotİcerik() {
        return notİcerik;
    }

    public void setNotİcerik(String notİcerik) {
        this.notİcerik = notİcerik;
    }

    public String getNotTarih() {
        return notTarih;
    }

    public void setNotTarih(String notTarih) {
        this.notTarih = notTarih;
    }

    @Override
    public String toString() {
        return "notTable{" +
                "notBaslik='" + notBaslik + '\'' +
                ", notİcerik='" + notİcerik + '\'' +
                ", notTarih=" + notTarih +
                '}';
    }
}
