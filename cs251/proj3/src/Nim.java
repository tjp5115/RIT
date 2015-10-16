import java.net.InetSocketAddress;
import java.net.Socket;
/**
 * Created by Crystal on 10/13/2015.
 */
public class Nim {
    public static void main(String[] args) throws Exception{
        if (args.length != 3) usage();
        String host = args[0];
        int port = Integer.parseInt(args[1]);
        String player = args[2];

        Socket socket = new Socket();
        socket.connect(new InetSocketAddress(host,port));

        NimUI view = NimUI.create(player);
        ModelProxy proxy = new ModelProxy(socket);
        view.setViewListener(proxy);
        proxy.setModelListener(view);
        view.join(player);

    }
    public static void usage(){
        System.out.println("Error");
        System.exit(1);
    }
}
