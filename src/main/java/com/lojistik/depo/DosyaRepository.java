package com.lojistik.depo;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Metin dosyasında her satıra bir kayıt yazan ortak (soyut) repository.
 * Her ekleme/silme işleminde dosya hemen güncellenir => veriler kalıcıdır.
 * Satıra çevirme ve satırdan okuma işi alt sınıflara bırakılmıştır (Template Method fikri).
 */
public abstract class DosyaRepository<T> implements Repository<T> {
    protected static final String AYRAC = ";";

    private final Path dosya;
    private final List<T> kayitlar = new ArrayList<>();

    protected DosyaRepository(Path dosya) {
        this.dosya = dosya;
    }

    protected abstract String satiraCevir(T nesne);

    protected abstract T satirdanOku(String[] alanlar);

    protected abstract String idGetir(T nesne);

    /** Kurucuda değil, alt sınıf hazır olduktan sonra çağrılır. */
    protected void yukle() {
        kayitlar.clear();
        if (!Files.exists(dosya)) return;
        try {
            for (String satir : Files.readAllLines(dosya, StandardCharsets.UTF_8)) {
                if (satir.isBlank() || satir.startsWith("#")) continue;
                try {
                    kayitlar.add(satirdanOku(satir.split(AYRAC)));
                } catch (RuntimeException e) {
                    System.err.println("Bozuk satır atlandı: " + satir);
                }
            }
        } catch (IOException e) {
            throw new UncheckedIOException("Dosya okunamadı: " + dosya, e);
        }
    }

    private void kaydet() {
        try {
            if (dosya.getParent() != null) Files.createDirectories(dosya.getParent());
            List<String> satirlar = new ArrayList<>();
            for (T k : kayitlar) satirlar.add(satiraCevir(k));
            Files.write(dosya, satirlar, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new UncheckedIOException("Dosyaya yazılamadı: " + dosya, e);
        }
    }

    @Override
    public List<T> hepsiniGetir() {
        return new ArrayList<>(kayitlar);
    }

    @Override
    public void ekle(T nesne) {
        String id = idGetir(nesne);
        if (kayitlar.stream().anyMatch(k -> idGetir(k).equalsIgnoreCase(id))) {
            throw new IllegalArgumentException("Bu kimlikle kayıt zaten var: " + id);
        }
        kayitlar.add(nesne);
        kaydet();
    }

    @Override
    public boolean sil(String id) {
        boolean silindi = kayitlar.removeIf(k -> idGetir(k).equalsIgnoreCase(id));
        if (silindi) kaydet();
        return silindi;
    }
}