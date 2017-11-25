
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

    public void printAvailable() {
        System.out.println("Printing Available:");
        System.out.print("[");
        for (int i = 0; i < numberOfResources; i++) {
            if (i != numberOfResources - 1) {
                System.out.print(available[i] + " ");
            } else {
                System.out.print(available[i]);
            }
        }
        System.out.println("]");
    }

    public void printAllocation() {
        System.out.println("Printing Allocation:");
        for (int i = 0; i < numberOfClients; i++) {
            System.out.print("[");
            for (int j = 0; j < numberOfResources; j++) {
                if (j != numberOfResources - 1) {
                    System.out.print(allocation[i][j] + " ");
                } else {
                    System.out.print(allocation[i][j]);
                }
            }
            System.out.println("]");
        }
    }

    public void printMax() {
        System.out.println("Printing Maximum:");

        for (int i = 0; i < numberOfClients; i++) {
            System.out.print("[");
            for (int j = 0; j < numberOfResources; j++) {
                if (j != numberOfResources - 1) {
                    System.out.print(maximum[i][j] + " ");
                } else {
                    System.out.print(maximum[i][j]);
                }
            }
            System.out.println("]");
        }
    }

    public synchronized void printRequest(int id, int[] req) {
        System.out.println("Customer " + id + " is making a request: ");
        System.out.print("[");
        for (int i = 0; i < numberOfResources; i++) {
            if (i != numberOfResources - 1) {
                System.out.print(req[i] + " ");
            } else {
                System.out.print(req[i]);
            }
        }
        System.out.println("]");
    }

    private synchronized boolean finished() {
        for (int i = 0; i < numberOfClients; i++) {
            if (!finish[i]) {
                return false;
            }
        }
        return true;
    }

    public synchronized boolean requestResources(int id, int[] req, int amount) {
        return false;
    }

    public synchronized boolean releaseResources(int id) {
        return false;
    }

    public synchronized boolean safetyAlgorithm(int id) {
        for (int j = 0; j < numberOfResources; j++) {
            if (available[j] < need[id][j]) {
                return false;
            }
        }
        return true;
    }

    private void populateArrays(int numCustomers, int numResources) {
        Random rand = new Random();
        int max = rand.nextInt(21);

        for (int i = 0; i < numberOfResources; i++) {
            available[i] = max;
            max = rand.nextInt(21);
        }

        for (int i = 0; i < numCustomers; i++) {
            for (int j = 0; j < numResources; j++) {
                max = rand.nextInt(available[j] + 1);
                maximum[i][j] = max;
                need[i][j] = max;
            }
        }
    }
}
