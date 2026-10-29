package com.lojistik.strateji;

import com.lojistik.model.Arac;
import com.lojistik.model.Paket;

import java.util.ArrayList;
import java.util.List;

/** Best-fit: her paketi, yüklendikten sonra en az boşluk kalacak araca koyar. */
public class EnIyiYerlesimStratejisi implements DagitimStratejisi {

    @Override
    public List<Paket> dagit(List<Paket> paketler, List<Arac> araclar) {
        List<Paket> atanamayanlar = new ArrayList<>();
        for (Paket paket : PaketSiralayici.sirala(paketler)) {
            Arac enIyi = null;
            for (Arac arac : araclar) {
                if (arac.sigarMi(paket) && (enIyi == null || arac.kalanKapasite() < enIyi.kalanKapasite())) {
                    enIyi = arac;
                }
            }
            if (enIyi != null) enIyi.yukle(paket);
            else atanamayanlar.add(paket);
        }
        return atanamayanlar;
    }
}
