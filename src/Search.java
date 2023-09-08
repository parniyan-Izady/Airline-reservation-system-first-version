import java.io.IOException;
import java.util.Scanner;

public class Search {
    Scanner scanner;
    Flights flights;
    RandomFlightFile randomFlightFile;

    public Search(RandomFlightFile randomFlightFile) {
        this.scanner = new Scanner(System.in);
        this.flights = new Flights();
        this.randomFlightFile = randomFlightFile;
    }

    public void copyInformation() throws IOException {
        String str = "";
        int length = (int) this.randomFlightFile.randomAccessFile.length() / 312;
        this.randomFlightFile.randomAccessFile.seek(0L);

        for (int i = 0; i < length; ++i) {
            str = "";
            this.flights.flight[i] = new Flight();

            int j;
            for (j = 312 * i; j < 312 * i + 60; j += 2) {
                str = str + this.randomFlightFile.randomAccessFile.readChar();
            }

            this.flights.flight[i].setFlightId(str.trim());
            str = "";

            for (j = 312 * i + 60; j < 312 * i + 120; j += 2) {
                str = str + this.randomFlightFile.randomAccessFile.readChar();
            }

            this.flights.flight[i].setOrigin(str.trim());
            str = "";

            for (j = 312 * i + 120; j < 312 * i + 180; j += 2) {
                str = str + this.randomFlightFile.randomAccessFile.readChar();
            }

            this.flights.flight[i].setDestination(str.trim());
            str = "";

            for (j = 312 * i + 180; j < 312 * i + 240; j += 2) {
                str = str + this.randomFlightFile.randomAccessFile.readChar();
            }

            this.flights.flight[i].setDate(str.trim());
            str = "";

            for (j = 380 * i + 240; j < 380 * i + 300; j += 2) {
                str = str + this.randomFlightFile.randomAccessFile.readChar();
            }

            this.flights.flight[i].setTime(str.trim());
            this.flights.flight[i].setPrice(this.randomFlightFile.randomAccessFile.readDouble());
            this.flights.flight[i].setRemainedSeats(this.randomFlightFile.randomAccessFile.readInt());
        }

    }

    public void searchFlightTicket() {
        System.out.println("Enter 0 if you don not need this option!");
        System.out.println();
        System.out.println("Desired origin?");
        System.out.print(">>");
        String testOrigin = this.scanner.next();
        if (!testOrigin.equals("0")) {
            for (int i = 0; i < 100; ++i) {
                if (this.flights.flight[i] != null && !this.flights.flight[i].getOrigin().equals(testOrigin)) {
                    this.flights.flight[i] = null;
                }
            }
        }

        System.out.println("Desired destination?");
        System.out.print(">>");
        String testDestination = this.scanner.next();
        if (!testDestination.equals("0")) {
            for (int i = 0; i < 100; ++i) {
                if (this.flights.flight[i] != null && !this.flights.flight[i].getDestination().equals(testDestination)) {
                    this.flights.flight[i] = null;
                }
            }
        }

        System.out.println("Desired Date?");
        System.out.print(">>");
        String testDate = this.scanner.next();
        if (!testDate.equals("0")) {
            for (int i = 0; i < 100; ++i) {
                if (this.flights.flight[i] != null && !this.flights.flight[i].getDate().equals(testDate)) {
                    this.flights.flight[i] = null;
                }
            }
        }

        System.out.println("Desired Time?");
        System.out.print(">>");
        String testTime = this.scanner.next();
        if (!testTime.equals("0")) {
            for (int i = 0; i < 100; ++i) {
                if (this.flights.flight[i] != null && !this.flights.flight[i].getTime().equals(testTime)) {
                    this.flights.flight[i] = null;
                }
            }
        }

        System.out.println("Desired Price?");
        System.out.print(">>");
        double testPrice = this.scanner.nextDouble();
        if (testPrice != 0.0) {
            for (int i = 0; i < 100; ++i) {
                if (this.flights.flight[i] != null && !(this.flights.flight[i].getPrice() <= testPrice)) {
                    this.flights.flight[i] = null;
                }
            }
        }

        int p = 0;

        for (int i = 0; i < 100; ++i) {
            if (this.flights.flight[i] != null) {
                System.out.println("Flight Id    |Origin       |Destination  |Date        |Time    |seat |Price  ");
                this.showSearchedTicket(i);
                p = 4;
            }
        }

        if (p == 0) {
            System.out.println("Sorry! We couldn't Find Any flights !");
        }

    }

    public void showSearchedTicket(int i) {
        System.out.print(this.flights.flight[i].getFlightId());

        int p;
        for (p = 0; p < 13 - this.flights.flight[i].getFlightId().length(); ++p) {
            System.out.print(" ");
        }

        System.out.print("|");
        System.out.print(this.flights.flight[i].getOrigin());

        for (p = 0; p < 13 - this.flights.flight[i].getOrigin().length(); ++p) {
            System.out.print(" ");
        }

        System.out.print("|");
        System.out.print(this.flights.flight[i].getDestination());

        for (p = 0; p < 13 - this.flights.flight[i].getDestination().length(); ++p) {
            System.out.print(" ");
        }

        System.out.print("|");
        System.out.print(this.flights.flight[i].getDate());

        for (p = 0; p < 12 - this.flights.flight[i].getDate().length(); ++p) {
            System.out.print(" ");
        }

        System.out.print("|");
        System.out.print(this.flights.flight[i].getTime());

        for (p = 0; p < 8 - this.flights.flight[i].getTime().length(); ++p) {
            System.out.print(" ");
        }

        System.out.print("|");
        Flight var10001 = this.flights.flight[i];
        System.out.print(var10001.getRemainedSeats() + "  ");
        if (this.flights.flight[i].getRemainedSeats() < 100) {
            System.out.print(" ");
        }

        System.out.print("|");
        System.out.print(this.flights.flight[i].getPrice());
        System.out.println();
    }
}
