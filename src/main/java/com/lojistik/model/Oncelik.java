package com.lojistik.model;

public enum Oncelik {
    NORMAL(1),
    HIZLI (2),
    ACIL (3);

    private final int seviye;

    Oncelik(int seviye) {
        this.seviye = seviye;
    }

    public  int getSeviye() {
        return seviye;
    }
}