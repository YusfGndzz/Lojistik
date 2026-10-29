package com.lojistik.rota;

import com.lojistik.model.Konum;

import java.util.List;

public interface RotaOptimizer {
    /** @return baslangic ile başlayıp baslangic ile biten, tüm durakları içeren tur */
    List<Konum> optimizeEt(Konum baslangic, List<Konum> duraklar);
}
