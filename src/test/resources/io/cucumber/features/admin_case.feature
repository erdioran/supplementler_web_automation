Feature: ADMIN - UI Admin


  @ADMIN @REGRESSION
  Scenario Outline: 1) ADMIN İndirim Kısıtlamaları (Supplementler çalışan rolü Tanımlaması) / (Nestle çalışan rolü Tanımlaması)
    Given open login page in "<base>"
    And login with "<type>" user
    And open admin page in "<base>"
    And load "<page>" page in "<base>"
    And search "<mail>" user in list with email
    And click "<button>" in customer page
    And click "<tab>" in customer detail
    When select "<roleCode>" checkbox in list
    And click KAYDET BUTTON in customer detail
    Then search "<mail>" user in list with email
    And customer has the role "<role>"
    And remove customer "<roleCode>" role and check "<role>"

    Examples:
      | base          | type        | page       | mail                          | button  | tab             | roleCode | role                   |
      | supplementler | User22Admin | Müşteriler | supplementler.qa22@yandex.com | Düzenle | Müşteri Rolleri | 45433    | Supplementler Çalışanı |


  @ADMIN @REGRESSION
  Scenario Outline: 2) ADMIN İndirim Kısıtlamaları (Aylık/Yıllık fiyat limit tanımlaması)
    Given open login page in "<base>"
    And login with "<type>" user
    And open admin page in "<base>"
    And check discount supplementler-nestle in "<base>"

    Examples:
      | base          | type        |
      | supplementler | User23Admin |


  @ADMIN @REGRESSION
  Scenario Outline: 3) ADMIN İndirim Kısıtlamaları (Aylık/Yıllık ürün limiti)
    Given open login page in "<base>"
    And login with "<type>" user
    And open admin page in "<base>"
    And check discount count supplementler-nestle in "<base>"

    Examples:
      | base          | type        |
      | supplementler | User24Admin |


  @ADMIN @REGRESSION
  Scenario Outline: 4) ADMIN Marka Skoru Ekranındaki "Marka Adı" Alanı Güncellenmesi - 3 Store (VA-162)
    Given open login page in "<base>"
    And login with "<type>" user
    And open admin page in "<base>"
    And load "<page>" page in "<base>"
    When click "<button>" in categories page
    Then MARKA ADI field update status is "<boolean>"

    Examples:
      | base          | type        | page          | button  | boolean |
      | supplementler | User25Admin | MarkaSkorları | Düzenle | false   |
      | vitaminler    | User26Admin | MarkaSkorları | Düzenle | false   |
      | fitmoda       | User27Admin | MarkaSkorları | Düzenle | false   |

  @ALPEREN @ALPEREN
  Scenario: VA-297 KVKK - Admin Panel Erişim Kontrol Listesi Siparişler Erişimi.
    Given open login page in "<base>"
    And login with "<type>" user
    And open admin page in "<base>"
    And load "<page>" page in "<base>"
    Then checkadminareamanageonlyorders


      | base          | type        | page               |
      | supplementler | User28Admin | adminloginsecurity |
      | vitaminler    | Admin       | adminloginsecurity |
      | fitmoda       | Admin       | adminloginsecurity |

  @ADMIN @REGRESSION @INDIRIM
  Scenario Outline: İndirim Tanımlaması Pasife Alma (VA-219)
    Given open login page in "<base>"
    And login with "<type>" user
    And load "<page>" page in "<base>"
    And click YENİ EKLE button
    And create discount "<name>", "<discountType>", with coupon code "<code>", "<customerID>"
    And Hemen Bitir Click and expiry date check


    Examples:
      | base          | type        | page       | name                 | discountType | code  | customerID |
      | supplementler | User22Admin | İndirimler | TestİndirimFiyat1    | fiyat        | false | customer13 |
      | supplementler | User22Admin | İndirimler | TestİndirimYüzdelik1 | yüzdelik     | false | customer14 |
      | vitaminler | User22Admin | İndirimler | TestİndirimFiyat1    | fiyat        | false | customer13 |
      | vitaminler | User22Admin | İndirimler | TestİndirimYüzdelik1 | yüzdelik     | false | customer14 |
      | fitmoda | User22Admin | İndirimler | TestİndirimFiyat1    | fiyat        | false | customer13 |
      | fitmoda | User22Admin | İndirimler | TestİndirimYüzdelik1 | yüzdelik     | false | customer14 |





