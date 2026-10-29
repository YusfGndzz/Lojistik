package com.lojistik.servis;

import com.lojistik.depo.AracRepository;
import com.lojistik.depo.PaketRepository;
import com.lojistik.model.Arac;
import com.lojistik.model.Konum;
import com.lojistik.model.Paket;
import com.lojistik.model.Rota;
import com.lojistik.rota.RotaOptimizer;
import com.lojistik.strateji.DagitimStratejisi;

import java.util.ArrayList;
import java.util.List;

public class LojistikServisi {
    private final PaketRepository paketler;
    private final AracRepository araclar;
    private DagitimStratejisi strateji;
    private final RotaOptimizer optimizer;

    public LojistikServisi(PaketRepository paketler, AracRepository araclar,
                           DagitimStratejisi strateji, RotaOptimizer optimizer) {
        this.paketler = paketler;
        this.araclar = araclar;
        this.strateji = strateji;
        this.optimizer = optimizer;
    }

    public void setStrateji(DagitimStratejisi strateji) {
        this.strateji = strateji;
    }

    public DagitimPlani planla() {
        List<Arac> aracListesi = araclar.hepsiniGetir();
        aracListesi.forEach(Arac::bosalt);

        List<Paket> atanamayanlar = strateji.dagit(paketler.hepsiniGetir(), aracListesi);

        List<Rota> rotalar = new ArrayList<>();
        for (Arac arac : aracListesi) {
            if (arac.getYuk().isEmpty()) continue;
            List<Konum> duraklar = arac.getYuk().stream().map(Paket::getHedef).distinct().toList();
            rotalar.add(new Rota(arac, optimizer.optimizeEt(Konum.depo(), duraklar)));
        }
        return new DagitimPlani(rotalar, atanamayanlar);
    }
}
