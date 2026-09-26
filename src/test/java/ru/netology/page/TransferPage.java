package ru.netology.page;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class TransferPage {
    private final SelenideElement amountField = $("[data-test-id=amount]").$("input");
    private final SelenideElement sourceCardField = $("[data-test-id=from]").$("input");
    private final SelenideElement destinationCardField = $("[data-test-id=to]").$("input");
    private final SelenideElement transferButton = $("[data-test-id=action-transfer]");

    public String getDestinationCardNumber() {
        return destinationCardField.shouldBe(visible).getValue();
    }

    /** Enters the source card and amount, submits the transfer and returns to the card list. */
    public DashboardPage transferFromCard(String sourceCardNumber, int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Transfer amount must be positive");
        }

        amountField.shouldBe(visible).setValue(String.valueOf(amount));
        sourceCardField.shouldBe(visible).setValue(sourceCardNumber);
        transferButton.shouldBe(visible).click();
        return new DashboardPage();
    }
}
