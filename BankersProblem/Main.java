
import java.util.Scanner;

/*
This class will run the program as well as handle all I/O with the user
*/
public class Main{
    public static void main(String args[]){
        Scanner scan = new Scanner(System.in);
        String input;
        int inputResources;
        int inputClients;
        
        System.out.print("Please input the number of bank resources: ");
        input = scan.nextLine();
        inputResources = Integer.parseInt(input);     
        System.out.println("");
        
        System.out.print("Please input the number of clients: ");
        input = scan.nextLine();
        inputClients = Integer.parseInt(input);
        System.out.println("");
    }
}