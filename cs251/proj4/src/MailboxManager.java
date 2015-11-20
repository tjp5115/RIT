import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketAddress;
import java.util.HashMap;

/**
 * Created by Crystal on 11/19/2015.
 */
public class MailboxManager {
    private DatagramSocket mailbox;
    private HashMap<SocketAddress,ViewProxy> proxyMap = new HashMap<SocketAddress,ViewProxy>();
    private byte[] payload = new byte[256];
    private SessionManager sessionManager = new SessionManager();


    /**
     * construct a new mailbox manager
     * @param mailbox - Mailbox to read the datagram from
     */
    public MailboxManager(DatagramSocket mailbox){
        this.mailbox = mailbox;
    }


    public void receiveMessage() throws IOException{
        DatagramPacket packet = new DatagramPacket(payload, payload.length);
        mailbox.receive(packet);
        SocketAddress clientAddress = packet.getSocketAddress();
        ViewProxy viewproxy = proxyMap.get(clientAddress);
        if (viewproxy == null){
            viewproxy = new ViewProxy(mailbox, clientAddress);
            viewproxy.setViewListener(sessionManager);
            proxyMap.put(clientAddress,viewproxy);
        }
        if(viewproxy.process(packet)){
            proxyMap.remove(clientAddress);
        }

    }
}
