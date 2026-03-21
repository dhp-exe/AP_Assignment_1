public class TaxReportVisitor implements InstrumentVisitor {
    private double totalTaxLiability = 0.0;
    private StringBuilder report = new StringBuilder("Tax Report Breakdown:\n");

    @Override
    public void visit(Stock stock) {
        double tax = stock.getCurrentPriceValue() * 0.15;
        totalTaxLiability += tax;
        report.append("Stock [")
                .append(stock.getSymbol())
                .append("] Liability: $")
                .append(String.format(java.util.Locale.US, "%.2f", tax))
                .append("\n");
    }

    @Override
    public void visit(Bond bond) {
        double tax = bond.annualCouponPayment(1) * 0.30;
        totalTaxLiability += tax;
        report.append("Bond [")
                .append(bond.getSymbol())
                .append("] Liability: $")
                .append(String.format(java.util.Locale.US, "%.2f", tax))
                .append("\n");
    }

    @Override
    public void visit(Option option) {
        double tax = option.getCurrentPriceValue() * 0.20;
        totalTaxLiability += tax;
        report.append("Option [")
                .append(option.getSymbol())
                .append("] Liability: $")
                .append(String.format(java.util.Locale.US, "%.2f", tax))
                .append("\n");
    }

    @Override
    public void visit(Future future) {
        double tax = future.getCurrentPriceValue() * 0.20;
        totalTaxLiability += tax;
        report.append("Future [")
                .append(future.getSymbol())
                .append("] Liability: $")
                .append(String.format(java.util.Locale.US, "%.2f", tax))
                .append("\n");
    }

    public double getTotalTaxLiability() {
        return totalTaxLiability;
    }

    public String getReport() {
        return report.toString() + " Total Tax Liability: $"
                + String.format(java.util.Locale.US, "%.2f", totalTaxLiability);
    }
}