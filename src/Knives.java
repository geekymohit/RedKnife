import javax.swing.*;
import java.io.*;

class Knives extends Thread implements Runnable
{
    private String IP;
    private int PORT;
    private SyncWithFile lock;
    private JTextField err;
    private String errorMessage;
    Knives(JTextField err, SyncWithFile lock, String IP, int PORT)
    {
        this.err=err;
        this.lock=lock;
        this.IP=IP;
        this.PORT=PORT;
        start();
    }

    @Override
    public void run()
    {
        try
        {
            Process cmd = Runtime.getRuntime().exec("cmd.exe");
            DataOutputStream cmdline = new DataOutputStream(cmd.getOutputStream());
            String proxyCommand = "set http_proxy=http://" + IP + ":" + PORT;
            String wgetCommand = "wget --no-check-certificate -t 1 -o "+PORT+".txt http://www.google.com";
            cmdline.writeBytes(proxyCommand + "\n" + wgetCommand + "\nexit");
            cmdline.flush();
            cmdline.close();
            cmd.waitFor();
            String t;
            int cp = 0, cg = 0;
            File knife = new File(PORT+".txt");
            BufferedReader buf = new BufferedReader(new FileReader(knife));
            while ((t = buf.readLine()) != null)
            {
                if (t.contains("prontonetworks"))
                    cp++;
                else if (t.contains("google"))
                    cg++;
            }
            if (cp > 0)
                lock.writeToSpoon(PORT);
            if (cp == 0 && cg > 1)
                lock.writeToKnife(PORT);
            buf.close();
            knife.delete();
            cmd.destroy();
        }
        catch (Exception E)
        {
            errorMessage+=E.getMessage()+"\n\n";
            err.setText(errorMessage);
        }
    }
}
