import java.util.ArrayList;
import javax.swing.JOptionPane;

public class SlotMachine {
    private ArrayList<Wheel> wheels;
    private ArrayList<Symbol> symbols;
    private boolean visible;
    private boolean operationOk;

    //Crea una maquina vacia
    public SlotMachine() {
        wheels = new ArrayList<Wheel>();
        symbols = new ArrayList<Symbol>();
        visible = false;
        operationOk = true;
    }
    
    public SlotMachine(int n) {
        wheels = new ArrayList<Wheel>();
        symbols = new ArrayList<Symbol>();
        visible = false;
        operationOk = true;
    
        String[] colors = {"red","blue","green","yellow","orange","magenta","pink","black","white","gray",
            "cyan","lightGray","darkGray","purple","brown","lime","navy","teal","olive","maroon","silver"};
    
        // Create n symbols
        for (int i = 0; i < n; i++) {
            Symbol symbol = new Symbol(colors[i]);
            symbols.add(symbol);
        }
    
        // Create n wheels
        for (int i = 0; i < n; i++) {
            int x = 70 + (i * 60);
            int y = 50;
    
            Wheel wheel = new Wheel(x, y);
    
            // Add the same symbols to every wheel
            for (int j = 0; j < symbols.size(); j++) {
                wheel.addSymbol(j + 1, symbols.get(j));
            }
    
            // Choose a random initial position
            int position = (int)(Math.random() * n) + 1;
            wheel.placeSymbol(symbols.get(position - 1));
    
            wheels.add(wheel);
        }
    }

    // Agrega una rueda en la posición indicada
    public void addWheel(int pos) {
        if (pos < 1) {
            pos = 1;
        }

        if (pos > wheels.size() + 1) {
            pos = wheels.size() + 1;
        }
        
        int x = 70 + ((pos - 1)*60);
        int y = 50;
        Wheel wheel = new Wheel(x, y);
        
        // La nueva rueda recibe todos los símbolos existentes
        for (int i = 0; i < symbols.size(); i++) {
        wheel.addSymbol(i + 1, symbols.get(i));
        }
        wheels.add(pos - 1, wheel);
        // Si la máquina ya está visible,
        // hacemos visible la nueva rueda
        if (visible) {
            wheel.makeVisible();
        }
        operationOk = true;
    }

    // Elimina una rueda
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

    // Intercambia dos ruedas
    public void swap(int wheel1, int wheel2) {
        if (wheels.isEmpty()) {
            operationOk = false;
            showError("There are no wheels.");
            return;
        }

        // Primera posición
        if (wheel1 < 1) {
            wheel1 = 1;
        }

        if (wheel1 > wheels.size()) {
            wheel1 = wheels.size();
        }

        // Segunda posición
        if (wheel2 < 1) {
            wheel2 = 1;
        }

        if (wheel2 > wheels.size()) {
            wheel2 = wheels.size();
        }

        Wheel temporary = wheels.get(wheel1 - 1);
        wheels.set(wheel1 - 1, wheels.get(wheel2 - 1));
        wheels.set(wheel2 - 1, temporary);

        operationOk = true;
    }

    // Bloquea una rueda
    public void lock(int wheel) {
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

        wheels.get(wheel - 1).lock();
        operationOk = true;
    }

    // Desbloquea una rueda
    public void unlock(int wheel) {
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

        wheels.get(wheel - 1).unlock();
        operationOk = true;
    }

    // Agrega un símbolo en una posición
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

        // Agrega el símbolo a todas las ruedas
        for (Wheel wheel : wheels) {
            wheel.addSymbol(pos, symbol);
        }

        operationOk = true;
    }

    // Elimina un símbolo
    public void delSymbol(String color) {
        Symbol symbol = findSymbol(color);

        if (symbol == null) {
            operationOk = false;
            showError("The symbol does not exist.");
            return;
        }
        symbols.remove(symbol);

        // Elimina el símbolo de todas las ruedas
        for (Wheel wheel : wheels) {
            wheel.delSymbol(symbol);
        }

        operationOk = true;
    }

    // Coloca un símbolo en una rueda
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

        wheels.get(wheel - 1).placeSymbol(selected);

        operationOk = true;
    }

    // Gira una rueda una vez
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

    // Gira todas las ruedas una vez
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

    // Gira una rueda un número de pasos
    public void spin(int wheel, int steps) {
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

        Wheel selected = wheels.get(wheel - 1); 
        
        if (steps > 0) { 
            for (int i = 0; i < steps; i++) { 
                selected.spin(); 
            } 
        } else if (steps < 0) { 
            int actual = selected.getCurrent(); 
            int cantidad = symbols.size(); 
            int nueva = actual + steps; 
            while (nueva < 1) { 
                nueva = nueva + cantidad; 
            } 
            while (nueva > cantidad) { 
                nueva = nueva - cantidad; 
            }
            Symbol symbol = selected.getSymbol(nueva); 
            selected.placeSymbol(symbol); 
        } 
        operationOk = true;
    }

    // Deja la máquina en una configuración dada
    public void spin(String[] setSymbols) {
        if (setSymbols == null) {
            operationOk = false;
            showError("The configuration cannot be null.");
            return;
        }

        if (setSymbols.length != wheels.size()) {
            operationOk = false;
            showError("The configuration has the wrong number of symbols.");
            return;
        }

        // Comprueba que todos los símbolos existan
        for (String color : setSymbols) {
            if (findSymbol(color) == null) {
                operationOk = false;
                showError("The symbol does not exist.");
                return;
            }
        }

        // Coloca cada rueda en el símbolo indicado
        for (int i = 0; i < wheels.size(); i++) {
            Symbol symbol = findSymbol(setSymbols[i]);
            wheels.get(i).placeSymbol(symbol);
        }

        operationOk = true;
    }

    // Retorna los colores de los símbolos
    public String[] symbols() {
        String[] result = new String[symbols.size()];

        for (int i = 0; i < symbols.size(); i++) {
            result[i] = symbols.get(i).getColor();
        }

        return result;
    }

    // Retorna el número de símbolos diferentes
    
    public int distinctSymbols() {
        String[] configuration = configuration();
        int different = 0;
    
        for (int i = 0; i < configuration.length; i++) {
            boolean found = false;
    
            for (int j = 0; j < i; j++) {
                if (configuration[i].equals(configuration[j])) {
                    found = true;
                }
            }
    
            if (!found) {
                different++;
            }
        }
    
        return different;
    }

    // Retorna la configuración actual
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

    // Comprueba si todas las ruedas muestran el mismo símbolo
    public boolean isJackpot() {
        if (wheels.isEmpty()) {
            return false;
        }
        String[] configuration = configuration();
        if (configuration[0] == null) {
            return false;
        }
        for (int i = 1; i < configuration.length; i++) {
            if (configuration[i] == null || !configuration[0].equals(configuration[i])) {
                return false;
            }
        }
        return true;
    }

    // Hace visible la máquina
    public void makeVisible() {
        visible = true;
        for (Wheel wheel : wheels) {
            wheel.makeVisible();
        }
        operationOk = true;
    }

    // Hace invisible la máquina
    public void makeInvisible() {
        for (Wheel wheel : wheels) {
            wheel.makeInvisible();
        }
        visible = false;
        operationOk = true;
    }

    // Sale de la maquina
    public void exit() {
        System.exit(0);
    }

    // Indica si la última operación fue correcta
    public boolean ok() {
        return operationOk;
    }

    // Busca un símbolo por color
    private Symbol findSymbol(String color) {
        for (Symbol symbol : symbols) {
            if (symbol.getColor().equals(color)) {
                return symbol;
            }
        }
        return null;
    }

    // Muestra un mensaje de error si la máquina está visible
    private void showError(String message) {
        if (visible) {
            JOptionPane.showMessageDialog(null, message);
        }
    }
}

