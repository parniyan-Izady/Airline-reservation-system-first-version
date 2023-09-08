import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class RandomFlightFile {
    private final int FIX_SIZE = 30;
    Flights flights;
    RandomAccessFile randomAccessFile = new RandomAccessFile("flightFile.dat", "rw");

    public RandomFlightFile(Flights flights) throws FileNotFoundException {
        this.flights = flights;
    }

    public void writeFlightToFile(String str) throws IOException {
        this.randomAccessFile.seek(this.randomAccessFile.length());
        this.randomAccessFile.writeChars(this.fixToWrite(str));
    }

    public String fixToWrite(String str) {
        while (str.length() < 30) {
            str = str + " ";
        }

        return str.substring(0, 30);
    }

    public int findFlight(String flightId) throws IOException {
        int length = (int) (this.randomAccessFile.length() / 312L);

        for (int i = 0; i < length; ++i) {
            this.randomAccessFile.seek((long) (312 * i));
            String str = "";

            for (int j = 312 * i; j < 312 * i + 60; j += 2) {
                str = str + this.randomAccessFile.readChar();
            }

            if (flightId.trim().equals(str.trim())) {
                return i;
            }
        }

        return 200;
    }
}
