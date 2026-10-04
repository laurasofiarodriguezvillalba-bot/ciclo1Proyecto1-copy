public class ShySymbol extends Symbol {
    private boolean visible;
    //Create a shy symbol
    public ShySymbol(String color) {
        super(color);
        visible = true;
    }

    //Indicate if the shy symbol is visible
    @Override
    public boolean isVisible() {
        return visible;
    }

    //Change the visibility of the symbol every time it is selected
    @Override
    public void select() {
        visible = !visible;
    }
}