/**
 * Created by Tyler on 10/14/2015.
 */
import java.io.IOException;
public interface ViewListener {

    /**
     * join a game session
     * @param name
     * @throws IOException
     */
    void join(String name)throws IOException;

    /**
     * number of markers to remove, and what heap to remove from
     * @param h - heap to remove from
     * @param m - markers to remove.
     * @throws IOException
     */
    void take(int h, int m)throws IOException;

    /**
     * create a new game
     * @throws IOException
     */
    void newGame()throws IOException;

    /**
     * quit the game
     * @throws IOException
     */
    void quit()throws IOException;
}
