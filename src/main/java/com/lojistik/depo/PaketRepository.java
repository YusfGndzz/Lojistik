package com.lojistik.depo;

import com.lojistik.model.Konum;
import com.lojistik.model.Oncelik;
import com.lojistik.model.Paket;

import java.nio.file.Path;

/** Satır biçimi: id;alici;agirlik;oncelik;konumAdi;x;y */
public class PaketRepository extends DosyaRepository<Paket> {

    public PaketRepository(Path dosya) {
        super(dosya);
    }

    @Override
    protected String anahtar(Paket paket) {
        return paket.getId();
    }

    @Override
    protected String satiraCevir(Paket p) {
        Konum h = p.getHedef();
        return String.join(AYIRAC, alan(p.getId()), alan(p.getAlici()), Double.toString(p.getAgirlik()),
                p.getOncelik().name(), alan(h.getAd()), Double.toString(h.getX()), Double.toString(h.getY()));
    }

    @Override
    protected Paket satirdanOlustur(String[] a) {
        Konum hedef = new Konum(a[4], Double.parseDouble(a[5]), Double.parseDouble(a[6]));
        return new Paket(a[0], a[1], Double.parseDouble(a[2]), hedef, Oncelik.valueOf(a[3]));
    }
}
