package com.lojistik.model;

/** Orta boy araç: şehir içi dağıtım için dengeli seçenek. */
public class Kamyonet extends Arac {

    public Kamyonet(String plaka, double kapasite) {
        super(plaka, kapasite);
    }

    @Override
    public String getTur() {
        return "Kamyonet";
    }

    @Override
    public double kmBasinaMaliyet() {
        return 6.0;
    }

    @Override
    public double sabitMaliyet() {
        return 200.0;
    }
}