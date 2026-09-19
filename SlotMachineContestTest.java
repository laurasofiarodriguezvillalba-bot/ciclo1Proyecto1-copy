import static org.junit.Assert.*;
import org.junit.Test;

public class SlotMachineContestTest {
    
    //Should:solve a machine with three wheels.
    @Test
    public void shouldSolveThreeWheels() {
        SlotMachineContest contest = new SlotMachineContest();
        int[][] solution = contest.solve(3);
        assertNotNull(solution);
        assertTrue(solution.length > 0);
    }

    //Should not: return movements with invalid wheel numbers.
    @Test
    public void shouldNotReturnInvalidWheel() {
        SlotMachineContest contest = new SlotMachineContest();
        int[][] solution = contest.solve(3);
        for (int i = 0; i < solution.length; i++) {
            int wheel = solution[i][0];
            assertFalse(wheel < 1 || wheel > 3);
        }
    }

    //Should: simulate a solution with three wheels.
    @Test
    public void shouldSimulateThreeWheels() {
        SlotMachineContest contest = new SlotMachineContest();
        contest.solve(3);
        contest.simulate(3);
    }

    /**
     * Should not:
     * reuse a solution when the number of wheels changes.
     */
    @Test
    public void shouldNotReuseSolutionForAnotherN() {
        SlotMachineContest contest = new SlotMachineContest();

        contest.solve(3);

        contest.simulate(5);
    }
}