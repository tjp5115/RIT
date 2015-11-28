import java.io.IOException;

/**
 * Created by Tyler on 11/19/2015.
 *
 * Session manager for the game (from writeup https://www.cs.rit.edu/~ark/251/p4/p4.shtml):
 *
 * If the server has no sessions, or if every session has two players, the server creates a new session and adds the
 * client to that session; the client then waits to start playing until another client joins the session. Otherwise,
 * the server has a session with one waiting client, and the server adds the client to that session; the two clients
 * then start playing each other.
 */
public class SessionManager implements ViewListener{

    NimModel model = null;
    ViewProxy gameOwnerProxy = null;

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
           model.addModelListener(proxy, name);
           gameOwnerProxy = proxy;
       }else{
           model.addModelListener(proxy, name);

           gameOwnerProxy.setViewListener(model);
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
        gameOwnerProxy = null;
        model = null;
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
