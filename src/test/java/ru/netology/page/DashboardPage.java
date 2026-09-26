package ru.netology.page;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import ru.netology.data.DataHelper;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$$;

public class DashboardPage {

    private final ElementsCollection cardRows = $$(".list__item div");
    private static final Pattern BALANCE_PATTERN =
            Pattern.compile("баланс\\s*:\\s*([\\d\\s]+)\\s*р\\.", Pattern.CASE_INSENSITIVE);

    public DashboardPage() {
        cardRows.first().shouldBe(visible);
    }

    public int getCardBalance(String cardNumber) {
        String cardText = cardRow(cardNumber).getText();
        Matcher matcher = BALANCE_PATTERN.matcher(cardText);
        if (!matcher.find()) {
            throw new IllegalStateException("Could not read card balance from: " + cardText);
        }
        String digits = matcher.group(1).replaceAll("\\s", "");
        return Integer.parseInt(digits);
    }

    public TransferPage chooseCardForDeposit(String destinationCardNumber) {
        cardRow(destinationCardNumber).$$("[data-test-id=action-deposit]").first().shouldBe(visible).click();
        return new TransferPage();
    }

    private SelenideElement cardRow(String cardNumber) {
        return cardRows.findBy(text(DataHelper.getCardSuffix(cardNumber))).shouldBe(visible);
    }
}
