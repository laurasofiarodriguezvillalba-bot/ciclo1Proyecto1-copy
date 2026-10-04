import java.util.ArrayList;

public class SlotMachineContest {
    private static SlotMachine machine;
    private static ArrayList<int[]> movements;
    private static int[][] solution;
    private static int solvedN;
    private static int simulatedMoves;

    //Solve the marathon problem
    public static int[][] solve(int n) {
        Canvas.getCanvas().eraseAll();
        machine = new SlotMachine(n);
        movements = new ArrayList<int[]>();
        simulatedMoves = 0;

        //It makes the wheels show different symbols
        for (int wheel = 2; wheel <= n; wheel++) {
            makeDistinct(wheel, n);
        }
        
        int[] foundWheel = new int[n - 1];

        for (int symbol = 1; symbol < n; symbol++) {
            rotate(1, 1);
            boolean found = false;
            for (int wheel = 2;
                 wheel <= n && !found;
                 wheel++) {
   
                boolean alreadyFound = false;
                for (int i = 0; i < symbol - 1; i++) {
                    if (foundWheel[i] == wheel) {
                        alreadyFound = true;
                    }
                }

                if (alreadyFound) {
                    continue;
                }
                
                rotate(wheel, -1);
                int k = machine.distinctSymbols();

                if (k == n) {
                    foundWheel[symbol - 1] = wheel;
                    found = true;
                } else {
                    rotate(wheel, 1);
                }
            }
        }

        rotate(1, 1);

        //Adjust each wheel to match the symbol it should show
        for (int i = 0; i < n - 1; i++) {
            rotate(foundWheel[i], -i);
        }

        //Save the solution's moves      
        solution = new int[movements.size()][2];

        for (int i = 0; i < movements.size(); i++) {
            solution[i][0] = movements.get(i)[0];
            solution[i][1] = movements.get(i)[1];
        }
        solvedN = n;

        //Return the machine to the initial settings
        for (int i = movements.size() - 1; i >= 0; i--) {
            int wheel = movements.get(i)[0];
            int steps = movements.get(i)[1];
            machine.spin(wheel, -steps);
        }
        return solution;
    }

    //Look for the position that produces the most different symbols
    private static void makeDistinct(int wheel, int n) {
        int bestK = -1;
        int bestPosition = 0;
        for (int position = 0; position < n; position++) {
            int k = machine.distinctSymbols();
            if (k > bestK) {
                bestK = k;
                bestPosition = position;
            }
            
            if (position < n - 1) {
                rotate(wheel, 1);
            }
        }
        int steps = difference(bestPosition, n - 1, n);
        rotate(wheel, steps);
    }

    //Turn a wheel and keep the motion
    private static void rotate(int wheel, int steps) {
        if (steps != 0) {
            machine.spin(wheel, steps);
            int[] movement = new int[2];
            movement[0] = wheel;
            movement[1] = steps;
            movements.add(movement);
        }
    }

    //Calculate the shortest movement between two positions   
    private static int difference(int target, int current, int n) {
        int difference = target - current;
        while (difference > n / 2) {
            difference = difference - n;
        }

        while (difference < -n / 2) {
            difference = difference + n;
        }
        return difference;
    }

    //Simulate the solution found    
    public static void simulate(int n) {
        //Create the solution if it doesn't exist yet
        if (solution == null || solvedN != n) {
            solve(n);
        }

        //Return to the initial state if there is already a solution
        if (simulatedMoves > 0) {
            for (int i = simulatedMoves - 1; i >= 0; i--) {
                int wheel = solution[i][0];
                int steps = solution[i][1];
                machine.spin(wheel, -steps);
            }
            simulatedMoves = 0;
        }
        machine.makeVisible();

        boolean finished = false;

        //Execute the solution's moves los movimientos de la solución
        for (int i = 0;i < solution.length && !finished;i++) {
            int wheel = solution[i][0];
            int steps = solution[i][1];
            machine.spin(wheel, steps);
            simulatedMoves = i + 1;

            //Gets the number of different symbols
            int k = machine.distinctSymbols();

            System.out.println(
                "Movimiento " + (i + 1)
                + ": rueda " + wheel
                + ", pasos " + steps
                + ", k = " + k
            );

            //If k = 1, all the symbols are the same
            if (k == 1) {
                finished = true;
            } else {
                Canvas.getCanvas().wait(200);
            }
        }
    }
}