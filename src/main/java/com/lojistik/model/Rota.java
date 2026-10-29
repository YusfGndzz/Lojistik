package com.lojistik.model;

import java.util.List;

public class Rota {
    private final Arac arac;
    private final List<Konum> duraklar;

    public Rota(Arac arac, List<Konum> duraklar) {
        this.arac = arac;
        this.duraklar = List.copyOf(duraklar);
    }

    public static double toplamMesafe(List<Konum> tur) {
        double toplam = 0;
        for (int i = 1; i < tur.size(); i++) {
            toplam += tur.get(i - 1).mesafe(tur.get(i));
        }
        return toplam;
    }

    public Arac getArac() { return arac;}
    public List<Konum> getDuraklar() { return duraklar;}
    public double getMesafe() { return toplamMesafe(duraklar);}

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < duraklar.size(); i++) {
            if (i > 0) sb.append(" -> ");
            sb.append(duraklar.get(i).getAd());
        }
        return String.format("%s | %s | %.2f km", arac.getPlaka(), sb, getMesafe());
    }
}
