//
// lightingParams.java
//
// Simple class for setting up the viewing and projection transforms
// for the Shading Assignment.
//
// Students are to complete this class.
//

import javax.media.opengl.*;
import javax.media.opengl.fixedfunc.*;
import java.util.Arrays;

public class lightingParams
{
    // Add any global class variables you need here.

    // Material properties
    private float[] Ax = {0.5f, 0.1f, 0.9f, 1.0f};
    private float   Ka = 0.5f;

    private float[] Dx = {0.89f, 0.0f, 0.0f, 1.0f};
    private float   Kd = 0.7f;

    private float[] Sx = {1.0f, 1.0f, 1.0f, 1.0f};
    private float   n = 10.0f;
    private float   Ks = 1.0f;

    // Light source properties
    private float[] Lx = {1.0f, 1.0f, 0.0f, 1.0f};
    private float[] ls_position = {0.0f, 5.0f, 2.0f, 1.0f};

    // Ambient light properties
    private float[] a_color = {0.5f, 0.5f, 0.5f, 1.0f};
    /**
     * constructor
     */
    public lightingParams()
    {
      
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
     *
     */
    public void setUpPhong (int program, GL2 gl2)
    {
        // Add your code here.
        int location = gl2.glGetUniformLocationARB(program,"Ax");
        System.out.println("Ax"+location);
        gl2.glUniform4fv(location, 1, Ax, 0);

        location = gl2.glGetUniformLocationARB(program,"Ka");
        System.out.println("Ka"+location);
        gl2.glUniform1f(location, Ka);

        location = gl2.glGetUniformLocationARB(program,"Dx");
        System.out.println("Dx"+location);
        gl2.glUniform4fv(location, 1, Dx, 0);

        location = gl2.glGetUniformLocationARB(program,"Kd");
        System.out.println("Kd"+location);
        gl2.glUniform1f(location, Kd);

        location = gl2.glGetUniformLocationARB(program,"Sx");
        System.out.println("Sx"+location);
        gl2.glUniform4fv(location, 1, Sx, 0);

        location = gl2.glGetUniformLocationARB(program,"n");
        System.out.println("n"+location);
        gl2.glUniform1f(location, n);

        location = gl2.glGetUniformLocationARB(program,"Ks");
        System.out.println("Ks"+location);
        gl2.glUniform1f(location, Ks);

        location = gl2.glGetUniformLocationARB(program,"Lx");
        System.out.println("Lx"+location);
        gl2.glUniform4fv(location, 1, Lx, 0);

        location = gl2.glGetUniformLocationARB(program,"ls_position");
        System.out.println("ls_pos"+location);
        gl2.glUniform4fv(location, 1, ls_position, 0);

        location = gl2.glGetUniformLocationARB(program,"a_color");
        System.out.println("a_color"+location);
        gl2.glUniform4fv(location, 1, a_color, 0);
    }
}
