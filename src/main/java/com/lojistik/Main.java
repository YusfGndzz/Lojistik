package com.lojistik;

import com.lojistik.arayuz.KonsolMenu;
import com.lojistik.depo.AracRepository;
import com.lojistik.depo.PaketRepository;
import com.lojistik.rota.EnYakinKomsuOptimizer;
import com.lojistik.servis.LojistikServisi;
import com.lojistik.strateji.EnIyiYerlesimStratejisi;

import java.nio.file.Path;
import java.util.Scanner;

/** Programın giriş noktası: nesneleri oluşturup birbirine bağlar ve menüyü başlatır. */
public class Main {
    public static void main(String[] args) {
        PaketRepository paketRepo = new PaketRepository(Path.of("data", "paketler.txt"));
        AracRepository aracRepo = new AracRepository(Path.of("data", "araclar.txt"));

        LojistikServisi servis = new LojistikServisi(
                paketRepo, aracRepo,
                new EnIyiYerlesimStratejisi(),
                new EnYakinKomsuOptimizer());

        new KonsolMenu(servis, paketRepo, aracRepo, new Scanner(System.in)).baslat();
    }
}