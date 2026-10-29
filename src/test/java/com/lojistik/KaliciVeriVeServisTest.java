package com.lojistik;

import com.lojistik.depo.AracRepository;
import com.lojistik.depo.PaketRepository;
import com.lojistik.model.*;
import com.lojistik.rota.EnYakinKomsuOptimizer;
import com.lojistik.servis.DagitimPlani;
import com.lojistik.servis.LojistikServisi;
import com.lojistik.strateji.EnIyiYerlesimStratejisi;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class KaliciVeriVeServisTest {

    @TempDir
    Path klasor;

    @Test
    void paketlerDosyayaYazilipGeriOkunur() {
        Path dosya = klasor.resolve("paketler.txt");
        new PaketRepository(dosya).ekle(
                new Paket("P1", "Ayşe", 3.5, new Konum("Kadıköy", 12, -4), Oncelik.ACIL));

        // Yeni bir repository = programın yeniden açılması gibi
        PaketRepository yeniden = new PaketRepository(dosya);
        Paket okunan = yeniden.hepsiniGetir().get(0);

        assertEquals("P1", okunan.getId());
        assertEquals(3.5, okunan.getAgirlik(), 1e-9);
        assertEquals(Oncelik.ACIL, okunan.getOncelik());
        assertEquals("Kadıköy", okunan.getHedef().getAd());
    }

    @Test
    void ayniPlakaIkiKezEklenemez() {
        AracRepository repo = new AracRepository(klasor.resolve("araclar.txt"));
        repo.ekle(new Kamyon("34AA01", 1000));
        assertThrows(IllegalArgumentException.class, () -> repo.ekle(new Kamyonet("34aa01", 300)));
    }

    @Test
    void servisPlanindaSigmayanPaketlerRaporlanir() {
        PaketRepository paketler = new PaketRepository(klasor.resolve("p.txt"));
        AracRepository araclar = new AracRepository(klasor.resolve("a.txt"));
        araclar.ekle(new Kamyonet("K1", 100));
        paketler.ekle(new Paket("P1", "A", 60, new Konum("A", 3, 4), Oncelik.NORMAL));
        paketler.ekle(new Paket("P2", "B", 70, new Konum("B", 6, 8), Oncelik.NORMAL));

        LojistikServisi servis = new LojistikServisi(paketler, araclar,
                new EnIyiYerlesimStratejisi(), new EnYakinKomsuOptimizer());
        DagitimPlani plan = servis.planla();

        assertEquals(1, plan.getRotalar().size());
        assertEquals(1, plan.getAtanamayanlar().size());
        // Algoritma ağır paketi (P2, 70 kg) önce yerleştirir, 60 kg'lık P1 açıkta kalır
        assertEquals("P1", plan.getAtanamayanlar().get(0).getId());
        assertEquals(20.0, plan.toplamMesafe(), 1e-9); // depo -> (6,8) -> depo = 10 + 10
    }
}