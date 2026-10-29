package com.lojistik.depo;

import java.util.List;
import java.util.Optional;

public interface Repository<T> {
    void ekle(T nesne);
    boolean sil(String anahtar);
    Optional<T> bul(String anahtar);
    List<T> hepsiniGetir();
}
