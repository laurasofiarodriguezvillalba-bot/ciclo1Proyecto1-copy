import java.util.ArrayList;

public class Wheel {
    private ArrayList<Symbol> symbols;
    private int current;
    private boolean locked;
    private Rectangle body;
    private Circle currentCircle;
    private int xPosition;
    private int yPosition;

    //Crea una rueda en una posición
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
        
    // Agrega un símbolo en una posición
    public void addSymbol(int pos, Symbol symbol) {
        if (pos < 1) {
            pos = 1;
        }
        if (pos > symbols.size() + 1) {
            pos = symbols.size() + 1;
        }
        symbols.add(pos - 1, symbol);
    }

    // Elimina un símbolo
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

    // Coloca un símbolo como símbolo actual
    public void placeSymbol(Symbol symbol) {
        int position = symbols.indexOf(symbol);
        if (position != -1) {
            current = position + 1;
            updateCircle();
        }
    }

    // Gira la rueda una posición
    public void spin() {
        if (!symbols.isEmpty() && !locked) {
            current++;
            if (current > symbols.size()) {
                current = 1;
            }
            updateCircle();
        }
    }

    // Retorna la posición actual
    public int getCurrent() {
        return current;
    }

    // Retorna el símbolo de una posición
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

    // Bloquea la rueda
    public void lock() {
        locked = true;
    }

    // Desbloquea la rueda
    public void unlock() {
        locked = false;
    }

    // Indica si la rueda está bloqueada
    public boolean isLocked() {
        return locked;
    }

    // Hace visibles los símbolos
    public void makeVisible() {
        body.makeVisible();
        updateCircle();
        
    }

    // Hace invisibles los símbolos
    public void makeInvisible() {
        body.makeInvisible();
        if (currentCircle != null) {
            currentCircle.makeInvisible();
        }
    }
    
    // Actualiza el círculo del símbolo actual.
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
