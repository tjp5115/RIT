import java.net.DatagramSocket;
import java.net.InetSocketAddress;

/**
 * Created by Crystal on 11/19/2015.
 */
public class NimServer {
    public static void main(String args[]){
        try {
            if (args.length != 2) usage();
            String host = args[0];
            int port = Integer.parseInt(args[1]);

            DatagramSocket mailbox = new DatagramSocket(new InetSocketAddress(host, port));
            MailboxManager manager = new MailboxManager (mailbox);
           for(;;){
               manager.receiveMessage();
           }
        }catch(Exception e){
            e.printStackTrace();
            usage();
        }
    }

    /**
     * usage method to inform user of incorrect parameters.
     */
    public static void usage(){
        System.out.println("Usage: java NimServer <serverhost> <serverport>");
        System.exit(1);
    }}
