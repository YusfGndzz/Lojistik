package com.lojistik.servis;

import com.lojistik.model.Paket;
import com.lojistik.model.Rota;

import java.util.List;

public class DagitimPlani {
    private final List<Rota> rotalar;
    private final List<Paket> atanamayanlar;

    public DagitimPlani(List<Rota> rotalar, List<Paket> atanamayanlar) {
        this.rotalar = List.copyOf(rotalar);
        this.atanamayanlar = List.copyOf(atanamayanlar);
    }

    public List<Rota> getRotalar() { return rotalar;}
    public List<Paket> getAtanamayanlar() { return atanamayanlar;}

    public double toplamMesafe() {
        return rotalar.stream().mapToDouble(Rota::getMesafe).sum();
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder("=== Dağıtım Planı ===\n");
        for (Rota rota : rotalar) {
            sb.append(rota.getArac()).append('\n');
            sb.append("  Rota: ").append(rota).append('\n');
        }
        sb.append(String.format("Toplam mesafe: %.2f km%n", toplamMesafe()));
        if (!atanamayanlar.isEmpty()) {
            sb.append("Sığmayan paketler:\n");
            atanamayanlar.forEach(p -> sb.append("  ").append(p).append('\n'));
        }
        return sb.toString();
    }
}
