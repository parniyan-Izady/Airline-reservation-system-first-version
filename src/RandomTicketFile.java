import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class RandomTicketFile {
    int FIX_SIZE = 30;
    RandomAccessFile randomAccessFile = new RandomAccessFile("TicketsFile.dat", "rw");

    public RandomTicketFile() throws FileNotFoundException {
    }

    public void writeTicketToFile(String str) throws IOException {
        this.randomAccessFile.seek(this.randomAccessFile.length());
        this.randomAccessFile.writeChars(this.fixToWrite(str));
    }

    public String fixToWrite(String str) {
        while (str.length() < this.FIX_SIZE) {
            str = str + " ";
        }

        return str.substring(0, this.FIX_SIZE);
    }
}
