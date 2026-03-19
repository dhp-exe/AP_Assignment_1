import java.util.*;

public class RiskAnalyzer<T extends Instrument> {
    private final List<T> instruments = new ArrayList<>();

    public void add(T instrument) {
        if (instrument != null) {
            instruments.add(instrument);
        }
    }

    public double averageRisk() {
        if (instruments.isEmpty()) {
            return 0.0;
        }
        double totalRisk = 0.0;
        for (T instrument : instruments) {
            totalRisk += instrument.riskScore();
        }
        return totalRisk / instruments.size();
    }

    public T highestRisk() {
        if(instruments.isEmpty()) {
            return null;
        }

        T highest = instruments.get(0);

        for (T instrument : instruments) {
            if (instrument.riskScore() > highest.riskScore()) {
                highest = instrument;
            }
        }
        return highest;

    }

    public T lowestRisk() {
        if(instruments.isEmpty()) {
            return null;
        }

        T lowest = instruments.get(0);

        for (T instrument : instruments) {
            if (instrument.riskScore() < lowest.riskScore()) {
                lowest = instrument;
            }
        }
        return lowest;
    }

    public List<T> getAboveRiskThreshold(double threshold) {
        List<T> result = new ArrayList<>();
        for (T instrument : instruments) {
            if (instrument.riskScore() > threshold) {
                result.add(instrument);
            }
        }
        return result;
    }
}
