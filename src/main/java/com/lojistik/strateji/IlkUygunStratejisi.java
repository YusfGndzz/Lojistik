package com.lojistik.strateji;

import com.lojistik.model.Arac;
import com.lojistik.model.Paket;

import java.util.ArrayList;
import java.util.List;

/**
 * ALGORİTMA: First Fit Decreasing (Azalan Sırada İlk Uygun).
 * Sıralanmış her paket, listede onu alabilen İLK araca konur.
 * Best Fit'e göre daha basit ve hızlıdır; karşılaştırma için ikinci strateji olarak var.
 */
public class IlkUygunStratejisi implements DagitimStratejisi {

    @Override
    public List<Paket> dagit(List<Paket> paketler, List<Arac> araclar) {
        List<Paket> atanamayan = new ArrayList<>();

        for (Paket paket : PaketSiralayici.onceligeVeAgirligaGore(paketler)) {
            boolean yuklendi = false;
            for (Arac arac : araclar) {
                if (arac.yukle(paket)) {
                    yuklendi = true;
                    break;
                }
            }
            if (!yuklendi) atanamayan.add(paket);
        }
        return atanamayan;
    }

    @Override
    public String getAd() {
        return "İlk Uygun (First Fit Decreasing)";
    }
}