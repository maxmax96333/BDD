package ru.netology.data;

public final class DataHelper {
    public static final String APP_URL = "http://localhost:9999/";

    public static final String FIRST_CARD_NUMBER = "5559 0000 0000 0001";
    public static final String SECOND_CARD_NUMBER = "5559 0000 0000 0002";
    public static final int DEFAULT_TRANSFER_AMOUNT = 5_000;

    private DataHelper() {
    }

    public static AuthInfo getAuthInfo() {
        return new AuthInfo("vasya", "qwerty123");
    }

    public static String getVerificationCode() {
        return "12345";
    }

    /** Returns only digits, so full and masked card numbers can be compared by their suffix. */
    public static String normalizeCardNumber(String cardNumber) {
        return cardNumber.replaceAll("\\D", "");
    }

    public static String getCardSuffix(String cardNumber) {
        String normalized = normalizeCardNumber(cardNumber);
        if (normalized.length() < 4) {
            throw new IllegalArgumentException("Card number must contain at least four digits");
        }
        return normalized.substring(normalized.length() - 4);
    }

    public static final class AuthInfo {
        private final String login;
        private final String password;

        private AuthInfo(String login, String password) {
            this.login = login;
            this.password = password;
        }

        public String getLogin() {
            return login;
        }

        public String getPassword() {
            return password;
        }
    }
}
