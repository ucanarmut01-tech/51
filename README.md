# GirisWebhook (Paper/Spigot eklentisi)

Yeni bir `/giriş` komutu KAYDETMEZ. Sadece dinler:

- `/giriş ...` ile başlayan her komut → Discord webhook'una gönderilir
  (komutu engellemez, mevcut eklentiniz normal şekilde çalışmaya devam eder)
- Oyuncuların yazdığı **her normal sohbet mesajı** → Discord webhook'una gönderilir

İkisini de `config.yml` içinden ayrı ayrı kapatabilirsiniz (`forward-giris-commands`,
`forward-all-chat`).

## ⚠️ Webhook URL güvenliği

Bu sohbette paylaştığınız webhook URL'si varsayılan olarak `config.yml` içine
konuldu. Discord'dan webhook'u silip yenisini oluşturmanızı ve
`plugins/GirisWebhook/config.yml` içindeki `webhook-url` değerini yeni URL ile
değiştirmenizi öneririm.

## Derleme (GitHub Actions ile, bilgisayarınıza bir şey kurmadan)

1. Bu klasörün TÜM içeriğini (src, build.gradle, settings.gradle, .github dahil)
   bir GitHub reposuna yükleyin.
2. Repo sayfasında **Actions** sekmesine gidin, "Build plugin" çalışmasının
   yeşil ✓ olmasını bekleyin (birkaç dakika sürer).
3. Çalışmanın altındaki **Artifacts** bölümünden `giriswebhook-build` dosyasını
   indirin, içinden `giriswebhook-1.0.0.jar` çıkar.

## Kurulum

1. Sunucunuzun **Paper** (veya Spigot) çalıştırdığından emin olun.
2. `giriswebhook-1.0.0.jar` dosyasını sunucunuzun `plugins/` klasörüne koyun.
3. Sunucuyu yeniden başlatın (veya `/reload` — önerilmez ama çalışır).
4. `plugins/GirisWebhook/config.yml` dosyası otomatik oluşur, webhook URL'nizi
   orada görür/değiştirirsiniz.

## Notlar

- Mevcut `/giriş` komutunuzu veren eklentiye hiç dokunmuyoruz; bu eklenti
  sadece "izliyor".
- `forward-all-chat: true` olduğu sürece oyuncuların yazdığı HER mesaj
  Discord'a gidecek — eğer bu istemiyorsanız `config.yml`'de `false` yapın.
- Konsoldan veya komut bloklarından çalıştırılan komutlar bu olayları
  tetiklemez; sadece gerçek oyuncuların sohbete/komut satırına yazdıkları
  yakalanır.
