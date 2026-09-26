package ru.netology.page;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Condition.visible;

public class VerificationPage {
    private final SelenideElement codeField = $("[data-test-id=code]").$("input");
    private final SelenideElement verifyButton = $("[data-test-id=action-verify]");

    public DashboardPage validVerify(String code) {
        codeField.shouldBe(visible).setValue(code);
        verifyButton.shouldBe(visible).click();
        return new DashboardPage();
    }
}
