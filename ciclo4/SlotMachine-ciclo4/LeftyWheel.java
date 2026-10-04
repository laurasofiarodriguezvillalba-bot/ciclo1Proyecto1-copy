public class LeftyWheel extends Wheel {
    //Creates a Lefty wheel and assigns it the color
    public LeftyWheel(int x, int y) {
        super(x, y);
        changeBodyColor("pink");
    }
    
    //Copy the state of the wheel that is on the left
    public void copyLeftState(Wheel left) {
        if (left != null) {
            copyState(left);
        }
    }
}