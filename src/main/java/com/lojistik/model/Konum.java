package com.lojistik.model;

import java.util.Objects;

public class Konum {
    private final String ad;
    private final double x;
    private final double y;

    public Konum(String ad, double x, double y) {
        if (ad == null || ad.isBlank()) throw new IllegalArgumentException("Konum adı boş olamaz");
        this.ad = ad;
        this.x = x;
        this.y = y;
    }

    /** Tüm rotaların başlayıp bittiği merkez depo (0, 0). */
    public static Konum depo() {
        return new Konum("Depo", 0, 0);
    }

    public String getAd() { return ad;}
    public double getX() { return x;}
    public double getY() { return y;}

    public double mesafe(Konum diger) {
        return Math.hypot(x - diger.x, y - diger.y);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Konum k)) return false;
        return Double.compare(x, k.x) == 0 && Double.compare(y, k.y) == 0 && ad.equals(k.ad);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ad, x, y);
    }

    @Override
    public String toString(){
        return String.format("%s(%.1f, %.1f)", ad, x, y);
    }
}
