package com.lojistik.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class Arac {
    private final String plaka;
    private final double kapasite;
    private final List<Paket> yuk = new ArrayList<>();

    protected Arac(String plaka, double kapasite) {
        if (plaka == null || plaka.isBlank()) throw new IllegalArgumentException("Plaka boş olamaz");
        if (kapasite <= 0) throw new IllegalArgumentException("Kapasite pozitif olmalı");
        this.plaka = plaka;
        this.kapasite = kapasite;
    }

    /** Dosyaya yazarken ve fabrikada kullanılan araç tipi adı. */
    public abstract String getTip();

    public String getPlaka() { return plaka;}
    public double getKapasite() { return kapasite;}
    public List<Paket> getYuk() { return Collections.unmodifiableList(yuk);}

    public double getDoluluk() {
        return yuk.stream().mapToDouble(Paket::getAgirlik).sum();
    }

    public double kalanKapasite() {
        return kapasite - getDoluluk();
    }

    public boolean sigarMi(Paket paket) {
        return paket.getAgirlik() <= kalanKapasite() + 1e-9;
    }

    public boolean yukle(Paket paket) {
        if (!sigarMi(paket)) return false;
        yuk.add(paket);
        return true;
    }

    public void bosalt() {
        yuk.clear();
    }

    @Override
    public String toString(){
        return String.format("%-9s %-10s %7.1f / %7.1f kg", getTip(), plaka, getDoluluk(), kapasite);
    }
}
