import java.util.*;

public class Portfolio implements Observable<String> {
    private final String portfolioId;
    private final String ownerName;
    private final List<Position> positions;
    private final List<Observer<String>> observers;

    public Portfolio(String portfolioId, String ownerName) {
        this.portfolioId = portfolioId;
        this.ownerName = ownerName;
        this.positions = new ArrayList<>();
        this.observers = new ArrayList<>();
    }

    public void addPosition(Instrument inst, int qty, double costBasis) {
        for (Position position : positions) {
            if (position.getInstrument().getSymbol().equals(inst.getSymbol())) {
                position.addQuantity(qty, costBasis);
                notifyObservers("ADDED: " + inst.getSymbol() + " x" + qty);
                return;
            }
        }
        positions.add(new Position(inst, qty, costBasis));
        notifyObservers("ADDED: " + inst.getSymbol() + " x" + qty);
    }

    public void removePosition(String symbol) throws PositionNotFoundException {
        Iterator<Position> iterator = positions.iterator();
        while (iterator.hasNext()) {
            Position position = iterator.next();
            if (position.getInstrument().getSymbol().equals(symbol)) {
                iterator.remove();
                notifyObservers("REMOVED: " + symbol);
                return;
            }
        }
        throw new PositionNotFoundException("Position not found for symbol: " + symbol);
    }

    public double totalMarketValue() {
        double total = 0.0;
        for (Position p : positions) {
            total += p.marketValue();
        }
        return total;
    }

    public double totalUnrealizedPnL() {
        double total = 0.0;
        for (Position p : positions) {
            total += p.unrealizedPnL();
        }
        return total;
    }

    public Position getPosition(String symbol) throws PositionNotFoundException {
        for (Position p : positions) {
            if (p.getInstrument().getSymbol().equals(symbol)) {
                return p;
            }
        }
        throw new PositionNotFoundException("Position not found for symbol: " + symbol);
    }

    public List<Position> getPositionsSortedByValue() {
        List<Position> sorted = new ArrayList<>(positions);
        sorted.sort(Comparator.comparingDouble(Position::marketValue).reversed());
        return sorted;
    }

    public Map<String, Double> allocationByAssetClass() {
        Map<String, Double> allocation = new HashMap<>();
        double totalValue = totalMarketValue();
        
        if (totalValue == 0) {
            return allocation;
        }

        for (Position p : positions) {
            String assetClass = p.getInstrument().assetClass();
            allocation.put(assetClass, allocation.getOrDefault(assetClass, 0.0) + p.marketValue());
        }
        
        for (Map.Entry<String, Double> entry : allocation.entrySet()) {
            allocation.put(entry.getKey(), (entry.getValue() / totalValue) * 100.0);
        }
        
        return allocation;
    }

    @Override
    public void addObserver(Observer<String> observer) {
        if(!observers.contains(observer)) {
            observers.add(observer);
        }
    }

    @Override
    public void removeObserver(Observer<String> observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(String event) {
        for (Observer<String> observer : observers) {
            observer.onEvent(event);
        }
    }

    public String getPortfolioId() {
        return portfolioId;
    }

    public String getOwnerName() {
        return ownerName;
    }
}
