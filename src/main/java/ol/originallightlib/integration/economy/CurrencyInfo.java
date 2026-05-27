package ol.originallightlib.integration.economy;

public class CurrencyInfo {

    private final String id;
    private final String name;
    private final String symbol;
    private final boolean decimal;

    public CurrencyInfo(String id, String name, String symbol, boolean decimal) {
        this.id = id;
        this.name = name;
        this.symbol = symbol;
        this.decimal = decimal;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSymbol() {
        return symbol;
    }

    public boolean isDecimal() {
        return decimal;
    }
}
