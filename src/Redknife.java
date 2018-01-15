import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Calendar;
import java.util.Random;

public class Redknife implements MouseListener
{
    private JFrame jFrame;
    private JTextField err;
    private JTextField ip;
    private JTextField port;
    private JProgressBar p;
    private JButton batch;
    private JTextField time;
    private Random randomGen;
    private String errorMessage;
    private String IP;
    private int INIT, TERM;
    private Redknife()
    {
        errorMessage="";
        IP="172.16.1.1";
        INIT=56300;
        TERM=56400;
    }
    public static void main(String args[])
    {
        Redknife redknife=new Redknife();
        redknife.init();
    }
    private void init()
    {
        jFrame=new JFrame("Redknife");
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jFrame.setVisible(true);
        jFrame.setLayout(null);
        jFrame.setBounds(80, 60, 800, 800);
        ip=new JTextField("Proxy Server");
        ip.setBounds(200, 100, 400, 30);
        jFrame.add(ip);
        port=new JTextField("Port");
        port.setBounds(200, 160, 400, 30);
        jFrame.add(port);
        batch=new JButton("Sniff");
        batch.setBounds(300, 260, 200, 50);
        jFrame.add(batch);
        batch.addMouseListener(this);
        err=new JTextField();
        err.setBounds(200, 400, 400, 100);
        jFrame.add(err);
        time=new JTextField("Start Time");
        time.setBounds(300, 550, 200, 30);
        jFrame.add(time);
        p=new JProgressBar(INIT, TERM+1);
        p.setBounds(100, 650, 600, 20); jFrame.add(p);
        randomGen=new Random();
        jFrame.repaint();
    }

    @Override
    public void mouseClicked(MouseEvent e)
    {
        new Thread(new Runnable() {
            @Override public void run()
            {
                long a = Calendar.getInstance().getTimeInMillis();
                SyncWithFile lock = new SyncWithFile();
                Knives[] knives = new Knives[TERM - INIT + 1];
                ip.setText(IP);
                port.setText(INIT + " ~ " + TERM);
                batch.setEnabled(false);
                for (int i = INIT; i <= TERM; i++)
                    knives[i - INIT] = new Knives(err, lock, IP, i);
                for (int i = INIT; i <= TERM; i++)
                {
                    try
                    {
                        knives[i - INIT].join();
                    }
                    catch (InterruptedException ie)
                    {
                        errorMessage += ie.getMessage() + "\n\n";
                        err.setText(errorMessage);
                    }
                }
                batch.setEnabled(true);
                long b = Calendar.getInstance().getTimeInMillis();
                time.setText(((b - a) / 60000.0) + "");
            }
        }
        ).start();
    }

    @Override
    public void mousePressed(MouseEvent e)
    {

    }
    @Override
    public void mouseReleased(MouseEvent e)
    {

    }
    @Override
    public void mouseEntered(MouseEvent e)
    {

    }
    @Override
    public void mouseExited(MouseEvent e)
    {

    }
}