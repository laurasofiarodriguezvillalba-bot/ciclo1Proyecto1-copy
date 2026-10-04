import static org.junit.Assert.*; 
import org.junit.Test;

public class SlotMachineC4Test {
    //Test that a normal symbol can be added
    @Test
    public void shouldAddNormalSymbol() {
        SlotMachine machine = new SlotMachine();
        machine.addSymbol("normal", 1, "red");
        assertTrue(machine.ok());
    }
    
    //Test that an ephemeral symbol can be added.
    @Test
    public void shouldAddEphemeralSymbol() {
        SlotMachine machine = new SlotMachine();
        machine.addSymbol("ephemeral", 1, "blue");
        assertTrue(machine.ok());
    }
    
    //Test that a shy symbol can be added
    @Test
    public void shouldAddShySymbol() {
        SlotMachine machine = new SlotMachine();
        machine.addSymbol("shy", 1, "green");
        assertTrue(machine.ok());
    }
    
    //Test that you can add a special symbol
    @Test
    public void shouldAddSpecialSymbol() {
        SlotMachine machine = new SlotMachine();
        machine.addSymbol("special", 1, "yellow");
        assertTrue(machine.ok());
    }
    
    //Test that you can't add two symbols with the same color
    @Test
    public void shouldNotAddRepeatedSymbol() {
        SlotMachine machine = new SlotMachine();
        machine.addSymbol("normal", 1, "red");
        machine.addSymbol("normal", 2, "red");
        assertFalse(machine.ok());
    }
    
    //Test if a symbol can be added on a wheel
    @Test
    public void shouldPlaceSymbol() {
        SlotMachine machine = new SlotMachine();
        machine.addSymbol("normal", 1, "red");
        machine.addSymbol("normal", 2, "blue");
        machine.addWheel("normal", 1);
        machine.placeSymbol(1, "blue");
        assertTrue(machine.ok());
    }
    
    //Test that a normal symbol can be used on a wheel
    @Test
    public void shouldUseNormalSymbolInWheel() {
        SlotMachine machine = new SlotMachine();
        machine.addSymbol("normal", 1, "red");
        machine.addWheel("normal", 1);
        machine.spin(1);
        assertTrue(machine.ok());
    }
    
    //Test that an ephemeral symbol can be used on a wheel
    @Test
    public void shouldUseEphemeralSymbolInWheel() {
        SlotMachine machine = new SlotMachine();
        machine.addSymbol("ephemeral", 1, "blue");
        machine.addWheel("normal", 1);
        machine.spin(1);
        assertTrue(machine.ok());
    }
    
    //Test that a soft hyphen symbol can be used in a wheel
    @Test
    public void shouldUseShySymbolInWheel() {
        SlotMachine machine = new SlotMachine();
        machine.addSymbol("shy", 1, "green");
        machine.addWheel("normal", 1);
        machine.spin(1);
        assertTrue(machine.ok());
    }
    
    //Test that a special symbol can be used on a wheel
    @Test
    public void shouldUseSpecialSymbolInWheel() {
        SlotMachine machine = new SlotMachine();
        machine.addSymbol("special", 1, "yellow");
        machine.addWheel("normal", 1);
        machine.spin(1);
        assertTrue(machine.ok());
    }
    
    //Test that an ephemeral symbol spins
    @Test
    public void shouldSpinEphemeralSymbol() {
        SlotMachine machine = new SlotMachine();
        machine.addSymbol("ephemeral", 1, "blue");
        machine.addSymbol("normal", 2, "red");
        machine.addWheel("normal", 1);
        machine.spin(1);
        assertTrue(machine.ok());
    }
    
    //Test that a shy symbol behaves correctly when selected
    @Test
    public void shouldSelectShySymbol() {
        SlotMachine machine = new SlotMachine();
        machine.addSymbol("shy", 1, "green");
        machine.addSymbol("normal", 2, "red");
        machine.addWheel("normal", 1);
        machine.spin(1);
        assertTrue(machine.ok());
    }
    
    //Test that a special symbol behaves correctly when rotating
    @Test
    public void shouldSpinSpecialSymbol() {
        SlotMachine machine = new SlotMachine();
        machine.addSymbol("special", 1, "yellow");
        machine.addSymbol("normal", 2, "red");
        machine.addWheel("normal", 1);
        machine.spin(1);
        assertTrue(machine.ok());
    }
    
    //Test that a symbol can be deleted
    @Test
    public void shouldDeleteSymbol() {
        SlotMachine machine = new SlotMachine();
        machine.addSymbol("normal", 1, "red");
        machine.delSymbol("red");
        assertTrue(machine.ok());
    }
    
    //Test that you can add a Rebel wheel
    @Test public void shouldAddRebelWheel() { 
        SlotMachine machine = new SlotMachine(); 
        machine.addWheel("rebel", 1); 
        assertTrue(machine.ok()); 
    }
    
    //Test that a Rebel wheel can spin
    @Test public void shouldSpinRebelWheel() { 
        SlotMachine machine = new SlotMachine(); 
        machine.addSymbol("normal", 1, "red"); 
        machine.addSymbol("normal", 2, "blue"); 
        machine.addWheel("rebel", 1); 
        machine.spin(1); 
        assertTrue(machine.ok()); 
    }
    
    //Test that a Rebel wheel CANNOT lock 
    @Test public void shouldNotLockRebelWheel() { 
        SlotMachine machine = new SlotMachine(); 
        machine.addWheel("rebel", 1); 
        machine.lock(1); 
        assertFalse(machine.ok()); 
    }
    
    //Test that a rebel wheel CANNOT be removed
    @Test public void shouldNotDeleteRebelWheel() { 
        SlotMachine machine = new SlotMachine(); 
        machine.addWheel("rebel", 1); 
        machine.delWheel(1); 
        assertFalse(machine.ok()); 
    }
    
    //Test that a rebel wheel CANNOT be swapped
    @Test public void shouldNotSwapRebelWheel() { 
        SlotMachine machine = new SlotMachine(); 
        machine.addWheel("rebel", 1); 
        machine.addWheel("normal", 2); 
        machine.swap(1, 2); 
        assertFalse(machine.ok()); 
    }
    
    //Test that a rebel wheel can still keep spinning after trying to lock it 
    @Test public void shouldSpinRebelAfterLockAttempt() { 
        SlotMachine machine = new SlotMachine(); 
        machine.addSymbol("normal", 1, "red"); 
        machine.addSymbol("normal", 2, "blue"); 
        machine.addWheel("rebel", 1); 
        machine.lock(1); 
        machine.spin(1); 
        assertTrue(machine.ok()); 
    }
}