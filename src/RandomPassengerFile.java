import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class RandomPassengerFile {
    private final int FIX_SIZE = 30;
    RandomAccessFile randomAccessFile = new RandomAccessFile("PassengerFile.dat", "rw");

    public RandomPassengerFile() throws FileNotFoundException {
    }

    public void writePassengerToFile(String str) throws IOException {
        this.randomAccessFile.seek(this.randomAccessFile.length());
        this.randomAccessFile.writeChars(this.fixToWrite(str));
    }

    public String fixToWrite(String str) {
        while (str.length() < 30) {
            str = str + " ";
        }

        return str.substring(0, 30);
    }
}
