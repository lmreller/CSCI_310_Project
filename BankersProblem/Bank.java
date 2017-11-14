
import java.util.Random;

/*
This class will represent the bank by holding the resources
Each max resource amount will be randomly generated
 */
public class Bank {

    int numberOfClients;
    int numberOfResources;

    int[] available;    //available amount of resources
    int need[][];       //current needs of each client
    int[][] maximum;    // the maximum demand of each client
    int[][] allocation; // the amount currently allocated to each client
    int[] work;         // the current amount of resources available
    boolean isGranted[];// array of booleans that represent if a clients request is granted
    boolean finish[];   // array of booleans that represent if each request allow the program to finish, i.e. no deadlock
    int[] sequence;     // the safe sequence found from the safety algorithm

    public Bank(int resources, int clients) {
        numberOfClients = clients;
        numberOfResources = resources;

        // Initialize arrays
        this.allocation = new int[numberOfClients][numberOfResources];
        this.available = new int[numberOfResources];
        this.maximum = new int[numberOfClients][numberOfResources];
        this.need = new int[numberOfClients][numberOfResources];
        this.isGranted = new boolean[numberOfClients];
        this.finish = new boolean[numberOfClients];
        this.work = new int[numberOfResources];
        this.sequence = new int[numberOfClients];

        populateArrays(numberOfClients, numberOfResources);
    }

    
    
    private void populateArrays(int numCustomers, int numResources) {
        Random rand = new Random();
        int maxValue = rand.nextInt(21); // 0 to 20

        for (int i = 0; i < numberOfResources; i++) {
            // Randomly assign number of total resources
            available[i] = maxValue;
            maxValue = rand.nextInt(21);
        }

        for (int i = 0; i < numCustomers; i++) {
            for (int j = 0; j < numResources; j++) {
                maxValue = rand.nextInt(available[j] + 1);
                maximum[i][j] = maxValue;
                need[i][j] = maxValue;
            }
        }
    }
}
