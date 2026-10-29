package com.lojistik.servis;

import com.lojistik.model.Paket;
import com.lojistik.model.Rota;

import java.util.Collections;
import java.util.List;

/** Planlama sonucunu tutar: araç rotaları + hiçbir araca sığmayan paketler. */
public class DagitimPlani {
    private final List<Rota> rotalar;
    private final List<Paket> atanamayanlar;

    public DagitimPlani(List<Rota> rotalar, List<Paket> atanamayanlar) {
        this.rotalar = List.copyOf(rotalar);
        this.atanamayanlar = List.copyOf(atanamayanlar);
    }

    public List<Rota> getRotalar() { return Collections.unmodifiableList(rotalar); }
    public List<Paket> getAtanamayanlar() { return Collections.unmodifiableList(atanamayanlar); }

    public double toplamMesafe() {
        return rotalar.stream().mapToDouble(Rota::toplamMesafe).sum();
    }

    public double toplamMaliyet() {
        return rotalar.stream().mapToDouble(Rota::maliyet).sum();
    }
}