
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
    int seqIndex = 0;     // helper for sequence

    public Bank(int resources, int clients) {
        numberOfClients = clients;
        numberOfResources = resources;

        this.allocation = new int[numberOfClients][numberOfResources];
        this.available = new int[numberOfResources];
        this.maximum = new int[numberOfClients][numberOfResources];
        this.need = new int[numberOfClients][numberOfResources];
        this.isGranted = new boolean[numberOfClients];
        this.finish = new boolean[numberOfClients];
        this.work = new int[numberOfResources];
        this.sequence = new int[numberOfClients];

        createArrays(numberOfClients, numberOfResources);
    }

    public void printAvailable() {
        System.out.println("Printing Initial/Final Available:");
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
        System.out.println("Printing Maximum Amounts:");

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

    public synchronized boolean requestResources(int id, int[] req, int amount) {
        isGranted[id] = true;
        System.out.println("Client " + id + " making request: ");
        System.out.print("[");
        for (int i = 0; i < numberOfResources; i++) {
            if (i != numberOfResources - 1) {
                System.out.print(req[i] + " ");
            } else {
                System.out.print(req[i]);
            }
        }
        System.out.println("]");

        for (int i = 0; i < numberOfResources; i++) {
            if (req[i] <= need[id][i]) {
                if (req[i] > available[i]) {
                    isGranted[id] = false;
                    break;
                }
            } else {
                System.out.println("error");
                System.exit(1);
            }
        }

        if (isGranted[id]) {
            for (int i = 0; i < numberOfResources; i++) {

                available[i] -= req[i];

                allocation[id][i] += req[i];
                
                need[id][i] -= req[i];
            }
        } else {
            System.out.println("Not enough resources");
            try {
                wait();
            } catch (InterruptedException e) {
            }

            return isGranted[id];
        }

        isGranted[id] = safetyAlgorithm(id);

        if (!isGranted[id]) {

            for (int j = 0; j < numberOfResources; j++) {
                available[j] += req[j];
                allocation[id][j] -= req[j];
                need[id][j] += req[j];
            }

            System.out.println("Client " + id + " must wait");

            try {
                wait();
            } catch (InterruptedException e) {
            }

            return isGranted[id];
        }
        System.out.println("Safe Seq:");
        System.out.print("[");
        for (int i = 0; i < sequence.length; i++) {
            if (i != sequence.length - 1) {
                System.out.print(sequence[i] + " ");
            } else {
                System.out.print(sequence[i]);
            }
        }
        System.out.println("]");

        printAllocation();
        System.out.println("Client " + id + " request " + amount + " granted");
        notifyAll();
        return isGranted[id];
    }

    public synchronized void releaseResources(int id) {
        System.out.println("Client " + id + " releasing resources:");
        System.out.print("[");
        for (int i = 0; i < numberOfResources; i++) {
            if (i != numberOfResources - 1) {
                System.out.print(allocation[id][i] + " ");
            } else {
                System.out.print(allocation[id][i]);
            }

            available[i] += allocation[id][i];
            allocation[id][i] = 0;
            need[id][i] = maximum[id][i];
        }
        System.out.println("]");
        notifyAll();
    }

    public synchronized boolean safetyAlgorithm(int id) {
        try {
            for (int j = 0; j < numberOfResources; j++) {
                if (available[j] <= need[id][j]) {
                    return false;
                }
            }
            sequence[seqIndex] = id;
            ++seqIndex;


        }catch(Exception e){
            System.out.println("No Safe Sequence");
        }
        return true;
    }



    private void createArrays(int numClients, int numResources) {
        Random rand = new Random();
        int max = rand.nextInt(21);

        for (int i = 0; i < numberOfResources; i++) {
            available[i] = max;
            max = rand.nextInt(21);
        }

        for (int i = 0; i < numClients; i++) {
            for (int j = 0; j < numResources; j++) {
                max = rand.nextInt(available[j] + 1);
                maximum[i][j] = max;
                need[i][j] = max;
            }
        }
    }
}
