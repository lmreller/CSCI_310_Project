/*
This class will represent the Client by requesting and releasing the resources from the bank
This will also be the class that extends/implements Thread
Each max clint need will be randomly generated
 */

public class Client extends Thread {

    int id;
    Bank bank;
    int requestsMade;//must be at least three by the end of execution
    
    public Client(int i, Bank b) {
        id = i;
        bank = b;
        requestsMade = 0;
    }

    public void run() {

    }
}
