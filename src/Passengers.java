import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

public class Passengers {
    Scanner scanner;
    RandomPassengerFile randomPassengerFile;
    RandomTicketFile randomTicketFile;
    RandomFlightFile randomFlightFile;
    Random random;

    public Passengers(RandomPassengerFile randomPassengerFile, RandomTicketFile randomTicketFile, RandomFlightFile randomFlightFile) {
        this.scanner = new Scanner(System.in);
        this.random = new Random();
        this.randomPassengerFile = randomPassengerFile;
        this.randomTicketFile = randomTicketFile;
        this.randomFlightFile = randomFlightFile;
    }

    public void passengerSignUp() throws IOException {
        int length = (int) this.randomPassengerFile.randomAccessFile.length();
        this.randomPassengerFile.randomAccessFile.seek((long) length);
        System.out.println("Create Your Username");
        System.out.print(">>");
        this.randomPassengerFile.writePassengerToFile(this.scanner.next());
        System.out.println("Create Your Password");
        System.out.print(">>");
        this.randomPassengerFile.writePassengerToFile(this.scanner.next());
        this.randomPassengerFile.randomAccessFile.writeDouble(0.0);
    }

    public int passengerSignIn() throws IOException {
        int length = (int) this.randomPassengerFile.randomAccessFile.length() / 128;
        this.randomPassengerFile.randomAccessFile.seek(0L);
        System.out.println("Please Enter Your Username");
        System.out.print(">>");
        String testUsername = this.scanner.next();
        System.out.println("Please Enter Your Password");
        System.out.print(">>");
        String testPassword = this.scanner.next();

        for (int y = 0; y < length; ++y) {
            String password = "";
            String username = "";
            this.randomPassengerFile.randomAccessFile.seek((long) (128 * y));

            int i;
            for (i = 128 * y; i < 128 * y + 60; i += 2) {
                username = username + this.randomPassengerFile.randomAccessFile.readChar();
            }

            for (i = 128 * y + 60; i < 128 * y + 120; i += 2) {
                password = password + this.randomPassengerFile.randomAccessFile.readChar();
            }

            if (testUsername.equals(username.trim()) && testPassword.equals(password.trim())) {
                return y;
            }
        }

        return 200;
    }

    public void bookingTicket(int passengerNumber) throws IOException {
        System.out.println("Please Enter Desired Flight Id ");
        System.out.print(">>");
        String flightId = this.scanner.next().toLowerCase().trim();
        int length = (int) this.randomFlightFile.randomAccessFile.length() / 312;
        String str = "";

        int i;
        for (i = 0; i < length; ++i) {
            this.randomFlightFile.randomAccessFile.seek((long) (312 * i));
            str = "";

            for (int j = 312 * i; j < 312 * i + 60; j += 2) {
                str = str + this.randomFlightFile.randomAccessFile.readChar();
            }

            if (flightId.equals(str.trim())) {
                this.addBookedTicket(passengerNumber, i, str);
                break;
            }
        }

        if (i == length) {
            System.out.println("Sorry! We Couldn't Find Any Flights !");
        }

    }

    public void addBookedTicket(int passengerNumber, int flightNumber, String flightId) throws IOException {
        this.randomPassengerFile.randomAccessFile.seek((long) (128 * passengerNumber + 120));
        this.randomFlightFile.randomAccessFile.seek((long) (312 * flightNumber + 300));
        if (this.randomPassengerFile.randomAccessFile.readDouble() >= this.randomFlightFile.randomAccessFile.readDouble()) {
            String str = "";
            this.randomTicketFile.randomAccessFile.seek(this.randomTicketFile.randomAccessFile.length());
            int seatNumber = this.random.nextInt(100) + 1;
            this.randomTicketFile.writeTicketToFile("" + seatNumber + flightId);
            this.randomTicketFile.randomAccessFile.writeChars(flightId);
            this.randomFlightFile.randomAccessFile.seek((long) (flightNumber * 312 + 60));
            str = "";

            int i;
            for (i = flightNumber * 312 + 60; i < flightNumber * 312 + 120; i += 2) {
                str = str + this.randomFlightFile.randomAccessFile.readChar();
            }

            this.randomTicketFile.randomAccessFile.writeChars(str);
            str = "";

            for (i = flightNumber * 312 + 120; i < flightNumber * 312 + 180; i += 2) {
                str = str + this.randomFlightFile.randomAccessFile.readChar();
            }

            this.randomTicketFile.randomAccessFile.writeChars(str);
            str = "";

            for (i = flightNumber * 312 + 180; i < flightNumber * 312 + 240; i += 2) {
                str = str + this.randomFlightFile.randomAccessFile.readChar();
            }

            this.randomTicketFile.randomAccessFile.writeChars(str);
            str = "";

            for (i = flightNumber * 312 + 240; i < flightNumber * 312 + 300; i += 2) {
                str = str + this.randomFlightFile.randomAccessFile.readChar();
            }

            this.randomTicketFile.randomAccessFile.writeChars(str);
            this.randomPassengerFile.randomAccessFile.seek((long) (passengerNumber * 128));
            str = "";

            for (i = passengerNumber * 128; i < passengerNumber * 128 + 60; i += 2) {
                str = str + this.randomPassengerFile.randomAccessFile.readChar();
            }

            this.randomTicketFile.randomAccessFile.writeChars(str);
            this.randomFlightFile.randomAccessFile.seek((long) (312 * flightNumber + 300));
            double flightPrice = this.randomFlightFile.randomAccessFile.readDouble();
            this.randomTicketFile.randomAccessFile.writeDouble(flightPrice);
            this.randomTicketFile.randomAccessFile.writeInt(seatNumber);
            this.randomPassengerFile.randomAccessFile.seek((long) (128 * passengerNumber + 120));
            double lastCharge = this.randomPassengerFile.randomAccessFile.readDouble();
            this.randomPassengerFile.randomAccessFile.seek((long) (128 * passengerNumber + 120));
            this.randomPassengerFile.randomAccessFile.writeDouble(lastCharge - flightPrice);
            this.randomFlightFile.randomAccessFile.seek((long) (312 * flightNumber + 308));
            int remainedSeats = this.randomFlightFile.randomAccessFile.readInt();
            this.randomFlightFile.randomAccessFile.seek((long) (312 * flightNumber + 308));
            this.randomFlightFile.randomAccessFile.writeInt(remainedSeats - 1);
            System.out.println("Your Ticket Has Been Reserved! ");
        } else {
            System.out.println("Your Charge Is Not Enough");
        }

    }

    public void ticketCancellation(int passengerNumber) throws IOException {
        int length = (int) (this.randomTicketFile.randomAccessFile.length() / 432L);
        System.out.println("Please Enter your Ticket ID");
        System.out.print(">>");
        String ticketId = this.scanner.next().toLowerCase().trim();
        String str = "";

        int i;
        for (i = 0; i < length; ++i) {
            this.randomTicketFile.randomAccessFile.seek((long) (432 * i));
            str = "";

            for (int j = 432 * i; j < 432 * i + 60; j += 2) {
                str = str + this.randomTicketFile.randomAccessFile.readChar();
            }

            if (str.trim().equals(ticketId)) {
                this.randomTicketFile.randomAccessFile.seek((long) (432 * i + 420));
                double ticketPrice = this.randomTicketFile.randomAccessFile.readDouble();
                this.removeBookedFlight(i, passengerNumber, ticketPrice);
                this.randomTicketFile.randomAccessFile.seek((long) (432 * i));

                for (int j = 0; j < 432; j += 2) {
                    this.randomTicketFile.randomAccessFile.writeChars(" ");
                }

                System.out.println("Your Ticket Has Been Canceled! ");
                break;
            }
        }

        if (i == length) {
            System.out.println("Sorry ! This Ticket Id Doesn't Exist");
        }

    }

    public void removeBookedFlight(int ticketNumber, int passengerNumber, double flightPrice) throws IOException {
        this.randomPassengerFile.randomAccessFile.seek((long) (passengerNumber * 128 + 120));
        double passengerCharge = this.randomPassengerFile.randomAccessFile.readDouble();
        this.randomPassengerFile.randomAccessFile.seek((long) (passengerNumber * 128 + 120));
        int newPassengerCharge = (int) (passengerCharge + flightPrice);
        this.randomPassengerFile.randomAccessFile.writeDouble((double) newPassengerCharge);
        System.out.println("Your Charge Is " + newPassengerCharge + " !");
        this.randomTicketFile.randomAccessFile.seek((long) (ticketNumber * 432 + 60));
        String flightId = "";

        int i;
        for (i = ticketNumber * 432 + 60; i < ticketNumber * 432 + 120; i += 2) {
            flightId = flightId + this.randomTicketFile.randomAccessFile.readChar();
        }

        flightId = flightId.trim();
        i = this.randomFlightFile.findFlight(flightId);
        this.randomFlightFile.randomAccessFile.seek((long) (312 * i + 308));
        int remainedSeatNumber = this.randomFlightFile.randomAccessFile.readInt();
        ++remainedSeatNumber;
        this.randomFlightFile.randomAccessFile.seek((long) (312 * i + 308));
        this.randomFlightFile.randomAccessFile.writeInt(remainedSeatNumber);
    }

    public void addCharge(int passengerNumber) throws IOException {
        System.out.println("Please Enter The Desired Amount ");
        System.out.print(">>");
        double addedCharge = this.scanner.nextDouble();
        this.randomPassengerFile.randomAccessFile.seek((long) (128 * passengerNumber + 120));
        double lastCharge = this.randomPassengerFile.randomAccessFile.readDouble();
        this.randomPassengerFile.randomAccessFile.seek((long) (128 * passengerNumber + 120));
        this.randomPassengerFile.randomAccessFile.writeDouble(addedCharge + lastCharge);
        this.randomPassengerFile.randomAccessFile.seek((long) (128 * passengerNumber + 120));
        System.out.println("Your Charge Is " + this.randomPassengerFile.randomAccessFile.readDouble() + "!");
    }

    public void showBookedTickets(int passengerNumber) throws IOException {
        String str = "";
        String passengerUsername = "";
        this.randomPassengerFile.randomAccessFile.seek((long) (128 * passengerNumber));

        int length;
        for (length = 0; length < 30; ++length) {
            passengerUsername = passengerUsername + this.randomPassengerFile.randomAccessFile.readChar();
        }

        length = (int) (this.randomTicketFile.randomAccessFile.length() / 432L);
        int r = 0;

        for (int i = 0; i < length; ++i) {
            this.randomTicketFile.randomAccessFile.seek((long) (432 * i + 360));
            str = "";

            int seatNumber;
            for (seatNumber = 432 * i + 360; seatNumber < 432 * i + 420; seatNumber += 2) {
                str = str + this.randomTicketFile.randomAccessFile.readChar();
            }

            if (passengerUsername.trim().equals(str.trim())) {
                r = 3;
                System.out.println("Ticket Id     |Flight Id     |Origin        |Destination   |Date          |Time      |Username         |Seat Number |Price  ");
                this.randomTicketFile.randomAccessFile.seek((long) (432 * i));
                str = "";

                for (seatNumber = 432 * i; seatNumber < 432 * i + 60; seatNumber += 2) {
                    str = str + this.randomTicketFile.randomAccessFile.readChar();
                }

                System.out.print(str.trim());

                for (seatNumber = 0; seatNumber < 14 - str.trim().length(); ++seatNumber) {
                    System.out.print(" ");
                }

                System.out.print("|");
                str = "";

                for (seatNumber = 432 * i; seatNumber < 432 * i + 60; seatNumber += 2) {
                    str = str + this.randomTicketFile.randomAccessFile.readChar();
                }

                System.out.print(str.trim());

                for (seatNumber = 0; seatNumber < 14 - str.trim().length(); ++seatNumber) {
                    System.out.print(" ");
                }

                System.out.print("|");
                str = "";

                for (seatNumber = 432 * i; seatNumber < 432 * i + 60; seatNumber += 2) {
                    str = str + this.randomTicketFile.randomAccessFile.readChar();
                }

                System.out.print(str.trim());

                for (seatNumber = 0; seatNumber < 14 - str.trim().length(); ++seatNumber) {
                    System.out.print(" ");
                }

                System.out.print("|");
                str = "";

                for (seatNumber = 432 * i; seatNumber < 432 * i + 60; seatNumber += 2) {
                    str = str + this.randomTicketFile.randomAccessFile.readChar();
                }

                System.out.print(str.trim());

                for (seatNumber = 0; seatNumber < 14 - str.trim().length(); ++seatNumber) {
                    System.out.print(" ");
                }

                System.out.print("|");
                str = "";

                for (seatNumber = 432 * i; seatNumber < 432 * i + 60; seatNumber += 2) {
                    str = str + this.randomTicketFile.randomAccessFile.readChar();
                }

                System.out.print(str.trim());

                for (seatNumber = 0; seatNumber < 14 - str.trim().length(); ++seatNumber) {
                    System.out.print(" ");
                }

                System.out.print("|");
                str = "";

                for (seatNumber = 432 * i; seatNumber < 432 * i + 60; seatNumber += 2) {
                    str = str + this.randomTicketFile.randomAccessFile.readChar();
                }

                System.out.print(str.trim());

                for (seatNumber = 0; seatNumber < 10 - str.trim().length(); ++seatNumber) {
                    System.out.print(" ");
                }

                System.out.print("|");
                str = "";

                for (seatNumber = 432 * i; seatNumber < 432 * i + 60; seatNumber += 2) {
                    str = str + this.randomTicketFile.randomAccessFile.readChar();
                }

                System.out.print(str.trim());

                for (seatNumber = 0; seatNumber < 17 - str.trim().length(); ++seatNumber) {
                    System.out.print(" ");
                }

                System.out.print("|");
                this.randomTicketFile.randomAccessFile.seek(432L * (long) i + 428L);
                seatNumber = this.randomTicketFile.randomAccessFile.readInt();
                if (seatNumber > 99) {
                    System.out.print("" + seatNumber + "         ");
                } else {
                    System.out.print("" + seatNumber + "          ");
                }

                System.out.print("|");
                this.randomTicketFile.randomAccessFile.seek(432L * (long) i + 420L);
                System.out.print(this.randomTicketFile.randomAccessFile.readDouble());
                System.out.println();
            }
        }

        if (r == 0) {
            System.out.println("Sorry ! We Couldn't Find Any Booked Flights For You !");
        }

    }
}
