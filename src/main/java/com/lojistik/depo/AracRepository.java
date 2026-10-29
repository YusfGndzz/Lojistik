package com.lojistik.depo;

import com.lojistik.fabrika.AracFabrikasi;
import com.lojistik.model.Arac;

import java.nio.file.Path;

/** Satır biçimi: tip;plaka;kapasite — plakalar büyük/küçük harf duyarsız benzersizdir. */
public class AracRepository extends DosyaRepository<Arac> {

    public AracRepository(Path dosya) {
        super(dosya);
    }

    @Override
    protected String anahtar(Arac arac) {
        return arac.getPlaka();
    }

    @Override
    protected String satiraCevir(Arac a) {
        return String.join(AYIRAC, a.getTip(), alan(a.getPlaka()), Double.toString(a.getKapasite()));
    }

    @Override
    protected Arac satirdanOlustur(String[] a) {
        return AracFabrikasi.olustur(a[0], a[1], Double.parseDouble(a[2]));
    }
}
