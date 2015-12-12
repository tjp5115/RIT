//
//  finalMain.java
// Author: Tyler Paulsen
//

import java.awt.*;
import java.awt.event.*;
import javax.media.opengl.*;
import javax.media.opengl.awt.GLCanvas;

public class finalMain implements GLEventListener, KeyListener
{
    enum shapes{
        CUBE,
        CYLINDER,
        SPHERE
    };

    ///
    // program and variable IDs
    ///
    public int program[];

    ///
    // view params
    ///
    viewParams myView;

    //lighting information
    lightingParams lightParam;
    int[] modelLight;

    ///
    // image selection and camera/transformation control
    ///
    int counter;
    boolean cameraAdjust;
    boolean transformsOn;

    ///
    // animation control
    ///
    private boolean animating = false;

    ///
    // rotation angles
    ///
    public float angles[];

    private boolean updateNeeded = false;
    ///
    // my canvas
    ///
    GLCanvas myCanvas;
    private static Frame frame;

    // objects for reading in the obj files.
    private BlenderObj modifiedCube;
    private BlenderObj modifiedCylinder;

    // texture model
    private cgShape sphere;
    private textureParams tex;
    ///
    // constructor
    ///
    public finalMain(GLCanvas G)
    {
        program = new int[2];
        angles = new float[shapes.values().length];

        myView = new viewParams();

        counter = 0;
        cameraAdjust = false;
        transformsOn = false;

        myCanvas = G;

        G.addGLEventListener(this);
        G.addKeyListener(this);

        float[] Lx = {1.0f, 1.0f, 1.0f, 1.0f};
        float[] pos = {-10.0f, 12.0f, 5.0f, 1.0f};
        float[] ambient = {0.5f, 0.5f, 0.5f, 1.0f};
        lightParam = new lightingParams(Lx, pos, ambient);
        modelLight = new int[shapes.values().length];

        modifiedCube = new BlenderObj("modified_cube.obj");

        // Material properties for Cube
        float[] Ax = {0.95f, 0.85f, .75f, 1.0f};
        float   Ka = 0.8f;

        float[] Dx = {0.6f, 0.5f, 0.4f, 1.0f};
        float   Kd = 0.7f;

        float[] Sx = {0.7f, 0.7f, 0.7f, 1.0f};
        float   n = 10.0f;
        float   Ks = 0.5f;
        modelLight[shapes.CUBE.ordinal()] = lightParam.addObject(Ax,Ka,Dx,Kd,Sx,n,Ks);


        // Material properties for Cube
        Ax = new float[]{0.95f, 0.85f, .75f, 1.0f};
        Ka = 0.9f;

        Dx = new float[]{0.8f, 0.7f, 0.6f, 1.0f};
        Kd = 0.6f;

        Sx = new float[]{0.7f, 0.7f, 0.7f, 1.0f};
        n = 10.0f;
        Ks = 1.0f;
        modifiedCylinder = new BlenderObj("modified_cylinder.obj");
        modelLight[shapes.CYLINDER.ordinal()] = lightParam.addObject(Ax,Ka,Dx,Kd,Sx,n,Ks);


        sphere = new cgShape();
        sphere.makeSphere(0.5f, 25, 25);
        tex = new textureParams();
    }

    private void errorCheck( GL2 gl2 )
    {
        int code = gl2.glGetError();
        if( code == GL.GL_NO_ERROR )
            System.err.println( "All is well" );
        else
            System.err.println( "Problem - error code : " + code );

    }


    ///
    // Called by the drawable to initiate OpenGL rendering by the client.
    ///
    public void display( GLAutoDrawable drawable )
    {
        // get GL
        GL2 gl2 = (drawable.getGL()).getGL2();

        // clear your frame buffers
        gl2.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
        gl2.glUseProgram(program[0]);
        myView.setUpFrustum(program[0], gl2);


        // set up the camera
        myView.setUpCamera(program[0], gl2,
                1.5f, 3.0f, 4.5f,
                0.0f, 1.0f, 0.0f,
                0.0f, 1.0f, 0.2f
        );

        //
        // Set up and Draw the Modified Cube
        //

        // set up Phong illumination
        lightParam.setUpPhong(program[0], gl2, shapes.CUBE.ordinal());
        // set up viewing and projection parameters
        // set up transformations for the modified cube
        myView.setUpTransforms(program[0], gl2,
                1.5f, 1.5f, 1.5f,
                angles[shapes.CUBE.ordinal()],
                angles[shapes.CUBE.ordinal()] - 20,
                angles[shapes.CUBE.ordinal()],
                -.4f, 1.0f, .0f
        );

        // draw the modified cube
        modifiedCube.drawModel(gl2, program[0]);

        //
        // Set up and Draw the Modified Cylinder
        //

        // set up Phong illumination
        lightParam.setUpPhong( program[0], gl2, shapes.CYLINDER.ordinal());
        ;
        // set up transformations for the modified cylinder
        myView.setUpTransforms(program[0], gl2,
                1.2f, 1.2f, 1.2f,
                angles[shapes.CYLINDER.ordinal()],
                angles[shapes.CYLINDER.ordinal()]-25,
                angles[shapes.CYLINDER.ordinal()],
                0.8f, -0.8f, -1.5f
        );
        // draw the modified cylinder
        modifiedCylinder.drawModel(gl2,program[0]);



        //
        // Set up and draw the sphere
        //
        gl2.glUseProgram(program[1]);
        myView.setUpFrustum(program[1], gl2);


        // set up the camera
        myView.setUpCamera(program[1], gl2,
                0.2f, 3.0f, 6.5f,
                0.0f, 1.0f, 0.0f,
                0.0f, 1.0f, 0.0f
        );

        // set up transformations for the modified cylinder
        myView.setUpTransforms(program[1], gl2,
                1.f, 1.f, 1.f,
                angles[shapes.SPHERE.ordinal()],
                angles[shapes.SPHERE.ordinal()]+70,
                angles[shapes.SPHERE.ordinal()]+40,
                1.5f,2.5f, 0f
        );
        // setup uniform variables for texture
        tex.setUpTextures (gl2);
        sphere.drawShape(program[1], gl2);

    }


    ///
    // Notifies the listener to perform the release of all OpenGL
    // resources per GLContext, such as memory buffers and GLSL
    // programs.
    ///
    public void dispose( GLAutoDrawable drawable )
    {

    }

    ///
    // Called by the drawable immediately after the OpenGL context is
    // initialized.
    ///
    public void init( GLAutoDrawable drawable )
    {
        // get the gl object
        GL2 gl2 = drawable.getGL().getGL2();
        // create the Animator now that we have the drawable
        // Load shaders
        shaderSetup myShaders = new shaderSetup();
        program[0] = myShaders.readAndCompile( gl2, "shader.vert", "shader.frag");
        if( program[0] == 0 ) {
            System.err.println(myShaders.errorString(myShaders.shaderErrorCode));
        }
        program[1] = myShaders.readAndCompile(gl2, "texture.vert", "texture.frag");
        if (program[1] == 0 ) {
            System.err.println(myShaders.errorString(myShaders.shaderErrorCode));
        }

        // Other GL initialization
        gl2.glEnable(GL.GL_DEPTH_TEST );
        gl2.glEnable(GL.GL_CULL_FACE);
        gl2.glCullFace(GL.GL_BACK);
        //gl2.glPolygonMode(GL.GL_FRONT_AND_BACK,GL.GL_FILL);
        gl2.glFrontFace(GL.GL_CCW);
        gl2.glClearColor(0.8f, 0.07f, 0.04f, 0.0f);
        gl2.glDepthFunc(GL.GL_LESS);
        gl2.glClearDepth(1.0f);

        tex.loadTexture("texture.png","png");

    }


    ///
    // Called by the drawable during the first repaint after the component
    // has been resized.
    ///
    public void reshape(GLAutoDrawable drawable, int x, int y, int width,
                     int height)
    {

    }

    ///
    // Because I am a Key Listener...we'll only respond to key presses
    ///
    public void keyTyped(KeyEvent e){}
    public void keyReleased(KeyEvent e){}

    ///
    // Invoked when a key has been pressed.
    ///
    public void keyPressed(KeyEvent e)
    {
        // Get the key that was pressed
        char key = e.getKeyChar();

        // Respond appropriately
        switch( key ) {

            // termination
            case 033:  // Escape key
            case 'q': case 'Q':
                frame.dispose();
                System.exit( 0 );
                break;
        }

        // do a redraw
        myCanvas.display();
    }

    ///
    // main program
    ///
    public static void main( String [] args )
    {
        // GL setup
        GLProfile glp = GLProfile.getDefault();
        GLCapabilities caps = new GLCapabilities( glp );
        GLCanvas canvas = new GLCanvas( caps );

        // create your finalMain
        finalMain myMain = new finalMain( canvas );

        frame = new Frame( "CG - Final Project" );
        frame.setSize( 512, 512 );
        frame.add( canvas );
        frame.setVisible( true );

        // by default, an AWT Frame doesn't do anything when you click
        // the close button; this bit of code will terminate the program when
        // the window is asked to close
        frame.addWindowListener( new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
		frame.dispose();
                System.exit(0);
            }
        } );
    }


}
