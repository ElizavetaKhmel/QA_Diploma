package ru.netology.test;

import ru.netology.page.BuyPage;
import ru.netology.page.CreditPage;
import ru.netology.page.MainPage;

import org.junit.jupiter.api.*;
import ru.netology.data.DataHelper;
import ru.netology.data.SQLHelper;


import static com.codeborne.selenide.Selenide.*;
import static ru.netology.data.SQLHelper.cleanDatabase;

public class BuyCreditTest {
    CreditPage creditPage;
    MainPage mainPage;


    @BeforeEach
    void setUp() {
        open("http://localhost:8080");
        mainPage=new MainPage();
        creditPage= mainPage.goToCreditPage();
    }

    @AfterEach
    void TearDownAll(){
        cleanDatabase();
    }

    @Test
    @DisplayName("Should get valid data form credit")
    public void shouldTest() {
        creditPage.putData(DataHelper.getFirstCardInfo(),DataHelper.month(),DataHelper.getRandomYear("yy"),DataHelper.ownerInfo(),DataHelper.cvcInfo());
       creditPage.successNotificationWait();
        Assertions.assertEquals("APPROVED", SQLHelper.geStatusInData());
    }

    @Test
    @DisplayName("Owner with double surname and hyphen form credit")
    public void shouldTestPositiveOwner() {
        creditPage.putData(DataHelper.getFirstCardInfo(),DataHelper.month(),DataHelper.getRandomYear("yy"),("VASILIEVA-VINOGRADOVA MARIA"),DataHelper.cvcInfo());
        creditPage.successNotificationWait();
        Assertions.assertEquals("APPROVED", SQLHelper.geStatusInData());
    }

    @Test
    @DisplayName("Invalid value in field Card Number form credit")
    public void shouldTestNegativeCardNumber() {
        creditPage.putData(DataHelper.getSecondCardInfo(),DataHelper.month(),DataHelper.getRandomYear("yy"),DataHelper.ownerInfo(),DataHelper.cvcInfo());
        creditPage.wrongCardNumberNotification();
        Assertions.assertEquals("DECLINED", SQLHelper.geStatusInData());
    }

    @Test
    @DisplayName("Null card number form credit")
    public void shouldTestNegativeNullCardNumber() {
        creditPage.putData("0000000000000000",DataHelper.month(),DataHelper.getRandomYear("yy"),DataHelper.ownerInfo(),DataHelper.cvcInfo());
        creditPage.wrongCardNumberNotification();
        Assertions.assertEquals("DECLINED", SQLHelper.geStatusInData());
    }

    @Test
    @DisplayName("Invalid value Month form credit")
    public void shouldTestNegativeMonth() {
        creditPage.putData(DataHelper.getFirstCardInfo(),DataHelper.getRandomMonth(),DataHelper.getRandomYear("yy"),DataHelper.ownerInfo(),DataHelper.cvcInfo());
        creditPage.wrongMonthNotification();
        Assertions.assertEquals("DECLINED", SQLHelper.geStatusInData());
    }

    @Test
    @DisplayName("Invalid value Year form credit")
    public void shouldTestNegativeYear() {
        creditPage.putData(DataHelper.getFirstCardInfo(),DataHelper.month(),("30"),DataHelper.ownerInfo(),DataHelper.cvcInfo());
        creditPage.validityErrorNotification();
        Assertions.assertEquals("DECLINED", SQLHelper.geStatusInData());
    }

    @Test
    @DisplayName("One letter in field Owner form credit")
    public void shouldTestNegativeOneLetterOwner() {
        creditPage.putData(DataHelper.getFirstCardInfo(),DataHelper.month(),DataHelper.getRandomYear("yy"),("А"),DataHelper.cvcInfo());
        creditPage.incorrectFormatOwnerNotificationWait();
        Assertions.assertEquals("DECLINED", SQLHelper.geStatusInData());
    }

    @Test
    @DisplayName("Invalid value CVC/CVV form credit")
    public void shouldTestNegativeCVC() {
        creditPage.putData(DataHelper.getFirstCardInfo(),DataHelper.month(),DataHelper.getRandomYear("yy"),DataHelper.ownerInfo(),DataHelper.cvcInfo());
        creditPage.wrongFormatCVVNotificationWait();
        Assertions.assertEquals("DECLINED", SQLHelper.geStatusInData());
    }

    @Test
    @DisplayName("Latin value in owner field form credit")
    public void shouldTestNegativeLatinValueOwner() {
        creditPage.putData(DataHelper.getFirstCardInfo(),DataHelper.month(),DataHelper.getRandomYear("yy"),("ИРИНА МИРОНОВА"),DataHelper.cvcInfo());
        creditPage.incorrectFormatOwnerNotificationWait();
        Assertions.assertEquals("DECLINED", SQLHelper.geStatusInData());
    }

    @Test
    @DisplayName("Special symbol and numbers in owner field form credit")
    public void shouldTestNegativeSpecialSymbolOwner() {
        creditPage.putData(DataHelper.getFirstCardInfo(),DataHelper.month(),DataHelper.getRandomYear("yy"),("$V1R1DOV A1EK$ANDR"),DataHelper.cvcInfo());
        creditPage.incorrectFormatOwnerNotificationWait();
        Assertions.assertEquals("DECLINED", SQLHelper.geStatusInData());
    }
    @Test
    @DisplayName("Card number field-empty, rest-valid form credit")
    public void shouldTestNegativeCardNumberEmpty() {
        creditPage.putData("","11","25","IVANOVA MARIA","543");
        creditPage.expiredCardNotificationWait();
        creditPage.notSuccessNotification();
        Assertions.assertEquals("DECLINED", SQLHelper.geStatusInData());
    }

    @Test
    @DisplayName("Month field-empty, rest-valid form credit")
    public void shouldTestNegativeMonthEmpty() {
        creditPage.putData("4444 4444 4444 4441","","25","IVANOVA MARIA","543");
        creditPage.wrongMonthNotification();
        creditPage.notSuccessNotification();
        Assertions.assertEquals("DECLINED", SQLHelper.geStatusInData());
    }

    @Test
    @DisplayName("Year field-empty, rest-valid form credit")
    public void shouldTestNegativeYearEmpty() {
        creditPage.putData("4444 4444 4444 4441","11","","IVANOVA MARIA","543");
        creditPage.wrongYearNotification();
        creditPage.notSuccessNotification();
        Assertions.assertEquals("DECLINED", SQLHelper.geStatusInData());
    }

    @Test
    @DisplayName("Owner field-empty, rest-valid form credit")
    public void shouldTestNegativeOwnerEmpty() {
        creditPage.putData("4444 4444 4444 4441","11","12","","543");
        creditPage.ownerEmptyNotificationWait();
        creditPage.notSuccessNotification();
        Assertions.assertEquals("DECLINED", SQLHelper.geStatusInData());
    }

    @Test
    @DisplayName("CVC/CVV field-empty, rest-valid form credit")
    public void shouldTestNegativeCVCEmpty() {
        creditPage.putData("4444 4444 4444 4441","11","12","IVANOVA MARIA","");
        creditPage.wrongFormatCVVNotificationWait();
        creditPage.notSuccessNotification();
        Assertions.assertEquals("DECLINED", SQLHelper.geStatusInData());
    }

    @Test
    @DisplayName("Test empty form credit")
    public void shouldTestEmptyForm() {
        creditPage.putData("","","","","");
        creditPage.wrongCardNumberNotification();
        creditPage.wrongMonthNotification();
        creditPage.wrongYearNotification();
        creditPage.ownerEmptyNotificationWait();
        creditPage.wrongFormatCVVNotificationWait();
        Assertions.assertNull(SQLHelper.getStatusForCreditForm());
    }













}
