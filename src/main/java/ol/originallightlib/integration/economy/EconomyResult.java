package ol.originallightlib.integration.economy;

import ol.originallightlib.common.Result;

public class EconomyResult extends Result {

    private final double amount;
    private final String currencyId;

    private EconomyResult(boolean success, String message, String currencyId, double amount) {
        super(success, message);
        this.currencyId = currencyId;
        this.amount = amount;
    }

    public static EconomyResult success(String currencyId, double amount) {
        return new EconomyResult(true, "", currencyId, amount);
    }

    public static EconomyResult success(String message, String currencyId, double amount) {
        return new EconomyResult(true, message, currencyId, amount);
    }

    public static EconomyResult failure(String message) {
        return new EconomyResult(false, message, "", 0D);
    }

    public double getAmount() {
        return amount;
    }

    public String getCurrencyId() {
        return currencyId;
    }
}
