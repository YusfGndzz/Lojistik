package com.lojistik.rota;

import com.lojistik.model.Konum;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/** En yakın komşu sezgiseli, isteğe bağlı 2-opt iyileştirmesiyle. */
public class EnYakinKomsuOptimizer implements RotaOptimizer {
    private final boolean ikiOpt;

    public EnYakinKomsuOptimizer() {
        this(true);
    }

    public EnYakinKomsuOptimizer(boolean ikiOpt) {
        this.ikiOpt = ikiOpt;
    }

    @Override
    public List<Konum> optimizeEt(Konum baslangic, List<Konum> duraklar) {
        List<Konum> kalan = new ArrayList<>(duraklar);
        List<Konum> tur = new ArrayList<>();
        tur.add(baslangic);

        Konum simdiki = baslangic;
        while (!kalan.isEmpty()) {
            Konum enYakin = kalan.get(0);
            for (Konum k : kalan) {
                if (simdiki.mesafe(k) < simdiki.mesafe(enYakin)) enYakin = k;
            }
            kalan.remove(enYakin);
            tur.add(enYakin);
            simdiki = enYakin;
        }
        tur.add(baslangic);

        if (ikiOpt) ikiOptUygula(tur);
        return tur;
    }

    /** Baş ve son (depo) sabit kalacak şekilde, kısaltan kenar değişimlerini uygular. */
    private void ikiOptUygula(List<Konum> tur) {
        boolean iyilesti = true;
        while (iyilesti) {
            iyilesti = false;
            for (int i = 1; i < tur.size() - 2; i++) {
                for (int k = i + 1; k < tur.size() - 1; k++) {
                    double once = tur.get(i - 1).mesafe(tur.get(i)) + tur.get(k).mesafe(tur.get(k + 1));
                    double sonra = tur.get(i - 1).mesafe(tur.get(k)) + tur.get(i).mesafe(tur.get(k + 1));
                    if (sonra < once - 1e-9) {
                        Collections.reverse(tur.subList(i, k + 1));
                        iyilesti = true;
                    }
                }
            }
        }
    }
}
