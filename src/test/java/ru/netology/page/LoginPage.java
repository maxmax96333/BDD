package ru.netology.page;

import com.codeborne.selenide.SelenideElement;
import ru.netology.data.DataHelper;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Condition.visible;

public class LoginPage {
    private final SelenideElement loginField = $("[data-test-id=login]").$("input");
    private final SelenideElement passwordField = $("[data-test-id=password]").$("input");
    private final SelenideElement loginButton = $("[data-test-id=action-login]");

    public VerificationPage validLogin(DataHelper.AuthInfo authInfo) {
        loginField.shouldBe(visible).setValue(authInfo.getLogin());
        passwordField.shouldBe(visible).setValue(authInfo.getPassword());
        loginButton.shouldBe(visible).click();
        return new VerificationPage();
    }
}
