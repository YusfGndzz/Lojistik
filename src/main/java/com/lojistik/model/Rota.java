package com.lojistik.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/** Bir aracın depodan çıkıp durakları gezip depoya döndüğü teslimat rotası. */
public class Rota {
    private final Arac arac;
    private final List<Konum> duraklar; // ilk ve son eleman depodur

    public Rota(Arac arac, List<Konum> duraklar) {
        this.arac = arac;
        this.duraklar = new ArrayList<>(duraklar);
    }

    public double toplamMesafe() {
        return toplamMesafe(duraklar);
    }

    /** Herhangi bir durak listesinin uzunluğu (algoritmalar da kullanır). */
    public static double toplamMesafe(List<Konum> liste) {
        double toplam = 0;
        for (int i = 0; i < liste.size() - 1; i++) {
            toplam += liste.get(i).mesafe(liste.get(i + 1));
        }
        return toplam;
    }

    public double maliyet() {
        return arac.maliyetHesapla(toplamMesafe());
    }

    public Arac getArac() { return arac; }

    public List<Konum> getDuraklar() {
        return Collections.unmodifiableList(duraklar);
    }
}