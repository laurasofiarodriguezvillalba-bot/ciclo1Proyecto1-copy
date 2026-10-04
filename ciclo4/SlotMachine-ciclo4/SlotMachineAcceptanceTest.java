import static org.junit.Assert.*;
import org.junit.Test;

public class SlotMachineAcceptanceTest {
    @Test
    public void acceptanceTest1() {
        //Create the machine
        SlotMachine machine = new SlotMachine();

        //Add symbols
        machine.addSymbol(1, "red");
        machine.addSymbol(2, "blue");
        machine.addSymbol(3, "green");
    
        //Add three wheels
        machine.addWheel(1);
        machine.addWheel(2);
        machine.addWheel(3);

        //Set up initial settings
        machine.spin(new String[]{"red", "blue", "green"});

        assertArrayEquals(
            new String[]{"red", "blue", "green"},
            machine.configuration()
        );

        //Block the front wheel
        machine.lock(1);

        //Try to turn a stuck wheel
        machine.spin(1, 2);

        //It shouldn't change
        assertArrayEquals(
            new String[]{"red", "blue", "green"},
            machine.configuration()
        );

        //Unlock the first wheel
        machine.unlock(1);

        //Turn the first wheel two steps
        machine.spin(1, 2);

        assertArrayEquals(
            new String[]{"green", "blue", "green"},
            machine.configuration()
        );

        //Set up a new configuration
        machine.spin(new String[]{"red", "red", "red"});

        assertArrayEquals(
            new String[]{"red", "red", "red"},
            machine.configuration()
        );

        //Check jackpot
        assertTrue(machine.isJackpot());

        //Swap the first and third wheel
        machine.swap(1, 3);

        //Since they all have a net, it's still a jackpot
        assertArrayEquals(
            new String[]{"red", "red", "red"},
            machine.configuration()
        );

        assertTrue(machine.isJackpot());

        //Try a different setting
        machine.spin(new String[]{"green", "blue", "red"});

        assertFalse(machine.isJackpot());

        //Check that the last operation was correct
        assertTrue(machine.ok());
        
        
    }
    
    @Test
    public void acceptanceTest2() {
        //Create the machine
        SlotMachine machine = new SlotMachine();
    
        //Add symbols
        machine.addSymbol(1, "red");
        machine.addSymbol(2, "blue");
        machine.addSymbol(3, "green");
        machine.addSymbol(4, "yellow");
    
        //Add four wheels
        machine.addWheel(1);
        machine.addWheel(2);
        machine.addWheel(3);
        machine.addWheel(4);
    
    
        //Set up initial settings
        machine.spin(new String[]{"red", "blue", "green", "yellow"});
    
        assertArrayEquals(
            new String[]{"red", "blue", "green", "yellow"},
            machine.configuration()
        );
    
        //Turn the second wheel three steps
        machine.spin(2, 3);
    
        assertArrayEquals(
            new String[]{"red", "red", "green", "yellow"},
            machine.configuration()
        );
    
        //lock the third wheel 
        machine.lock(3);
    
        //Try to turn the third wheel
        machine.spin(3, 2);
    
        //It shouldn't change because it's locked
        assertArrayEquals(
            new String[]{"red", "red", "green", "yellow"},
            machine.configuration()
        );
    
        //Unlock the third wheel
        machine.unlock(3);
    
        //Turn it once
        machine.spin(3);
    
        assertArrayEquals(
            new String[]{"red", "red", "yellow", "yellow"},
            machine.configuration()
        );
    
        //Swap the first and fourth wheel
        machine.swap(1, 4);
    
        assertArrayEquals(
            new String[]{"yellow", "red", "yellow", "red"},
            machine.configuration()
        );
    
        //Trying to set up a configuration with a symbol that DOESN'T exist
        machine.spin(new String[]{"red", "purple", "yellow", "blue"});
    
        //The operation must fail
        assertFalse(machine.ok());
    
        //The previous setting should be kept
        assertArrayEquals(
            new String[]{"yellow", "red", "yellow", "red"},
            machine.configuration()
        );
    }
    
    //Acceptance tests Cycle 3:
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
    

    //Acceptance test Cycle 4:
    //Check that the different types of symbols and wheels work
    @Test
    public void shouldUseDifferentTypesOfSymbolsAndWheels() {
        SlotMachine machine = new SlotMachine();
        machine.addSymbol("normal", 1, "red");
        machine.addSymbol("ephemeral", 2, "blue");
        machine.addSymbol("shy", 3, "green");
        machine.addSymbol("special", 4, "yellow");
        machine.addWheel("normal", 1);
        machine.addWheel("lefty", 2);
        machine.addWheel("rebel", 3);
        machine.spin(1);
        assertTrue(machine.ok());
        machine.spin(2);
        assertTrue(machine.ok());
        machine.spin(3);
        assertTrue(machine.ok());
    }

    //Check that a rebel wheel cannot be locked, removed, or swapped
    @Test
    public void shouldRespectRebelWheelRestrictions() {
        SlotMachine machine = new SlotMachine();
        machine.addSymbol("normal", 1, "red");
        machine.addSymbol("normal", 2, "blue");
        machine.addWheel("rebel", 1);
        machine.addWheel("normal", 2);
        //The caster wheel can't be locked
        machine.lock(1);
        assertFalse(machine.ok());
        //The rebel wheel can't be removed
        machine.delWheel(1);
        assertFalse(machine.ok());
        //The Rebel can't be exchanged
        machine.swap(1, 2);
        assertFalse(machine.ok());
        //The Rebel can turn
        machine.spin(1);
        assertTrue(machine.ok());
    }
}