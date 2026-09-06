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

    //swap

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

    // Spin(wheel,steps)

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
    public void accordingQrShouldNotSpinWhenStepsAreNegative() {
        machine.spin(new String[]{"red", "blue", "green"});
        machine.spin(1, -2);
        assertArrayEquals(
            new String[]{"red", "blue", "green"},
            machine.configuration()
        );
        assertFalse(machine.ok());
    }

    // spin(SetSymbols)

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

    //Jackpot

    @Test
    public void accordingQrShouldRecognizeJackpotAfterConfiguration() {
        machine.spin(new String[]{"green", "green", "green"});
        assertTrue(machine.isJackpot());
        assertTrue(machine.ok());
    }

    @Test
    public void accordingQrShouldNotRecognizeJackpotWithDifferentSymbols() {
        machine.spin(new String[]{"red", "blue", "green"});
        assertFalse(machine.isJackpot());
        assertTrue(machine.ok());
    }
}