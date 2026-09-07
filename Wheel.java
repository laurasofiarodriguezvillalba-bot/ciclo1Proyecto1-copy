import java.util.ArrayList;

public class Wheel {
    private ArrayList<Symbol> symbols;
    private int current;
    private boolean locked;
    private Rectangle body;
    private Circle currentCircle;
    private int xPosition;
    private int yPosition;

    //Create a wheel in a position
    public Wheel(int x, int y) {
        symbols = new ArrayList<Symbol>();
        current = 1;
        locked = false;        
        xPosition = x;
        yPosition = y;
        body = new Rectangle();
        body.changeSize(50, 50);
        body.moveHorizontal(x - 70);
        body.moveVertical(y - 15);
        currentCircle = null;
    }
        
    // Add a symbol in a position
    public void addSymbol(int pos, Symbol symbol) {
        if (pos < 1) {
            pos = 1;
        }
        if (pos > symbols.size() + 1) {
            pos = symbols.size() + 1;
        }
        symbols.add(pos - 1, symbol);
    }

    // Remove a symbol
    public void delSymbol(Symbol symbol) {
    
        symbols.remove(symbol);
    
        if (symbols.isEmpty()) {
            current = 1;
            if (currentCircle != null) {
                currentCircle.makeInvisible();
                currentCircle = null;
            }
        } else if (current > symbols.size()) {
            current = symbols.size();
        }
        updateCircle();
    }

    // Set a symbol as the current symbol
    public void placeSymbol(Symbol symbol) {
        int position = symbols.indexOf(symbol);
        if (position != -1) {
            current = position + 1;
            updateCircle();
        }
    }

    // Turn the wheel one position
    public void spin() {
        if (!symbols.isEmpty() && !locked) {
            current++;
            if (current > symbols.size()) {
                current = 1;
            }
            updateCircle();
        }
    }

    // Returns to current position
    public int getCurrent() {
        return current;
    }

    // Returns the symbol of a position
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

    // Lock the wheel
    public void lock() {
        locked = true;
    }

    // Unlock the wheel
    public void unlock() {
        locked = false;
    }

    // Indicates if the wheel is locked
    public boolean isLocked() {
        return locked;
    }

    // It makes the symbols visible.
    public void makeVisible() {
        body.makeVisible();
        updateCircle();
        
    }

    //It makes the symbols invisible.
    public void makeInvisible() {
        body.makeInvisible();
        if (currentCircle != null) {
            currentCircle.makeInvisible();
        }
    }
    
    // Update the circle of the current symbol.
    private void updateCircle() {
        if (symbols.isEmpty()) {
            if (currentCircle != null) {
                currentCircle.makeInvisible();
                currentCircle = null;
            }
            return;
        }

        Symbol symbol = symbols.get(current - 1);

        if (currentCircle != null) {
            currentCircle.makeInvisible();
        }
        currentCircle = new Circle();
        currentCircle.changeColor(symbol.getColor());
        currentCircle.changeSize(25);
        int circleX = xPosition + 12;
        int circleY = yPosition + 12;
        currentCircle.moveHorizontal(circleX - 20);
        currentCircle.moveVertical(circleY - 15);
        currentCircle.makeVisible();
    }
}
