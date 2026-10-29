package com.lojistik.fabrika;

import com.lojistik.model.Arac;
import com.lojistik.model.Kamyon;
import com.lojistik.model.Kamyonet;
import com.lojistik.model.Motokurye;

import java.util.Locale;

public final class AracFabrikasi {
    private AracFabrikasi() {}

    public static Arac olustur(String tip, String plaka, double kapasite) {
        if (tip == null) throw new IllegalArgumentException("Araç tipi boş olamaz");
        return switch (tip.trim().toUpperCase(Locale.ROOT)) {
            case "KAMYON" -> new Kamyon(plaka, kapasite);
            case "KAMYONET" -> new Kamyonet(plaka, kapasite);
            case "MOTOKURYE" -> new Motokurye(plaka, kapasite);
            default -> throw new IllegalArgumentException("Bilinmeyen araç tipi: " + tip);
        };
    }
}
