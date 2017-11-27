
import java.util.Random;

/*
This class will represent the Client by requesting and releasing the resources from the bank
This will also be the class that extends/implements Thread
Each max client need will be randomly generated
 */
public class Client extends Thread {

    int id;
    Bank bank;
    int requestsMade;//must be at least three to end

    public Client(int i, Bank b) {
        id = i;
        bank = b;
        requestsMade = 0;
    }

    @Override
    public void run() {
        int count = 0;
        Random rand = new Random();
        int num;
        int request[] = new int[bank.numberOfResources];

        //have each client run 3 times
        while (count < 3) {
            //set a random request amount
            for (int i = 0; i < bank.numberOfResources; i++) {
                request[i] = rand.nextInt(bank.need[id][i] + 1);
            }

            //try to make a request
            if (!bank.requestResources(id, request, count)) {
                Thread.yield();
            }

            num = 1 + rand.nextInt(5);

            //sleep 1 to 5 sec
            try {
                Thread.sleep(num * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            count++;
        }

        bank.releaseResources(id);
    }
}
