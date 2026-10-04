public class SpecialSymbol extends Symbol {
    //Create a special symbol
    public SpecialSymbol(String color) {
        super(color);
    }

    //The Special symbol is represented with a square
    @Override
    public String getShape() {
        return "square";
    }
}