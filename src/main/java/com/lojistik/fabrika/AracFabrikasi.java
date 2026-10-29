package com.lojistik.fabrika;

import com.lojistik.model.Arac;
import com.lojistik.model.Kamyon;
import com.lojistik.model.Kamyonet;
import com.lojistik.model.Motokurye;

/**
 * TASARIM KALIBI: Factory (Fabrika).
 * Araç nesnelerinin "new" ile nerede, hangi sınıftan oluşturulacağı tek yerde toplanır.
 * Menü ve dosyadan okuma kodu somut araç sınıflarını bilmek zorunda kalmaz.
 * Yeni bir araç türü eklemek için sadece burası ve yeni sınıf değişir.
 */
public final class AracFabrikasi {

    private AracFabrikasi() { }

    public static Arac olustur(String tur, String plaka, double kapasite) {
        if (tur == null) throw new IllegalArgumentException("Araç türü boş olamaz");
        switch (tur.trim().toLowerCase(java.util.Locale.ROOT)) {
            case "kamyon":
                return new Kamyon(plaka, kapasite);
            case "kamyonet":
                return new Kamyonet(plaka, kapasite);
            case "motokurye":
            case "motor":
                return new Motokurye(plaka, kapasite);
            default:
                throw new IllegalArgumentException("Bilinmeyen araç türü: " + tur);
        }
    }
}