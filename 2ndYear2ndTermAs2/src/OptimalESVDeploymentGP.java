import java.util.ArrayList;
import java.util.Collections;

/**
 * This class accomplishes Mission Eco-Maintenance
 */
public class OptimalESVDeploymentGP
{
    private ArrayList<Integer> maintenanceTaskEnergyDemands;

    /*
     * Should include tasks assigned to ESVs.
     * For the sample input:
     * 8 100
     * 20 50 40 70 10 30 80 100 10
     * 
     * The list should look like this:
     * [[100], [80, 20], [70, 30], [50, 40, 10], [10]]
     * 
     * It is expected to be filled after getMinNumESVsToDeploy() is called.
     */
    private ArrayList<ArrayList<Integer>> maintenanceTasksAssignedToESVs = new ArrayList<>();

    ArrayList<ArrayList<Integer>> getMaintenanceTasksAssignedToESVs() {
        return maintenanceTasksAssignedToESVs;
    }

    public OptimalESVDeploymentGP(ArrayList<Integer> maintenanceTaskEnergyDemands) {
        this.maintenanceTaskEnergyDemands = maintenanceTaskEnergyDemands;
    }

    public ArrayList<Integer> getMaintenanceTaskEnergyDemands() {
        return maintenanceTaskEnergyDemands;
    }

    /**
     *
     * @param maxNumberOfAvailableESVs the maximum number of available ESVs to be deployed
     * @param maxESVCapacity the maximum capacity of ESVs
     * @return the minimum number of ESVs required using first fit approach over reversely sorted items.
     * Must return -1 if all tasks can't be satisfied by the available ESVs
     */
    public int getMinNumESVsToDeploy(int maxNumberOfAvailableESVs, int maxESVCapacity)
    {
        Collections.sort(maintenanceTaskEnergyDemands, Collections.reverseOrder());
        ArrayList<Integer> ESVsEnergyCapacities = new ArrayList<>();
        for (int i = 0; i < maxNumberOfAvailableESVs; i++) {
            ESVsEnergyCapacities.add(maxESVCapacity);
        }
        for (int i = 0; i < maxNumberOfAvailableESVs; i++) {
            // Create an empty row
            ArrayList<Integer> row = new ArrayList<>();
            // Add the row to the 2D ArrayList
            maintenanceTasksAssignedToESVs.add(row);
        }

        for (int i = 0; i < maintenanceTaskEnergyDemands.size(); i++) {
            boolean taskStatus=false;
            for (int j = 0; j < ESVsEnergyCapacities.size(); j++) {
                if(ESVsEnergyCapacities.get(j) >= maintenanceTaskEnergyDemands.get(i)){ //task can fit in this esv
                    int newCapacity = ESVsEnergyCapacities.get(j) - maintenanceTaskEnergyDemands.get(i);
                    ESVsEnergyCapacities.set(j,newCapacity);
                    taskStatus=true;
                    maintenanceTasksAssignedToESVs.get(j).add(maintenanceTaskEnergyDemands.get(i));
                    break;
                } // else move to next esv
            }
            //if task iterate over all esvs and cant fit any of them then mission fails
            if(!taskStatus){
                return -1;
            }
        }
        int minNumber=0;
        for (int i = 0; i < maintenanceTasksAssignedToESVs.size(); i++) {
            if(!maintenanceTasksAssignedToESVs.get(i).isEmpty()){
                minNumber++;
            }
        }


        // TODO: Your code goes here
        return minNumber;
    }

}
