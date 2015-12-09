import com.jogamp.common.nio.Buffers;
import com.sun.prism.impl.BufferUtil;

import java.io.FileReader;
import java.io.File;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.Buffer;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import javax.media.opengl.GL;
import javax.media.opengl.GL2;

/**
 * Created by Tyler Paulsen on 12/6/2015.
 */
public class BlenderObj {
    // index for indicies
    // file for obj
    private String path;
    // verts normals and faces of the polygon
    private ArrayList<float[]> verts = new ArrayList(); //list of verts
    private ArrayList<float[]> tex = new ArrayList(); // texture cord
    private ArrayList<float[]> normals = new ArrayList(); // list of normals
    private int[] vbuffer;
    private int[] ebuffer;

    private ArrayList<Short> elements = new ArrayList<>();

    FloatBuffer texBuff;
    FloatBuffer normBuff;
    FloatBuffer vertBuff;

    // indicies for the obj
    private ArrayList<int[]>  vertIndices = new ArrayList();
    private ArrayList<int[]>  texIndices = new ArrayList();
    private ArrayList<int[]>  normalIndices = new ArrayList();
    private int triangleCount;
    // init properties to bind to gl2.
    boolean firstDraw;
    boolean textured;
    boolean bufferInit;
    short nverts = 0;
    // constructor. 1) process file     2) create model data buffer
    BlenderObj(String path){
        this.path = path;

        load();
        firstDraw = true;
        vbuffer = new int[1];
        ebuffer = new int[1];
    }
    private void load(){
        String line;
        try {
            File file = new File(path);
            FileReader reader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(reader);
            String values[];
            // read the file
            while ((line = bufferedReader.readLine()) != null) {
                values = line.split(" ");
                switch (values[0]) {
                    case "vt":
                        tex.add(processFloat(values));
                        break;
                    case "vn":
                        normals.add(processFloat(values));
                        break;
                    case "v":
                        verts.add(processFloat(values));
                        nverts++;
                        break;
                    case "f":
                        triangleCount++;
                        processIndicies(values);
                    default:
                        break;
                }
            }
            bufferedReader.close();
        }catch ( FileNotFoundException fnfe){
            System.err.println("Object file for polygon not found.");
            System.exit(1);
        }catch (IOException ioe){
            System.err.println("IO Exception while parsing file");
            System.exit(1);
        }

    }

    private float[] processFloat(String[] values){
        float []data = new float[values.length-1];
        for (int i = 1; i < values.length; ++i){
            data[i-1] = Float.parseFloat(values[i]);
        }
        return data;
    }
    private void processIndicies(String[] values){
        int[]vertI= new int[3];
        int[]normalI= new int[3];
        String val[];
        for (int i = 1; i < values.length; ++i){
            val = values[i].split("//");
            if(val.length != 2){
                System.err.println("Length of face indices out of bounds.");
                System.err.println(Arrays.toString(val));
                System.exit(1);
            }
            vertI[i-1] = Integer.parseInt(val[0]);
            normalI[i-1] = Integer.parseInt(val[1]);
        }
        vertIndices.add(vertI);
        normalIndices.add(normalI);
    }

    /**
     * create the buffers to bind
     */
    private void createBufferData(GL2 gl2){
          // holder variables
        int [] v,n;
        int []t = new int[3];
        // get the standard size of the buffer
        int bufSize = 3 * triangleCount;

        // set up buffers
        vertBuff = BufferUtil.newFloatBuffer(bufSize * 3);
        vertBuff.position(0);
        normBuff = BufferUtil.newFloatBuffer(bufSize * 3);
        normBuff.position(0);
        texBuff = BufferUtil.newFloatBuffer(bufSize * 2);
        texBuff.position(0);

        // put data into the buffers in the correct order.
        for(int i=0; i<vertIndices.size();i++){
            v = vertIndices.get(i);
            n = normalIndices.get(i);
            if(textured) t = texIndices.get(i);
            for(int k=0; k<v.length;k++){
                // add each vertex to their respected buffers.
                if(textured) texBuff.put(tex.get(t[k]-1));
                normBuff.put(normals.get(n[k]-1));
                vertBuff.put(verts.get(v[k]-1));
               // System.out.println(Arrays.toString(verts.get(v[k]-1)));
            }
        }
        texBuff.flip();
        normBuff.flip();
        vertBuff.flip();

        int vSize = vertBuff.limit() * Buffers.SIZEOF_FLOAT;
        int nSize = normBuff.limit() * Buffers.SIZEOF_FLOAT;

        gl2.glGenBuffers(1, vbuffer, 0);
        gl2.glBindBuffer(GL.GL_ARRAY_BUFFER, vbuffer[0]);
        gl2.glBufferData(GL.GL_ARRAY_BUFFER, nSize + vSize, null, GL.GL_STATIC_DRAW);
        gl2.glBufferSubData(GL.GL_ARRAY_BUFFER, 0, vSize, vertBuff);
        gl2.glBufferSubData(GL.GL_ARRAY_BUFFER, vSize, nSize, normBuff);
    }


    public void cleanup(){
        normBuff.clear();
        texBuff.clear();
        vertBuff.clear();
        verts.clear();
        tex.clear();
        normals.clear();
        normalIndices.clear();
        texIndices.clear();
        vertIndices.clear();
    }
    public void drawModel(GL2 gl2, int program){
        if(firstDraw){
            createBufferData(gl2);
            cleanup();
            firstDraw = false;
        }
        gl2.glUseProgram(program);

        // bind buffer
        gl2.glBindBuffer(GL.GL_ARRAY_BUFFER, vbuffer[0]);

        // offset for the second
        long offset = (long)vertBuff.limit() * Buffers.SIZEOF_FLOAT;;
        // set up the vertex attribute variables
        int vPosition = gl2.glGetAttribLocation( program, "vPosition" );
        gl2.glEnableVertexAttribArray( vPosition );
        gl2.glVertexAttribPointer(vPosition, 3, GL.GL_FLOAT, false, 0, 0l);
        // set up the normal attribute variables.
        int vNormal = gl2.glGetAttribLocation( program, "vNormal" );
        gl2.glEnableVertexAttribArray(vNormal);
        gl2.glVertexAttribPointer(vNormal, 3, GL.GL_FLOAT, false, 0, offset);

        gl2.glDrawArrays(GL.GL_TRIANGLES, 0, triangleCount * 3);
    }
}
