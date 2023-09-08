import java.io.IOException;
import java.util.Scanner;

public class Admin {
    Scanner scanner;
    RandomFlightFile randomFlightFile;
    private final String adminUsername;
    private final String adminPassword;

    public Admin(RandomFlightFile randomFlightFile) {
        this.scanner = new Scanner(System.in);
        this.adminUsername = "admin";
        this.adminPassword = "admin";
        this.randomFlightFile = randomFlightFile;
    }

    public String getAdminUsername() {
        return "admin";
    }

    public String getAdminPassword() {
        return "admin";
    }

    public void adminSignIn() {
        String testUsername;
        do {
            System.out.println("Please Enter Your Username ");
            System.out.print(">>");
            testUsername = this.scanner.next().toLowerCase().trim();
        } while (!testUsername.equals(this.getAdminUsername()));

        while (true) {
            System.out.println("Please Enter Your Password ");
            System.out.print(">>");
            String testPassword = this.scanner.next().toLowerCase().trim();
            if (testPassword.equals(this.getAdminPassword())) {
                return;
            }

            System.out.println("Entered Password Doesn't Match!");
        }
    }

    public void addFlight() throws IOException {
        this.randomFlightFile.randomAccessFile.seek(this.randomFlightFile.randomAccessFile.length());
        System.out.println("Flight Id?");
        System.out.print(">>");
        this.randomFlightFile.writeFlightToFile(this.scanner.next().toLowerCase().trim());
        System.out.println("Origin?");
        System.out.print(">>");
        this.randomFlightFile.writeFlightToFile(this.scanner.next().toLowerCase().trim());
        System.out.println("Destination?");
        System.out.print(">>");
        this.randomFlightFile.writeFlightToFile(this.scanner.next().toLowerCase().trim());
        System.out.println("Date?");
        System.out.print(">>");
        this.randomFlightFile.writeFlightToFile(this.scanner.next().toLowerCase().trim());
        System.out.println("Time?");
        System.out.print(">>");
        this.randomFlightFile.writeFlightToFile(this.scanner.next().toLowerCase().trim());
        System.out.println("Price?");
        System.out.print(">>");
        this.randomFlightFile.randomAccessFile.writeDouble(this.scanner.nextDouble());
        this.randomFlightFile.randomAccessFile.writeInt(100);
        System.out.println();
    }

    public void updateFlight() throws IOException {
        System.out.println("Please Enter The Flight Id?");
        System.out.print(">>");
        String flightId = this.scanner.next().toLowerCase().trim();
        int numberOfFlight = this.randomFlightFile.findFlight(flightId);
        if (numberOfFlight != 200) {
            this.randomFlightFile.randomAccessFile.seek((long) (numberOfFlight * 312));

            for (int i = numberOfFlight * 312; i < numberOfFlight * 312 + 312; ++i) {
                this.randomFlightFile.randomAccessFile.writeChars(" ");
            }

            this.randomFlightFile.randomAccessFile.seek((long) (numberOfFlight * 312));
            System.out.println("New Flight Id?");
            System.out.print(">>");
            this.randomFlightFile.writeFlightToFile(this.scanner.next().toLowerCase().trim());
            System.out.println("New Origin?");
            System.out.print(">>");
            this.randomFlightFile.writeFlightToFile(this.scanner.next().toLowerCase().trim());
            System.out.println("New Destination?");
            System.out.print(">>");
            this.randomFlightFile.writeFlightToFile(this.scanner.next().toLowerCase().trim());
            System.out.println("New Date?");
            System.out.print(">>");
            this.randomFlightFile.writeFlightToFile(this.scanner.next().toLowerCase().trim());
            System.out.println("New Time?");
            System.out.print(">>");
            this.randomFlightFile.writeFlightToFile(this.scanner.next().toLowerCase().trim());
            System.out.println("New Price?");
            System.out.print(">>");
            this.randomFlightFile.randomAccessFile.writeDouble(this.scanner.nextDouble());
            this.randomFlightFile.randomAccessFile.writeInt(100);
        } else {
            System.out.println("Sorry This Flight Id Doesn't Exist!");
        }

    }

    public void removeFlight() throws IOException {
        int length = (int) this.randomFlightFile.randomAccessFile.length() / 312;
        String str = "";
        System.out.println("Please Enter the Flight Id?");
        System.out.print(">>");
        String flightId = this.scanner.next();

        for (int i = 0; i < length; ++i) {
            str = "";
            int j = 312 * i;
            if (j < 312 * i + 60) {
                str = str + this.randomFlightFile.randomAccessFile.readChar();
                if (flightId.equals(str.trim())) {
                    this.randomFlightFile.randomAccessFile.seek((long) (312 * i));

                    for (int k = 312 * i; k < 312 * (i + 1); k += 2) {
                        this.randomFlightFile.randomAccessFile.writeChars(" ");
                    }
                }
            }

            if (i == length) {
                System.out.println("This flight id doesn't exist!");
            }
        }

    }

    public void showFlightsSchedules() throws IOException {
        int s = (int) this.randomFlightFile.randomAccessFile.length() / 312;
        this.randomFlightFile.randomAccessFile.seek(0L);

        int i;
        for (i = 0; i < s; ++i) {
            String str = "";
            System.out.println("Flight Id     |Origin        |Destination   |Date        |Time        |Seats   |Price     ");
            str = "";

            int remainedSeats;
            for (remainedSeats = 312 * i; remainedSeats < 312 * i + 60; remainedSeats += 2) {
                str = str + this.randomFlightFile.randomAccessFile.readChar();
            }

            System.out.print(str.trim());

            for (remainedSeats = 0; remainedSeats < 14 - str.trim().length(); ++remainedSeats) {
                System.out.print(" ");
            }

            System.out.print("|");
            str = "";

            for (remainedSeats = 312 * i + 0; remainedSeats < 312 * i + 60; remainedSeats += 2) {
                str = str + this.randomFlightFile.randomAccessFile.readChar();
            }

            System.out.print(str.trim());

            for (remainedSeats = 0; remainedSeats < 14 - str.trim().length(); ++remainedSeats) {
                System.out.print(" ");
            }

            System.out.print("|");
            str = "";

            for (remainedSeats = 312 * i + 0; remainedSeats < 312 * i + 60; remainedSeats += 2) {
                str = str + this.randomFlightFile.randomAccessFile.readChar();
            }

            System.out.print(str.trim());

            for (remainedSeats = 0; remainedSeats < 14 - str.trim().length(); ++remainedSeats) {
                System.out.print(" ");
            }

            System.out.print("|");
            str = "";

            for (remainedSeats = 312 * i + 0; remainedSeats < 312 * i + 60; remainedSeats += 2) {
                str = str + this.randomFlightFile.randomAccessFile.readChar();
            }

            System.out.print(str.trim());

            for (remainedSeats = 0; remainedSeats < 12 - str.trim().length(); ++remainedSeats) {
                System.out.print(" ");
            }

            System.out.print("|");
            str = "";

            for (remainedSeats = 312 * i + 0; remainedSeats < 312 * i + 60; remainedSeats += 2) {
                str = str + this.randomFlightFile.randomAccessFile.readChar();
            }

            System.out.print(str.trim());

            for (remainedSeats = 0; remainedSeats < 12 - str.trim().length(); ++remainedSeats) {
                System.out.print(" ");
            }

            System.out.print("|");
            this.randomFlightFile.randomAccessFile.seek((long) (312 * i + 308));
            remainedSeats = this.randomFlightFile.randomAccessFile.readInt();
            if (remainedSeats == 100) {
                System.out.print("" + remainedSeats + "     ");
            }

            if (remainedSeats < 100) {
                System.out.print("" + remainedSeats + "      ");
            }

            if (remainedSeats < 10) {
                System.out.print("" + remainedSeats + "       ");
            }

            System.out.print("|");
            this.randomFlightFile.randomAccessFile.seek((long) (312 * i + 300));
            System.out.print(this.randomFlightFile.randomAccessFile.readDouble());
            this.randomFlightFile.randomAccessFile.seek((long) (312 * (i + 1)));
            System.out.println();
        }

        if (i > s) {
            System.out.println("Sorry ! We couldn't find any Flights !");
        }

    }
}
