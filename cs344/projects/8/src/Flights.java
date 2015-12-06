import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

/**
 * Created by Tyler on 12/2/2015.
 *
 *
 * Used JAVA 1.8
 */
public class Flights{
    // hashmap of connected flights
    HashMap<String,HashSet<String>> flight;
    boolean found = false;

    /**
     * constructor for the object
     */
    Flights(){
        init();
    }

    /**
     * inits the base map for the object.
     * Used in the FlightExists function to stop infinite loops
     */
    private void init(){
        flight = new HashMap<>();
        HashSet<String> connected = new HashSet();

        connected.add("nyc");
        connected.add("dc");
        connected.add("detroit");
        flight.put("rochester", connected);

        connected = new HashSet();
        connected.add("dc");
        connected.add("paris");
        connected.add("miami");
        flight.put("nyc", connected);

        connected = new HashSet();
        connected.add("miami");
        connected.add("nyc");
        connected.add("denver");
        flight.put("dc", connected);

        connected = new HashSet();
        connected.add("rochester");
        connected.add("dc");
        flight.put("detroit", connected);

        connected = new HashSet();
        connected.add("detroit");
        connected.add("miami");
        connected.add("dc");
        flight.put("denver", connected);
        
        connected = new HashSet();
        connected.add("london");
        flight.put("miami", connected);
        
        connected = new HashSet();
        connected.add("nyc");
        connected.add("berlin");
        connected.add("paris");
        flight.put("london", connected);
        
        connected = new HashSet();
        connected.add("miami");
        flight.put("berlin", connected);
        
    }

    /**
     * if a direct flight exists or not
     * @param src - source of the flight
     * @param dest - destination of the flight
     * @return boolean - if the flight exists or not.
     */
    public boolean direct(String src, String dest){
        init();
        System.out.print("direct("+src+", "+dest+") : ");
        if (flight.get(src).contains(dest)){
            System.out.println(src + " -> " + dest);
            return true;
        }else{
            System.out.println("No flight path found");
        }
        return false;
    }

    /**
     * Modified DFS algorithm to find a path of a certain length or if a path exists.
     * @param src - source of flight ( passed as a connected flight -- contently changing until src == dest)
     * @param dest - destination of flight
     * @param numStops - number of stops needed in between src and dest. value of -3 if just a path is needed.
     * @param path - path of the traversal.
     */
    private void dfs(String src, String dest, int numStops, LinkedList<String> path){
        path.addLast(src);
        // precondition
        if ( !found && numStops != -2 ) {
            // sets the precondition.
            if (src.equals(dest) && (numStops == -1 || numStops == -3)) {
                found = true;
                System.out.println(path);
            }
            if (flight.containsKey(src)) {
                for (String connected : flight.get(src)) {
                    if(numStops == -3){
                        // remove src, so we don't visit again.
                        flight.remove(src);
                        dfs(connected, dest, numStops, new LinkedList<>(path));
                    }else {
                        dfs(connected, dest, numStops - 1, new LinkedList<>(path));
                    }
                }
            }
        }
    }
    /**
     * if a one stop flight exists or not
     * @param src - source of the flight
     * @param dest - destination of the flight
     * @return boolean - if the flight exists or not.
    */
    public boolean oneStop(String src, String dest){
        init();
        found = false;
        System.out.print("oneStop(" + src + ", " + dest + ") : ");
        dfs(src,dest,1, new LinkedList<>());
        if( ! found ) System.out.println("No flight path found");
        return found;
    }
    /**
     * if a two stop flight exists or not
     * @param src - source of the flight
     * @param dest - destination of the flight
     * @return boolean - if the flight exists or not.
     */
    public boolean twoStop(String src, String dest){
        init();
        found = false;
        System.out.print("twoStop(" + src + ", " + dest + ") : ");
        dfs(src,dest,2, new LinkedList<>());
        if( ! found ) System.out.println("No flight path found");
        return found;
    }
    /**
     * if a three stop flight exists or not
     * @param src - source of the flight
     * @param dest - destination of the flight
     * @return boolean - if the flight exists or not.
     */
    public boolean threeStop(String src, String dest){
        init();
        found = false;
        System.out.print("threeStop(" + src + ", " + dest + ") : ");
        dfs(src,dest,3, new LinkedList<>());
        if( ! found ) System.out.println("No flight path found");
        return found;
    }
    /**
     * if a four stop flight exists or not
     * @param src - source of the flight
     * @param dest - destination of the flight
     * @return boolean - if the flight exists or not.
     */
    public boolean fourStop(String src, String dest){
        init();
        found = false;
        System.out.print("fourStop("+src+", "+dest+") : ");
        dfs(src,dest,4, new LinkedList<>());
        if( ! found ) System.out.println("No flight path found");
        return found;
    }
    /**
     * if a flight exists or not
     * @param src - source of the flight
     * @param dest - destination of the flight
     * @return boolean - if the flight exists or not.
     */
    public boolean flightExists(String src, String dest){
        init();
        found = false;
        System.out.print("exists(" + src + ", " + dest + ") : ");
        dfs(src,dest,-3, new LinkedList<>());
        if( ! found ) System.out.println("No flight path found");
        return found;
    }
}
