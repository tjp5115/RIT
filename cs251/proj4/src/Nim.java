import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.Socket;
/**
 * Created by Tyler Paulsen on 10/13/2015.
 *
 * Main program
 */
public class Nim {
    /**
     * main method
     * @param args - parameters sent to the program. <host> <port> <playername>
     */
    public static void main(String[] args) {
        try {
            if (args.length != 5) usage();
            String serverHost = args[0];
            int serverPort = Integer.parseInt(args[1]);
            String clientHost = args[2];
            int clientPort = Integer.parseInt(args[3]);
            String player = args[4];

            DatagramSocket mailbox = new DatagramSocket(new InetSocketAddress(clientHost,clientPort));
            NimUI view = NimUI.create(player);
            ModelProxy proxy = new ModelProxy(mailbox, new InetSocketAddress(serverHost,serverPort));
            view.setViewListener(proxy);
            proxy.setModelListener(view);
            view.join(player);
        }catch(Exception e){
            System.err.println(e.toString());
            usage();
        }

    }

    /**
     * usage method to inform user of incorrect parameters.
     */
    public static void usage(){
        System.err.println("Usage: java Nim <serverhost> <serverport> <clienthost> <clientport> <playername>");
        System.exit(1);
    }
}
