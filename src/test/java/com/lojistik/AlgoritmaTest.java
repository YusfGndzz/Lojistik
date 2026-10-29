package com.lojistik;

import com.lojistik.model.*;
import com.lojistik.rota.EnYakinKomsuOptimizer;
import com.lojistik.strateji.EnIyiYerlesimStratejisi;
import com.lojistik.strateji.IlkUygunStratejisi;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AlgoritmaTest {

    private Paket paket(String id, double kg, Oncelik o) {
        return new Paket(id, "Test", kg, new Konum(id, kg, 1), o);
    }

    @Test
    void bestFitPaketiEnSikiSigacagiAracaKoyar() {
        Arac buyuk = new Kamyon("BUYUK", 1000);
        Arac kucuk = new Kamyonet("KUCUK", 100);
        new EnIyiYerlesimStratejisi().dagit(List.of(paket("P1", 90, Oncelik.NORMAL)), List.of(buyuk, kucuk));
        assertEquals(1, kucuk.getYuk().size(), "90 kg, 100'lük araca daha iyi oturur");
        assertTrue(buyuk.getYuk().isEmpty());
    }

    @Test
    void kapasiteYetmezseAcilPaketOnceliklidir() {
        Arac arac = new Kamyonet("K1", 100);
        Paket normal = paket("NORMAL", 80, Oncelik.NORMAL);
        Paket acil = paket("ACIL", 60, Oncelik.ACIL);

        List<Paket> kalan = new IlkUygunStratejisi().dagit(List.of(normal, acil), List.of(arac));

        assertTrue(arac.getYuk().contains(acil), "Acil paket yüklenmeli");
        assertEquals(List.of(normal), kalan, "Sığmayan normal paket raporlanmalı");
    }

    @Test
    void rotaDepodanBaslarDepoyaDonerVeHerDuragiGezer() {
        List<Konum> duraklar = List.of(
                new Konum("A", 5, 5), new Konum("B", -3, 2), new Konum("C", 8, -1), new Konum("D", 1, 9));

        List<Konum> tur = new EnYakinKomsuOptimizer().optimizeEt(Konum.depo(), duraklar);

        assertEquals("Depo", tur.get(0).getAd());
        assertEquals("Depo", tur.get(tur.size() - 1).getAd());
        assertEquals(duraklar.size() + 2, tur.size());
        assertTrue(tur.containsAll(duraklar));
    }

    @Test
    void ikiOptRotayiUzatmaz() {
        List<Konum> duraklar = List.of(
                new Konum("A", 10, 0), new Konum("B", 0, 10), new Konum("C", 10, 10),
                new Konum("D", 5, -2), new Konum("E", -4, 6), new Konum("F", 7, 3));

        double sadeceKomsu = Rota.toplamMesafe(new EnYakinKomsuOptimizer(false).optimizeEt(Konum.depo(), duraklar));
        double ikiOptli = Rota.toplamMesafe(new EnYakinKomsuOptimizer(true).optimizeEt(Konum.depo(), duraklar));

        assertTrue(ikiOptli <= sadeceKomsu + 1e-9);
    }
}