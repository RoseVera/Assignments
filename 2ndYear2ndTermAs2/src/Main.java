import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

/**
 * Main class
 */
// FREE CODE HERE
public class Main {
    public static void main(String[] args) throws IOException {

       /** MISSION POWER GRID OPTIMIZATION BELOW **/
        System.out.println("##MISSION POWER GRID OPTIMIZATION##");
        // TODO: Your code goes here
        //ArrayList<Integer> demands = new ArrayList<>(Arrays.asList(10, 5, 8, 12, 6, 9));
       String fileName = args[0]; // Replace "input.txt" with your file name
        //String fileName = "C:\\Users\\gulve\\OneDrive\\Masaüstü\\java_projects\\4thTerm\\as2\\src\\demandSchedule.dat";
        ArrayList<Integer> demands = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line = br.readLine();
            if (line != null) {
                String[] numbers = line.split(" ");
                for (String number : numbers) {
                    demands.add(Integer.parseInt(number));
                }
            } else {
                System.out.println("The file is empty.");
            }
        } catch (IOException e) {
            System.err.println("Error reading from the file: " + e.getMessage());
        }

        PowerGridOptimization pGo = new PowerGridOptimization(demands);
        OptimalPowerGridSolution dP = pGo.getOptimalPowerGridSolutionDP();
        int maxHours = dP.getmaxNumberOfSatisfiedDemands();
        ArrayList<Integer> dischargeHours = dP.getHoursToDischargeBatteriesForMaxEfficiency();
        int total=0;
        for (int i = 0; i < demands.size(); i++) {
            total+=demands.get(i);
        }
        System.out.println("The total number of demanded gigawatts: "+total);
        System.out.println("Maximum number of satisfied gigawatts: "+maxHours);
        System.out.print("Hours at which the battery bank should be discharged:");
        for (int i =0;i<dischargeHours.size();i++) {
            if (i!=dischargeHours.size()-1){
                System.out.print(" "+dischargeHours.get(i)+",");
            }else {
                System.out.print(" "+dischargeHours.get(i));
            }
        }
        int a = total-maxHours;
        System.out.println("\nThe number of unsatisfied gigawatts: "+a);
        // You are expected to read the file given as the first command-line argument to read 
        // the energy demands arriving per hour. Then, use this data to instantiate a 
        // PowerGridOptimization object. You need to call getOptimalPowerGridSolutionDP() method
        // of your PowerGridOptimization object to get the solution, and finally print it to STDOUT.
        System.out.println("##MISSION POWER GRID OPTIMIZATION COMPLETED##");





        /** MISSION ECO-MAINTENANCE BELOW **/
        ArrayList<ArrayList<Integer>> lists = new ArrayList<>();
        //String filePath="C:\\Users\\gulve\\OneDrive\\Masaüstü\\java_projects\\4thTerm\\as2\\src\\ESVMaintenance.dat";
        String filePath = args[1];
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                ArrayList<Integer> numberList = new ArrayList<>();
                // Split each line into an array of strings
                String[] numbers = line.split("\\s+");
                // Convert each string to an integer and add it to the list
                for (String number : numbers) {
                    numberList.add(Integer.parseInt(number));
                }
                lists.add(numberList);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        System.out.println("##MISSION ECO-MAINTENANCE##");
       ArrayList<Integer> tasks =  new ArrayList<>(Arrays.asList(20 ,50, 40 ,70 ,10, 30, 80 ,100, 10));
        //ArrayList<Integer> tasks = lists.get(1);
        OptimalESVDeploymentGP esv = new OptimalESVDeploymentGP(tasks);
        int min = esv.getMinNumESVsToDeploy(lists.get(0).get(0),lists.get(0).get(1));
        if(min!=-1){
        System.out.println("The minimum number of ESVs to deploy: "+min);
        for (int i = 0; i < esv.getMaintenanceTasksAssignedToESVs().size(); i++) {
            if (!esv.getMaintenanceTasksAssignedToESVs().get(i).isEmpty()) {
                int taskNumber = i+1;
                System.out.print("ESV " + taskNumber+ " tasks: [");
                for (int j = 0; j < esv.getMaintenanceTasksAssignedToESVs().get(i).size(); j++) {
                    if ( j != esv.getMaintenanceTasksAssignedToESVs().get(i).size() - 1){
                        System.out.print(esv.getMaintenanceTasksAssignedToESVs().get(i).get(j) + ", ");
                    }else{ System.out.print(esv.getMaintenanceTasksAssignedToESVs().get(i).get(j));}

                }
                System.out.print("]");
                System.out.println();
            }
        }
        }else{
            System.out.println("Warning: Mission Eco-Maintenance Failed.");
        }
        // TODO: Your code goes here
        // You are expected to read the file given as the second command-line argument to read
        // the number of available EVs, the capacity of each available ESV, and the energy requirements
        // of the maintenance tasks. Then, use this data to instantiate an OptimalESVDeploymentGP object.
        // You need to call getMinNumESVsToDeploy(int maxNumberOfAvailableESVs, int maxESVCapacity) method
        // of your OptimalESVDeploymentGP object to get the solution, and finally print it to STDOUT.
        System.out.println("##MISSION ECO-MAINTENANCE COMPLETED##");
    }
}
