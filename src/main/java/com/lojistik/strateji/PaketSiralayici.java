package com.lojistik.strateji;

import com.lojistik.model.Paket;

import java.util.Comparator;
import java.util.List;

public final class PaketSiralayici {
    /** Önce yüksek öncelik, aynı öncelikte ağır paket önce. */
    public static final Comparator<Paket> ONCELIK_VE_AGIRLIK =
            Comparator.comparing(Paket::getOncelik).reversed()
                    .thenComparing(Comparator.comparingDouble(Paket::getAgirlik).reversed());

    private PaketSiralayici() {}

    public static List<Paket> sirala(List<Paket> paketler) {
        return paketler.stream().sorted(ONCELIK_VE_AGIRLIK).toList();
    }
}
