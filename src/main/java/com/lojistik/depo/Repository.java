package com.lojistik.depo;

import java.util.List;

/**
 * TASARIM KALIBI: Repository.
 * Verinin nerede/nasıl saklandığı (dosya, veritabanı...) bu arayüzün arkasında gizlenir.
 * Servis katmanı sadece bu arayüzü bilir (Dependency Inversion).
 */
public interface Repository<T> {

    List<T> hepsiniGetir();

    void ekle(T nesne);

    boolean sil(String id);
}