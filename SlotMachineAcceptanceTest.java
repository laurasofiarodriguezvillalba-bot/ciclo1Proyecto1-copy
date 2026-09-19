import static org.junit.Assert.*;
import org.junit.Test;

public class SlotMachineAcceptanceTest {
    @Test
    public void acceptanceTest1() {
        // Create the machine
        SlotMachine machine = new SlotMachine();

        // Add symbols
        machine.addSymbol(1, "red");
        machine.addSymbol(2, "blue");
        machine.addSymbol(3, "green");
    
        // Add three wheels
        machine.addWheel(1);
        machine.addWheel(2);
        machine.addWheel(3);
        
        //machine.makeVisible();

        // Set initial configuration
        machine.spin(new String[]{"red", "blue", "green"});
        assertArrayEquals(
            new String[]{"red", "blue", "green"},
            machine.configuration()
        );

        // Lock the first wheel
        machine.lock(1);

        // Trying to turn a locked wheel
        machine.spin(1, 2);

        // It should not change
        assertArrayEquals(
            new String[]{"red", "blue", "green"},
            machine.configuration()
        );

        // Unlock the first wheel
        machine.unlock(1);

        // Turn the first wheel two steps
        machine.spin(1, 2);

        assertArrayEquals(
            new String[]{"green", "blue", "green"},
            machine.configuration()
        );

        // Set a new configuration
        machine.spin(new String[]{"red", "red", "red"});
        assertArrayEquals(
            new String[]{"red", "red", "red"},
            machine.configuration()
        );

        // Check jackpot
        assertTrue(machine.isJackpot());

        // Swap the first and third wheels
        machine.swap(1, 3);

        // Since they all have red in them, it's still a jackpot.
        assertArrayEquals(
            new String[]{"red", "red", "red"},
            machine.configuration()
        );
        assertTrue(machine.isJackpot());

        // Try a different configuration
        machine.spin(new String[]{"green", "blue", "red"});

        assertFalse(machine.isJackpot());

        // Verify that the last operation was correct
        assertTrue(machine.ok());

    }
    
    @Test
    public void acceptanceTest2() {
        // 1. Create the machine
        SlotMachine machine = new SlotMachine();
    
        // 2. Add symbols
        machine.addSymbol(1, "red");
        machine.addSymbol(2, "blue");
        machine.addSymbol(3, "green");
        machine.addSymbol(4, "yellow");
    
        // 3. Add four wheels
        machine.addWheel(1);
        machine.addWheel(2);
        machine.addWheel(3);
        machine.addWheel(4);
        
        //machine.makeVisible();
    
        // 4. Set initial configuration
        machine.spin(new String[]{"red", "blue", "green", "yellow"});
    
        assertArrayEquals(
            new String[]{"red", "blue", "green", "yellow"},
            machine.configuration()
        );
    
        // 5. Turn the second wheel three steps
        machine.spin(2, 3);
    
        assertArrayEquals(
            new String[]{"red", "red", "green", "yellow"},
            machine.configuration()
        );
    
        // 6. Lock the third wheel
        machine.lock(3);
    
        // Try to turn the third wheel
        machine.spin(3, 2);
    
        // No debe cambiar porque está bloqueada
        assertArrayEquals(
            new String[]{"red", "red", "green", "yellow"},
            machine.configuration()
        );
    
        // 7. Unlock the third wheel
        machine.unlock(3);
    
        // Turn it once
        machine.spin(3);
    
        assertArrayEquals(
            new String[]{"red", "red", "yellow", "yellow"},
            machine.configuration()
        );
    
        // 8. Swap the first and fourth wheels
        machine.swap(1, 4);
    
        assertArrayEquals(
            new String[]{"yellow", "red", "yellow", "red"},
            machine.configuration()
        );
    
        // 9. Trying to set a configuration with a symbol that does NOT exist
        machine.spin(new String[]{"red", "purple", "yellow", "blue"});
        assertFalse(machine.ok());
    
        // The previous configuration must be maintained
        assertArrayEquals(
            new String[]{"yellow", "red", "yellow", "red"},
            machine.configuration()
        );
    }
     @Test
    public void acceptanceShouldSolveMarathon() {
        SlotMachineContest contest = new SlotMachineContest();
        int[][] solution = contest.solve(3);
        assertNotNull(solution);
        assertTrue(solution.length > 0);
        for (int i = 0; i < solution.length; i++) {
            assertTrue(solution[i][0] >= 1);
            assertTrue(solution[i][0] <= 3);
        }
    }

    @Test
    public void acceptanceShouldSimulateSolution() {
        SlotMachineContest contest = new SlotMachineContest();
        contest.solve(3);
        contest.simulate(3);
        assertTrue(true);
    }
}
