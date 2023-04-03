Feature: MWEB VA BOARD - UI Mweb

  @MWEB @REGRESSION @PRODUCT_DETAIL
  Scenario Outline: 1) Mweb Sticky Bar Fonksiyon (VA-211)
    Given load home page in "<base>"
    And go to a product detail from the "<category>" tab mweb
    Then check sticky bar visibility "<status1>"
    And scroll down until the for sticky bar
    Then check sticky bar visibility "<status2>"
    And check sticky background color is "<colorCode>" and sticky discount background is "<colorCodeDiscount>"
    And check sticky font colors is "<fontColor>"
    And scroll down until the MASAUSTU GORONUM field
    Then check sticky bar visibility "<status2>"

    Examples:
      | base           | category            | status1 | status2 | colorCode                        | colorCodeDiscount                        | fontColor                       |
      | msupplementler | Sporcu Vitaminleri  | false   | true    | stickyBarBackgroundSupplementler | stickyBarDiscountBackgroundSupplementler | stickyBarFontColorSupplementler |
      | mvitaminler    | Diğer Takviyeler    | false   | true    | stickyBarBackgroundVitaminler    | stickyBarDiscountBackgroundVitaminler    | stickyBarFontColorVitaminler    |
      | mfitmoda       | Fitness Ekipmanları | false   | true    | stickyBarBackgroundFitmoda       | stickyBarDiscountBackgroundFitmoda       | stickyBarFontColorFitmoda       |


  @MWEB @REGRESSION @GUEST @SIPARIS
  Scenario Outline: 2) Mweb Guest Checkout Sipariş Tamamlanan (VA-170)
    Given load home page in "<base>"
    And add a "<count>" products from "<category>" tab to the cart mweb
    Then add guest "<mail>" address and proceed to checkout mweb
    And complete the guest order with the "<payment>" method
    And hotmail login with "<mail>" user and check "<store>" mail - guest

    Examples:
      | base           | category            | count | mail   | store         | payment |
      | mvitaminler    | Diğer Takviyeler    | 1     | Guest2 | Vitaminler    | havale  |
      | mfitmoda       | Fitness Ekipmanları | 1     | Guest3 | Fitmoda       | havale  |
      | msupplementler | Sporcu Vitaminleri  | 1     | Guest1 | Supplementler | havale  |


  @MWEB @REGRESSION @GUEST
  Scenario Outline: 4) Mweb Guest Checkout Kayıtlı Kullanıcı Maili Hatası (VA-170)
    Given load home page in "<base>"
    And add a "<count>" products from "<category>" tab to the cart mweb
    When click DEVAM ET button in checkout
    And guest trial with registered mail "<mail>" mweb
    And try member login after error mweb
    And clear basket mweb

    Examples:
      | base           | category            | count | mail  |
      | msupplementler | Sporcu Vitaminleri  | 1     | User1 |
      | mvitaminler    | Diğer Takviyeler    | 1     | User2 |
      | mfitmoda       | Fitness Ekipmanları | 1     | User3 |


  @MWEB @REGRESSION @CHECKOUT
  Scenario Outline: 5) Mweb Checkout V2 Geliştirmesi Tasarım ve Fonksiyon (VA-124)
    Given open login page in "<base>"
    And login with "<mail>" user mweb
    And clear user adress - 1 address remains
    And clear basket mweb
    And add a "<count>" products from "<category>" tab to the cart mweb
    When SEPETIM page check
    Then ADRES page check - "<mail>"
    And ODEME KREDI KARTI page check
    And ODEME HAVALE page check
    And ODEME KAPIDA KREDI KARTI page check
    And ODEME KAPIDA NAKIT page check
    And ODEME ALISVERIS KREDISI check
    And progress bar click check mweb

    Examples:
      | base           | category            | count | mail  |
      | msupplementler | Sporcu Vitaminleri  | 3     | User4 |
      | mvitaminler    | Diğer Takviyeler    | 3     | User5 |
      | mfitmoda       | Fitness Ekipmanları | 3     | User6 |

  @MWEB @REGRESSION @GUEST
  Scenario Outline: 3) Mweb Guest Checkout Sipariş Tamamlanmayan (VA-170)
    Given load home page in "<base>"
    And add a "<count>" products from "<category>" tab to the cart mweb
    Then add guest "<mail>" address and proceed to checkout mweb
    And complete the guest order with the "<payment>" method

    Examples:
      | base           | category            | count | payment     | mail   |
      | msupplementler | Sporcu Vitaminleri  | 1     | kredi kartı | Guest1 |
      | mvitaminler    | Diğer Takviyeler    | 1     | kredi kartı | Guest2 |
      | mfitmoda       | Fitness Ekipmanları | 1     | kredi kartı | Guest3 |

  @CANCEL
  Scenario Outline:  6) Mweb AGT Sipariş Tamamlanan
    Given open login page in "<base>"
    And login with "<type>" user mweb
    And clear basket mweb
    And add a "<count>" products from "<category>" tab to the cart mweb
    And go to address step in basket mweb
    And AGT delivery selection and go to payment step MWEB
    And complete AGT order by "<payment>" transfer mweb

    Examples:
      | base           | category            | count | type  | payment |
      | msupplementler | Sporcu Vitaminleri  | 1     | User7 | havale  |
      | mvitaminler    | Diğer Takviyeler    | 1     | User8 | havale  |
      | mfitmoda       | Fitness Ekipmanları | 1     | User9 | havale  |


  @MWEB @REGRESSION @USER
  Scenario Outline:  7) Mweb Kullanıcı Bilgileri Güncelle - (mWeb ad soyad guncelleme)
    Given open login page in "<base>"
    And login with "<type>" user mweb
    And go to my account mWeb
    And update user info mWeb

    Examples:
      | base           | type   |
      | msupplementler | User10 |
      | mvitaminler    | User11 |
      | mfitmoda       | User12 |

  @MWEB @REGRESSION @HOME
  Scenario Outline:  8) Mweb Anasayfa - Açıklama alanı
    Given load home page in "<base>"
    And verify the descriptions

    Examples:
      | base        |
      | mvitaminler |

  @MWEB @REGRESSION @HOME
  Scenario Outline: 9) User should be read KVKK (Aydınlatma Metni)
    Given load home page in "<base>"
    And go to KVKK page and verfy the text mWeb

    Examples:
      | base           |
      | msupplementler |
      | mvitaminler    |
      | mfitmoda       |


  @MWEB @REGRESSION @CHECKOUT
  Scenario Outline: Logged out kullanıcı için sepeti onayla isleminden sonra, login olduğu durumda adres adımından isleme devam edilmesi. (VA-324)
    Given load home page in "<base>"
    And add a "<count>" products from "<category>" tab to the cart mweb
    And click SEPETI ONAYLA
    And if there is a gift, choose and pass
    And login with "<mail>" user mweb
    And the ADRES EKLE BUTTON appears
    And clear basket mweb

    Examples:
      | base           | count | category            | mail   |
      | msupplementler | 1     | Sporcu Vitaminleri  | User13 |
      | mvitaminler    | 1     | Diğer Takviyeler    | User14 |
      | mfitmoda       | 1     | Fitness Ekipmanları | User15 |


  @MWEB @REGRESSION
  Scenario Outline: Sepet Boş Açıklama kontrolü
    Given load home page in "<base>"
    And HEADER CART click and verify the cart null

    Examples:
      | base           |
      | msupplementler |
      | mvitaminler    |
      | mfitmoda       |


  @MWEB @REGRESSION @HOME
  Scenario Outline:  İletişim Sayfası Talep Oluşturma
    Given load home page in "<base>"
    And click İLETİŞİM in bottom
    And fill out the contact form
    And click KVKK checkbox in ILETISIM page
    And click GONDER button in ILETISIM page
    And form submitted page check

    Examples:
      | base           |
      | mvitaminler    |
      | msupplementler |


  @MWEB @REGRESSION @MENU
  Scenario Outline: 10) Kişisel Tavsiye Alan Kontrolleri mWeb vitaminler
    Given load home page in "<base>"
    And click top menu mWeb
    And click on Kisisel Tavsiye
    And click TESTE BASLA button
    And select "<sex>"
    And select age "<secenek>" for young
    And select eating and drinking habits for man
    And select your health "<problem>"
    And click URUNLERI GOSTER button and verify the result

    Examples:
      | base        | sex   | problem                        | secenek |
      | mvitaminler | ERKEK | ENERJİ, YORGUNLUK ve BİTKİNLİK | 2       |


  @MWEB @REGRESSION @HOME
  Scenario Outline:  Search Kontrol
    Given load home page in "<base>"
    And enter "<text>" into Search Bar mWeb
    And check "<text>" in first product tittle

    Examples:
      | base           | text       |
      | msupplementler | Kingsize |
      | mvitaminler    | A Vitamini |
      | mfitmoda       | Adidas |

  @MWEB @REGRESSION @MENU
  Scenario Outline: 11) Kolajen
    Given load home page in "<base>"
    And click top menu mWeb
    And click Kategoriler
    And click Kolajen and verify the sub titles
    And click "<title>" and verify the page "<name>" and the products


    Examples:
      | base        | title                             | name                              |
      | mvitaminler | Tip I-III Kolajen                 | Tip I-III Kolajen                 |
      | mvitaminler | Tip II Kolajen                    | Tip II Kolajen                    |
      | mvitaminler | Balık (Marine) Kolajeni           | Balık (Marine) Kolajeni           |
      | mvitaminler | Hyalüronik Asit (Hyaluronic Acid) | Hyalüronik Asit (Hyaluronic Acid) |

  @REGRESSION @MWEB @MENU
  Scenario Outline: 12) Mineraller
    Given load home page in "<base>"
    And click top menu mWeb
    And click Kategoriler
    And click "<category>" and verify the sub titles
    And click "<title>" and verify the page "<name>" and the products


    Examples:
      | base        | category   | title               | name                |
      | mvitaminler | Mineraller | İyot                | İyot                |
      | mvitaminler | Mineraller | Çinko               | Çinko               |
      | mvitaminler | Mineraller | Demir               | Demir               |
      | mvitaminler | Mineraller | Kalsiyum            | Kalsiyum            |
      | mvitaminler | Mineraller | Krom                | Krom                |
      | mvitaminler | Mineraller | Magnezyum           | Magnezyum           |
      | mvitaminler | Mineraller | Selenyum            | Selenyum            |
      | mvitaminler | Mineraller | Kompleks Mineraller | Kompleks Mineraller |



  @MWEB @REGRESSION @PRODUCT_DETAIL_LP
  Scenario Outline: Web - LP Filtreleme ve Sıralama - Filtrelerin Eklenmesi (VA-300)
    Given open login page in "<base>"
    And login with "<type>" user
    Then load "<url1>" url in "<base>"
    And landing page status change "<status1>"
    And landing page Filtreleme-Sıralama type change "<type1>"
    And click KAYDET VE DEVAM ET button
    And wait <second>
    And click landing page link
    And switch tab
    And convert web to mweb
    And check that the page is not displayed
    And switch tab
    And close tabs other than the main tab
    And landing page status change "<status2>"
    And landing page Filtreleme-Sıralama type change "<type2>"
    And click KAYDET VE DEVAM ET button
    And wait <second>
    And click landing page link
    And switch tab
    And convert web to mweb
    And check that landing page is displayed with "<filter1>"
    And switch tab
    And close tabs other than the main tab
    And landing page status change "<status2>"
    And landing page Filtreleme-Sıralama type change "<type1>"
    And click KAYDET VE DEVAM ET button
    And wait <second>
    And switch tab
    And click landing page link
    And switch tab
    And convert web to mweb
    And check that landing page is displayed with "<filter2>"
    And the number of products in the category filter must match the number of products listed

    Examples:
      | base          | type        | second | url1                          | status1 | type1 | status2 | type2   | filter1 | type2 | filter2 |
      | supplementler | User25Admin | 55     | landingPageAdminSupplementler | inaktif | aktif | aktif   | inaktif | inaktif | aktif | aktif   |
      | vitaminler    | User26Admin | 55     | landingPageAdminVitaminler    | inaktif | aktif | aktif   | inaktif | inaktif | aktif | aktif   |
      | fitmoda       | User27Admin | 70     | landingPageAdminFitmoda       | inaktif | aktif | aktif   | inaktif | inaktif | aktif | aktif   |

