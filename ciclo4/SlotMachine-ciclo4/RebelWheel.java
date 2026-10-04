public class RebelWheel extends Wheel {
    public RebelWheel(int x, int y) {
        super(x, y);
        changeBodyColor("cyan");
    }
    
    //The rebel wheel cannot be removed
    @Override
    public boolean canDelete() {
        return false;
    }
    
    //The rebel wheel can't be swapped with another wheel
    @Override
    public boolean canSwap() {
        return false;
    }

    //The rebel wheel can't be locked
    @Override
    public boolean canLock() {
        return false;
    }
}