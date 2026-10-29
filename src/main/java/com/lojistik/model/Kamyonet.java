package com.lojistik.model;

public class Kamyonet extends Arac {
    public Kamyonet(String plaka, double kapasite) {
        super(plaka, kapasite);
    }

    @Override
    public String getTip() { return "KAMYONET";}
}
