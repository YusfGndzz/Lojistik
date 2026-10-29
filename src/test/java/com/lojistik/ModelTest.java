package com.lojistik;

import com.lojistik.fabrika.AracFabrikasi;
import com.lojistik.model.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ModelTest {

    @Test
    void konumlarArasiMesafeOklidYontemiyleHesaplanir() {
        Konum a = new Konum("A", 0, 0);
        Konum b = new Konum("B", 3, 4);
        assertEquals(5.0, a.mesafe(b), 1e-9);
    }

    @Test
    void aracKapasitesiniAsanPaketiYuklemez() {
        Arac arac = new Motokurye("34MK01", 10);
        assertTrue(arac.yukle(new Paket("P1", "A", 7, new Konum("A", 1, 1), Oncelik.NORMAL)));
        assertFalse(arac.yukle(new Paket("P2", "B", 4, new Konum("B", 2, 2), Oncelik.NORMAL)));
        assertEquals(7.0, arac.getDoluluk(), 1e-9);
        assertEquals(3.0, arac.kalanKapasite(), 1e-9);
    }

    @Test
    void gecersizDegerlerReddedilir() {
        Konum k = new Konum("A", 1, 1);
        assertThrows(IllegalArgumentException.class, () -> new Paket("P1", "A", 0, k, Oncelik.NORMAL));
        assertThrows(IllegalArgumentException.class, () -> new Paket(" ", "A", 5, k, Oncelik.NORMAL));
        assertThrows(IllegalArgumentException.class, () -> new Kamyon("34KM01", -1));
        assertThrows(IllegalArgumentException.class, () -> new Kamyon("", 100));
    }

    @Test
    void oncelikVerilmezseNormalOlur() {
        Paket p = new Paket("P1", "A", 5, new Konum("A", 1, 1), null);
        assertEquals(Oncelik.NORMAL, p.getOncelik());
    }

    @Test
    void fabrikaTipeGoreDogruAraciUretir() {
        assertInstanceOf(Kamyon.class, AracFabrikasi.olustur("kamyon", "34A1", 1000));
        assertInstanceOf(Kamyonet.class, AracFabrikasi.olustur("KAMYONET", "34A2", 300));
        assertInstanceOf(Motokurye.class, AracFabrikasi.olustur(" Motokurye ", "34A3", 20));
        assertThrows(IllegalArgumentException.class, () -> AracFabrikasi.olustur("UCAK", "34A4", 50));
    }
}
