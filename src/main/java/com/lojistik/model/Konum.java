package com.lojistik.model;

public final class Konum{
    private final String ad;
    private final double x;
    private final double y;

    public Konum(String ad, double x, double y){
        if (ad == null || ad.isBlank()){
            throw new IllegalArgumentException("Konum adı boş olamaz");
        }
        this.ad = ad;
        this.x = x;
        this.y = y;
    }

    public static Konum depo() {
        return new Konum("Depo", 0, 0);
    }

    public double mesafe(Konum diger){
        double dx = x - diger.x;
        double dy = y - diger.y;
        return Math.sqrt(dx * dx +dy * dy);
    }

    public String getAd() { return ad; }
    public double getX() { return x; }
    public double getY() {return y;}

    @Override 
    public String toString() {
        return String.format("%s(%.1f, %.1f)", ad ,x ,y);
    }
}