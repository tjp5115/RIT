/**
 *
 * textureParams.java
 *
 * Simple class for setting up the textures for the textures
 * Assignment.
 *
 * Students are to complete this class.
 *
 * Author: Tyler Paulsen
 */

import java.io.*;
import javax.media.opengl.*;
import com.jogamp.opengl.util.texture.*;

public class textureParams
{
    private Texture tex;
    int[] tbuffer;
	/**
	 * constructor
	 */
	public textureParams()
	{
        tbuffer = new int[1];
	}
    
    /**
     * This functions loads texture data to the GPU.
     *
     * You will need to write this function, and maintain all of the values needed
     * to be sent to the various shaders.
     *
     * @param filename - The name of the texture file.
     *
     */
    public void loadTexture (String filename, String type)
    {
        try {
            InputStream stream = new FileInputStream(filename);
            tex = TextureIO.newTexture(stream, false, type);
        }
        catch (IOException exc) {
            exc.printStackTrace();
            System.exit(1);
        }

    }

    
    /**
     * This functions sets up the parameters
     * for texture use.
     *
     * You will need to write this function, and maintain all of the values needed
     * to be sent to the various shaders.
     *
     * @param gl2 - GL2 object on which all OpenGL calls are to be made
     *
     */
    public void setUpTextures (GL2 gl2)
    {
        tex.enable(gl2);
        tex.bind(gl2);
    }
}
