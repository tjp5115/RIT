/**
 * cgShape.java
 *
 * Class that includes routines for tessellating a number of basic shapes
 *
 * Students are to supply their implementations for the
 * functions in this file using the function "addTriangle()" to do the 
 * tessellation.
 *
 */

import java.nio.*;
import javax.media.opengl.*;

public class cgShape extends simpleShape
{

    int[] vbuffer;
    int[] ebuffer;
    boolean firstDraw = true;
    /**
     * constructor
     */
    public cgShape()
    {
        vbuffer = new int[1];
        ebuffer = new int[1];
    }
    

    ///
    // makeSphere - Create sphere of a given radius, centered at the origin,
    // using spherical coordinates with separate number of thetha and
    // phi subdivisions.
    //
    // @param radius - Radius of the sphere
    // @param slides - number of subdivisions in the theta direction
    // @param stacks - Number of subdivisions in the phi direction.
    //
    // Can only use calls to addTriangle
    ///
    void makeSphere (float radius, int slices, int stacks) {
        // IF USING RECURSIVE SUBDIVISION, MODIFY THIS TO USE
        // A MINIMUM OF 1 AND A MAXIMUM OF 5 FOR 'slices'

        if (slices < 3)
            slices = 3;

        if (stacks < 3)
            stacks = 3;

        // YOUR IMPLEMENTATION HERE
        float originalRad = radius;
        float pi = 3.14159265359f;
        double theta = 2.0f*pi / slices;
        double thetaInc = theta;
        double phiInc = pi / stacks;
        double phi = 0;
        float x, z, y, u, v;
        float radiusInc =  radius / stacks;
        Quadrilateral q;
        Point [][] circle = new Point[stacks+1][slices+1];
        for (int i = 0; i < stacks+1;i++ ) {
            int k = 0;
            theta = thetaInc;
            for (; k < slices+1; ++k) {
                x = (float) (radius * Math.cos(theta) * Math.sin(phi));
                y = (float) (radius * Math.sin(theta) * Math.sin(phi));
                z = (float) (radius * Math.cos(phi));
                u = (float) (theta/(2.0f*pi));
                v = (float) (1.0f - phi/pi);
                circle[i][k] = new Point(x, y, z, u, v);
                theta += thetaInc;
                if (k > 0 && i > 0){
                    //polygon segments.
                    q = new Quadrilateral();
                    q.p1 = circle[i-1][k-1];
                    q.p2 = circle[i-1][k];
                    q.p3 = circle[i][k-1];
                    q.p4 = circle[i][k];
                    q.draw();
                }
            }
            radius += radiusInc;
            if(radius >= originalRad) radius = originalRad;
            phi += phiInc;
        }
    }

    /**
     * class to draw triangle via quad.
     */
    public class Quadrilateral {
        Point p1,p2,p3,p4;
        Quadrilateral(Point p1, Point p2, Point p3, Point p4){
            this.p1 = p1;
            this.p2 = p2;
            this.p3 = p3;
            this.p4 = p4;
        }
        Quadrilateral(){
            this(new Point(),new Point(),new Point(), new Point());
        }
        public void draw(){
            addTriangle(
                    p1.x, p1.y, p1.z, p1.u, p1.v,
                    p3.x, p3.y, p3.z, p3.u, p3.v,
                    p2.x, p2.y, p2.z, p2.u, p2.v);
            addTriangle(
                    p3.x, p3.y, p3.z, p3.u, p3.v,
                    p4.x, p4.y, p4.z, p4.u, p4.v,
                    p2.x, p2.y, p2.z, p2.u, p2.v);
        }


    }

    /**
     * simple point class. Used by Quad
     */
    public class Point {
        float x,y,z,u,v;
        Point(float x, float y, float z, float u, float v){
            this.x = x;
            this.y = y;
            this.z = z;
            this.u = u;
            this.v = v;
        }
        Point(){
            this(0,0,0,0,0);
        }
        public String toString(){
            return "("+x+","+y+","+z+")";
        }
    }

    /**
     * initialize the shape for drawing
     * @param gl2
     */
    public void init(GL2 gl2){

        Buffer points = getVertices();
        Buffer elements = getElements();
        Buffer texCoords = getUV();

        // set up the vertex buffer
        gl2.glGenBuffers (1, vbuffer, 0);
        long vertBsize = getNVerts() * 4l * 4l;
        long tdataSize = getNVerts() * 2l * 4l;
        gl2.glBindBuffer(GL.GL_ARRAY_BUFFER, vbuffer[0]);
        gl2.glBufferData(GL.GL_ARRAY_BUFFER, vertBsize + tdataSize,
                null, GL.GL_STATIC_DRAW);
        gl2.glBufferSubData(GL.GL_ARRAY_BUFFER, 0, vertBsize, points);
        gl2.glBufferSubData(GL.GL_ARRAY_BUFFER, vertBsize, tdataSize,
                texCoords);

        gl2.glGenBuffers (1, ebuffer, 0);
        long eBuffSize = getNVerts() * 2l;
        gl2.glBindBuffer(GL.GL_ELEMENT_ARRAY_BUFFER, ebuffer[0]);
        gl2.glBufferData ( GL.GL_ELEMENT_ARRAY_BUFFER, eBuffSize,elements,
                GL.GL_STATIC_DRAW);
    }


    /**
     * draw the shape
     * @param shaderProgID
     * @param gl2
     */
    public void drawShape(int shaderProgID, GL2 gl2){
        gl2.glUseProgram(shaderProgID);
        if (firstDraw){
            init(gl2);
            firstDraw = true;
        }
        // bind your vertex buffer
        gl2.glBindBuffer ( GL.GL_ARRAY_BUFFER, vbuffer[0]);

        // bind your element array buffer
        gl2.glBindBuffer(GL.GL_ELEMENT_ARRAY_BUFFER, ebuffer[0]);

        // set up your attribute variables
        long dataSize = getNVerts() * 4l * 4l;
        int  vPosition = gl2.glGetAttribLocation (shaderProgID, "vPosition");
        gl2.glEnableVertexAttribArray ( vPosition );
        gl2.glVertexAttribPointer(vPosition, 4, GL.GL_FLOAT, false,
                0, 0l);
        int  vTex = gl2.glGetAttribLocation (shaderProgID, "vTexCoord");
        gl2.glEnableVertexAttribArray ( vTex );
        gl2.glVertexAttribPointer(vTex, 2, GL.GL_FLOAT, false,
                0, dataSize);

        // draw your shapes
        gl2.glDrawElements ( GL.GL_TRIANGLES, getNVerts(),
                GL.GL_UNSIGNED_SHORT, 0l);

    }

}
