import java.io.FileWriter;
import java.io.IOException;

public class Ticket {
    private char row;
    private int seat, price;
    private Person person;

    public Ticket(char row, int seat, int price, Person person) {
        this.row = row;
        this.seat = seat;
        this.price = price;
        this.person = person;
    }

    public char getRow() {
        return row;
    }

    public void setRow(char row) {
        this.row = row;
    }

    public int getSeat() {
        return seat;
    }

    public void setSeat(int seat) {
        this.seat = seat;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    // Method to print the ticket information
    public void printTicketInformation() {
        System.out.println("\nTicket Information:");
        System.out.println("\tRow: " + getRow());
        System.out.println("\tSeat: " + getSeat());
        System.out.println("\tPrice: £" + getPrice());
        person.printPersonInformation();
    }

    // Method to save the ticket information to a text file
    public void save(){
        String fileName = "Passenger Ticket Information\\" + getRow() + getSeat() + ".txt";
        String ticketInfo = "Ticket Information for the Seat " + getRow() + getSeat() + "\n"+
                "----------------------------------\n" +
                "\tRow: " + getRow() + "\n" +
                "\tSeat: " + getSeat() + "\n" +
                "\tPrice £: " + getPrice() + "\n" +
                "\nPassenger Information:\n" +
                "\tFirst Name: " + person.getName() + "\n" +
                "\tSurname: " + person.getSurname() + "\n" +
                "\tEmail: " + person.getEmail() + "\n";
        try {
            FileWriter newFile = new FileWriter(fileName);
            newFile.write(ticketInfo);
            newFile.close();
            System.out.println("\nTicket information saved to file: " + fileName);
        } catch (IOException e){
            System.out.println("An error occurred while saving ticket information to file: " + e.getMessage());
        }
    }
}