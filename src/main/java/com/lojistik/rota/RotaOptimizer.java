package com.lojistik.rota;

import com.lojistik.model.Konum;

import java.util.List;

/** Bir başlangıç noktası ve durak listesinden en kısa turu üreten algoritmaların arayüzü. */
public interface RotaOptimizer {

    /**
     * @return başlangıç ile başlayıp başlangıç ile biten, tüm durakları içeren sıralı liste
     */
    List<Konum> optimizeEt(Konum baslangic, List<Konum> duraklar);
}