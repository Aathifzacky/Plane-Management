import java.io.File;
import java.util.InputMismatchException;
import java.util.Scanner;

public class PlaneManagement {
    public static Scanner input = new Scanner(System.in);

    // Declaring a 2D array with 4 rows to representing the seating plan.
    public static int [][] seatingPlane = new int[4][];

    // An array to store sold tickets with maximum of 52 Ticket.
    private static final Ticket[] soldTickets = new Ticket[52];

    public static void main(String[] args) {
        // Initializing the seats per row in the array.
        seatingPlane[0] = new int[14];
        seatingPlane[1] = new int[12];
        seatingPlane[2] = new int[12];
        seatingPlane[3] = new int[14];

        System.out.println("\n   Welcome to the Plane Management application");
        display_menu_selection();
    }

    // Method to print the menu of the application
    private static void display_menu(){
        characters(50, '*');
        System.out.print("\n*");
        characters(18, ' ');
        System.out.print("MENU OPTIONS");
        characters(18, ' ');
        System.out.println("*");
        characters(50, '*');
        System.out.println();

        System.out.println("""
                \t1) Buy a seat
                \t2) Cancel a seat
                \t3) Find first available seat
                \t4) Show seating plan
                \t5) Print ticket information and total sales
                \t6) Search ticket
                \t0) Quit""");
        characters(50, '*');

        System.out.print("\nPlease select an option: ");
    }

    /* Method to display the menu and perform an action based on
    user input and repeats until user choose to exit.*/
    private static void display_menu_selection(){
        int option = -1;            // Initializing a value to option variable to enter the loop.
        do{
            // Display the menu options.
            display_menu();
                while (true) {
                    try {
                        // Read user input to perform an action.
                        option = input.nextInt();

                        switch (option) {
                            case 1:
                                // Perform action to buy a seat.
                                buy_seat();
                                // Ask user to continue to main menu or exit the program.
                                continue_program();
                                break;
                            case 2:
                                // Perform action to cancel a seat.
                                cancel_seat();
                                continue_program();
                                break;
                            case 3:
                                // Perform action to find first available seat.
                                find_first_available();
                                continue_program();
                                break;
                            case 4:
                                // Perform action to show seating plan.
                                show_seating_plan();
                                continue_program();
                                break;
                            case 5:
                                // Perform action to print ticket information.
                                print_tickets_info();
                                continue_program();
                                break;
                            case 6:
                                // Perform action to search for a ticket.
                                search_ticket();
                                continue_program();
                                break;
                            case 0:
                                System.out.println("Exiting the program...");
                                return;
                            default:
                                System.out.print("Invalid option. Please try again: ");
                                break;
                        }
                    } catch (InputMismatchException e) {
                        System.out.print("Enter a valid option: ");
                        // Clears the invalid input entered by user.
                        input.nextLine();
                    }
                }
        } while (option != 0);
        // Continue until the user chooses to exit.
    }

    private static void characters(int num, char character){
        for (int i = 0; i < num; i++) {
            System.out.print(character);
        }
    }

    private static void buy_seat(){
        int row, seatNumber;
        while (true){
            // Call the getRow method and assign it to row.
            row = getRow();
            // Call the getSeat method and assign it to seatNumber.
            seatNumber = getSeat(row);

            // Calculates the price based on the seat number.
            int price;
            if (seatNumber < 6){
                price = 200;
            } else if (seatNumber < 10) {
                price = 150;
            } else {
                price = 180;
            }

            // Checks the array if the selected seat is available.
            if (seatingPlane[row][seatNumber - 1] == 0){
                seatingPlane[row][seatNumber - 1] = 1;     // Mark the seat as booked.

                System.out.print("Enter your name: ");
                String name = input.next();
                System.out.print("Enter your surname: ");
                String surname = input.next();
                String email;
                // Check the entered email address is in the correct format.
                do {
                    System.out.print("Enter your email: ");
                    email = input.next();
                    if (!email.contains("@")){
                        System.out.println("Invalid email format.\nPlease enter a valid email (example@gmail.com).");
                    }
                } while (!email.contains("@"));

                // Creates a Person object with the entered details.
                Person person = new Person(name, surname, email);

                // Converting the row number to a character representing the row.
                char rowLetter = (char) (row + 65);

                // Creating a Ticket object with the seat details and person information.
                Ticket ticket = new Ticket(rowLetter, seatNumber, price, person);

                for (int i = 0; i < soldTickets.length; i++){
                    if (soldTickets[i] == null){
                        soldTickets[i] = ticket;
                        break;
                    }
                }

                System.out.println("\nSeat " + ticket.getRow() + ticket.getSeat() + " is booked successfully.");

                // Save the ticket information.
                ticket.save();
                break;      // Exit the loop after booking the seat.
            } else {
                System.out.println("Entered seat is already booked. Please try another seat.");
                String response;
                //Prompt user to book another seat or go back to menu
                do {
                    System.out.print("\nWould you like to book another seat (Yes\\No): ");
                    response = input.next().toUpperCase();
                    if (response.equals("YES")) {
                        buy_seat();
                    } else if (response.equals("NO")){
                        display_menu_selection();
                    } else {
                        System.out.println("Invalid response. Please enter 'YES' or 'NO'.");
                    }
                } while (!response.equals("YES") && !response.equals("NO"));
            }
        }
    }
    private static void cancel_seat(){
        int row, seatNumber;
        while (true){
            // Call the getRow method and assign it to row.
            row = getRow();
            // Call the getSeat method and assign it to seatNumber.
            seatNumber = getSeat(row);

            // Checks the array if the selected seat is already booked.
            if (seatingPlane[row][seatNumber - 1] == 1){
                seatingPlane[row][seatNumber - 1] = 0;      // Mark the seat as available.

                // Converting the row number to a character representing the row.
                char rowLetter = (char) (row + 65);

                /* Check array for rowLetter and seatNumber to match the
                entered cancellation seat and make the array empty.*/
                for (int i = 0; i < soldTickets.length; i++){
                    if (soldTickets[i] != null && soldTickets[i].getRow() == rowLetter && soldTickets[i].getSeat() == seatNumber){
                        soldTickets[i] = null;
                        System.out.println("\nSeat canceled successfully.");
                        // File name with the path.
                        String filename = "Passenger Ticket Information\\" + rowLetter + seatNumber + ".txt";
                        File file = new File(filename);
                        // Delete the ticket information.
                        file.delete();
                        System.out.println("Your ticket information is deleted.");
                    }
                }
                break;
            } else {
                System.out.println("\nEntered seat is not booked.");
                String response;
                // Prompt user to repeat the cancel process or go back to menu.
                do {
                    System.out.print("\nWould you like to cancel another seat? (Yes\\No): ");
                    response = input.next();
                } while (!response.equalsIgnoreCase("yes") && !response.equalsIgnoreCase("no"));
                if (response.equalsIgnoreCase("yes")) {
                    cancel_seat();
                }
                else {
                    System.out.println("Exiting seat cancellation process...\n");
                    display_menu_selection();
                }
            }
        }
    }

    private static void find_first_available() {
        char rowLetter = 'A';  // Initialize the row letter
        // Iterate through each row of the seatingPlane array.
        for (int[] seats : seatingPlane) {
            int seatNumber = 1; // Initialize the seat number for each row
            // Iterate through each seat in the current row.
            for (int seat : seats) {
                if (seat == 0) { // Checking if the current seat is available
                    System.out.println("First available seat is: " + rowLetter + " " + seatNumber);
                    return;
                }
                seatNumber++;
            }
            rowLetter++;
        }
        System.out.println("No available seats found.");
    }

    private static void show_seating_plan(){
        System.out.println("\nSeating plan for the plane.\n");

        // Print seat numbers
        System.out.print("\t");
        for (int i = 1; i <= 14; i++) {
            System.out.printf("%3d ", i);
        }
        System.out.println();
        characters(60, '-');
        System.out.println();

        // Iterate through each row of the seatingPlane array.
        char rowLetter = 'A';
        for (int[] seats : seatingPlane) {
            System.out.print("  " + rowLetter + "\t");      // Print the row letter.
            // Iterate through each seat in the current row.
            for (int seat : seats) {
                // Print 'O' for available seats and 'X' for booked seats.
                if (seat == 0) {
                    System.out.print("  O ");
                } else {
                    System.out.print("  X ");
                }
            }
            System.out.println();
            rowLetter++;
        }
        System.out.println();
    }

    private static void print_tickets_info(){
        int totalTicketsPrice = 0;

        characters(32, '_');
        System.out.println("\nInformation of Tickets sold are:");
        characters(32, '-');
        // Iterate through each ticket in the soldTickets array.
        for (Ticket ticket : soldTickets){
            // Check if the current ticket is sold
            if (ticket != null){
                ticket.printTicketInformation();
                totalTicketsPrice += ticket.getPrice();
                characters(30, '-');
            }
        }
        System.out.println("\nTotal amount of tickets sold: £" + totalTicketsPrice);
    }

    private static void search_ticket(){
        int row, seatNumber;
        // Call the getRow method and assign it to row.
        row = getRow();
        // Call the getSeat method and assign it to seatNumber.
        seatNumber = getSeat(row);

        row += 65;      // Convert the row letter to its corresponding ASCII.

        boolean found = false;
        // Iterate through each ticket in the soldTickets array
        for (Ticket ticket : soldTickets) {
            if (ticket != null && ticket.getRow() == row && ticket.getSeat() == seatNumber) {
                found = true;
                System.out.println("\nThe seat " + (char)(row) + seatNumber + " is already booked.");
                ticket.printTicketInformation();
                break;
            }
        }

        if (!found) {
            System.out.println("\nThe seat " + (char)(row) + seatNumber + " is available.");
        }
    }

    // Method to get the row as an input from the user.
    private static int getRow(){
        // Continuously prompt the user until a valid row letter is entered.
        while (true) {
            System.out.println();
            System.out.print("Enter row letter (A - D): ");
            String rowLetter = input.next().toUpperCase();

            if (rowLetter.length() > 1 || rowLetter.charAt(0) < 'A' || rowLetter.charAt(0) > 'D') {
                System.out.println("Invalid row.");
                continue;
            }

            /* Convert the row letter to its corresponding
            row index using a switch statement.*/
            int row = -1;
            switch (rowLetter){
                case "A":
                    row = 0;
                    break;
                case "B":
                    row = 1;
                    break;
                case "C":
                    row = 2;
                    break;
                case "D":
                    row = 3;
                    break;
            }
            return row;
            // Return the row index if a valid row letter is entered.
        }
    }

    // Method to get the seat number as an input from the user.
    private static int getSeat(int row){
        int seatNumber, maxSeat;
        // Determine the maximum seat number based on the row index.
        maxSeat = row == 0 || row == 3 ? 14 : 12;
        System.out.print("Enter seat number (1 - " + maxSeat + "): ");

        // Continuously prompt the user until a valid seat number is entered.
        while (true) {
            try {
                seatNumber = input.nextInt();

                if (seatNumber < 1 || seatNumber > maxSeat) {
                    System.out.print("Invalid seat number.\nPlease enter a valid seat number (1 - " + maxSeat + " ): ");
                } else {
                    break;      // If the entered seat number is valid, break the loop.
                }
            } catch (InputMismatchException e){
                System.out.print("Please enter a valid seat number (1 - " + maxSeat + " ):");
                input.nextLine();       // Clear the input buffer.
            }
        }
        return seatNumber;          // Return the valid seat number entered by the user.
    }

    // Method to prompt after every action to go back to menu or quit the program.
    private static void continue_program(){
        int response;
        do {
            System.out.print("""
                    \nEnter 1 to go back to menu.
                    Enter 0 to quit the program.
                    \nEnter Your Response:\s""");
            // Continuously prompt the user until an integer is entered.
            while (true) {
                try {
                    response = input.nextInt();
                    System.out.println();
                    break;
                } catch (InputMismatchException e) {
                    System.out.print("Please enter a valid number: ");
                    input.nextLine();       // Clear the input buffer.
                }
            }
            if (response != 1 && response != 0) {
                System.out.println("Invalid response.");
            }
        } while (response != 1 && response != 0);

        if (response == 1) {
            // If the response is 1, go back to the main menu
            display_menu_selection();
        }else {
            // If the response is 0, terminate the program.
            System.out.println("Program is quitting....");
            System.exit(0);
        }
    }
}