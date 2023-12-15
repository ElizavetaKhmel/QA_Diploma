package ru.netology.page;

import com.codeborne.selenide.SelenideElement;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class CreditPage {
    private SelenideElement heading = $("h3").shouldHave(exactText("Оплата по карте"));
    private SelenideElement cardNumber = $(".input [placeholder='0000 0000 0000 0000']");
    private SelenideElement monthField = $(".input [placeholder='08']");
    private SelenideElement yearField = $(".input [placeholder='22']");
    private SelenideElement ownerField = $$("input__control").get(3);
    private SelenideElement cvcField = $(".input [placeholder='999']");

    private SelenideElement successNotification = $$(".notification__content").find(text("Операция одобрена банком"));
    private SelenideElement notSuccessNotification = $$(".notification__content").find(text("Ошибка! Банк отказал в проведении операции"));

    private SelenideElement incorrectCardNumber = $(byText("Неверный формат"));
    private SelenideElement incorrectMonth = $(byText("Неверный формат"));
    private SelenideElement incorrectYear = $(byText("Неверный формат"));
    private SelenideElement incorrectValidity = $(byText("Неверно указан срок действия карты"));
    private SelenideElement expiredValidity = $(byText("Истёк срок действия карты"));
    private SelenideElement blankOwnerField = $(byText("Поле обязательно для заполнения"));
    private SelenideElement incorrectOwner = $(byText("Неверный формат"));
    private SelenideElement incorrectCVC = $(byText("Неверный формат"));
    private SelenideElement continueButton = $$("button span.button__text").find(exactText("Продолжить"));

    public CreditPage() {
        heading.shouldBe(visible);
    }


    public void successNotificationWait() {
        successNotification.shouldBe(visible, Duration.ofSeconds(20));
    }

    public void notSuccesNotificationWait() {
        notSuccessNotification.shouldBe(visible, Duration.ofSeconds(20));
    }



    public void incorrectCardNumberNotificationWait() {
        incorrectCardNumber.shouldBe(visible, Duration.ofSeconds(20));
    }

    public void incorrectMonthNotificationWait() {
        incorrectMonth.shouldBe(visible, Duration.ofSeconds(20));
    }

    public void incorrectYearNotificationWait() {
        incorrectYear.shouldBe(visible, Duration.ofSeconds(20));
    }

    public void incorrectOwnerNotificationWait() {
        incorrectOwner.shouldBe(visible, Duration.ofSeconds(20));
    }

    public void incorrectCVCNotificationWait() {
        incorrectCVC.shouldBe(visible, Duration.ofSeconds(20));
    }

    public void incorrectValidityNotificationWait() {
        incorrectValidity.shouldBe(visible, Duration.ofSeconds(20));
    }

    public void expiredValidityNotificationWait() {
        expiredValidity.shouldBe(visible, Duration.ofSeconds(20));
    }

    public void blankOwnerFieldNotificationWait() {
        blankOwnerField.shouldBe(visible, Duration.ofSeconds(20));
    }


    public void putData(String number, String month, String year, String owner, String CVC) {
        cardNumber.setValue(number);
        monthField.setValue(month);
        yearField.setValue(year);
        ownerField.setValue(owner);
        cvcField.setValue(CVC);
        continueButton.click();
    }
}
