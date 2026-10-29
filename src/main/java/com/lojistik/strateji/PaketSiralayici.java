package com.lojistik.strateji;

import com.lojistik.model.Paket;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/** Paketleri önce önceliğe (ACIL en önde), sonra ağırlığa (ağır en önde) göre sıralar. */
final class PaketSiralayici {
    private PaketSiralayici() { }

    static List<Paket> onceligeVeAgirligaGore(List<Paket> paketler) {
        List<Paket> sirali = new ArrayList<>(paketler);
        sirali.sort(Comparator
                .comparingInt((Paket p) -> p.getOncelik().getSeviye()).reversed()
                .thenComparing(Comparator.comparingDouble(Paket::getAgirlik).reversed()));
        return sirali;
    }
}