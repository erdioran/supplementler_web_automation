Feature: WEB VA BOARD - UI Web

  @WEB @REGRESSION @PRODUCT_DETAIL @test
  Scenario Outline: 1) WEB Ürün Detay Video Konumlandırması (VA-187)
    Given load "<url>" url in "<base>"
    Then check video location

    Examples:
      | base    | url                 |
      | fitmoda | productYoutubeVideo |


  @WEB @REGRESSION @test
  Scenario Outline: 2) WEB Fittest,Kişisel Tavsiye- URL'lere Parametre Eklenmesi - SUPPLEMENTLER / VITAMINLER (VA-194)
    Given load home page in "<base>"
    When click TEST
    Then check test popup close icon visibility
    And check tab url is "<url>" in "<base>"

    Examples:
      | base          | url             |
      | supplementler | /?fittest        |
      | vitaminler    | /?kisiseltavsiye |


  @WEB @REGRESSION
  Scenario Outline: AH-21 - Geçersiz kupon kodu, uyarı mesajı - 3 store
    Given open login page in "<base>"
    And login with "<type>" user
    And clear basket
    And add a "<count>" products from "<category>" tab to the cart
    And get total cart amount
    When click KUPON KODU checkbox
    And enter "<coupon>" in coupon code
    And click INDIRIMI KULLAN
    Then check that the "<alert>" alert message appears
    And check total cart amount is same as previous step
    And clear basket

    Examples:
      | base          | type  | count | category            | coupon | alert           |
      | supplementler | User4 | 1     | Sporcu Vitaminleri  | test   | KuponKoduHatası |
      | vitaminler    | User5 | 1     | Diğer Takviyeler    | test   | KuponKoduHatası |
      | fitmoda       | User6 | 1     | Fitness Ekipmanları | test   | KuponKoduHatası |


  @WEB @REGRESSION @MAIL
  Scenario Outline: 5) Siparişiniz alınmıştır mailindeki indirim alanının rengi ve not kısmındaki metnin kontrolü - 3 store (VA-192)
    Given open login page in "<base>"
    And login with "<type>" user
    And clear basket
    And add a "<count>" products from "<category>" tab to the cart
    When complete test order
    Then hotmail login with "<type>" user and check "<store>" mail

    Examples:
      | base          | type  | count | category            | store         |
      | supplementler | User1 | 1     | Sporcu Vitaminleri  | Supplementler |
      | vitaminler    | User2 | 1     | Diğer Takviyeler    | Vitaminler    |
      | fitmoda       | User3 | 1     | Fitness Ekipmanları | Fitmoda       |


  @WEB @REGRESSION
  Scenario Outline: VA-164 - Guest Checkout Web - 3 store
    Given load home page in "<base>"
    And add a "<count>" products from "<category>" tab to the cart
    Then complete test order with guest "<mail>" web
    And hotmail login with "<mail>" user and check "<store>" mail - guest

    Examples:
      | base          | count | category            | store         | mail   |
      | supplementler | 1     | Sporcu Vitaminleri  | Supplementler | Guest1 |
      | vitaminler    | 1     | Diğer Takviyeler    | Vitaminler    | Guest2 |
      | fitmoda       | 1     | Fitness Ekipmanları | Fitmoda       | Guest3 |


  @CANCEL
  Scenario Outline: Web AGT Sipariş Tamamlanan
    Given open login page in "<base>"
    And login with "<type>" user
    And clear basket
    And add a "<count>" products from "<category>" tab to the cart
    And go to address step in basket web
    And AGT delivery selection and go to payment step WEB
    And complete AGT order by "<payment>" transfer Web

    Examples:
      | base          | category            | count | type  | payment |
      | supplementler | Sporcu Vitaminleri  | 1     | User7 | havale  |
      | vitaminler    | Diğer Takviyeler    | 1     | User8 | havale  |
      | fitmoda       | Fitness Ekipmanları | 1     | User9 | havale  |


  @WEB @REGRESSION @CHECKOUT
  Scenario Outline: 4) WEB Sipariş Özeti Ürün Adedi Gösterimi - 3 store (VA-212)
    Given open login page in "<base>"
    And login with "<type>" user
    And clear basket
    And add a "<count>" products from "<category>" tab to the cart
    When update the quantity of the <sequence> product in the cart as <num>
    And click SEPETI ONAYLA
    And choose gift popup action is "<action>"
    And check that the "<element>" is visible in order steps
    Then click SIPARIS OZETINI GENISLET icon
    And order description field <num> product quantity badge
    And check that all products have a quantity symbol
    And clear basket

    Examples:
      | base          | type   | category            | count | sequence | num | action | element         |
      | supplementler | User10 | Sporcu Vitaminleri  | 1     | 1        | 3   | true   | Yeni Adres Ekle |
      | vitaminler    | User11 | Diğer Takviyeler    | 1     | 1        | 3   | true   | Yeni Adres Ekle |
      | fitmoda       | User12 | Fitness Ekipmanları | 1     | 1        | 3   | true   | Yeni Adres Ekle |


  @WEB @REGRESSION @CHECKOUT
  Scenario Outline: 8) WEB Sepette X Adet Bulundurma Koşulu (VA-220)
    Given open login page in "<base>"
    And login with "<type>" user
    And load "<page>" page in "<base>"
    And click YENİ EKLE button
    And create discount "<name>", "<discountType>", with coupon code "<code>", "<customerID>"
    And add Belirtilen üründen sepette "<adet>" bulunması koşulu - "<productID>"
    And logout admin panel
    And open login page in "<base>"
    And login with "<type2>" user
    And clear basket
    And load "<url>" url in "<base>"
    And add product to cart web
    And check Belirtilen üründen sepette "<adet>" bulunması koşulu discount "<discountType>" - "<code>"
    And logout site
    Given open login page in "<base>"
    And login with "<type>" user
    And delete discount "<name>"

    Examples:
      | base          | type        | page       | name                | discountType | code  | customerID | adet | productID      | type2  | url                   |
      | supplementler | User22Admin | İndirimler | TestİndirimFiyat    | fiyat        | false | customer13 | 2    | supplementler1 | User13 | indirimSupplementler1 |
      | supplementler | User22Admin | İndirimler | TestİndirimYüzdelik | yüzdelik     | false | customer14 | 2    | supplementler2 | User14 | indirimSupplementler2 |


  @WEB @REGRESSION @GUEST
  Scenario Outline: 7) WEB Guest Checkout Üye girişi yapmadan devam et butonuna tıklandığında, üyelik olan bir mail girilir ise, uyarı alınır -3 Store (VA-164)
    Given load home page in "<base>"
    And add a "<count>" products from "<category>" tab to the cart
    And guest trial with registered "<mail>"
    And try member login after error

    Examples:
      | base          | count | category            | mail   |
      | supplementler | 1     | Sporcu Vitaminleri  | User17 |
      | vitaminler    | 1     | Diğer Takviyeler    | User18 |
      | fitmoda       | 1     | Fitness Ekipmanları | User19 |


  @WAIT @WAIT
  Scenario Outline: VA-124 - Web - Checkout V2 Geliştirmesi  - WEB tarafı - 3 Store
    Given open login page in "<base>"
    And login with "<type>" user
    And clear basket
    And add a "<count>" products from "<category>" tab to the cart
    When SEPETIM page check web
    Then ADRES page check web
    And ODEME KREDI KARTI page check web
    And ODEME HAVALE page check
    And ODEME KAPIDA KREDI KARTI page check
    And ODEME KAPIDA NAKIT page check
    And ODEME ALISVERIS KREDISI check


    Examples:
      | base          | category            | count | type     |
      | supplementler | Sporcu Vitaminleri  | 1     | Customer |
      | vitaminler    | Diğer Takviyeler    | 3     | Customer |
      | fitmoda       | Fitness Ekipmanları | 3     | Customer |

  @WEB @REGRESSION @CHECKOUT
  Scenario Outline: Logged out kullanıcı için sepeti onayla isleminden sonra, login olduğu durumda adres adımından isleme devam edilmesi. (VA-324)
    Given load home page in "<base>"
    And add a "<count>" products from "<category>" tab to the cart
    And click SEPETI ONAYLA guest
    And if there is a gift, choose and pass
    And login with "<mail>" user popup
    And the ADRES EKLE BUTTON appears
    And clear basket


    Examples:
      | base          | count | category            | mail  |
      | supplementler | 1     | Sporcu Vitaminleri  | User1 |
      | vitaminler    | 1     | Diğer Takviyeler    | User2 |
      | fitmoda       | 1     | Fitness Ekipmanları | User3 |


  @WEB @REGRESSION
  Scenario Outline: VU-6535 Desktop Web Hızlı Sepete Ekle Özelliği
    Given load home page in "<base>"
    And search for hardline in the search bar
    And filter from Expensive to Cheap
    And click SEPETE EKLE button on the desired product
    And close the popup
    And click SEPETE EKLE button on the desired product
    And click Urun Detayina Git
    And go to previous page
    And click SEPETE EKLE button on the desired product
    And select an aroma
    And select a gift
    And select piece of product
    And click Sepete Ekle on the popup
    And click Sepete Git on the small popup
    And go to previous page
    And click SEPETE EKLE button on the desired product
    And click Sepete Ekle on the popup to verify five+ message

    Examples:
      | base          |
      | supplementler |

  @WEB @REGRESSION
  Scenario Outline: VU-6535 Desktop Web Hızlı Sepete Ekle Özelliği varyantsız ve hediyesiz ürünler için
    Given load home page in "<base>"
    And add a  "<category>" tab to the cart

    Examples:
      | base          | category                 |
      | supplementler | Sağlıklı Atıştırmalıklar |


  @WEB @REGRESSION @PRODUCT_DETAIL_LP
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
    And check that the page is not displayed
    And switch tab
    And close tabs other than the main tab
    And landing page status change "<status2>"
    And landing page Filtreleme-Sıralama type change "<type2>"
    And click KAYDET VE DEVAM ET button
    And wait <second>
    And click landing page link
    And switch tab
    And check that landing page is displayed with "<filter1>"
    And switch tab
    And close tabs other than the main tab
    And landing page status change "<status2>"
    And landing page Filtreleme-Sıralama type change "<type1>"
    And click KAYDET VE DEVAM ET button
    And wait <second>
    And click landing page link
    And switch tab
    And check that landing page is displayed with "<filter2>"
    And the number of products in the category filter must match the number of products listed
    And get first category count
    And click first category filter
    And check number of products listed

    Examples:
      | base          | type        | second | url1                          | status1 | type1 | status2 | type2   | filter1 | type2 | filter2 |
      | supplementler | User25Admin | 55     | landingPageAdminSupplementler | inaktif | aktif | aktif   | inaktif | inaktif | aktif | aktif   |
      | vitaminler    | User26Admin | 55     | landingPageAdminVitaminler    | inaktif | aktif | aktif   | inaktif | inaktif | aktif | aktif   |
      | fitmoda       | User27Admin | 70     | landingPageAdminFitmoda       | inaktif | aktif | aktif   | inaktif | inaktif | aktif | aktif   |


  @WEB @REGRESSION @PRODUCT_LIST
  Scenario Outline: Stokta Olmayan Ürünler İçin Fiyat Gösterilmemesi (VA-320) (Search)
    Given load home page in "<base>"
    And search "<searchText>"
    And switch to last page
    And Out of stock product icon and price control


    Examples:
      | base          | searchText |
      | supplementler | thsirt     |
      | vitaminler    | a vitamini |

  @WEB @REGRESSION @PRODUCT_LIST
  Scenario Outline: Stokta Olmayan Ürünler İçin Fiyat Gösterilmemesi (VA-320) (Kategori) OK
    Given load home page in "<base>"
    When select "<category>" page in top menu
    And switch to last page
    And Out of stock product icon and price control


    Examples:
      | base          | category     |
      | supplementler | Protein Tozu |
      | vitaminler    | Vitaminler   |

  @WEB @REGRESSION @PRODUCT_LIST
  Scenario Outline: Stokta Olmayan Ürünler İçin Fiyat Gösterilmemesi (VA-320) (Marka) OK
    Given load home page in "<base>"
    And brands select "<brand>"
    And switch to last page
    And Out of stock product icon and price control

    Examples:
      | base          | brand   |
      | supplementler | Adidas  |
      | vitaminler    | Day2day |

  @WEB @REGRESSION @PRODUCT_LIST
  Scenario Outline: Stokta Olmayan Ürünler İçin Fiyat Gösterilmemesi (VA-320) (Kampanyalar) OK
    Given load home page in "<base>"
    And open the campaigns page
    And switch to last page
    And Out of stock product icon and price control

    Examples:
      | base          |
      | supplementler |
      | vitaminler    |

  @WEB @REGRESSION @PRODUCT_LIST
  Scenario Outline: Stokta Olmayan Ürünler İçin Fiyat Gösterilmemesi (VA-320) (Kombinasyon) OK
    Given load home page in "<base>"
    And open the combinations page
    And switch to last page
    And Out of stock product icon and price control

    Examples:
      | base          |
      | supplementler |
      | vitaminler    |



