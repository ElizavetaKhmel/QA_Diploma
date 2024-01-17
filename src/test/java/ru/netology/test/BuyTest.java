package ru.netology.test;

import ru.netology.page.BuyPage;
import ru.netology.page.MainPage;

import org.junit.jupiter.api.*;
import ru.netology.data.DataHelper;
import ru.netology.data.SQLHelper;


import static com.codeborne.selenide.Selenide.*;
import static ru.netology.data.SQLHelper.cleanDatabase;

public class BuyTest {
    BuyPage buyPage;
    MainPage mainPage;


    @BeforeEach
    void setUp() {
        open("http://localhost:8080");
        mainPage=new MainPage();
        buyPage= mainPage.goToBuyPage();
    }

    @AfterEach
    void TearDownAll(){
        cleanDatabase();
    }

    @Test
    @DisplayName("Should get valid data form Buy")
    public void shouldTest() {
        buyPage.putData("4444 4444 4444 4441",DataHelper.month(),DataHelper.getRandomYear("yy"),DataHelper.ownerInfo(),DataHelper.cvcInfo());
        buyPage.successNotificationWait();
        Assertions.assertEquals("APPROVED", SQLHelper.getStatusInData());
    }

    @Test
    @DisplayName("Owner with double surname and hyphen form Buy")
    public void shouldTestPositiveOwner() {
        buyPage.putData("4444 4444 4444 4441",DataHelper.month(),DataHelper.getRandomYear("yy"),("VASILIEVA-VINOGRADOVA MARIA"),DataHelper.cvcInfo());
        buyPage.successNotificationWait();
        Assertions.assertEquals("APPROVED", SQLHelper.getStatusInData());
    }

    @Test
    @DisplayName("Invalid value in field Card Number form Buy")
    public void shouldTestNegativeCardNumber() {
        buyPage.putData("4444 4444 4444",DataHelper.month(),DataHelper.getRandomYear("yy"),DataHelper.ownerInfo(),DataHelper.cvcInfo());
        buyPage.incorrectCardNumberNotification();
        Assertions.assertEquals("DECLINED", SQLHelper.getStatusInData());
    }

    @Test
    @DisplayName("Null card number form Buy")
    public void shouldTestNegativeNullCardNumber() {
        buyPage.putData("0000000000000000",DataHelper.month(),DataHelper.getRandomYear("yy"),DataHelper.ownerInfo(),DataHelper.cvcInfo());
        buyPage.incorrectCardNumberNotification();
        Assertions.assertEquals("DECLINED", SQLHelper.getStatusInData());
    }

    @Test
    @DisplayName("Invalid value Month form Buy")
    public void shouldTestNegativeMonth() {
        buyPage.putData("4444 4444 4444 4442",("13"),DataHelper.getRandomYear("yy"),DataHelper.ownerInfo(),DataHelper.cvcInfo());
        buyPage.incorrectMonthNotification();
        Assertions.assertEquals("DECLINED", SQLHelper.getStatusInData());
    }

    @Test
    @DisplayName("Invalid value Year form Buy")
    public void shouldTestNegativeYear() {
        buyPage.putData("4444 4444 4444 4442",DataHelper.month(),("31"),DataHelper.ownerInfo(),DataHelper.cvcInfo());
        buyPage.validityErrorNotification();
        Assertions.assertEquals("DECLINED", SQLHelper.getStatusInData());
    }

    @Test
    @DisplayName("One letter in field Owner form Buy")
    public void shouldTestNegativeOneLetterOwner() {
        buyPage.putData("4444 4444 4444 4442",DataHelper.month(),DataHelper.getRandomYear("yy"),("А"),DataHelper.cvcInfo());
        buyPage.ownerEmptyNotificationWait();
        Assertions.assertEquals("DECLINED", SQLHelper.getStatusInData());
    }

    @Test
    @DisplayName("Invalid value CVC/CVV form Buy")
    public void shouldTestNegativeCVC() {
        buyPage.putData("4444 4444 4444 4441",DataHelper.month(),DataHelper.getRandomYear("yy"),DataHelper.ownerInfo(),("65"));
        buyPage.incorrectFormatCVVNotificationWait();
        Assertions.assertEquals("DECLINED", SQLHelper.getStatusInData());
    }

    @Test
    @DisplayName("Latin value in owner field form Buy")
    public void shouldTestNegativeLatinValueOwner() {
        buyPage.putData("4444 4444 4444 4442",DataHelper.month(),DataHelper.getRandomYear("yy"),("ИРИНА МИРОНОВА"),DataHelper.cvcInfo());
        buyPage.ownerEmptyNotificationWait();
        Assertions.assertEquals("DECLINED", SQLHelper.getStatusInData());
    }

    @Test
    @DisplayName("Special symbol and numbers in owner field form Buy")
    public void shouldTestNegativeSpecialSymbolOwner() {
        buyPage.putData("4444 4444 4444 4442",DataHelper.month(),DataHelper.getRandomYear("yy"),("$V1R1DOV A1EK$ANDR"),DataHelper.cvcInfo());
        buyPage.ownerEmptyNotificationWait();
        Assertions.assertEquals("DECLINED", SQLHelper.getStatusInData());
    }
    @Test
    @DisplayName("Card number field-empty, rest-valid form Buy")
    public void shouldTestNegativeCardNumberEmpty() {
        buyPage.putData("","11","25","IVANOVA MARIA","543");
        buyPage.incorrectCardNotificationWait();
        Assertions.assertNull(SQLHelper.getStatusInData());
    }

    @Test
    @DisplayName("Month field-empty, rest-valid form Buy")
    public void shouldTestNegativeMonthEmpty() {
        buyPage.putData("4444 4444 4444 4442","","25","IVANOVA MARIA","543");
        buyPage.incorrectMonthNotification();
        Assertions.assertNull(SQLHelper.getStatusInData());
    }

    @Test
    @DisplayName("Year field-empty, rest-valid form Buy")
    public void shouldTestNegativeYearEmpty() {
        buyPage.putData("4444 4444 4444 4442","11","","IVANOVA MARIA","543");
        buyPage.validityErrorNotification();
        Assertions.assertNull(SQLHelper.getStatusInData());
    }

    @Test
    @DisplayName("Owner field-empty, rest-valid form Buy")
    public void shouldTestNegativeOwnerEmpty() {
        buyPage.putData("4444 4444 4444 4442","11","12","","543");
        buyPage.ownerEmptyNotificationWait();
        Assertions.assertNull(SQLHelper.getStatusInData());
    }

    @Test
    @DisplayName("CVC/CVV field-empty, rest-valid form Buy")
    public void shouldTestNegativeCVCEmpty() {
        buyPage.putData("4444 4444 4444 4442","11","12","IVANOVA MARIA","");
        buyPage.incorrectFormatCVVNotificationWait();
        Assertions.assertNull(SQLHelper.getStatusInData());
    }

    @Test
    @DisplayName("Test empty form")
    public void shouldTestEmptyForm() {
        buyPage.putData("","","","","");
        buyPage.incorrectCardNumberNotification();
        buyPage.incorrectMonthNotification();
        buyPage.incorrectYearNotification();
        buyPage.ownerEmptyNotificationWait();
        Assertions.assertNull(SQLHelper.getStatusInData());
    }
}
