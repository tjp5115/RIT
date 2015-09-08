/**
 * Created by Crystal on 9/5/2015.
 */
public class PascalTriangle {
    private int n;
    private int a;
    private int b;
    private int s;
    private TriPiece[][] tri;
    public PascalTriangle(String n,String a,String b,String s){
        this(Integer.parseInt(n), Integer.parseInt(a), Integer.parseInt(b), Integer.parseInt(s));
    }
    public PascalTriangle(int n,int a,int b,int s){
        this.n = n;
        this.a = a;
        this.b = b;
        this.s = s;
        tri = new TriPiece[n][];
        for(int i = 0;i < n;++i){
            tri[i] = new TriPiece[i+1];
        }

    }
    public String toString(){
        for(int r=0;r<n;++r){
            for(int c=0;c < (r+1);++c){
                System.out.print(tri[r][c].getValue());
            }
            System.out.println();
        }
        return "";
    }
}
