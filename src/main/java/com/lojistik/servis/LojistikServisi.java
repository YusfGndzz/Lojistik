package com.lojistik.servis;

import com.lojistik.depo.Repository;
import com.lojistik.model.Arac;
import com.lojistik.model.Konum;
import com.lojistik.model.Paket;
import com.lojistik.model.Rota;
import com.lojistik.rota.RotaOptimizer;
import com.lojistik.strateji.DagitimStratejisi;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * Uygulamanın beyni: paketleri araçlara dağıtır, her araç için rota çıkarır.
 *
 * DEPENDENCY INVERSION: Somut sınıfları değil arayüzleri (Repository, DagitimStratejisi,
 * RotaOptimizer) bilir; hepsi kurucudan dışarıdan verilir (constructor injection).
 */
public class LojistikServisi {
    private final Repository<Paket> paketRepo;
    private final Repository<Arac> aracRepo;
    private final RotaOptimizer rotaOptimizer;
    private DagitimStratejisi strateji;

    public LojistikServisi(Repository<Paket> paketRepo, Repository<Arac> aracRepo,
                           DagitimStratejisi strateji, RotaOptimizer rotaOptimizer) {
        this.paketRepo = paketRepo;
        this.aracRepo = aracRepo;
        this.strateji = strateji;
        this.rotaOptimizer = rotaOptimizer;
    }

    /** Strategy kalıbı: dağıtım yöntemi çalışma anında değiştirilebilir. */
    public void setStrateji(DagitimStratejisi strateji) {
        this.strateji = strateji;
    }

    public DagitimStratejisi getStrateji() {
        return strateji;
    }

    public DagitimPlani planla() {
        List<Arac> araclar = aracRepo.hepsiniGetir();
        araclar.forEach(Arac::bosalt);

        List<Paket> atanamayan = strateji.dagit(paketRepo.hepsiniGetir(), araclar);

        List<Rota> rotalar = new ArrayList<>();
        for (Arac arac : araclar) {
            if (arac.getYuk().isEmpty()) continue; // boş araç yola çıkmaz
            List<Konum> tur = rotaOptimizer.optimizeEt(Konum.depo(), benzersizDuraklar(arac));
            rotalar.add(new Rota(arac, tur));
        }
        return new DagitimPlani(rotalar, atanamayan);
    }

    /** Aynı adrese giden birden çok paket varsa durak bir kez ziyaret edilir. */
    private List<Konum> benzersizDuraklar(Arac arac) {
        Set<String> gorulen = new LinkedHashSet<>();
        List<Konum> duraklar = new ArrayList<>();
        for (Paket p : arac.getYuk()) {
            Konum k = p.getHedef();
            String anahtar = k.getAd() + "@" + k.getX() + "," + k.getY();
            if (gorulen.add(anahtar)) duraklar.add(k);
        }
        return duraklar;
    }
}