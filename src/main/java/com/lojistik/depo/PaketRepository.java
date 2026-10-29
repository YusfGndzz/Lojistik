package com.lojistik.depo;

import com.lojistik.model.Konum;
import com.lojistik.model.Oncelik;
import com.lojistik.model.Paket;

import java.nio.file.Path;
import java.util.Locale;

/** Paketleri data/paketler.txt dosyasında saklar. Satır: id;alici;agirlik;konumAdi;x;y;oncelik */
public class PaketRepository extends DosyaRepository<Paket> {

    public PaketRepository(Path dosya) {
        super(dosya);
        yukle();
    }

    @Override
    protected String satiraCevir(Paket p) {
        Konum h = p.getHedef();
        return String.join(AYRAC, p.getId(), p.getAlici(),
                String.format(Locale.ROOT, "%.2f", p.getAgirlik()),
                h.getAd(),
                String.format(Locale.ROOT, "%.2f", h.getX()),
                String.format(Locale.ROOT, "%.2f", h.getY()),
                p.getOncelik().name());
    }

    @Override
    protected Paket satirdanOku(String[] a) {
        Konum hedef = new Konum(a[3], Double.parseDouble(a[4]), Double.parseDouble(a[5]));
        return new Paket(a[0], a[1], Double.parseDouble(a[2]), hedef, Oncelik.valueOf(a[6]));
    }

    @Override
    protected String idGetir(Paket p) {
        return p.getId();
    }
}