package com.lojistik.arayuz;

import com.lojistik.depo.Repository;
import com.lojistik.fabrika.AracFabrikasi;
import com.lojistik.model.Arac;
import com.lojistik.model.Konum;
import com.lojistik.model.Oncelik;
import com.lojistik.model.Paket;
import com.lojistik.model.Rota;
import com.lojistik.servis.DagitimPlani;
import com.lojistik.servis.LojistikServisi;
import com.lojistik.strateji.EnIyiYerlesimStratejisi;
import com.lojistik.strateji.IlkUygunStratejisi;

import java.util.List;
import java.util.Locale;
import java.util.Scanner;

/** Sadece kullanıcıyla konuşmaktan (girdi/çıktı) sorumlu sınıf (Single Responsibility). */
public class KonsolMenu {
    private final LojistikServisi servis;
    private final Repository<Paket> paketRepo;
    private final Repository<Arac> aracRepo;
    private final Scanner sc;

    public KonsolMenu(LojistikServisi servis, Repository<Paket> paketRepo,
                      Repository<Arac> aracRepo, Scanner sc) {
        this.servis = servis;
        this.paketRepo = paketRepo;
        this.aracRepo = aracRepo;
        this.sc = sc;
    }

    public void baslat() {
        System.out.println("=== AKILLI LOJİSTİK SİSTEMİ ===");
        while (true) {
            System.out.println();
            System.out.println("1) Paketleri listele      2) Paket ekle      3) Paket sil");
            System.out.println("4) Araçları listele       5) Araç ekle       6) Araç sil");
            System.out.println("7) Dağıtım planı oluştur  8) Strateji değiştir (şu an: " + servis.getStrateji().getAd() + ")");
            System.out.println("0) Çıkış");
            String secim = sor("Seçim: ");
            try {
                switch (secim) {
                    case "1" -> listele(paketRepo.hepsiniGetir(), "paket");
                    case "2" -> paketEkle();
                    case "3" -> sonuc(paketRepo.sil(sor("Silinecek paket id: ")));
                    case "4" -> listele(aracRepo.hepsiniGetir(), "araç");
                    case "5" -> aracEkle();
                    case "6" -> sonuc(aracRepo.sil(sor("Silinecek plaka: ")));
                    case "7" -> planYazdir(servis.planla());
                    case "8" -> stratejiDegistir();
                    case "0" -> {
                        System.out.println("Veriler kaydedildi. Görüşmek üzere!");
                        return;
                    }
                    default -> System.out.println("Geçersiz seçim.");
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Hata: " + e.getMessage());
            }
        }
    }

    private void paketEkle() {
        String id = sor("Paket id (örn. P10): ");
        String alici = sor("Alıcı adı: ");
        double agirlik = sayiSor("Ağırlık (kg): ");
        String konumAdi = sor("Teslimat yeri adı: ");
        double x = sayiSor("X koordinatı (km): ");
        double y = sayiSor("Y koordinatı (km): ");
        String o = sor("Öncelik (NORMAL / HIZLI / ACIL): ").toUpperCase(Locale.ROOT);
        Oncelik oncelik = o.isBlank() ? Oncelik.NORMAL : Oncelik.valueOf(o);
        paketRepo.ekle(new Paket(id, alici, agirlik, new Konum(konumAdi, x, y), oncelik));
        System.out.println("Paket eklendi.");
    }

    private void aracEkle() {
        String tur = sor("Tür (kamyon / kamyonet / motokurye): ");
        String plaka = sor("Plaka: ");
        double kapasite = sayiSor("Kapasite (kg): ");
        aracRepo.ekle(AracFabrikasi.olustur(tur, plaka, kapasite));
        System.out.println("Araç eklendi.");
    }

    private void stratejiDegistir() {
        System.out.println("1) En İyi Yerleşim (Best Fit)   2) İlk Uygun (First Fit)");
        String s = sor("Seçim: ");
        servis.setStrateji(s.equals("2") ? new IlkUygunStratejisi() : new EnIyiYerlesimStratejisi());
        System.out.println("Strateji: " + servis.getStrateji().getAd());
    }

    private void planYazdir(DagitimPlani plan) {
        System.out.println("\n--- DAĞITIM PLANI (" + servis.getStrateji().getAd() + ") ---");
        for (Rota r : plan.getRotalar()) {
            Arac a = r.getArac();
            System.out.printf("%n%s %s  | doluluk %.1f / %.0f kg (%%%.0f)%n", a.getTur(), a.getPlaka(),
                    a.doluluk(), a.getKapasite(), 100 * a.doluluk() / a.getKapasite());
            for (Paket p : a.getYuk()) System.out.println("   paket " + p);
            StringBuilder yol = new StringBuilder("   Rota: ");
            for (Konum k : r.getDuraklar()) yol.append(k.getAd()).append(" -> ");
            yol.setLength(yol.length() - 4);
            System.out.println(yol);
            System.out.printf("   Mesafe: %.2f km   Maliyet: %.2f TL%n", r.toplamMesafe(), r.maliyet());
        }
        if (!plan.getAtanamayanlar().isEmpty()) {
            System.out.println("\n!! Hiçbir araca sığmayan paketler:");
            plan.getAtanamayanlar().forEach(p -> System.out.println("   " + p));
        }
        System.out.printf("%nTOPLAM: %d araç, %.2f km, %.2f TL%n",
                plan.getRotalar().size(), plan.toplamMesafe(), plan.toplamMaliyet());
    }

    private void listele(List<?> liste, String ad) {
        if (liste.isEmpty()) System.out.println("Kayıtlı " + ad + " yok.");
        liste.forEach(System.out::println);
    }

    private void sonuc(boolean basarili) {
        System.out.println(basarili ? "Silindi." : "Kayıt bulunamadı.");
    }

    private String sor(String mesaj) {
        System.out.print(mesaj);
        return sc.hasNextLine() ? sc.nextLine().trim() : "0";
    }

    private double sayiSor(String mesaj) {
        while (true) {
            try {
                return Double.parseDouble(sor(mesaj).replace(',', '.'));
            } catch (NumberFormatException e) {
                System.out.println("Lütfen sayı girin.");
            }
        }
    }
}