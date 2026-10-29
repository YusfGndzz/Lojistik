package com.lojistik.strateji;

import com.lojistik.model.Arac;
import com.lojistik.model.Paket;

import java.util.List;

/**
 * TASARIM KALIBI: Strategy.
 * Paketlerin araçlara nasıl dağıtılacağı bu arayüzün arkasına saklanır;
 * servis hangi stratejinin kullanıldığını bilmez, çalışma anında değiştirilebilir.
 */
public interface DagitimStratejisi {

    /**
     * Paketleri araçlara yükler.
     * @return hiçbir araca sığmayan (atanamayan) paketler
     */
    List<Paket> dagit(List<Paket> paketler, List<Arac> araclar);

    String getAd();
}