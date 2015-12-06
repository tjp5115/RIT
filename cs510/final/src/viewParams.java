//
// viewParams.java
//
// Created by Joe Geigel on 1/23/13.
//
// Contributor: Tyler Paulsen
//

import javax.media.opengl.*;
import javax.media.opengl.fixedfunc.*;
import java.util.Arrays;

public class viewParams
{

    //projection transformations variables.
    private static float l = -1.0f;
    private static float r = 1.0f;
    private static float t = 1.0f;
    private static float b = -1.0f;
    private static float n = 0.9f;
    private static float f = 4.5f;
    // orthographical projection matrix
    private float[] ortho = {
            2f/(r-l), 0f, 0f, 0f,
            0f, 2f/(t-b), 0f, 0f,
            0f, 0f, -2f/(f-n), 0f,
            -1f*(r+l)/(r-l), -1f*(t+b)/(t-b), -1f*(f+n)/(f-n), 1f
    };
    // frustum projection matrix
    private float[] frustum= {
            (2f*n)/(r-l),0f ,0f,0f,
            0f,(2f*n)/(t-b),0f,0f,
            (r+l)/(r-l),(t+b)/(t-b),-1f*(f+n)/(f-n),-1f,
            0f,0f,(-2f*f*n)/(f-n),0f
    };

    ///
    // constructor
    ///
    public viewParams()
    {

    }


    ///
    // This function sets up the view and projection parameter for a frustum
    // projection of the scene. See the assignment description for the values
    // for the projection parameters.
    //
    // You will need to write this function, and maintain all of the values
    // needed to be sent to the vertex shader.
    //
    // @param program - The ID of an OpenGL (GLSL) shader program to which
    //    parameter values are to be sent
    //
    // @param gl2 - GL2 object on which all OpenGL calls are to be made
    //
    ///
    public void setUpFrustum (int program, GL2 gl2)
    {
        // Add your code here.
        int location = gl2.glGetUniformLocationARB(program,"projection");
        gl2.glUniformMatrix4fvARB(location,1,false,frustum,0);
    }

    ///
    // This function sets up the view and projection parameter for an
    // orthographic projection of the scene. See the assignment description
    // for the values for the projection parameters.
    //
    // You will need to write this function, and maintain all of the values
    // needed to be sent to the vertex shader.
    //
    // @param program - The ID of an OpenGL (GLSL) shader program to which
    //    parameter values are to be sent
    //
    // @param gl2 - GL2 object on which all OpenGL calls are to be made
    //
    ///
    public void setUpOrtho (int program, GL2 gl2)
    {
        // Add your code here.

        //set the projection matrix as the private ortho array
        int location = gl2.glGetUniformLocationARB(program,"projection");
        gl2.glUniformMatrix4fvARB(location,1,false,ortho,0);

    }


    ///
    // This function clears any transformations, setting the values to the
    // defaults: no scaling (scale factor of 1), no rotation (degree of
    // rotation = 0), and no translation (0 translation in each direction).
    //
    // You will need to write this function, and maintain all of the values
    // which must be sent to the vertex shader.
    //
    // @param program - The ID of an OpenGL (GLSL) shader program to which
    //    parameter values are to be sent
    // @param gl2 - GL2 object on which all OpenGL calls are to be made
    ///
    public void clearTransforms( int program, GL2 gl2 )
    {
        // Add your code here.

        //default angles.
        float angles[] = {1f, 1f, 1f};
        int location = gl2.glGetUniformLocationARB(program,"theta");
        gl2.glUniform3fv(location,1,angles,0);

        //default scale
        float scale[] = {1f, 1f, 1f};
        location = gl2.glGetUniformLocationARB(program,"scale");
        gl2.glUniform3fv(location,1,scale,0);

        //default translate
        float translate[] = {0f, 0f, 0f };
        location = gl2.glGetUniformLocationARB(program,"translate");
        gl2.glUniform3fv(location,1,translate,0);
    }


    ///
    // This function sets up the transformation parameters for the vertices
    // of the teapot.  The order of application is specified in the driver
    // program.
    //
    // You will need to write this function, and maintain all of the values
    // which must be sent to the vertex shader.
    //
    // @param program - The ID of an OpenGL (GLSL) shader program to which
    //    parameter values are to be sent
    // @param gl2 - GL2 object on which all OpenGL calls are to be made
    // @param scaleX - amount of scaling along the x-axis
    // @param scaleY - amount of scaling along the y-axis
    // @param scaleZ - amount of scaling along the z-axis
    // @param rotateX - angle of rotation around the x-axis, in degrees
    // @param rotateY - angle of rotation around the y-axis, in degrees
    // @param rotateZ - angle of rotation around the z-axis, in degrees
    // @param translateX - amount of translation along the x axis
    // @param translateY - amount of translation along the y axis
    // @param translateZ - amount of translation along the z axis
    ///
    public void setUpTransforms( int program, GL2 gl2,
        float scaleX, float scaleY, float scaleZ,
        float rotateX, float rotateY, float rotateZ,
        float translateX, float translateY, float translateZ )
    {
        // Add your code here.

        // set rotation
        float angles[] = {rotateX,rotateY,rotateZ};
        int location = gl2.glGetUniformLocationARB(program,"theta");
        gl2.glUniform3fv(location,1,angles,0);

        //set scale
        float scale[] = {scaleX,scaleY,scaleZ};
        location = gl2.glGetUniformLocationARB(program,"scale");
        gl2.glUniform3fv(location,1,scale,0);

        //set translate
        float translate[] = {translateX,translateY,translateZ};
        location = gl2.glGetUniformLocationARB(program,"translate");
        gl2.glUniform3fv(location,1,translate,0);
    }


    ///
    // This function clears any changes made to camera parameters, setting the
    // values to the defaults: eye (0.0,0.0,0.0), lookat (0,0,0.0,-1.0),
    // and up vector (0.0,1.0,0.0).
    //
    // You will need to write this function, and maintain all of the values
    // which must be sent to the vertex shader.
    //
    // @param program - The ID of an OpenGL (GLSL) shader program to which
    //    parameter values are to be sent
    // @param gl2 - GL2 object on which all OpenGL calls are to be made
    ///
    void clearCamera( int program, GL2 gl2 )
    {
        // Add your code here.

        // default eye vector
        float eye[] = {0f,0f,0f};
        int location = gl2.glGetUniformLocationARB(program,"eye");
        gl2.glUniform3fv(location,1,eye,0);

        //default lookat vector
        float lookat[] = {0f,0f,-1f};
        location = gl2.glGetUniformLocationARB(program,"lookat");
        gl2.glUniform3fv(location,1,lookat,0);

        //default up vector
        float up [] = {0f,1f,0f};
        location = gl2.glGetUniformLocationARB(program,"up");
        gl2.glUniform3fv(location,1,up,0);

    }


    ///
    // This function sets up the camera parameters controlling the viewing
    // transformation.
    //
    // You will need to write this function, and maintain all of the values
    // which must be sent to the vertex shader.
    //
    // @param program - The ID of an OpenGL (GLSL) shader program to which
    //    parameter values are to be sent
    // @param gl2 - GL2 object on which all OpenGL calls are to be made
    // @param eyeX - x coordinate of the camera location
    // @param eyeY - y coordinate of the camera location
    // @param eyeZ - z coordinate of the camera location
    // @param lookatX - x coordinate of the lookat point
    // @param lookatY - y coordinate of the lookat point
    // @param lookatZ - z coordinate of the lookat point
    // @param upX - x coordinate of the up vector
    // @param upY - y coordinate of the up vector
    // @param upZ - z coordinate of the up vector
    ///
    void setUpCamera( int program, GL2 gl2,
        float eyeX, float eyeY, float eyeZ,
        float lookatX, float lookatY, float lookatZ,
        float upX, float upY, float upZ )
    {
        // Add your code here.

        // set eye vector
        float eye[] = {eyeX,eyeY,eyeZ};
        int location = gl2.glGetUniformLocationARB(program,"eye");
        gl2.glUniform3fv(location,1,eye,0);


        //set lookat vector
        float lookat[] = {lookatX,lookatY,lookatZ};
        location = gl2.glGetUniformLocationARB(program,"lookat");
        gl2.glUniform3fv(location,1,lookat,0);

        //set up vector
        float up [] = {upX,upY,upZ};
        location = gl2.glGetUniformLocationARB(program,"up");
        gl2.glUniform3fv(location,1,up,0);
    }

}
