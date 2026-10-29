package com.lojistik.rota;

import com.lojistik.model.Konum;
import com.lojistik.model.Rota;

import java.util.ArrayList;
import java.util.List;

/**
 * ALGORİTMA: En Yakın Komşu + 2-opt iyileştirmesi (Gezgin Satıcı Problemi sezgiseli).
 *
 * Adım 1 - En Yakın Komşu: Depodan başla, her seferinde henüz gidilmemiş EN YAKIN durağa git,
 *          bütün duraklar bitince depoya dön. Hızlı ama genelde en kısa değildir.
 *
 * Adım 2 - 2-opt: Rotadaki iki kenarı seç (i-i+1 ve j-j+1). Aradaki parçayı ters çevirmek
 *          rotayı kısaltıyorsa ters çevir. Hiçbir iyileşme kalmayana kadar tekrarla.
 *          Bu, birbirini kesen (çapraz) yolları açar.
 */
public class EnYakinKomsuOptimizer implements RotaOptimizer {

    private final boolean ikiOptKullan;

    public EnYakinKomsuOptimizer() {
        this(true);
    }

    public EnYakinKomsuOptimizer(boolean ikiOptKullan) {
        this.ikiOptKullan = ikiOptKullan;
    }

    @Override
    public List<Konum> optimizeEt(Konum baslangic, List<Konum> duraklar) {
        List<Konum> tur = enYakinKomsu(baslangic, duraklar);
        return ikiOptKullan ? ikiOpt(tur) : tur;
    }

    private List<Konum> enYakinKomsu(Konum baslangic, List<Konum> duraklar) {
        List<Konum> kalan = new ArrayList<>(duraklar);
        List<Konum> tur = new ArrayList<>();
        tur.add(baslangic);
        Konum simdiki = baslangic;

        while (!kalan.isEmpty()) {
            Konum enYakin = kalan.get(0);
            for (Konum k : kalan) {
                if (simdiki.mesafe(k) < simdiki.mesafe(enYakin)) enYakin = k;
            }
            tur.add(enYakin);
            kalan.remove(enYakin);
            simdiki = enYakin;
        }
        tur.add(baslangic); // depoya dönüş
        return tur;
    }

    private List<Konum> ikiOpt(List<Konum> tur) {
        List<Konum> enIyi = new ArrayList<>(tur);
        boolean iyilesti = true;

        while (iyilesti) {
            iyilesti = false;
            // ilk ve son eleman (depo) sabit kalır
            for (int i = 1; i < enIyi.size() - 2; i++) {
                for (int j = i + 1; j < enIyi.size() - 1; j++) {
                    List<Konum> yeni = parcayiTersCevir(enIyi, i, j);
                    if (Rota.toplamMesafe(yeni) + 1e-9 < Rota.toplamMesafe(enIyi)) {
                        enIyi = yeni;
                        iyilesti = true;
                    }
                }
            }
        }
        return enIyi;
    }

    private List<Konum> parcayiTersCevir(List<Konum> tur, int i, int j) {
        List<Konum> yeni = new ArrayList<>(tur.subList(0, i));
        List<Konum> orta = new ArrayList<>(tur.subList(i, j + 1));
        java.util.Collections.reverse(orta);
        yeni.addAll(orta);
        yeni.addAll(tur.subList(j + 1, tur.size()));
        return yeni;
    }
}