import java.io.IOException;
import java.util.LinkedList;

/**
 * Created by Crystal on 11/19/2015.
 */
public class NimModel implements ViewListener{
    private LinkedList<ModelListener> listeners = new LinkedList<ModelListener>();
    private String[] names = new String[2];
    private int[] board = new int[3];
    private int turn = 0;
    private int []score = new int[2];
    NimModel(){

    }
    public synchronized void addModelListener(ModelListener modelListener, String name){
        try {
            int id = listeners.size();
            modelListener.heap(0, 3);
            modelListener.heap(1, 4);
            modelListener.heap(2, 5);
            modelListener.id(id);
            names[id] = name;
            listeners.add(modelListener);
            for(int i = 0; i < listeners.size();++i){
                modelListener.name(i,names[i]);
                if (listeners.size() == 2){
                    System.out.println("Two people in game");
                    listeners.get(i).turn(0);
                }
            }
        }catch(Exception e){}
    }

    /**
     * join a game session
     *
     * @param proxy - proxy object
     * @param name  - name of player
     * @throws IOException
     */
    @Override
    public void join(ViewProxy proxy, String name) throws IOException {

    }

    /**
     * number of markers to remove, and what heap to remove from
     *
     * @param h - heap to remove from
     * @param m - markers to remove.
     * @throws IOException
     */
    @Override
    public synchronized void take(int h, int m) throws IOException {
        board[h] -= m;
        turn++;
        for (ModelListener listener: listeners){
            listener.heap(h,board[h]);
            listener.turn(turn%2);
        }
        boolean winner = true;
        for (int i = 0; i < board.length; ++i){
           if (board[i] != 0){
               winner = false;
               break;
           }
        }
        if (winner){
            turn--;
            for (ModelListener listener: listeners){
                listener.win(turn%2);
                listener.score(turn%2,++score[turn%2]);
            }
        }
    }

    /**
     * create a new game
     *
     * @throws IOException
     */
    @Override
    public synchronized void newGame() throws IOException {
        board[0] = 3;
        board[1] = 4;
        board[2] = 5;
        turn = 0;
        for(ModelListener listener:  listeners){
            listener.heap(0, 3);
            listener.heap(1, 4);
            listener.heap(2, 5);
            listener.turn(0);
        }
    }

    /**
     * quit the game
     *
     * @throws IOException
     */
    @Override
    public synchronized void quit() throws IOException {
        for (ModelListener listener: listeners){
            listener.quit();
        }
    }

    /**
     * used if the user closes the game. closes socket connection, and exits program.
     *
     * @throws IOException
     */
    @Override
    public synchronized void exit() throws IOException {

    }
}
