package com.lojistik;

import com.lojistik.fabrika.AracFabrikasi;
import com.lojistik.model.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ModelTest {

    private Paket paket(String id, double kg) {
        return new Paket(id, "Test", kg, new Konum("X", 1, 1), Oncelik.NORMAL);
    }

    @Test
    void konumlarArasiMesafeDogruHesaplanir() {
        Konum a = new Konum("A", 0, 0);
        Konum b = new Konum("B", 3, 4);
        assertEquals(5.0, a.mesafe(b), 1e-9); // 3-4-5 üçgeni
    }

    @Test
    void negatifAgirlikliPaketOlusturulamaz() {
        assertThrows(IllegalArgumentException.class,
                () -> new Paket("P1", "Ali", -2, Konum.depo(), Oncelik.NORMAL));
    }

    @Test
    void aracKapasitesiAsilamaz() {
        Arac kamyonet = new Kamyonet("34TEST", 100);
        assertTrue(kamyonet.yukle(paket("P1", 70)));
        assertFalse(kamyonet.yukle(paket("P2", 40)), "70+40 > 100, yüklenmemeli");
        assertEquals(30, kamyonet.kalanKapasite(), 1e-9);
    }

    @Test
    void motokuryeAgirPaketiReddeder() {
        Arac motor = new Motokurye("34MOTO", 50);
        assertFalse(motor.yukle(paket("P1", 8)), "5 kg üstü paket motokuryeye verilemez");
        assertTrue(motor.yukle(paket("P2", 3)));
    }

    @Test
    void maliyetAracTurunegoreFarklidir() {
        // Çok biçimlilik: aynı metot, farklı sınıflarda farklı sonuç
        double km = 10;
        assertEquals(500 + 10 * 12, new Kamyon("K", 1000).maliyetHesapla(km), 1e-9);
        assertEquals(200 + 10 * 6, new Kamyonet("K", 400).maliyetHesapla(km), 1e-9);
        assertEquals(10 * 2, new Motokurye("M", 20).maliyetHesapla(km), 1e-9);
    }

    @Test
    void fabrikaDogruAracTurunuUretir() {
        assertInstanceOf(Kamyon.class, AracFabrikasi.olustur("kamyon", "A1", 1000));
        assertInstanceOf(Motokurye.class, AracFabrikasi.olustur("MOTOKURYE", "A2", 20));
        assertThrows(IllegalArgumentException.class, () -> AracFabrikasi.olustur("uçak", "A3", 10));
    }
}