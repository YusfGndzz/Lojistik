package com.lojistik.model;

public class Kamyon extends Arac {
    public Kamyon(String plaka, double kapasite) {
        super(plaka, kapasite);
    }

    @Override
    public String getTip() { return "KAMYON";}
}
