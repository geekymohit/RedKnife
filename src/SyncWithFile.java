import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

class SyncWithFile
{
    synchronized void writeToSpoon(int PORT)
    {
        try
        {
            BufferedWriter bufW = new BufferedWriter(new FileWriter(new File("spoons.txt"), true));
            bufW.write(PORT + "");
            bufW.newLine();
            bufW.flush();
            bufW.close();
        }
        catch (IOException io)
        {}
    }
    synchronized void writeToKnife(int PORT)
    {
        try
        {
            BufferedWriter bufW = new BufferedWriter(new FileWriter(new File("knives.txt"), true));
            bufW.write(PORT + "");
            bufW.newLine();
            bufW.flush();
            bufW.close();
        }
        catch (IOException io)
        {}
    }
}