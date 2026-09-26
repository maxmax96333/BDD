package test;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.chrome.ChromeOptions;
import ru.netology.data.DataHelper;
import ru.netology.page.LoginPage;

import static com.codeborne.selenide.Configuration.browserCapabilities;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MoneyTransferTest {
    @BeforeAll
    static void configureHeadlessChromeInRestrictedEnvironments() {
        if (Boolean.getBoolean("selenide.headless")) {
            ChromeOptions options = new ChromeOptions();
            options.addArguments(
                    "--no-sandbox",
                    "--disable-gpu",
                    "--disable-gpu-compositing",
                    "--disable-extensions",
                    "--disable-features=VizDisplayCompositor"
            );
            browserCapabilities = options;
        }
    }

    @Test
    void shouldTransferMoneyBetweenOwnCards() {
        open(DataHelper.APP_URL);

        var verificationPage = new LoginPage().validLogin(DataHelper.getAuthInfo());
        var dashboard = verificationPage.validVerify(DataHelper.getVerificationCode());

        int firstCardBalanceBefore = dashboard.getCardBalance(DataHelper.FIRST_CARD_NUMBER);
        int secondCardBalanceBefore = dashboard.getCardBalance(DataHelper.SECOND_CARD_NUMBER);

        String sourceCard;
        String destinationCard;
        int sourceBalance;
        int destinationBalance;
        if (secondCardBalanceBefore > 0) {
            sourceCard = DataHelper.SECOND_CARD_NUMBER;
            destinationCard = DataHelper.FIRST_CARD_NUMBER;
            sourceBalance = secondCardBalanceBefore;
            destinationBalance = firstCardBalanceBefore;
        } else {
            sourceCard = DataHelper.FIRST_CARD_NUMBER;
            destinationCard = DataHelper.SECOND_CARD_NUMBER;
            sourceBalance = firstCardBalanceBefore;
            destinationBalance = secondCardBalanceBefore;
        }

        assertTrue(sourceBalance > 0, "At least one card must have a positive balance");
        int amount = Math.min(DataHelper.DEFAULT_TRANSFER_AMOUNT, sourceBalance);

        var transferPage = dashboard.chooseCardForDeposit(destinationCard);
        assertEquals(
                DataHelper.getCardSuffix(destinationCard),
                DataHelper.getCardSuffix(transferPage.getDestinationCardNumber()),
                "The selected card must be the transfer destination"
        );

        var dashboardAfterTransfer = transferPage.transferFromCard(sourceCard, amount);

        assertEquals(
                destinationBalance + amount,
                dashboardAfterTransfer.getCardBalance(destinationCard),
                "The destination card balance must increase by the transfer amount"
        );
        assertEquals(
                sourceBalance - amount,
                dashboardAfterTransfer.getCardBalance(sourceCard),
                "The source card balance must decrease by the transfer amount"
        );
    }
}
