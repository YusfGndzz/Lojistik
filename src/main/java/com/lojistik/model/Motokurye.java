package com.lojistik.model;

public class Motokurye extends Arac {
    public Motokurye(String plaka, double kapasite) {
        super(plaka, kapasite);
    }

    @Override
    public String getTip() { return "MOTOKURYE";}
}
