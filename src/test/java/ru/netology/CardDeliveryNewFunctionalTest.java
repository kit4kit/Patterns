package ru.netology;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;


public class CardDeliveryNewFunctionalTest {

    @BeforeEach
    void setUpAll() {
        open("http://localhost:9999/");
    }

    @Test
    void shouldCardDeliveryOnDifferentDate() {
        $("[data-test-id=city] input").setValue(DataGenerator.Registration.generateByCard().getCity());
        $("[data-test-id=date] input").sendKeys(Keys.CONTROL+"A"+Keys.DELETE);
        $("[data-test-id=date] input").setValue(DataGenerator.Registration.getDayVisit(3));
        $("[data-test-id=name] input").setValue(DataGenerator.Registration.generateByCard().getName());
        $("[data-test-id=phone] input").setValue(DataGenerator.Registration.generateByCard().getPhone());
        $("[data-test-id=agreement]").click();
        $("button.button").click();
        $(withText("Успешно!")).shouldBe(visible);
        $("[data-test-id=success-notification] .notification__content").shouldHave(exactText("Встреча " +
                "успешно запланирована на "+DataGenerator.Registration.getDayVisit(3)));
        $("[data-test-id=date] input").sendKeys(Keys.CONTROL+"A"+Keys.DELETE);
        $("[data-test-id=date] input").setValue(DataGenerator.Registration.getDayVisit(7));
        $("button.button").click();
        $(withText("Необходимо подтверждение")).waitUntil(visible,15000);
        $("[data-test-id=replan-notification] .button").click();
        $(withText("Успешно!")).waitUntil(visible,15000);
        $("[data-test-id=success-notification] .notification__content").shouldHave(exactText("Встреча " +
                "успешно запланирована на "+DataGenerator.Registration.getDayVisit(7)));
    }
}