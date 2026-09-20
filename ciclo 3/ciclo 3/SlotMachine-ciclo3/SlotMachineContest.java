import java.util.ArrayList;

public class SlotMachineContest {
    private SlotMachine machine;
    private ArrayList<int[]> movements;
    private String[] initialConfiguration;
    private int [][] solution;
    private int solvedN;

    //Solves the marathon problem for a machine with n wheels and n symbols.
    public int[][] solve(int n) {
        machine = new SlotMachine(n);
        movements = new ArrayList<int[]>();

        // Save the initial configuration
        initialConfiguration = machine.configuration();
        int[] initialValues = exploreWheel(1, n);
        int minimum = initialValues[0];

        for (int i = 1; i < n; i++) {
            if (initialValues[i] < minimum) {
                minimum = initialValues[i];
            }
        }

        int targetPosition = -1;
        int referencePosition = -1;

        // Find the target
        for (int i = 0; i < n; i++) {
            if (initialValues[i] == minimum + 1) {
                targetPosition = i;
                break;
            }
        }

        // Find the reference
        for (int i = 0; i < n; i++) {
            if (initialValues[i] == minimum) {
                referencePosition = i;
                break;
            }
        }

        if (targetPosition == -1) {
            targetPosition = 0;
        }

        if (referencePosition == -1) {
            referencePosition = (targetPosition + 1) % n;
        }

        // Place wheel 1 on the target
        rotate(1, targetPosition);

        int[] targets = new int[n];
        targets[0] = targetPosition;

        // Find the target in each wheel
        for (int wheel = 2; wheel <= n; wheel++) {
            int[] withTarget = exploreWheel(wheel, n);
            int rotation = difference(referencePosition,targetPosition,n);
            rotate(1, rotation);

            int[] withReference = exploreWheel(wheel, n);

            // Compare the two explorations
            int position = findTarget(withTarget,withReference);

            targets[wheel - 1] = position;
            rotation = difference(targetPosition,referencePosition,n);
            rotate(1, rotation);
        }

        // Place all wheels on their target
        for (int wheel = 2; wheel <= n; wheel++) {
            rotate(wheel, targets[wheel - 1]);
        }
         // Create the final solution array
        int[][] finalSolution = new int[movements.size()][2];

        for (int i = 0; i < movements.size(); i++) {
            finalSolution[i][0] = movements.get(i)[0];
            finalSolution[i][1] = movements.get(i)[1];
        }

        // Save the solution
        solution = finalSolution;
        solvedN = n;

        return solution;
    }

    //Explores all positions of a wheel.
    //@return the k values found
    private int[] exploreWheel(int wheel, int n) {
        int[] values = new int[n];

        for (int position = 0; position < n; position++) {
            values[position] = machine.distinctSymbols();
            rotate(wheel, 1);
        }
        return values;
    }

    //Finds the position that corresponds to the target.    
    private int findTarget(int[] withTarget,int[] withReference) {
        int targetMinimum = withTarget[0];
        for (int i = 1; i < withTarget.length; i++) {
            if (withTarget[i] < targetMinimum) {
                targetMinimum = withTarget[i];
            }
        }

        int referenceMinimum = withReference[0];

        for (int i = 1; i < withReference.length; i++) {
            if (withReference[i] < referenceMinimum) {
                referenceMinimum = withReference[i];
            }
        }
        
        for (int i = 0; i < withTarget.length; i++) {
            boolean isTarget = withTarget[i] == targetMinimum;
            boolean isReference = withReference[i] == referenceMinimum;
            if (isTarget && !isReference) {
                return i;
            }
        }

        for (int i = 0; i < withTarget.length; i++) {
            if (withTarget[i] != withReference[i]) {
                return i;
            }
        }
        return 0;
    }

    //Executes a rotation and saves it in the list of movements.
    private void rotate(int wheel, int steps) {
        if (steps != 0) {
            machine.spin(wheel, steps);
            int[] movement = new int[2];
            movement[0] = wheel;
            movement[1] = steps;
            movements.add(movement);
        }
    }

    
    //Calculates the shortest rotation between two positions.
    private int difference(int target, int current, int n) {
        int difference = target - current;

        while (difference > n / 2) {
            difference = difference - n;
        }

        while (difference < -n / 2) {
            difference = difference + n;
        }
        return difference;
    }

    //Simulates the solution found by solve.     
    public void simulate(int n) {
        int waitingTime = 100;
        
        //Otherwise calculate a new solution.
        if (solution == null || solvedN != n) {
            solution = solve(n);
        }
        
        // Remove previous drawings from the Canvas
        Canvas.getCanvas().eraseAll();
        
        // Create a new machine
        machine = new SlotMachine(n);

        // Reconstruct exactly the initial configuration
        for (int wheel = 1; wheel <= n; wheel++) {
            machine.placeSymbol(wheel,initialConfiguration[wheel - 1]);
        }

        machine.makeVisible();

        System.out.println("Simulation for n = " + n);
        System.out.println();
        boolean finished = false;

        //Execute the movements
        for (int i = 0; i < solution.length && !finished; i++) {

            int wheel = solution[i][0];
            int steps = solution[i][1];

            // Execute the movement
            machine.spin(wheel, steps);
            
            try {
                Thread.sleep(waitingTime);
            } catch (InterruptedException e) {
            }

            // Calculate k after the movement
            int k = machine.distinctSymbols();
            System.out.println(
                "Movement " + (i + 1) +
                ": wheel " + wheel +
                ", steps " + steps +
                ", k = " + k
            );

            if (k == 1) {
                finished = true;
                System.out.println();
                System.out.println("Jackpot achieved.");
            }
        }
    }
}