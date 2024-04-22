import java.util.ArrayList;
import java.util.List;

/**
 * This class accomplishes Mission POWER GRID OPTIMIZATION
 */
public class PowerGridOptimization {
    private ArrayList<Integer> amountOfEnergyDemandsArrivingPerHour;
    private ArrayList<Integer> D;

    public ArrayList<Integer> getSOL() {
        return SOL;
    }

    private ArrayList<Integer> SOL = new ArrayList<>();

    public ArrayList<ArrayList<Integer>> getHOURS() {
        return HOURS;
    }

    private ArrayList<ArrayList<Integer>> HOURS = new ArrayList<>();

    public PowerGridOptimization(ArrayList<Integer> amountOfEnergyDemandsArrivingPerHour){
        this.amountOfEnergyDemandsArrivingPerHour = amountOfEnergyDemandsArrivingPerHour;
        this.D = new ArrayList<>(amountOfEnergyDemandsArrivingPerHour);
    }


    public ArrayList<Integer> getAmountOfEnergyDemandsArrivingPerHour() {
        return amountOfEnergyDemandsArrivingPerHour;
    }
    public int E(int k){
        return k*k;
    }
    public int min(int a, int b){
        if(a<b){return a;}
        else {return b;}
    }

    /**
     *     Function to implement the given dynamic programming algorithm
     *     SOL(0) <- 0
     *     HOURS(0) <- [ ]
     *     For{j <- 1...N}
     *         SOL(j) <- max_{0<=i<j} [ (SOL(i) + min[ E(j), P(j − i) ] ]
     *         HOURS(j) <- [HOURS(i), j]
     *     EndFor
     *
     * @return OptimalPowerGridSolution
     */
    public OptimalPowerGridSolution getOptimalPowerGridSolutionDP(){
        // TODO: YOUR CODE HERE
        int N = amountOfEnergyDemandsArrivingPerHour.size();

        SOL.add(0);
        HOURS.add(new ArrayList<>());

        for (int j = 1; j <= N; j++) {
            int max =0;
            ArrayList<Integer> hoursToDischarge = new ArrayList<>();

            for (int i = 0; i < j; i++) {
                int temp = SOL.get(i) + min(D.get(j-1),E(j-i));
                if(temp>max){
                    max=temp;
                    hoursToDischarge=new ArrayList<>(HOURS.get(i));
                    hoursToDischarge.add(j);
                }
            }

            SOL.add(j,max);
            HOURS.add(hoursToDischarge);
        }


        return new OptimalPowerGridSolution(SOL.get(N),HOURS.get(N));
    }
}
