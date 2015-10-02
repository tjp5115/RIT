/**
 * Created by Crystal on 10/1/2015.
 */
import edu.rit.pj2.LongLoop;
import edu.rit.pj2.Task;
import edu.rit.util.Random;
import edu.rit.pj2.vbl.LongVbl;

public class GamblersRuin extends Task{
    long seed, N;
    int alexDollar, blakeDollar;
    LongVbl countBlake;
    public void main(String []args) {
        if (args.length != 4) usage();
        try {
            seed = Long.parseLong(args[0]);
            alexDollar = Integer.parseInt(args[1]);
            blakeDollar = Integer.parseInt(args[2]);
            N = Long.parseLong(args[3]);
        }catch(NumberFormatException nfe){
            usage();
        }

        if (alexDollar <= 0 || blakeDollar <= 0 || N <= 0) usage();

        countBlake = new LongVbl.Sum(0);
        parallelFor (0, N - 1) .exec(new LongLoop() {
            Random rand;
            LongVbl thrCountBlake;

            public void start() {
                rand = new Random(seed + rank());
                thrCountBlake = threadLocal(countBlake);
            }

            public void run(long i) {
                int ad = new Integer(alexDollar);
                int bd = new Integer(blakeDollar);
                while (ad != 0 && bd != 0) {
                    if (rand.nextBoolean()) {
                        ad -= 1;
                        bd += 1;
                    } else {
                        ad += 1;
                        bd -= 1;
                    }
                }
                if (ad == 0) {
                    // System.out.println("Blake WON!!!");
                    ++thrCountBlake.item;
                }
            }
        });
        float p =  (N - countBlake.item)/ (float)N;
        System.out.println(p);
        p = countBlake.item / (float)N;
        System.out.println(p);
    }
    public static void usage(){
        System.err.println("java pj2 GamblersRuin <seed> <A> <B> <N>");
        System.err.println("<seed> is a random seed; it must be an integer (type long).");
        System.err.println("<A> is Alex's initial dollar amount; it must be an integer greater than 0 (type int).");
        System.err.println("<B> is Blake's initial dollar amount; it must be an integer greater than 0 (type int).");
        System.err.println("<N> is the number of repetitions; it must be an integer greater than 0 (type long).");
        System.exit(1);
    }
}
