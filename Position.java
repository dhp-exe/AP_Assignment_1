public class Position {
    private final Instrument instrument;
    private int quantity;
    private double averageCostBasis;

    public Position(Instrument instrument, int quantity, double averageCostBasis) {
        this.instrument = instrument;
        this.quantity = quantity;
        this.averageCostBasis = averageCostBasis;
    }

    public double marketValue() {
        return quantity * instrument.getCurrentPriceValue();
    }

    public double unrealizedPnL() {
        return marketValue() - (quantity * averageCostBasis);
    }

    public void addQuantity(int qty, double costBasis) {
        int newQuantity = this.quantity + qty;
        if (newQuantity == 0) {
            this.quantity = 0;
            this.averageCostBasis = 0.0;
            return;
        }
        double currentCost = this.quantity * this.averageCostBasis;
        double additionalCost = qty * costBasis;
        this.averageCostBasis = (currentCost + additionalCost) / newQuantity;
        this.quantity = newQuantity;
    }

    public Instrument getInstrument() {
        return instrument;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getAverageCostBasis() {
        return averageCostBasis;
    }

    @Override
    public String toString() {
        return "Position[symbol=" + instrument.getSymbol() + ", quantity=" + quantity + ", avgCost=" + averageCostBasis + "]";
    }
}
