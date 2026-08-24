import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 * Represents a slot machine simulator.
 */
public class SlotMachine {
    private ArrayList<Wheel> wheels;
    private ArrayList<Symbol> symbols;
    private boolean visible;
    private boolean operationOk;

    /**
     * Creates an empty slot machine.
     */
    public SlotMachine() {
        wheels = new ArrayList<Wheel>();
        symbols = new ArrayList<Symbol>();
        visible = false;
        operationOk = true;
    }

    /**
     * Adds a wheel at the specified position.
     *
     * @param pos the position of the wheel
     */
    public void addWheel(int pos) {
        Wheel wheel = new Wheel();
        if (pos < 1) {
            pos = 1;
        }
        if (pos > wheels.size() + 1) {
            pos = wheels.size() + 1;
        }
        wheels.add(pos - 1, wheel);
        operationOk = true;
    }

    /**
     * Deletes a wheel at the specified position.
     *
     * @param pos the position of the wheel
     */
    public void delWheel(int pos) {
        if (wheels.isEmpty()) {
            operationOk = false;
            showError("There are no wheels.");
            return;
        }
        if (pos < 1) {
            pos = 1;
        }
        if (pos > wheels.size()) {
            pos = wheels.size();
        }
        wheels.remove(pos - 1);
        operationOk = true;
    }

    /**
     * Adds a symbol to the list of available symbols.
     *
     * @param pos the position of the symbol
     * @param color the CSS color of the symbol
     */
    public void addSymbol(int pos, String color) {
        if (findSymbol(color) != null) {
            operationOk = false;
            showError("The symbol already exists.");
            return;
        }
        Symbol symbol = new Symbol(color);
        if (pos < 1) {
            pos = 1;
        }
        if (pos > symbols.size() + 1) {
            pos = symbols.size() + 1;
        }
        symbols.add(pos - 1, symbol);
        operationOk = true;
    }

    /**
     * Deletes a symbol from the list of available symbols.
     *
     * @param color the color of the symbol
     */
    public void delSymbol(String color) {
        Symbol symbol = findSymbol(color);
        if (symbol == null) {
            operationOk = false;
            showError("The symbol does not exist.");
            return;
        }
        symbols.remove(symbol);
        operationOk = true;
    }

    /**
     * Places a symbol on a specified wheel.
     *
     * @param wheel the position of the wheel
     * @param symbol the color of the symbol
     */
    public void placeSymbol(int wheel, String symbol) {
        Symbol selected = findSymbol(symbol);
        if (selected == null || wheels.isEmpty()) {
            operationOk = false;
            showError("The wheel or symbol does not exist.");
            return;
        }
        if (wheel < 1) {
            wheel = 1;
        }
        if (wheel > wheels.size()) {
            wheel = wheels.size();
        }
        wheels.get(wheel - 1).addSymbol(selected);
        operationOk = true;
    }

    /**
     * Spins one specified wheel.
     *
     * @param wheel the position of the wheel
     */
    public void spin(int wheel) {
        if (wheels.isEmpty()) {
            operationOk = false;
            showError("There are no wheels.");
            return;
        }
        if (wheel < 1) {
            wheel = 1;
        }
        if (wheel > wheels.size()) {
            wheel = wheels.size();
        }
        wheels.get(wheel - 1).spin();
        operationOk = true;
    }

    /**
     * Spins all the wheels.
     */
    public void spin() {
        if (wheels.isEmpty()) {
            operationOk = false;
            showError("There are no wheels.");
            return;
        }
        for (Wheel wheel : wheels) {
            wheel.spin();
        }
        operationOk = true;
    }

    /**
     * Returns the colors of the available symbols.
     *
     * @return the colors of the symbols
     */
    public String[] symbols() {
        String[] result = new String[symbols.size()];
        for (int i = 0; i < symbols.size(); i++) {
            result[i] = symbols.get(i).getColor();
        }
        return result;
    }

    /**
     * Returns the number of different symbols.
     *
     * @return the number of symbols
     */
    public int distinctSymbols() {
        return symbols.size();
    }

    /**
     * Returns the current configuration of the machine.
     *
     * @return the current color of every wheel
     */
    public String[] configuration() {
        String[] result = new String[wheels.size()];
        for (int i = 0; i < wheels.size(); i++) {
            Wheel wheel = wheels.get(i);
            Symbol symbol = wheel.getSymbol(wheel.getCurrent());

            if (symbol != null) {
                result[i] = symbol.getColor();
            }
        }
        return result;
    }

    /**
     * Checks whether the current configuration is a jackpot.
     *
     * @return true if all wheels show the same color
     */
    public boolean isJackpot() {
        if (wheels.isEmpty()) {
            return false;
        }
        String[] configuration = configuration();
        if (configuration[0] == null) {
            return false;
        }
        for (int i = 1; i < configuration.length; i++) {
            if (configuration[i] == null ||
                !configuration[0].equals(configuration[i])) {
                return false;
            }
        }
        return true;
    }

    /**
     * Makes the simulator visible.
     */
    public void makeVisible() {
        visible = true;
        for (Wheel wheel : wheels) {
            wheel.makeVisible();
        }
        operationOk = true;
    }

    /**
     * Makes the simulator invisible.
     */
    public void makeInvisible() {
        for (Wheel wheel : wheels) {
            wheel.makeInvisible();
        }
        visible = false;
        operationOk = true;
    }

    /**
     * Terminates the simulator.
     */
    public void exit() {
        System.exit(0);
    }

    /**
     * Returns whether the last operation was successful.
     *
     * @return true if the last operation was successful
     */
    public boolean ok() {
        return operationOk;
    }

    /**
     * Finds a symbol by its color.
     *
     * @param color the color to search
     * @return the symbol or null if it does not exist
     */
    private Symbol findSymbol(String color) {
        for (Symbol symbol : symbols) {
            if (symbol.getColor().equals(color)) {
                return symbol;
            }
        }
        return null;
    }

    /**
     * Displays an error message when the simulator is visible.
     *
     * @param message the message to display
     */
    private void showError(String message) {
        if (visible) {
            JOptionPane.showMessageDialog(null, message);
        }
    }
}