package com.lojistik.model;

public class Paket {
    private final String id;
    private final String alici;
    private final double agirlik;
    private final Konum hedef;
    private final Oncelik oncelik;

    public Paket(String id, String alici, double agirlik, Konum hedef, Oncelik oncelik) {
        if (id == null || id.isBlank()) throw new IllegalArgumentException("Paket id boş olamaz");
        if (agirlik <= 0) throw new IllegalArgumentException("Ağırlık pozitif olmalı");
        if (hedef == null) throw new IllegalArgumentException("Hedef konum gerekli");
        this.id = id;
        this.alici = alici;
        this.agirlik = agirlik;
        this.hedef = hedef;
        this.oncelik = oncelik == null ? Oncelik.NORMAL : oncelik;
    }

    public String getId() { return id;}
    public String getAlici() { return alici;}
    public double getAgirlik() { return agirlik;}
    public Konum getHedef() { return hedef;}
    public Oncelik getOncelik() { return oncelik;}

    @Override 
    public String toString(){
        return String.format("[%s] %-12s %6.1f kg  %-7s -> %s", id, alici, agirlik, oncelik, hedef);
    }
}