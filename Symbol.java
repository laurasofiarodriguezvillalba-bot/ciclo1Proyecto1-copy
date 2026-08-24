/**
 * Represents a symbol of the slot machine.
 * Each symbol is identified by a CSS color.
 */
public class Symbol {
    private String color;
    private Circle circle;

    /**
     * Creates a symbol with the specified color.
     *
     * @param color the CSS color of the symbol
     */
    public Symbol(String color) {
        this.color = color;
        circle = new Circle();
        circle.changeColor(color);
    }

    /**
     * Returns the color of the symbol.
     *
     * @return the color of the symbol
     */
    public String getColor() {
        return color;
    }

    /**
     * Makes the symbol visible.
     */
    public void makeVisible() {
        circle.makeVisible();
    }

    /**
     * Makes the symbol invisible.
     */
    public void makeInvisible() {
        circle.makeInvisible();
    }
}