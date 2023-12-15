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
        buyPage.putData(DataHelper.getFirstCardInfo(),DataHelper.month(),DataHelper.getRandomYear("yy"),DataHelper.ownerInfo(),DataHelper.cvcInfo());
        buyPage.successNotificationWait();
        Assertions.assertEquals("APPROVED", SQLHelper.geStatusInData());
    }

    @Test
    @DisplayName("Owner with double surname and hyphen form Buy")
    public void shouldTestPositiveOwner() {
        buyPage.putData(DataHelper.getFirstCardInfo(),DataHelper.month(),DataHelper.getRandomYear("yy"),DataHelper.ownerInfo(),DataHelper.cvcInfo());
        buyPage.successNotificationWait();
        Assertions.assertEquals("APPROVED", SQLHelper.geStatusInData());
    }

    @Test
    @DisplayName("Invalid value in field Card Number form Buy")
    public void shouldTestNegativeCardNumber() {
        buyPage.putData(DataHelper.getFirstCardInfo(),DataHelper.month(),DataHelper.getRandomYear("yy"),DataHelper.ownerInfo(),DataHelper.cvcInfo());
        buyPage.notSuccessNotificationWait();
        Assertions.assertEquals("DECLINED", SQLHelper.geStatusInData());
    }

    @Test
    @DisplayName("Null card number form Buy")
    public void shouldTestNegativeNullCardNumber() {
        buyPage.putData(DataHelper.getFirstCardInfo(),DataHelper.month(),DataHelper.getRandomYear("yy"),DataHelper.ownerInfo(),DataHelper.cvcInfo());
        buyPage.notSuccessNotificationWait();
        Assertions.assertEquals("DECLINED", SQLHelper.geStatusInData());
    }

    @Test
    @DisplayName("Invalid value Month form Buy")
    public void shouldTestNegativeMonth() {
        buyPage.putData(DataHelper.getFirstCardInfo(),DataHelper.month(),DataHelper.getRandomYear("yy"),DataHelper.ownerInfo(),DataHelper.cvcInfo());
        buyPage.notSuccessNotificationWait();
        Assertions.assertEquals("DECLINED", SQLHelper.geStatusInData());
    }

    @Test
    @DisplayName("Invalid value Year form Buy")
    public void shouldTestNegativeYear() {
        buyPage.putData(DataHelper.getFirstCardInfo(),DataHelper.month(),DataHelper.getRandomYear("yy"),DataHelper.ownerInfo(),DataHelper.cvcInfo());
        buyPage.notSuccessNotificationWait();
        Assertions.assertEquals("DECLINED", SQLHelper.geStatusInData());
    }

    @Test
    @DisplayName("One letter in field Owner form Buy")
    public void shouldTestNegativeOneLetterOwner() {
        buyPage.putData(DataHelper.getFirstCardInfo(),DataHelper.month(),DataHelper.getRandomYear("yy"),DataHelper.ownerInfo(),DataHelper.cvcInfo());
        buyPage.notSuccessNotificationWait();
        Assertions.assertEquals("DECLINED", SQLHelper.geStatusInData());
    }

    @Test
    @DisplayName("Invalid value CVC/CVV form Buy")
    public void shouldTestNegativeCVC() {
        buyPage.putData(DataHelper.getFirstCardInfo(),DataHelper.month(),DataHelper.getRandomYear("yy"),DataHelper.ownerInfo(),DataHelper.cvcInfo());
        buyPage.notSuccessNotificationWait();
        Assertions.assertEquals("DECLINED", SQLHelper.geStatusInData());
    }

    @Test
    @DisplayName("Latin value in owner field form Buy")
    public void shouldTestNegativeLatinValueOwner() {
        buyPage.putData(DataHelper.getFirstCardInfo(),DataHelper.month(),DataHelper.getRandomYear("yy"),DataHelper.ownerInfo(),DataHelper.cvcInfo());
        buyPage.notSuccessNotificationWait();
        Assertions.assertEquals("DECLINED", SQLHelper.geStatusInData());
    }

    @Test
    @DisplayName("Special symbol and numbers in owner field form Buy")
    public void shouldTestNegativeSpecialSymbolOwner() {
        buyPage.putData(DataHelper.getFirstCardInfo(),DataHelper.month(),DataHelper.getRandomYear("yy"),DataHelper.ownerInfo(),DataHelper.cvcInfo());
        buyPage.notSuccessNotificationWait();
        Assertions.assertEquals("DECLINED", SQLHelper.geStatusInData());
    }
    @Test
    @DisplayName("Card number field-empty, rest-valid form Buy")
    public void shouldTestNegativeCardNumberEmpty() {
        buyPage.putData("","11","25","IVANOVA MARIA","543");
        buyPage.incorrectCardNumberNotificationWait();
        buyPage.notSuccessNotificationWait();
        Assertions.assertEquals("DECLINED", SQLHelper.geStatusInData());
    }

    @Test
    @DisplayName("Month field-empty, rest-valid form Buy")
    public void shouldTestNegativeMonthEmpty() {
        buyPage.putData("4444 4444 4444 4441","","25","IVANOVA MARIA","543");
        buyPage.incorrectMonthNotificationWait();
        buyPage.notSuccessNotificationWait();
        Assertions.assertEquals("DECLINED", SQLHelper.geStatusInData());
    }

    @Test
    @DisplayName("Year field-empty, rest-valid form Buy")
    public void shouldTestNegativeYearEmpty() {
        buyPage.putData("4444 4444 4444 4441","11","","IVANOVA MARIA","543");
        buyPage.incorrectYearNotificationWait();
        buyPage.notSuccessNotificationWait();
        Assertions.assertEquals("DECLINED", SQLHelper.geStatusInData());
    }

    @Test
    @DisplayName("Owner field-empty, rest-valid form Buy")
    public void shouldTestNegativeOwnerEmpty() {
        buyPage.putData("4444 4444 4444 4441","11","12","","543");
        buyPage.incorrectOwnerNotificationWait();
        buyPage.notSuccessNotificationWait();
        Assertions.assertEquals("DECLINED", SQLHelper.geStatusInData());
    }

    @Test
    @DisplayName("CVC/CVV field-empty, rest-valid form Buy")
    public void shouldTestNegativeCVCEmpty() {
        buyPage.putData("4444 4444 4444 4441","11","12","IVANOVA MARIA","");
        buyPage.incorrectCVCNotificationWait();
        buyPage.notSuccessNotificationWait();
        Assertions.assertEquals("DECLINED", SQLHelper.geStatusInData());
    }

    @Test
    @DisplayName("Test empty form")
    public void shouldTestEmptyForm() {
        buyPage.putData("","","","","");
        buyPage.incorrectCardNumberNotificationWait();
        buyPage.incorrectMonthNotificationWait();
        buyPage.incorrectYearNotificationWait();
        buyPage.incorrectOwnerNotificationWait();
        buyPage.incorrectCVCNotificationWait();
        Assertions.assertNull(SQLHelper.getStatusForCreditForm());
    }
}
