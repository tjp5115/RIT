/**
 * Created by Tyler Paulsen 10/14/2015.
 */
import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.Scanner;

/**
 * NimModel proxy to communicate direct communication between the UI and the Server.
 */
public class ModelProxy implements ViewListener{
    private DatagramSocket mailbox;
    private SocketAddress destination;
    private ModelListener modelListener;

    /**
     * constructor for the proxy
     * @param mailbox - Mailbox
     * @param destination - Destination mailbox
     * @throws IOException
     */
    public ModelProxy(DatagramSocket mailbox, SocketAddress destination) throws IOException {
        this.mailbox = mailbox;
        this.destination = destination;
    }

    /**
     * sets the listener.
     * @param modelListener - listener to set.
     */
    public void setModelListener(ModelListener modelListener){
        this.modelListener = modelListener;
        new ReaderThread().start();
    }

    /**
     * join a game session
     * @param name - name of the player who joined.
     * @param proxy - viewproxy object
     * @throws IOException
     */
    public void join(ViewProxy proxy, String name)throws IOException{
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(baos);
        out.writeByte('J');
        out.writeUTF(name);
        out.close();
        byte[] payload = baos.toByteArray();
        mailbox.send(new DatagramPacket(payload,payload.length,destination));
    }

    /**
     * number of markers to remove, and what heap to remove from
     * @param h - heap to remove from
     * @param m - markers to remove.
     * @throws IOException
     */
    public void take(int h, int m)throws IOException{
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(baos);
        out.writeByte('T');
        out.writeByte(h);
        out.writeByte(m);
        out.close();
        byte[] payload = baos.toByteArray();
        mailbox.send(new DatagramPacket(payload,payload.length,destination));
    }

    /**
     * create a new game
     * @throws IOException
     */
    public void newGame()throws IOException{
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(baos);
        out.writeByte('N');
        out.close();
        byte[] payload = baos.toByteArray();
        mailbox.send(new DatagramPacket(payload,payload.length,destination));
    }

    /**
     * quit the game
     * @throws IOException
     */
    public void quit()throws IOException{
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(baos);
        out.writeByte('Q');
        out.close();
        byte[] payload = baos.toByteArray();
        mailbox.send(new DatagramPacket(payload,payload.length,destination));
    }

    /**
     * used if the user closes the game. closes socket connection, and exits program.
     * @throws IOException
     */
    public void exit() throws IOException{
        mailbox.close();
        System.exit(0);
    }

    /**
     * close the socket of the game if this player has exited.
     * @throws IOException
     */
    private class ReaderThread extends Thread{
        int id;
        public void run(){
            byte[] payload = new byte[256];
           try{
               for(;;){
                   DatagramPacket packet = new DatagramPacket(payload,payload.length);
                   mailbox.receive(packet);
                   DataInputStream in = new DataInputStream(new ByteArrayInputStream(payload, 0 ,packet.getLength()));


                   byte b = in.readByte();
                   switch(b){
                       case 'I':
                           id = in.readByte();
                           System.out.println("id ");
                           System.out.println(id);
                           modelListener.id(id);
                           break;
                       case 'N':
                           id = in.readByte();
                           String name = in.readUTF();
                           System.out.println("name ");
                           System.out.println(id);
                           System.out.println(name);
                           modelListener.name(id, name);
                           break;
                       case 'S':
                           id = in.readByte();
                           int score = in.readByte();
                           System.out.println("score ");
                           System.out.println(id);
                           System.out.println(score);
                           modelListener.score(id, score);
                           break;
                       case 'H':
                           int heap = in.readByte();
                           int number = in.readByte();
                           System.out.println("Heap");
                           System.out.println(heap);
                           System.out.println(number);
                           modelListener.heap(heap, number);
                           break;
                       case 'T':
                           id = in.readByte();
                           System.out.println("turn ");
                           System.out.println(id);
                           modelListener.turn(id);
                           break;
                       case 'W':
                           id = in.readByte();
                           System.out.println("win");
                           System.out.println(id);
                           modelListener.win(id);
                           break;
                       case 'Q':
                           System.out.println("quit");
                           mailbox.close();
                           modelListener.quit();
                           break;
                   }
               }
           }catch(IOException ioe) {}
           finally {
               mailbox.close();
           }
        }
    }

}
