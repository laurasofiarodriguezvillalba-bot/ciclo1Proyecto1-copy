import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class SlotMachineC2Test {

    private SlotMachine machine;

    @Before
    public void setUp() {
        machine = new SlotMachine();
        machine.addSymbol(1, "red");
        machine.addSymbol(2, "blue");
        machine.addSymbol(3, "green");
        machine.addWheel(1);
        machine.addWheel(2);
        machine.addWheel(3);
    }

    // addWheel

    @Test
    public void accordingQrShouldAddWheel() {
        machine.delWheel(3);
        machine.addWheel(3);
        assertEquals(3, machine.configuration().length);
        assertTrue(machine.ok());
    }

    @Test
    public void accordingQrShouldNotAddWheelAtInvalidPosition() {
        machine.addWheel(100);
        assertEquals(4, machine.configuration().length);
        assertTrue(machine.ok());
    }

    // delWheel

    @Test
    public void accordingQrShouldDeleteWheel() {
        machine.delWheel(2);
        assertEquals(2, machine.configuration().length);
        assertTrue(machine.ok());
    }

    @Test
    public void accordingQrShouldNotFailWhenDeletingFromEmptyMachine() {
        machine.delWheel(1);
        machine.delWheel(1);
        machine.delWheel(1);
        machine.delWheel(1);
        assertFalse(machine.ok());
    }

    // swap

    @Test
    public void accordingQrShouldSwapWheels() {
        machine.spin(new String[]{"red", "blue", "green"});
        machine.swap(1, 3);
        assertArrayEquals(
            new String[]{"green", "blue", "red"},
            machine.configuration()
        );
        assertTrue(machine.ok());
    }

    @Test
    public void accordingQrShouldNotChangeConfigurationWhenSwappingSameWheel() {
        machine.spin(new String[]{"red", "blue", "green"});
        machine.swap(2, 2);
        assertArrayEquals(
            new String[]{"red", "blue", "green"},
            machine.configuration()
        );
        assertTrue(machine.ok());
    }

    // lock

    @Test
    public void accordingQrShouldLockWheel() {
        machine.spin(new String[]{"red", "blue", "green"});
        machine.lock(1);
        machine.spin(1);
        assertArrayEquals(
            new String[]{"red", "blue", "green"},
            machine.configuration()
        );
        assertTrue(machine.ok());
    }

    @Test
    public void accordingQrShouldNotSpinLockedWheel() {
        machine.spin(new String[]{"red", "blue", "green"});
        machine.lock(2);
        machine.spin(2, 3);
        assertArrayEquals(
            new String[]{"red", "blue", "green"},
            machine.configuration()
        );
        assertTrue(machine.ok());
    }

    // unlock

    @Test
    public void accordingQrShouldUnlockWheel() {
        machine.spin(new String[]{"red", "blue", "green"});
        machine.lock(1);
        machine.unlock(1);
        machine.spin(1);
        assertArrayEquals(
            new String[]{"blue", "blue", "green"},
            machine.configuration()
        );
        assertTrue(machine.ok());
    }

    @Test
    public void accordingQrShouldNotKeepWheelLockedAfterUnlock() {
        machine.spin(new String[]{"red", "blue", "green"});
        machine.lock(1);
        machine.unlock(1);
        machine.spin(1, 2);
        assertArrayEquals(
            new String[]{"green", "blue", "green"},
            machine.configuration()
        );
        assertTrue(machine.ok());
    }

    // addSymbol

    @Test
    public void accordingQrShouldAddSymbol() {
        machine.addSymbol(4, "yellow");
        assertArrayEquals(
            new String[]{"red", "blue", "green", "yellow"},
            machine.symbols()
        );
        assertTrue(machine.ok());
    }

    @Test
    public void accordingQrShouldNotAddDuplicateSymbol() {
        machine.addSymbol(4, "red");
        assertArrayEquals(
            new String[]{"red", "blue", "green"},
            machine.symbols()
        );
        assertFalse(machine.ok());
    }

    // delSymbol

    @Test
    public void accordingQrShouldDeleteSymbol() {
        machine.delSymbol("blue");
        assertArrayEquals(
            new String[]{"red", "green"},
            machine.symbols()
        );
        assertTrue(machine.ok());
    }

    @Test
    public void accordingQrShouldNotDeleteUnknownSymbol() {
        machine.delSymbol("yellow");
        assertArrayEquals(
            new String[]{"red", "blue", "green"},
            machine.symbols()
        );
        assertFalse(machine.ok());
    }

    // placeSymbol

    @Test
    public void accordingQrShouldPlaceSymbolInWheel() {
        machine.placeSymbol(2, "green");
        assertEquals(
            "green",
            machine.configuration()[1]
        );
        assertTrue(machine.ok());
    }

    @Test
    public void accordingQrShouldNotPlaceUnknownSymbol() {
        machine.spin(new String[]{"red", "blue", "green"});
        machine.placeSymbol(2, "yellow");
        assertArrayEquals(
            new String[]{"red", "blue", "green"},
            machine.configuration()
        );
        assertFalse(machine.ok());
    }

    // spin(wheel)

    @Test
    public void accordingQrShouldSpinOneWheel() {
        machine.spin(new String[]{"red", "blue", "green"});
        machine.spin(1);
        assertArrayEquals(
            new String[]{"blue", "blue", "green"},
            machine.configuration()
        );
        assertTrue(machine.ok());
    }

    @Test
    public void accordingQrShouldNotChangeOtherWheelsWhenSpinningOneWheel() {
        machine.spin(new String[]{"red", "blue", "green"});
        machine.spin(1);
        assertEquals("blue", machine.configuration()[0]);
        assertEquals("blue", machine.configuration()[1]);
        assertEquals("green", machine.configuration()[2]);
        assertTrue(machine.ok());
    }

    // spin(wheel, steps)

    @Test
    public void accordingQrShouldSpinWheelSeveralSteps() {
        machine.spin(new String[]{"red", "blue", "green"});
        machine.spin(1, 2);
        assertArrayEquals(
            new String[]{"green", "blue", "green"},
            machine.configuration()
        );
        assertTrue(machine.ok());
    }

    @Test
    public void accordingQrShouldSpinWheelBackwards() {
        machine.spin(new String[]{"red", "blue", "green"});
        machine.spin(1, -1);
        assertArrayEquals(
            new String[]{"green", "blue", "green"},
            machine.configuration()
        );
        assertTrue(machine.ok());
    }

    // spin(String[])

    @Test
    public void accordingQrShouldSetConfiguration() {
        machine.spin(new String[]{"green", "red", "blue"});
        assertArrayEquals(
            new String[]{"green", "red", "blue"},
            machine.configuration()
        );
        assertTrue(machine.ok());
    }

    @Test
    public void accordingQrShouldNotSetConfigurationWithUnknownSymbol() {
        machine.spin(new String[]{"red", "blue", "green"});
        machine.spin(new String[]{"red", "yellow", "green"});
        assertArrayEquals(
            new String[]{"red", "blue", "green"},
            machine.configuration()
        );
        assertFalse(machine.ok());
    }

    // spin()

    @Test
    public void accordingQrShouldSpinAllWheels() {
        machine.spin(new String[]{"red", "blue", "green"});
        machine.spin();
        assertArrayEquals(
            new String[]{"blue", "green", "red"},
            machine.configuration()
        );
        assertTrue(machine.ok());
    }

    @Test
    public void accordingQrShouldNotSpinAllWheelsWhenThereAreNoWheels() {
        machine.delWheel(1);
        machine.delWheel(1);
        machine.delWheel(1);
        machine.spin();
        assertFalse(machine.ok());
    }

    // symbols()

    @Test
    public void accordingQrShouldReturnSymbols() {
        assertArrayEquals(
            new String[]{"red", "blue", "green"},
            machine.symbols()
        );
    }

    @Test
    public void accordingQrShouldNotReturnAnUnknownSymbol() {
        String[] symbols = machine.symbols();
        assertFalse(
            symbols[0].equals("yellow")
        );
    }

    // configuration()

    @Test
    public void accordingQrShouldReturnCurrentConfiguration() {
        machine.spin(new String[]{"green", "red", "blue"});
        assertArrayEquals(
            new String[]{"green", "red", "blue"},
            machine.configuration()
        );
    }

    @Test
    public void accordingQrShouldNotReturnWrongConfiguration() {
        machine.spin(new String[]{"green", "red", "blue"});
        assertFalse(
            java.util.Arrays.equals(
                new String[]{"red", "blue", "green"},
                machine.configuration()
            )
        );
    }

    // distinctSymbols()

    @Test
    public void accordingQrShouldCountDistinctSymbols() {
        machine.spin(new String[]{"red", "blue", "red"});
        assertEquals(
            2,
            machine.distinctSymbols()
        );
    }

    @Test
    public void accordingQrShouldNotCountRepeatedSymbolsTwice() {
        machine.spin(new String[]{"red", "red", "red"});
        assertFalse(
            machine.distinctSymbols() == 3
        );
        assertEquals(1, machine.distinctSymbols());
    }

    // isJackpot()

    @Test
    public void accordingQrShouldRecognizeJackpot() {
        machine.spin(new String[]{"green", "green", "green"});
        assertTrue(machine.isJackpot());
    }

    @Test
    public void accordingQrShouldNotRecognizeJackpotWithDifferentSymbols() {
        machine.spin(new String[]{"red", "blue", "green"});
        assertFalse(machine.isJackpot());
    }

    // makeVisible()

    @Test
    public void accordingQrShouldMakeMachineVisible() {
        machine.makeVisible();
        assertTrue(machine.ok());
    }

    @Test
    public void accordingQrShouldNotMakeOperationIncorrectWhenMakingVisible() {
        machine.makeVisible();
        assertTrue(machine.ok());
    }

    // makeInvisible()

    @Test
    public void accordingQrShouldMakeMachineInvisible() {
        machine.makeVisible();
        machine.makeInvisible();
        assertTrue(machine.ok());
    }

    @Test
    public void accordingQrShouldNotMakeOperationIncorrectWhenMakingInvisible() {
        machine.makeInvisible();
        assertTrue(machine.ok());
    }

    // ok()

    @Test
    public void accordingQrShouldReturnTrueAfterCorrectOperation() {
        machine.addWheel(4);
        assertTrue(machine.ok());
    }

    @Test
    public void accordingQrShouldReturnFalseAfterIncorrectOperation() {
        machine.delSymbol("yellow");
        assertFalse(machine.ok());
    }
}