import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class Menus {
    Scanner scanner;
    Flights flights;
    RandomFlightFile randomFlightFile;
    RandomPassengerFile randomPassengerFile;
    RandomTicketFile randomTicketFile;
    Passengers passengers;
    Admin admin;

    public Menus() throws FileNotFoundException {
        this.scanner = new Scanner(System.in);
        this.flights = new Flights();
        this.randomFlightFile = new RandomFlightFile(this.flights);
        this.randomPassengerFile = new RandomPassengerFile();
        this.randomTicketFile = new RandomTicketFile();
        this.passengers = new Passengers(this.randomPassengerFile, this.randomTicketFile, this.randomFlightFile);
        this.admin = new Admin(this.randomFlightFile);
    }

    public void menu() throws IOException {
        label89:
        while (true) {
            int a = 10;
            int b = 20;
            System.out.println();
            this.showFirstMenu();
            System.out.print(">>");
            a = this.scanner.nextInt();
            int y;
            if (a != 1) {
                if (a == 2) {
                    this.admin.adminSignIn();
                    y = 10;

                    while (y != 0) {
                        this.showAdminMenuOptions();
                        System.out.print(">>");
                        y = this.scanner.nextInt();
                        if (y == 1) {
                            this.admin.addFlight();
                        } else if (y == 2) {
                            this.admin.updateFlight();
                        } else if (y == 3) {
                            this.admin.removeFlight();
                        } else if (y == 4) {
                            this.admin.showFlightsSchedules();
                        }
                    }
                }
            } else {
                while (true) {
                    label63:
                    while (true) {
                        if (b == 0) {
                            continue label89;
                        }

                        System.out.println();
                        this.showEnterPassengerMenu();
                        System.out.print(">>");
                        b = this.scanner.nextInt();
                        if (b == 1) {
                            for (y = this.passengers.passengerSignIn(); y == 200; y = this.passengers.passengerSignIn()) {
                                System.out.println("This account doesn't exist");
                            }

                            int s = 20;

                            while (true) {
                                while (true) {
                                    if (s == 0) {
                                        continue label63;
                                    }

                                    this.showPassengerMenuOptions();
                                    System.out.print(">>");
                                    s = this.scanner.nextInt();
                                    if (s == 1) {
                                        System.out.println("Please Enter Your New Password");
                                        System.out.print(">>");
                                        this.randomPassengerFile.randomAccessFile.seek((long) (128 * y + 60));

                                        for (int i = 128 * y + 60; i < 128 * y + 120; i += 2) {
                                            this.randomPassengerFile.randomAccessFile.writeChars(" ");
                                        }

                                        this.randomPassengerFile.randomAccessFile.seek((long) (128 * y + 60));
                                        this.randomPassengerFile.randomAccessFile.writeChars(this.scanner.next().toLowerCase().trim());
                                        System.out.println("Your Password Changed !");
                                    } else if (s == 2) {
                                        Search search = new Search(this.randomFlightFile);
                                        search.copyInformation();
                                        search.searchFlightTicket();
                                    } else if (s == 3) {
                                        this.passengers.bookingTicket(y);
                                    } else if (s == 4) {
                                        this.passengers.ticketCancellation(y);
                                    } else if (s == 5) {
                                        this.passengers.showBookedTickets(y);
                                    } else if (s == 6) {
                                        this.passengers.addCharge(y);
                                    }
                                }
                            }
                        } else if (b == 2) {
                            this.passengers.passengerSignUp();
                        }
                    }
                }
            }
        }
    }

    public void showFirstMenu() {
        System.out.println();
        System.out.println("""
                -------------------------------------
                WELCOME TO AIRLINE RESERVATION SYSTEM
                -------------------------------------
                1> Passenger
                2> Admin
                -------------------------------------""");
    }

    public void showEnterPassengerMenu() {
        System.out.println();
        System.out.println("""
                -------------------------------------
                          Passenger Menu
                -------------------------------------
                1> Sign in
                2> Sign up
                0> Return to previous menu
                -------------------------------------""");
    }

    public void showAdminMenuOptions() {
        System.out.println();
        System.out.println("""
                -------------------------------------
                            Admin Menu
                -------------------------------------
                1> Add
                2> Update
                3> Remove
                4> Flight Schedules
                0> Sign out
                -------------------------------------""");
    }

    public void showPassengerMenuOptions() {
        System.out.println();
        System.out.println("""
                -------------------------------------
                         Passenger Menu
                -------------------------------------
                1> Change Password
                2> Search Flight Ticket
                3> Booking Ticket
                4> Ticket Cancellation
                5> Booked Tickets
                6> Add charge
                0> Sign out
                -------------------------------------""");
    }
}
