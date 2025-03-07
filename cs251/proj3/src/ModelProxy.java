/**
 * Created by Tyler Paulsen 10/14/2015.
 */
import java.io.PrintStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

/**
 * Model proxy to communicate direct communication between the UI and the Server.
 */
public class ModelProxy implements ViewListener{
    private Socket socket;
    private PrintStream out;
    private Scanner in;
    private ModelListener modelListener;

    /**
     * constructor for the proxy
     * @param socket - socket connection
     * @throws IOException
     */
    public ModelProxy(Socket socket) throws IOException {
        this.socket = socket;
        out = new PrintStream(socket.getOutputStream(),true);
        in = new Scanner(socket.getInputStream());
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
     * @param name
     * @throws IOException
     */
    public void join(String name)throws IOException{
        out.printf("join %s\n", name);
    }

    /**
     * number of markers to remove, and what heap to remove from
     * @param h - heap to remove from
     * @param m - markers to remove.
     * @throws IOException
     */
    public void take(int h, int m)throws IOException{
        out.printf("take %s %s\n", Integer.toString(h),Integer.toString(m));
    }

    /**
     * create a new game
     * @throws IOException
     */
    public void newGame()throws IOException{
        out.printf("new\n");
    }

    /**
     * quit the game
     * @throws IOException
     */
    public void quit()throws IOException{
        out.printf("quit\n");
    }

    /**
     * used if the user closes the game. closes socket connection, and exits program.
     * @throws IOException
     */
    public void exit() throws IOException{
        socket.close();
        System.exit(0);
    }

    /**
     * close the socket of the game if this player has exited.
     * @throws IOException
     */
    private class ReaderThread extends Thread{
        int id;
        public void run(){
           try{
               while(in.hasNextLine()){
                   String msg = in.nextLine();
                   Scanner s = new Scanner(msg);
                   String op = s.next();
                   switch(op){
                       case "id":
                           id = s.nextInt();
                           modelListener.id(id);
                           break;
                       case "name":
                           id = s.nextInt();
                           String name = s.next();
                           modelListener.name(id, name);
                           break;
                       case "score":
                           id = s.nextInt();
                           int score = s.nextInt();
                           modelListener.score(id, score);
                           break;
                       case "heap":
                           int heap = s.nextInt();
                           int number = s.nextInt();
                           modelListener.heap(heap, number);
                           break;
                       case "turn":
                           id = s.nextInt();
                           modelListener.turn(id);
                           break;
                       case "win":
                           id = s.nextInt();
                           modelListener.win(id);
                           break;
                       case "quit":
                           socket.close();
                           modelListener.quit();
                           break;
                   }
               }
           }catch(IOException ioe) {}
           finally {
               try{
                   socket.close();
               }catch(IOException ioe){}
           }
        }
    }

}
