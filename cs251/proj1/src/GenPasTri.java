/**
 * Created by Crystal on 9/5/2015.
 */
public class GenPasTri {
    public static void main(String args[]){
        if (args.length != 4){
            System.out.println("Invalid number of arguments - expected 4. GenPasTri <N> <A> <B> <S>");
        }

        PascalTriangle pasTri = new PascalTriangle(args[0], args[1], args[2], args[3]);

    }
}
