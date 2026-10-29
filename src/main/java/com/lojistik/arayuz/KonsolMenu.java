package com.lojistik.arayuz;

import com.lojistik.depo.AracRepository;
import com.lojistik.depo.PaketRepository;
import com.lojistik.fabrika.AracFabrikasi;
import com.lojistik.model.Konum;
import com.lojistik.model.Oncelik;
import com.lojistik.model.Paket;
import com.lojistik.servis.LojistikServisi;
import com.lojistik.strateji.EnIyiYerlesimStratejisi;
import com.lojistik.strateji.IlkUygunStratejisi;

import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class KonsolMenu {
    private final PaketRepository paketler;
    private final AracRepository araclar;
    private final LojistikServisi servis;
    private final Scanner sc = new Scanner(System.in);

    public KonsolMenu(PaketRepository paketler, AracRepository araclar, LojistikServisi servis) {
        this.paketler = paketler;
        this.araclar = araclar;
        this.servis = servis;
    }

    public void baslat() {
        while (true) {
            System.out.println("""

                    === Akıllı Lojistik ===
                    1) Paket ekle
                    2) Araç ekle
                    3) Paketleri listele
                    4) Araçları listele
                    5) Dağıtım planı (en iyi yerleşim)
                    6) Dağıtım planı (ilk uygun)
                    0) Çıkış""");
            String secim = oku("Seçiminiz: ");
            try {
                switch (secim) {
                    case "1" -> paketEkle();
                    case "2" -> aracEkle();
                    case "3" -> listele(paketler.hepsiniGetir(), "Kayıtlı paket yok.");
                    case "4" -> listele(araclar.hepsiniGetir(), "Kayıtlı araç yok. Önce 2 ile araç ekleyin.");
                    case "5" -> { servis.setStrateji(new EnIyiYerlesimStratejisi()); System.out.println(servis.planla()); }
                    case "6" -> { servis.setStrateji(new IlkUygunStratejisi()); System.out.println(servis.planla()); }
                    case "0" -> { return; }
                    default -> System.out.println("Geçersiz seçim.");
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Hata: " + e.getMessage());
            }
        }
    }

    private void paketEkle() {
        String id = oku("Paket id: ");
        String alici = oku("Alıcı: ");
        double agirlik = sayiOku("Ağırlık (kg): ");
        String konumAdi = oku("Hedef adı: ");
        double x = sayiOku("Hedef x: ");
        double y = sayiOku("Hedef y: ");
        Oncelik oncelik = Oncelik.valueOf(oku("Öncelik (DUSUK/NORMAL/ACIL): ").toUpperCase(Locale.ROOT));
        paketler.ekle(new Paket(id, alici, agirlik, new Konum(konumAdi, x, y), oncelik));
        System.out.println("Paket eklendi.");
    }

    private void aracEkle() {
        String tip = oku("Tip (KAMYON/KAMYONET/MOTOKURYE): ");
        String plaka = oku("Plaka: ");
        double kapasite = sayiOku("Kapasite (kg): ");
        araclar.ekle(AracFabrikasi.olustur(tip, plaka, kapasite));
        System.out.println("Araç eklendi.");
    }

    private void listele(List<?> liste, String bosMesaj) {
        if (liste.isEmpty()) System.out.println(bosMesaj);
        else liste.forEach(System.out::println);
    }

    private String oku(String mesaj) {
        System.out.print(mesaj);
        return sc.hasNextLine() ? sc.nextLine().trim() : "0";
    }

    private double sayiOku(String mesaj) {
        String girdi = oku(mesaj).replace(',', '.');
        try {
            return Double.parseDouble(girdi);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Geçersiz sayı: " + girdi);
        }
    }
}
