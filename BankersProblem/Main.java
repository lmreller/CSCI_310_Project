
import java.util.Scanner;

/*
This class will run the program as well as handle all I/O with the user
 */
public class Main {

    public static void main(String args[]) {
        int inputResources = 0;
        int inputClients = 0;

        Bank bank;
        Client clients[];

        Scanner scan = new Scanner(System.in);
        String input;
        Boolean isValid = false;
        while (!isValid) {
            System.out.print("Please input the number of bank resources (between 1-10): ");
            input = scan.nextLine();
            inputResources = Integer.parseInt(input);
            System.out.println("");

            System.out.print("Please input the number of clients (between 1-10): ");
            input = scan.nextLine();
            inputClients = Integer.parseInt(input);
            System.out.println("");

            if ((inputResources > 0 && inputResources <= 10) && (inputClients > 0 && inputClients <= 10)) {
                isValid = true;
            } else {
                System.out.println("Invalid input, please input values between 1 and 10");
            }
        }

        bank = new Bank(inputResources, inputClients);
        clients = new Client[inputClients];

        bank.printAvailable();
        bank.printAllocation();
        bank.printMax();

        for (int i = 0; i < inputClients; i++) {
            clients[i] = new Client(i, bank);
            clients[i].start();
        }

        try {
            for (int i = 0; i < inputClients; i++) {
                clients[i].join();
            }
        } catch (InterruptedException e) {
        }

        System.out.println("Ending Available Vector:");
        bank.printAvailable();
        System.out.println("Ending Allocation Matrix:");
        bank.printAllocation();

        return;
    }
}
