import com.sun.prism.impl.BufferUtil;

import java.io.FileReader;
import java.io.File;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.FloatBuffer;
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
    private ArrayList verts = new ArrayList(); //list of verts
    private ArrayList tex = new ArrayList(); // texture cord
    private ArrayList normals = new ArrayList(); // list of normals
    private FloatBuffer vertBuff;
    private FloatBuffer texBuff;
    private FloatBuffer normBuff;
    private int[] vbuffer,ebuffer;
    // indicies for the obj
    private ArrayList  vertIndices = new ArrayList();
    private ArrayList  texIndices = new ArrayList();
    private ArrayList  normalIndices = new ArrayList();
    private int triangleCount;
    // init properties to bind to gl2.
    boolean firstDraw;
    boolean textured;
    // constructor. 1) process file     2) create model data buffer
    BlenderObj(String path){
        this.path = path;
        load();
        //reorder the points
        verts = index(vertIndices,verts);
        tex = index(texIndices, tex);
        normals = index(normalIndices, normals);
        firstDraw = true;
    }
    private void load(){
        String line = null;
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
                        break;
                    case "f":
                        triangleCount++;
                        processIndicies(values);
                    default:
                        break;
                }
            }

        }catch ( FileNotFoundException fnfe){
            System.err.println("Object file for polygon not found.");
            System.exit(1);
        }catch (IOException ioe){
            System.err.println("IO Exception while parsing file");
            System.exit(1);
        }

    }

    public ArrayList index(ArrayList indices, ArrayList points) {
        int index;
        float []point;
        ArrayList newPoints = new ArrayList();
        for (int i = 0; i < indices.size(); ++i) {
            index = (int) indices.get(i);
            point = (float [])points.get(index-1);
            newPoints.add(point);
        }
        return newPoints;
    }
    private float[] processFloat(String[] values){
        float []data = new float[values.length-1];
        for (int i = 1; i < values.length; ++i){
            data[i-1] = Float.parseFloat(values[i]);
        }
        return data;
    }
    private void processIndicies(String[] values){
        float []vertI= new float[3];
        float []normalI= new float[3];
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
        int[] v, n, t;
        float point[] = new float[3];
        float texPoint[] = new float[2];

        // get the standard size of the buffer
        int bufSize = 3 * triangleCount;

        // set up buffers
        vertBuff = BufferUtil.newFloatBuffer(bufSize * 3);
        vertBuff.position(0);
        normBuff = BufferUtil.newFloatBuffer(bufSize * 3);
        normBuff.position(0);
        texBuff = BufferUtil.newFloatBuffer(bufSize * 2);
        texBuff.position(0);

        // put data into the model data.
        for(int i=0; i<vertIndices.size();i++){
            v = (int[])vertIndices.get(i);
            n = (int[])normalIndices.get(i);
            t = (int[])texIndices.get(i);
            for(int k=0; k<v.length;k++){
                // put in the texture data.
                if(textured) {
                    for (int tI = 0; tI < texPoint.length; ++tI) {
                        texPoint[tI] = ((float[]) tex.get(t[k] - 1))[tI];
                    }
                    texBuff.put(texPoint);
                }

                // put in the normal points
                for(int nI = 0; nI < point.length; ++nI){
                    point[nI] = ((float[])normals.get(n[k] - 1))[nI];
                }
                normBuff.put(point);

                // put in the vertex points
                for(int vI = 0; vI < point.length; ++vI){
                    point[vI] = ((float[])verts.get(v[k] - 1))[vI];
                }
                vertBuff.put(point);
            }
        }
        texBuff.position(0);
        normBuff.position(0);
        vertBuff.position(0);


    }
    public int triCount(){
        return triangleCount;
    }
    public void drawModel(GL2 gl2, int program){
        if(firstDraw){
            createBufferData(gl2);

            gl2.glGenBuffers(1, vbuffer, 0);

            gl2.glBindBuffer(GL.GL_ARRAY_BUFFER, vbuffer[0]);
            gl2.glBufferData(GL.GL_ARRAY_BUFFER, vertBuff.capacity() + normBuff.capacity(), null, GL.GL_STATIC_DRAW);
            gl2.glBufferSubData(GL.GL_ARRAY_BUFFER, 0, vertBuff.capacity(), vertBuff);
            gl2.glBufferSubData(GL.GL_ARRAY_BUFFER, vertBuff.capacity(), normBuff.capacity(), normBuff);

            // generate the element buffer
            gl2.glGenBuffers (1, ebuffer, 0);
            gl2.glBindBuffer( GL.GL_ELEMENT_ARRAY_BUFFER, ebuffer[0] );
            gl2.glBufferData( GL.GL_ELEMENT_ARRAY_BUFFER, edataSize, elements,
                    GL.GL_STATIC_DRAW );
            firstDraw = false;
        }
        gl2.glEnable(GL2.GL_CULL_FACE);
        gl2.glCullFace(GL2.GL_BACK);
        gl2.glPolygonMode(GL2.GL_FRONT,GL2.GL_LINE);
        gl2.glDrawArrays(3, 0, triangleCount*3); //
        gl2.glDisable(GL2.GL_CULL_FACE);
    }
}
