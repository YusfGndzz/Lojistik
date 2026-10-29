# Akıllı Lojistik ve Dağıtım Planlama Sistemi

**Hazırlayan:** Yusuf Kebapcı Gündüz – 1250505083 (bireysel proje)

Paketleri araçlara kapasite ve önceliğe göre dağıtan, her araç için depodan başlayıp depoya dönen kısa bir rota hesaplayan konsol uygulaması. Paket ve araç kayıtları metin dosyalarında saklanır, program kapanıp açıldığında veriler kaybolmaz.

## Özellikler

- Paket ekleme ve listeleme (id, alıcı, ağırlık, hedef konum, öncelik: DUSUK / NORMAL / ACIL)
- Araç ekleme ve listeleme (Kamyon, Kamyonet, Motokurye)
- İki dağıtım stratejisi:
  - **En iyi yerleşim (Best-Fit):** paketi, yüklendikten sonra en az boşluk kalacak araca koyar
  - **İlk uygun (First-Fit):** paketi sığdığı ilk araca koyar
- Paketler önce önceliğe, aynı öncelikte ağırlığa göre sıralanır (acil ve ağır paket önce)
- Rota optimizasyonu: en yakın komşu sezgiseli + 2-opt iyileştirmesi
- Hiçbir araca sığmayan paketler planda ayrıca raporlanır

## Nesneye Yönelik Programlama İlkeleri

| İlke | Projede kullanımı |
|---|---|
| Sınıf ve nesne | 23 sınıf/arayüz/enum; `Paket`, `Arac`, `Konum`, `Rota` gibi alan nesneleri |
| Kapsülleme | Tüm alanlar `private`; `Arac` yük listesini dışarıya yalnızca değiştirilemez liste olarak verir, yükleme sadece `yukle()` ile ve kapasite kontrolüyle yapılır |
| Kalıtım | `Arac` → `Kamyon`, `Kamyonet`, `Motokurye`; `DosyaRepository<T>` → `PaketRepository`, `AracRepository` |
| Soyutlama | `Arac` ve `DosyaRepository<T>` soyut sınıf; `DagitimStratejisi`, `RotaOptimizer`, `Repository<T>` arayüz |
| Çok biçimlilik | `getTip()` her araçta farklı; servis, stratejiyi ve optimizer'ı arayüz üzerinden çağırır, menüden çalışma anında strateji değiştirilir |

## SOLID Prensipleri

| Prensip | Projede kullanımı |
|---|---|
| **S** – Tek Sorumluluk | Her sınıfın tek işi var: `PaketSiralayici` sadece sıralar, `DosyaRepository` sadece dosyaya okur/yazar, `KonsolMenu` sadece kullanıcıyla konuşur, `LojistikServisi` sadece planlar |
| **O** – Açık/Kapalı | Yeni bir dağıtım algoritması veya araç tipi eklemek için mevcut kod değiştirilmez, yeni sınıf yazılır (`DagitimStratejisi` / `Arac` genişletilir) |
| **L** – Liskov Yerine Geçme | `Kamyon`, `Kamyonet`, `Motokurye` her yerde `Arac` yerine kullanılabilir; iki strateji de birbirinin yerine sorunsuz geçer |
| **I** – Arayüz Ayrımı | Küçük ve amaca özel arayüzler: `DagitimStratejisi` tek metot, `RotaOptimizer` tek metot, `Repository<T>` sadece CRUD |
| **D** – Bağımlılığın Tersine Çevrilmesi | `LojistikServisi` somut sınıflara değil `DagitimStratejisi` ve `RotaOptimizer` arayüzlerine bağlıdır; bağımlılıklar `Main` içinde yapıcıdan verilir |

## Tasarım Kalıpları

| Kalıp | Nerede |
|---|---|
| Strategy | `DagitimStratejisi` → `EnIyiYerlesimStratejisi`, `IlkUygunStratejisi`; `RotaOptimizer` → `EnYakinKomsuOptimizer` |
| Factory | `AracFabrikasi.olustur(tip, plaka, kapasite)` hem menüde hem dosyadan okurken araç üretir |
| Repository | `Repository<T>` → `DosyaRepository<T>` → `PaketRepository`, `AracRepository` |
| Template Method | `DosyaRepository` okuma/yazma akışını tanımlar, alt sınıflar sadece `satiraCevir` / `satirdanOlustur` / `anahtar` adımlarını doldurur |

## Algoritmalar

- **Paket dağıtımı:** Bin packing problemi için Best-Fit ve First-Fit sezgiselleri. Paketler önce önceliğe göre (ACIL > NORMAL > DUSUK), sonra ağırlığa göre büyükten küçüğe sıralanır (Decreasing).
- **Rota optimizasyonu:** Gezgin satıcı problemi için en yakın komşu sezgiseli ile ilk tur kurulur, ardından 2-opt ile çapraz kenarlar düzeltilerek tur kısaltılır. Depo turun başında ve sonunda sabittir.

Sınıf diyagramı `uml.puml` dosyasındadır (PlantUML).

## Proje Yapısı

```
src/main/java/com/lojistik/
├── Main.java          giriş noktası
├── model/             Konum, Oncelik, Paket, Arac ve alt sınıfları, Rota
├── strateji/          dağıtım stratejileri ve paket sıralayıcı
├── rota/              rota optimizasyonu
├── fabrika/           araç fabrikası
├── depo/              dosya tabanlı kalıcı veri (repository)
├── servis/            dağıtım planlama servisi
└── arayuz/            konsol menüsü
src/test/java/com/lojistik/   JUnit 5 testleri
data/                         paketler.txt, araclar.txt
```

## Veri Dosyaları

Alanlar `;` ile ayrılır.

- `data/paketler.txt` → `id;alici;agirlik;oncelik;konumAdi;x;y`
- `data/araclar.txt` → `tip;plaka;kapasite`

Merkez depo (0, 0) noktasındadır, mesafeler düzlemde düz çizgi (Öklid) olarak hesaplanır.

## Çalıştırma

Gereksinim: JDK 17 veya üzeri, Maven.

```
mvn compile exec:java -Dexec.mainClass=com.lojistik.Main
```

veya jar oluşturup:

```
mvn package
java -jar target/akilli-lojistik-1.0.jar
```

Program `data/` klasörünü bulunduğu dizinde aradığı için proje kök klasöründen çalıştırılmalıdır.

## Testler

```
mvn test
```

Model, algoritma (strateji ve rota) ve kalıcı veri/servis katmanları için toplam 12 test bulunur.
