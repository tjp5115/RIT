import java.io.IOException;

/**
 * Created by Crystal on 11/19/2015.
 */
public class SessionManager implements ViewListener{

    NimModel model = null;


    /**
     * join a game session
     *
     * @param proxy - proxy object
     * @param name  - name of player
     * @throws IOException
     */
    @Override
    public synchronized void join(ViewProxy proxy, String name) throws IOException {
       if (model == null){
           model = new NimModel();
           model.addModelListener(proxy,name);
           proxy.setViewListener(model);
       }else{
           model.addModelListener(proxy,name);
           proxy.setViewListener(model);
           model = null;
       }
    }

    /**
     * number of markers to remove, and what heap to remove from
     *
     * @param h - heap to remove from
     * @param m - markers to remove.
     * @throws IOException
     */
    @Override
    public void take(int h, int m) throws IOException {

    }

    /**
     * create a new game
     *
     * @throws IOException
     */
    @Override
    public void newGame() throws IOException {

    }

    /**
     * quit the game
     *
     * @throws IOException
     */
    @Override
    public void quit() throws IOException {

    }

    /**
     * used if the user closes the game. closes socket connection, and exits program.
     *
     * @throws IOException
     */
    @Override
    public void exit() throws IOException {

    }
}
