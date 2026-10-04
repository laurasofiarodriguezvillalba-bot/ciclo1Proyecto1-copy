public class Symbol {
    private String color;

    //Create a symbol with a color
    public Symbol(String color) {
        this.color = color;
    }

    //Returns the color of the symbol
    public String getColor() {
        return color;
    }
    
    //Symbol behavior on each spin of the wheel
    public void spin() {
    }

    //Behavior of the symbol when it is selected
    public void select() {
    }

    //Returns the size of the symbol
    public int getSize() {
        return 25;
    }

    //Indicate if the symbol is visible
    public boolean isVisible() {
        return true;
    }
    
    //Returns the shape of the symbol
    public String getShape() {
        return "circle";
    }
}