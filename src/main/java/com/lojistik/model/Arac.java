package com.lojistik.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Tüm araç türlerinin ortak atası.
 * SOYUTLAMA: Arac doğrudan oluşturulamaz, sadece alt sınıfları (Kamyon, Kamyonet, Motokurye) kullanılır.
 * KALITIM: ortak alanlar ve yükleme mantığı burada, türe özgü davranış alt sınıflarda.
 */
public abstract class Arac {
    private final String plaka;
    private final double kapasite; // kg
    private final List<Paket> yuk = new ArrayList<>();

    protected Arac(String plaka, double kapasite) {
        if (plaka == null || plaka.isBlank()) throw new IllegalArgumentException("Plaka boş olamaz");
        if (kapasite <= 0) throw new IllegalArgumentException("Kapasite pozitif olmalı");
        this.plaka = plaka;
        this.kapasite = kapasite;
    }

    /** Araç türünün adı (ÇOK BİÇİMLİLİK: her alt sınıf kendi adını döndürür). */
    public abstract String getTur();

    /** Kilometre başına yakıt/işletme maliyeti, TL. */
    public abstract double kmBasinaMaliyet();

    /** Sefer başına sabit maliyet (şoför, sigorta vb.). Alt sınıflar değiştirebilir. */
    public double sabitMaliyet() {
        return 0;
    }

    /** Verilen mesafe için toplam maliyet. Alt sınıfların farklı formülleri burada birleşir. */
    public final double maliyetHesapla(double km) {
        return sabitMaliyet() + km * kmBasinaMaliyet();
    }

    /**
     * Bu araç paketi alabilir mi? Varsayılan kural: kalan kapasite yetiyorsa evet.
     * Alt sınıflar ek kural koyabilir (örn. Motokurye ağır paket almaz).
     */
    public boolean paketiAlabilirMi(Paket p) {
        return p.getAgirlik() <= kalanKapasite();
    }

    public boolean yukle(Paket p) {
        if (!paketiAlabilirMi(p)) return false;
        yuk.add(p);
        return true;
    }

    public void bosalt() {
        yuk.clear();
    }

    public double doluluk() {
        return yuk.stream().mapToDouble(Paket::getAgirlik).sum();
    }

    public double kalanKapasite() {
        return kapasite - doluluk();
    }

    public String getPlaka() { return plaka; }
    public double getKapasite() { return kapasite; }

    /** Dışarıya değiştirilemez liste verilir (kapsülleme). */
    public List<Paket> getYuk() {
        return Collections.unmodifiableList(yuk);
    }

    @Override
    public String toString() {
        return String.format("%-10s %-10s kapasite: %6.0f kg  (%.1f TL/km)", getTur(), plaka, kapasite, kmBasinaMaliyet());
    }
}