//
// lightingParams.java
//
// Simple class for setting up the viewing and projection transforms
// for the Shading Assignment.
//
// Students are to complete this class.
// Author: Tyler Paulsen
//

import javax.media.opengl.*;
import javax.media.opengl.fixedfunc.*;
import java.util.ArrayList;
import java.util.Arrays;

public class lightingParams
{
    // Add any global class variables you need here.

    // Material properties
    private ArrayList<float[]> Ax = new ArrayList();
    private ArrayList<Float> Ka = new ArrayList();

    private ArrayList<float[]> Dx = new ArrayList();
    private ArrayList<Float> Kd = new ArrayList();

    private ArrayList<float[]> Sx = new ArrayList();
    private ArrayList<Float> n = new ArrayList();
    private ArrayList<Float>  Ks = new ArrayList();

    // Light source properties
    private ArrayList<float[]> Lx = new ArrayList();
    private ArrayList<float[]> pos = new ArrayList();

    // Ambient light properties
    float[] ambient;

    int next_id;
    /**
     * constructor
     */
    public lightingParams(float [] Lx,float []pos, float[] ambient)
    {
        next_id = 0;
        this.Lx.add(Lx);
        this.pos.add(pos);
        this.ambient = ambient;


    }

    /**
     * add an object to the light params
     * @param Ax - ambient properties of object
     * @param Ka - ambient coefficient
     * @param Dx - diffuse properties of object
     * @param Kd - diffuce coefficient
     * @param Sx - specular properties of object
     * @param n  - shinyness
     * @param Ks - specular coefficient
     * @return the id of the lighting properties
     */
    public int addObject(float Ax[], float Ka,
                         float[] Dx, float Kd,
                         float[] Sx, float n, float Ks){

        this.Ax.add(Ax);
        this.Ka.add(Ka);
        this.Dx.add(Dx);
        this.Kd.add(Kd);
        this.Sx.add(Sx);
        this.n.add(n);
        this.Ks.add(Ks);

        return next_id++;
    }

    /**
     * This functions sets up the lighting, material, and shading parameters
     * for the Phong shader.
     *
     * You will need to write this function, and maintain all of the values
     * needed to be sent to the vertex shader.
     *
     * @param program - The ID of an OpenGL (GLSL) shader program to which
     * parameter values are to be sent
     *
     * @param gl2 - GL2 object on which all OpenGL calls are to be made
     * @param id - id of the light params
     *
     */
    public void setUpPhong (int program, GL2 gl2, int id)
    {
        // Add your code here.

        int location = gl2.glGetUniformLocationARB(program,"a_color");
        //System.out.println("a_color"+location);
        gl2.glUniform4fv(location, 1, ambient, 0);

        location = gl2.glGetUniformLocationARB(program,"Ax");
        //System.out.println("Ax"+location);
        gl2.glUniform4fv(location, 1, Ax.get(id), 0);

        location = gl2.glGetUniformLocationARB(program,"Ka");
        //System.out.println("Ka"+location);
        gl2.glUniform1f(location, Ka.get(id));

        location = gl2.glGetUniformLocationARB(program,"Dx");
        //System.out.println("Dx"+location);
        gl2.glUniform4fv(location, 1, Dx.get(id), 0);

        location = gl2.glGetUniformLocationARB(program,"Kd");
        //System.out.println("Kd"+location);
        gl2.glUniform1f(location, Kd.get(id));

        location = gl2.glGetUniformLocationARB(program,"Sx");
        //System.out.println("Sx"+location);
        gl2.glUniform4fv(location, 1, Sx.get(id), 0);

        location = gl2.glGetUniformLocationARB(program,"n");
        //System.out.println("n"+location);
        gl2.glUniform1f(location, n.get(id));

        location = gl2.glGetUniformLocationARB(program,"Ks");
        //System.out.println("Ks"+location);
        gl2.glUniform1f(location, Ks.get(id));


        // for the two lighting params, if multiple light sources are going to be used, will need to change this.
        // right now, just use the light source created with the object.

        location = gl2.glGetUniformLocationARB(program,"Lx");
        //System.out.println("Lx"+location);
        gl2.glUniform4fv(location, 1, Lx.get(0), 0);

        location = gl2.glGetUniformLocationARB(program,"ls_position");
        //System.out.println("ls_pos"+location);
        gl2.glUniform4fv(location, 1, pos.get(0), 0);


    }
}
