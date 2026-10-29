package com.lojistik.model;

/**
 * Hafif ve ucuz araç. Tek seferde en fazla TEK_PAKET_LIMIT kg'lık paket alabilir.
 * ÇOK BİÇİMLİLİK: paketiAlabilirMi metodunu ezerek (override) kendi kuralını ekler.
 */
public class Motokurye extends Arac {
    public static final double TEK_PAKET_LIMIT = 5.0;

    public Motokurye(String plaka, double kapasite) {
        super(plaka, kapasite);
    }

    @Override
    public String getTur() {
        return "Motokurye";
    }

    @Override
    public double kmBasinaMaliyet() {
        return 2.0;
    }

    @Override
    public boolean paketiAlabilirMi(Paket p) {
        return p.getAgirlik() <= TEK_PAKET_LIMIT && super.paketiAlabilirMi(p);
    }
}