package com.lojistik.strateji;

import com.lojistik.model.Arac;
import com.lojistik.model.Paket;

import java.util.ArrayList;
import java.util.List;

/**
 * ALGORİTMA: Best Fit Decreasing (Azalan Sırada En İyi Yerleşim) - kutu doldurma (bin packing) sezgiseli.
 *
 * 1) Paketleri önceliğe, sonra ağırlığa göre büyükten küçüğe sırala.
 * 2) Her paket için, paketi alabilen araçlar arasından yüklemeden sonra
 *    EN AZ boş yer kalacak aracı seç (araçlar daha sıkı dolar, daha az araç gerekir).
 * 3) Hiçbir araca sığmayan paket "atanamadı" listesine girer.
 *
 * Karmaşıklık: O(n log n + n*m)  (n = paket, m = araç)
 */
public class EnIyiYerlesimStratejisi implements DagitimStratejisi {

    @Override
    public List<Paket> dagit(List<Paket> paketler, List<Arac> araclar) {
        List<Paket> atanamayan = new ArrayList<>();

        for (Paket paket : PaketSiralayici.onceligeVeAgirligaGore(paketler)) {
            Arac enIyi = null;
            double enAzBosluk = Double.MAX_VALUE;

            for (Arac arac : araclar) {
                if (!arac.paketiAlabilirMi(paket)) continue;
                double bosluk = arac.kalanKapasite() - paket.getAgirlik();
                if (bosluk < enAzBosluk) {
                    enAzBosluk = bosluk;
                    enIyi = arac;
                }
            }

            if (enIyi != null) {
                enIyi.yukle(paket);
            } else {
                atanamayan.add(paket);
            }
        }
        return atanamayan;
    }

    @Override
    public String getAd() {
        return "En İyi Yerleşim (Best Fit Decreasing)";
    }
}