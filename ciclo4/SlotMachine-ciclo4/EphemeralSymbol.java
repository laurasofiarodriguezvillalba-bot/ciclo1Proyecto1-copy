public class EphemeralSymbol extends Symbol {
    private int size;
    //Create an ephemeral symbol
    public EphemeralSymbol(String color) {
        super(color);
        size = 25;
    }

    //Reduce the size of the symbol with each turn until it reaches 1
    @Override
    public void spin() {
        if (size > 1) {
            size = size - 5;

            if (size < 1) {
                size = 1;
            }
        }
    }

    //Returns the current size of the symbol
    @Override
    public int getSize() {
        return size;
    }
}