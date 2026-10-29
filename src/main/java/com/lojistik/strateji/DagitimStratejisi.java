package com.lojistik.strateji;

import com.lojistik.model.Arac;
import com.lojistik.model.Paket;

import java.util.List;

public interface DagitimStratejisi {
    /**
     * Paketleri araçlara yükler.
     * @return hiçbir araca sığmayan paketler
     */
    List<Paket> dagit(List<Paket> paketler, List<Arac> araclar);
}
