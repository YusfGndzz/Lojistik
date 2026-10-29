package com.lojistik.model;

/** Büyük kapasiteli, pahalı ama çok yük taşıyan araç. */
public class Kamyon extends Arac {

    public Kamyon(String plaka, double kapasite) {
        super(plaka, kapasite);
    }

    @Override
    public String getTur() {
        return "Kamyon";
    }

    @Override
    public double kmBasinaMaliyet() {
        return 12.0;
    }

    @Override
    public double sabitMaliyet() {
        return 500.0; // şoför + otoyol vb.
    }
}