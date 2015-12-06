/**
 * Created by Tyler on 12/2/2015.
 *
 *
 * Used JAVA 1.8
 *
 *
 * Questions:
 1. Does there exist a flight path from Rochester to Paris?
     Yes, [rochester, nyc, miami, london, paris]
 2. Does there exist a flight path from Paris to Rochester?
    No
 3. Does there exist a direct flight from Berlin to Miami?
    Yes
 4. Does there exist a one stop flight from London to Rochester?
    No
 5. Does there exist a two stop flight from London to Rochester?
    No
 6. Does there exist a three stop flight from London to Rochester?
    No
 7. Does there exist a four stop flight from London to Rochester?
    Yes, [london, nyc, dc, denver, detroit, rochester]
 8. Does your flightExists query give you the longest or shortest possible path? Can you explain how to make it do it
 the opposite way, if it is even possible?
    Returns the shortest path. To make it the opposite way would be impossible, unless you had infinite time -- you can
    loop between two cities forever.
 9. Which language was easier to implement this scenario in, Java or Prolog?
    Java was easy, but more lines of code. Prolog was less readable -- less experience.
 */
public class proj08 {
    static Flights flights;
    public static void main(String args[]){
        flights = new Flights();
        /*
        direct("rochester","nyc");
        oneStop("rochester","nyc");
        twoStop("rochester","nyc");
        threeStop("rochester","nyc");
        fourStop("rochester","nyc");
        direct("rochester","miami");
        oneStop("rochester","miami");
        twoStop("rochester","miami");
        threeStop("rochester","miami");
        fourStop("rochester","miami");
        flightExists("rochester","miami");
        flightExists("rochester","paris");
        flightExists("paris","rochester");
        direct("berlin","miami");
        oneStop("london","rochester");
        twoStop("london","rochester");
        threeStop("london","rochester");
        fourStop("london","rochester");
        oneStop("paris","miami");
        twoStop("paris","miami");
        threeStop("paris","miami");
        fourStop("paris","miami");
        flightExists("paris","miami");
        flightExists("rochester","nyc");
        flightExists("rochester","dc");
        flightExists("rochester","detroit");
        flightExists("rochester","denver");
        flightExists("rochester","miami");
        flightExists("rochester","london");
        flightExists("rochester","berlin");
        */
    }

    /**
     * wrapper for the object. read comments in Flights.java
     */
    public static boolean direct(String src, String dest){
        return flights.direct(src,dest);
    }
    /**
     * wrapper for the object. read comments in Flights.java
     */
    public static boolean oneStop(String src, String dest){
        return flights.oneStop(src,dest);
    }
    /**
     * wrapper for the object. read comments in Flights.java
     */
    public static boolean twoStop(String src, String dest){
        return flights.twoStop(src,dest);
    }
    /**
     * wrapper for the object. read comments in Flights.java
     */
    public static boolean threeStop(String src, String dest){
        return flights.threeStop(src,dest);
    }
    /**
     * wrapper for the object. read comments in Flights.java
     */
    public static boolean fourStop(String src, String dest){
        return flights.fourStop(src,dest);
    }
    /**
     * wrapper for the object. read comments in Flights.java
     */
    public static boolean flightExists(String src, String dest){
        return flights.flightExists(src,dest);
    }
}
