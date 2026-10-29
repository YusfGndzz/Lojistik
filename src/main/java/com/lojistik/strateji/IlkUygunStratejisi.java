package com.lojistik.strateji;

import com.lojistik.model.Arac;
import com.lojistik.model.Paket;

import java.util.ArrayList;
import java.util.List;

/** First-fit: her paketi sığdığı ilk araca koyar. */
public class IlkUygunStratejisi implements DagitimStratejisi {

    @Override
    public List<Paket> dagit(List<Paket> paketler, List<Arac> araclar) {
        List<Paket> atanamayanlar = new ArrayList<>();
        for (Paket paket : PaketSiralayici.sirala(paketler)) {
            boolean yuklendi = false;
            for (Arac arac : araclar) {
                if (arac.yukle(paket)) {
                    yuklendi = true;
                    break;
                }
            }
            if (!yuklendi) atanamayanlar.add(paket);
        }
        return atanamayanlar;
    }
}
