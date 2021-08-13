package ru.netology.test;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import ru.netology.data.DataGenerator;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class CardDeliveryOrderDateChangeTest {

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    @BeforeEach
    void setup() {
        open("http://localhost:9999");
    }

    @Test
    @DisplayName("Should successful plan and replan meeting")
    void shouldSuccessfulPlanAndReplanMeeting() {
        var validUser = DataGenerator.Registration.generateUser("ru");
        var daysToAddForFirstMeeting = 4;
        var firstMeetingDate = DataGenerator.generateDate(daysToAddForFirstMeeting);
        var daysToAddForSecondMeeting = 7;
        var secondMeetingDate = DataGenerator.generateDate(daysToAddForSecondMeeting);
        $("[data-test-id=city] input").sendKeys(validUser.getCity());
        $("[data-test-id=date] input").doubleClick().sendKeys("{KEY_BKSP}");
        $("[data-test-id=date] input").sendKeys(firstMeetingDate);
        $("[data-test-id=name] input").sendKeys(validUser.getName());
        $("[data-test-id=phone] input").sendKeys(validUser.getPhone());
        $("[data-test-id=agreement]").click();
        $(byText("Запланировать")).click();
        $(byText("Успешно!")).shouldBe(Condition.visible);
        $("[data-test-id=success-notification] .notification__content")
                .should(Condition.have(Condition.text(firstMeetingDate)))
                .shouldBe(Condition.visible);
        $(".notification__closer .icon").click();
        $("[data-test-id=date] input").doubleClick().sendKeys("{KEY_BKSP}");
        $("[data-test-id=date] input").sendKeys(secondMeetingDate);
        $(byText("Запланировать")).click();
        $(byText("Необходимо подтверждение")).shouldBe(Condition.visible);
        $("[data-test-id=replan-notification] [role=button]").click();
        $(byText("Успешно!")).shouldBe(Condition.visible);
        $("[data-test-id=success-notification] .notification__content")
                .should(Condition.have(Condition.text(secondMeetingDate)))
                .shouldBe(Condition.visible);
    }
}
