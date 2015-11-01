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
            if (args.length != 3) usage();
            String host = args[0];
            int port = Integer.parseInt(args[1]);
            String player = args[2];

            Socket socket = new Socket();
            socket.connect(new InetSocketAddress(host, port));

            NimUI view = NimUI.create(player);
            ModelProxy proxy = new ModelProxy(socket);
            view.setViewListener(proxy);
            proxy.setModelListener(view);
            view.join(player);
        }catch(Exception e){
            usage();
        }

    }

    /**
     * usage method to inform user of incorrect parameters.
     */
    public static void usage(){
        System.out.println("Usage: java Nim <host> <port> <playername>");
        System.exit(1);
    }
}
