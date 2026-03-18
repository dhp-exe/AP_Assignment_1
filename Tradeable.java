public interface Tradeable {
    String getSymbol();

    double getCurrentPriceValue();

    boolean isAvailableForTrading();

    default String getTradingInfo() {
        String availability = isAvailableForTrading() ? "Available" : "Unavailable";
        return "Tradeable: " + getSymbol() + " at $" + getCurrentPriceValue() + " (" + availability + ")";
    }
}
