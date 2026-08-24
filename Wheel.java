import java.util.ArrayList;

/**
 * Represents a wheel of the slot machine.
 * A wheel contains an ordered list of symbols.
 */
public class Wheel {
    private ArrayList<Symbol> symbols;
    private int current;

    /**
     * Creates an empty wheel.
     */
    public Wheel() {
        symbols = new ArrayList<Symbol>();
        current = 1;
    }

    /**
     * Adds a symbol at the end of the wheel.
     *
     * @param symbol the symbol to add
     */
    public void addSymbol(Symbol symbol) {
        symbols.add(symbol);
    }

    /**
     * Spins the wheel one position.
     */
    public void spin() {
        if (!symbols.isEmpty()) {
            current++;
            if (current > symbols.size()) {
                current = 1;
            }
        }
    }

    /**
     * Returns the current position of the wheel.
     *
     * @return the current position
     */
    public int getCurrent() {
        return current;
    }

    /**
     * Returns the symbol at the specified position.
     *
     * @param pos the desired position
     * @return the symbol at the position
     */
    public Symbol getSymbol(int pos) {
        if (symbols.isEmpty()) {
            return null;
        }
        if (pos < 1) {
            pos = 1;
        }
        if (pos > symbols.size()) {
            pos = symbols.size();
        }
        return symbols.get(pos - 1);
    }

    /**
     * Makes all symbols of the wheel visible.
     */
    public void makeVisible() {
        for (Symbol symbol : symbols) {
            symbol.makeVisible();
        }
    }

    /**
     * Makes all symbols of the wheel invisible.
     */
    public void makeInvisible() {
        for (Symbol symbol : symbols) {
            symbol.makeInvisible();
        }
    }
}