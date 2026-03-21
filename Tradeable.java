public interface Tradeable {
    String getSymbol();

    double getCurrentPriceValue();

    boolean isAvailableForTrading();

    default String getTradingInfo() {
        String availability = isAvailableForTrading() ? "AVAILABLE" : "UNAVAILABLE";
        return getSymbol() + " @ "
            + String.format(java.util.Locale.US, "%.2f", getCurrentPriceValue())
            + " [" + availability + "]";
    }
}
