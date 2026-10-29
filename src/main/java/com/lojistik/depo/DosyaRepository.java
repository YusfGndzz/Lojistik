package com.lojistik.depo;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

/**
 * Kayıtları bir metin dosyasında satır satır tutan ortak repository.
 * Açılışta dosyayı okur, her değişiklikte dosyayı yeniden yazar.
 */
public abstract class DosyaRepository<T> implements Repository<T> {
    protected static final String AYIRAC = ";";

    private final Path dosya;
    private final List<T> kayitlar = new ArrayList<>();

    protected DosyaRepository(Path dosya) {
        this.dosya = dosya;
        yukle();
    }

    protected abstract String anahtar(T nesne);
    protected abstract String satiraCevir(T nesne);
    protected abstract T satirdanOlustur(String[] alanlar);

    @Override
    public void ekle(T nesne) {
        if (bul(anahtar(nesne)).isPresent()) {
            throw new IllegalArgumentException("Bu kayıt zaten var: " + anahtar(nesne));
        }
        kayitlar.add(nesne);
        kaydet();
    }

    @Override
    public boolean sil(String anahtar) {
        boolean silindi = kayitlar.removeIf(k -> ayniAnahtar(anahtar(k), anahtar));
        if (silindi) kaydet();
        return silindi;
    }

    @Override
    public Optional<T> bul(String anahtar) {
        return kayitlar.stream().filter(k -> ayniAnahtar(anahtar(k), anahtar)).findFirst();
    }

    @Override
    public List<T> hepsiniGetir() {
        return Collections.unmodifiableList(kayitlar);
    }

    private static boolean ayniAnahtar(String a, String b) {
        return a.toUpperCase(Locale.ROOT).equals(b.toUpperCase(Locale.ROOT));
    }

    protected static String alan(String deger) {
        if (deger.contains(AYIRAC)) {
            throw new IllegalArgumentException("Değer '" + AYIRAC + "' karakteri içeremez: " + deger);
        }
        return deger;
    }

    private void yukle() {
        if (!Files.exists(dosya)) return;
        try {
            for (String satir : Files.readAllLines(dosya, StandardCharsets.UTF_8)) {
                if (satir.isBlank()) continue;
                kayitlar.add(satirdanOlustur(satir.split(AYIRAC, -1)));
            }
        } catch (IOException e) {
            throw new UncheckedIOException("Dosya okunamadı: " + dosya, e);
        }
    }

    private void kaydet() {
        List<String> satirlar = kayitlar.stream().map(this::satiraCevir).toList();
        try {
            if (dosya.getParent() != null) Files.createDirectories(dosya.getParent());
            Files.write(dosya, satirlar, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new UncheckedIOException("Dosya yazılamadı: " + dosya, e);
        }
    }
}
