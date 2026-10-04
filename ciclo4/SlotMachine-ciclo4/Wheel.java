import java.util.ArrayList;

public class Wheel {
    private ArrayList<Symbol> symbols;
    private int current;
    private boolean locked;
    private Rectangle body;
    private Circle currentCircle;
    private Rectangle currentRectangle;
    private int xPosition;
    private int yPosition;
    private boolean visible;

    //Create a wheel in a position
    public Wheel(int x, int y) {
        symbols = new ArrayList<Symbol>();
        current = 1;
        locked = false; 
        visible = false;
        xPosition = x;
        yPosition = y;
        body = new Rectangle();
        body.changeSize(50, 50);
        body.moveHorizontal(x - 70);
        body.moveVertical(y - 15);
        currentCircle = null;
        currentRectangle = null;
    }
        
    //Add a symbol in a position
    public void addSymbol(int pos, Symbol symbol) {
        if (pos < 1) {
            pos = 1;
        }
        if (pos > symbols.size() + 1) {
            pos = symbols.size() + 1;
        }
        symbols.add(pos - 1, symbol);
    }

    //Remove a symbol from the wheel
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

    //Place a symbol as the current wheel symbol
    public void placeSymbol(Symbol symbol) {
        int position = symbols.indexOf(symbol);
        if (position != -1) {
            current = position + 1;
            updateCircle();
        }
    }

    //Turn the wheel one position
    public void spin() {
        if (!symbols.isEmpty() && !locked) {
            current++;
            if (current > symbols.size()) {
                current = 1;
            }
            for (Symbol symbol : symbols) {
                symbol.spin();
            }
            Symbol selected = symbols.get(current - 1);
            selected.select();
            updateCircle();
        }
    }

    //Returns the current position of the wheel
    public int getCurrent() {
        return current;
    }

    //Returns the symbol of a position
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

    //Block the wheel
    public void lock() {
        locked = true;
    }

    //Unlock the wheel
    public void unlock() {
        locked = false;
    }

    //Indicate if the wheel is locked
    public boolean isLocked() {
        return locked;
    }

    //It makes the wheel and the symbol visible
    public void makeVisible() {
        visible = true;
        body.makeVisible();
        updateCircle();
        
    }

    //Makes the wheel and the symbol invisible
    public void makeInvisible() {
        body.makeInvisible();
        if (currentCircle != null) {
            currentCircle.makeInvisible();
        }
    }
    
    //Visually update the current wheel icon
    private void updateCircle() {
        if (symbols.isEmpty()) {
            if (currentCircle != null) {
                currentCircle.makeInvisible();
                currentCircle = null;
            }
            if (currentRectangle != null) {
                currentRectangle.makeInvisible();
                currentRectangle = null;
            }
            return;
        }
    
        Symbol symbol = symbols.get(current - 1);
    
        if (currentCircle != null) {
            currentCircle.makeInvisible();
            currentCircle = null;
        }
        if (currentRectangle != null) {
            currentRectangle.makeInvisible();
            currentRectangle = null;
        }
        if (symbol.getShape().equals("circle")) {
            updateCircleSymbol(symbol);
        } else if (symbol.getShape().equals("square")) {
            updateRectangleSymbol(symbol);
        }
    }
    
    //Create and visually update a circle-shaped symbol
    private void updateCircleSymbol(Symbol symbol) {
        currentCircle = new Circle();
        currentCircle.changeColor(symbol.getColor());
        currentCircle.changeSize(symbol.getSize());
        int x = xPosition + 12;
        int y = yPosition + 12;
        currentCircle.moveHorizontal(x - 20);
        currentCircle.moveVertical(y - 15);
        if (visible && symbol.isVisible()) {
            currentCircle.makeVisible();
        }
    }

    //Create and visually update a square-shaped symbol
    private void updateRectangleSymbol(Symbol symbol) {
        currentRectangle = new Rectangle();
        currentRectangle.changeColor(symbol.getColor());
        currentRectangle.changeSize(symbol.getSize(), symbol.getSize());
        int x = xPosition + 12;
        int y = yPosition + 12;
        currentRectangle.moveHorizontal(x - 70);
        currentRectangle.moveVertical(y - 15);
            if (visible && symbol.isVisible()) {
            currentRectangle.makeVisible();
        }
    }
    
    //Change the visual position of the wheel
    public void moveTo(int x, int y) {
        int dx = x - xPosition;
        int dy = y - yPosition;
        body.moveHorizontal(dx);
        body.moveVertical(dy);
        if (currentCircle != null) {
            currentCircle.moveHorizontal(dx);
            currentCircle.moveVertical(dy);
        }
        if (currentRectangle != null) {
            currentRectangle.moveHorizontal(dx);
            currentRectangle.moveVertical(dy);
        }
        xPosition = x;
        yPosition = y;
    }
    
    //Indicate if the wheel can be removed
    public boolean canDelete() {
        return true;
    }
    
    //Indicate if the wheel can be swapped with another wheel
    public boolean canSwap() {
        return true;
    }
    
    //Indicate if the wheel can be locked
    public boolean canLock() {
        return true;
    }
    
    //Copy the current position and lock status of another wheel
    public void copyState(Wheel other) {
        if (other != null) {
            current = other.getCurrent();
            locked = other.isLocked();
            updateCircle();
        }
    }
    
    //The wheel changes color
    public void changeBodyColor(String color) {
        body.changeColor(color);
    }
}