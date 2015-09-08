/*
 * Implementing [[main]]
 * 
 * The [[main]] program recognizes the -q option
 * (``quiet,'' to suppress prompts), initializes the
 * interpreter, and runs the read-eval-print loop.
 * <impcore.c>=
 */
#include "all.h"

int main(int argc, char *argv[]) {
    Funenv functions = mkFunenv(NULL, NULL);
    Valenv globals   = mkValenv(NULL, NULL);
    int doprompt = (argc <= 1) || (strcmp(argv[1], "-q") != 0);
    Prompts prompts = doprompt ? STD_PROMPTS : NO_PROMPTS;
    XDefreader input = xdefreader(filereader("standard input", stdin), prompts);
    /*
     * Implementations of printers are distributed
     * throughout the interpreter, but all the printers are
     * installed here.
     * <install conversion specifications in print module>=
     */
    installprinter('d', printdecimal);
    installprinter('e', printexp);
    installprinter('E', printexplist);
    installprinter('f', printfun);
    installprinter('n', printname);
    installprinter('N', printnamelist);
    installprinter('p', printpar);
    installprinter('P', printparlist);
    installprinter('s', printstring);
    installprinter('t', printdef);
    installprinter('v', printvalue);
    installprinter('V', printvaluelist);
    /*
     * The initial basis includes both primitives and
     * user-defined functions. We install the primitives
     * first.
     * <install the initial basis in [[functions]]>=
     */
    {
        static char *prims[] = { "+", "-", "*", "/", "<", ">", "=", "print", 0 }
                                                                               ;
        char **p;
        for (p=prims; *p; p++) {
            Name n = strtoname(*p);
            bindfun(n, mkPrimitive(n), functions);
        }
    }
    /*
     * [*]
     */

    /*
     * <install the initial basis in [[functions]]>=
     */
    {
        /*
         * <C representation of initial basis for {\impcore}>=
         */
        const char *basis=
          "(define and (b c) (if b c b))\n"
          "(define or  (b c) (if b b c))\n"
          "(define not (b)   (if b 0 1))\n"
          "(define <= (x y) (not (> x y)))\n"
          "(define >= (x y) (not (< x y)))\n"
          "(define != (x y) (not (= x y)))\n"
          "(define mod (m n) (- m (* n (/ m n))))\n";
        if (setjmp(errorjmp))
            assert(0); /* fail if error in basis */
        readevalprint(xdefreader(stringreader("initial basis", basis),
                                                                    NO_PROMPTS),
                      globals, functions, SILENT);
    }
    while (setjmp(errorjmp))
        ;
    readevalprint(input, globals, functions, ECHOING);
    return 0;
}
