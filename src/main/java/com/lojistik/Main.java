package com.lojistik;

import com.lojistik.arayuz.KonsolMenu;
import com.lojistik.depo.AracRepository;
import com.lojistik.depo.PaketRepository;
import com.lojistik.rota.EnYakinKomsuOptimizer;
import com.lojistik.servis.LojistikServisi;
import com.lojistik.strateji.EnIyiYerlesimStratejisi;

import java.nio.file.Path;

public class Main {
    public static void main(String[] args) {
        PaketRepository paketler = new PaketRepository(Path.of("data", "paketler.txt"));
        AracRepository araclar = new AracRepository(Path.of("data", "araclar.txt"));
        LojistikServisi servis = new LojistikServisi(paketler, araclar,
                new EnIyiYerlesimStratejisi(), new EnYakinKomsuOptimizer());
        new KonsolMenu(paketler, araclar, servis).baslat();
    }
}
