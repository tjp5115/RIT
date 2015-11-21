import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketAddress;
import java.util.Arrays;

/**
 * Created by Tyler on 11/19/2015.
 */
public class ViewProxy implements ModelListener{

    private DatagramSocket mailbox;
    private SocketAddress clientAddress;
    private ViewListener viewListener;

    /**
     * constructor for viewproxy
     * @param mailbox - server mailbox
     * @param clientAddress client mailbox address
     */
    public ViewProxy(DatagramSocket mailbox, SocketAddress clientAddress){
        this.mailbox = mailbox;
        this.clientAddress = clientAddress;
    }

    /**
     * sets the view Listener for the object
     * @param viewListener - View Listener
     */
    public void setViewListener(ViewListener viewListener){
        this.viewListener = viewListener;
    }
    /**
     * Sent to a client when a game session has been joined
     *
     * @param id - id of the player
     * @throws IOException
     */
    @Override
    public void id(int id) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(baos);
        out.writeByte('I');
        out.writeByte(id);
        out.close();
        byte[] payload = baos.toByteArray();
        System.out.println(Arrays.toString(payload));
        mailbox.send(new DatagramPacket(payload, payload.length, clientAddress));
    }

    /**
     * set the name and id to a player
     *
     * @param id   - id of the player
     * @param name - name of the player
     * @throws IOException
     */
    @Override
    public void name(int id, String name) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(baos);
        out.writeByte('N');
        out.writeByte(id);
        out.writeUTF(name);
        out.close();
        byte[] payload = baos.toByteArray();
        System.out.println(Arrays.toString(payload));
        mailbox.send(new DatagramPacket(payload, payload.length, clientAddress));
    }

    /**
     * reports the score of a player
     *
     * @param id    - id of the player
     * @param score - score of the player
     * @throws IOException
     */
    @Override
    public void score(int id, int score) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(baos);
        out.writeByte('S');
        out.writeByte(id);
        out.writeByte(score);
        out.close();
        byte[] payload = baos.toByteArray();
        System.out.println(Arrays.toString(payload));
        mailbox.send(new DatagramPacket(payload, payload.length, clientAddress));
    }

    /**
     * number of markers in one of the heaps
     *
     * @param heap    - heap to the markers
     * @param markers - number of markers in heap
     * @throws IOException
     */
    @Override
    public void heap(int heap, int markers) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(baos);
        out.writeByte('H');
        out.writeByte(heap);
        out.writeByte(markers);
        out.close();
        byte[] payload = baos.toByteArray();
        System.out.println(Arrays.toString(payload));
        mailbox.send(new DatagramPacket(payload, payload.length, clientAddress));
    }

    /**
     * what clients turn it is.
     *
     * @param i - id of the client
     * @throws IOException
     */
    @Override
    public void turn(int i) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(baos);
        out.writeByte('T');
        out.writeByte(i);
        out.close();
        byte[] payload = baos.toByteArray();
        System.out.println(Arrays.toString(payload));
        mailbox.send(new DatagramPacket(payload, payload.length, clientAddress));
    }

    /**
     * report a client win
     *
     * @param id - id of the client
     * @throws IOException
     */
    @Override
    public void win(int id) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(baos);
        out.writeByte('W');
        out.writeByte(id);
        out.close();
        byte[] payload = baos.toByteArray();
        System.out.println(Arrays.toString(payload));
        mailbox.send(new DatagramPacket(payload, payload.length, clientAddress));
    }

    /**
     * @throws IOException
     */
    @Override
    public void quit() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(baos);
        out.writeByte('Q');
        out.close();
        byte[] payload = baos.toByteArray();
        System.out.println(Arrays.toString(payload));
        mailbox.send(new DatagramPacket(payload, payload.length, clientAddress));
    }


    /**
     *  process a datagram
     * @param datagram Datagram
     * @return boolean - id this proxy should be discarded in the map.
     * @throws IOException - IO error if thrown.
     */
    public boolean process(DatagramPacket datagram) throws IOException{

        boolean discard = false;
        DataInputStream in = new DataInputStream(new ByteArrayInputStream(datagram.getData(), 0, datagram.getLength()));
        byte b = in.readByte();
        switch(b){
            case 'J':
                String name = in.readUTF();
                System.out.println("Join ");
                System.out.println(name);
                viewListener.join(this,name);
                break;
            case 'T':
                int h = in.readByte();
                int m = in.readByte();
                System.out.println("Take "+ h+" " + m);
                viewListener.take(h,m);
                break;
            case 'N':
                viewListener.newGame();
                System.out.println("newgame");
                break;
            case 'Q':
                System.out.println("quit");
                viewListener.quit();
                break;
            default:
                System.err.println("Bad Message");
                break;

        }
        return discard;
    }
}
