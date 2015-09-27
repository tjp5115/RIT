/**
 * Main method, creates the objects and starts the threads.
 * Usage: java GenPasTri <N> <A> <B> <S>"
 * @author  Tyler Paulsen
 * @version 9/5/2015.
 */
public class GenPasTri {
    /**
     * @param args - arguments sent to the main method. Requires 4 arguments and all arguments to parse to an Integer.
     */
    public static void main(String args[]){
        if (args.length != 4){
            System.out.println("Invalid number of arguments - expected 4. GenPasTri <N> <A> <B> <S>");
            System.exit(0);
        }
        PascalTriangle pasTri = new PascalTriangle(args[0], args[1], args[2], args[3]);
        PasTriOutput out = new PasTriOutput(pasTri);
        out.start();
        pasTri.startReverse();
    }
}
