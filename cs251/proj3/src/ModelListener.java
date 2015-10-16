/**
 * Created by Crystal on 10/14/2015.
 */
import java.io.IOException;
public interface ModelListener {
        /**
         * Sent to a client when a game session has been joined
         * @param id - id of the player
         * @throws IOException
         */
        void id(int id)throws IOException;

        /**
         * set the name and id to a player
         * @param id - id of the player
         * @param name - name of the player
         * @throws IOException
         */
        void name(int id, String name)throws IOException;

        /**
         * reports the score of a player
         * @param id - id of the player
         * @param score - score of the player
         * @throws IOException
         */
        void score(int id, int score)throws IOException;

        /**
         * number of markers in one of the heaps
         * @param heap - heap to the markers
         * @param markers - number of markers in heap
         * @throws IOException
         */
        void heap(int heap, int markers)throws IOException;

        /**
         * what clients turn it is.
         * @param i - id of the client
         * @throws IOException
         */
        void turn(int i)throws IOException;

        /**
         * report a client win
         * @param i - id of the client
         * @throws IOException
         */
        void win(int i)throws IOException;

        /**
         *
         * @throws IOException
         */
        void quit()throws IOException;
}
