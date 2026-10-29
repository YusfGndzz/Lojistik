package com.lojistik.depo;

import com.lojistik.fabrika.AracFabrikasi;
import com.lojistik.model.Arac;

import java.nio.file.Path;
import java.util.Locale;

/** Araçları data/araclar.txt dosyasında saklar. Satır: tur;plaka;kapasite */
public class AracRepository extends DosyaRepository<Arac> {

    public AracRepository(Path dosya) {
        super(dosya);
        yukle();
    }

    @Override
    protected String satiraCevir(Arac a) {
        return String.join(AYRAC, a.getTur(), a.getPlaka(),
                String.format(Locale.ROOT, "%.0f", a.getKapasite()));
    }

    @Override
    protected Arac satirdanOku(String[] a) {
        return AracFabrikasi.olustur(a[0], a[1], Double.parseDouble(a[2]));
    }

    @Override
    protected String idGetir(Arac a) {
        return a.getPlaka();
    }
}