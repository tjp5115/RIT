//
//  cgShape.java
//
//  Class that includes routines for tessellating a number of basic shapes.
//
//  Students are to supply their implementations for the functions in
//  this file using the function "addTriangle()" to do the tessellation.
//
//  Contributor:  Tyler Paulsen
//

import java.awt.*;
import java.lang.reflect.Array;
import java.nio.*;
import java.awt.event.*;
import javax.media.opengl.*;
import javax.media.opengl.awt.GLCanvas;
import java.io.*;
import java.util.ArrayList;

public class cgShape extends simpleShape
{
    ///
    // constructor
    ///
    public cgShape()
    {
    }

    ///
    // makeCube - Create a unit cube, centered at the origin, with a given
    // number of subdivisions in each direction on each face.
    //
    // @param subdivision - number of equal subdivisons to be made in each
    //        direction along each face
    //
    // Can only use calls to addTriangle()
    ///
    public void makeCube (int subdivisions)
    {
        if( subdivisions < 1 )
            subdivisions = 1;

        // YOUR IMPLEMENTATION HERE
        // default point values for th top left corner
        Point p1 = new Point(-.5f,.5f,.5f);
        Point p2 = new Point(.5f,.5f,.5f);
        Point p3 = new Point(-.5f,-.5f,.5f);
        Point p4 = new Point(.5f,-.5f,.5f);


        float len = 1.0f / subdivisions;
        float v = len;
        float u = len;

        Point qU,rU,uv;
        // an object for each side of the cube to make .

        Quadrilateral q2,q3,q4,q5,q6,q1;
        q1 = new Quadrilateral();
        q2 = new Quadrilateral();
        q3 = new Quadrilateral();
        q4 = new Quadrilateral();
        q5 = new Quadrilateral();
        q6 = new Quadrilateral();
        // the top left position to base calculations off of.
        Point s1 = new Point(p1.x,p1.y,p1.z);
        for(int r = 0; r < subdivisions;++r){
            for (int c = 0; c < subdivisions; ++c){
                // calculate a new square from the corner (top left) of each side
                qU = new Point( (1-u)*p1.x+u*p2.x, (1-u)*p1.y+u*p2.y, p1.z);
                rU = new Point( (1-u)*p3.x+u*p4.x, (1-u)*p3.y+u*p4.y, p1.z);
                uv = new Point( (1-v)*qU.x + v*rU.x, (1-v)*qU.y + v*rU.y, p1.z);

                //notes:
                // s1.z is the constant for each side.

                q1.p1 = s1;
                q1.p4 = uv;
                q1.p2 = new Point(uv.x,s1.y,s1.z);
                q1.p3 = new Point(s1.x,uv.y,s1.z);

                q2.p1 = new Point(s1.y,s1.x,-1*s1.z);
                q2.p2 = new Point(s1.y,uv.x,-1*s1.z);
                q2.p4 = new Point(uv.y,uv.x,-1*s1.z);
                q2.p3 = new Point(uv.y,s1.x,-1*s1.z);

                q3.p1 = new Point(s1.z,s1.x,s1.y);
                q3.p2 = new Point(s1.z,uv.x,s1.y);
                q3.p3 = new Point(s1.z,s1.x,uv.y);
                q3.p4 = new Point(s1.z,uv.x,uv.y);

                q4.p1 = new Point(-1*s1.z,s1.y,s1.x);
                q4.p2 = new Point(-1*s1.z,s1.y,uv.x);
                q4.p3 = new Point(-1*s1.z,uv.y,s1.x);
                q4.p4 = new Point(-1*s1.z,uv.y,uv.x);

                q5.p1 = new Point(s1.x,-1*s1.z,s1.y);
                q5.p2 = new Point(uv.x,-1*s1.z,s1.y);
                q5.p3 = new Point(s1.x,-1*s1.z,uv.y);
                q5.p4 = new Point(uv.x,-1*s1.z,uv.y);

                q6.p1 = new Point(s1.y,s1.z,s1.x);
                q6.p2 = new Point(s1.y,s1.z,uv.x);
                q6.p3 = new Point(uv.y,s1.z,s1.x);
                q6.p4 = new Point(uv.y,s1.z,uv.x);

                q1.draw();
                q2.draw();
                q3.draw();
                q4.draw();
                q5.draw();
                q6.draw();
                // update the to the next column position.
                s1 = new Point(q1.p2.x,q1.p2.y,s1.z);
                u += len;
            }
            u = len;
            v += len;
            //move to the next row
            s1 = new Point(p1.x,q1.p3.y,p1.z);
        }
    }

    ///
    // makeCylinder - Create polygons for a cylinder with unit height, centered
    // at the origin, with separate number of radial subdivisions and height
    // subdivisions.
    //
    // @param radius - Radius of the base of the cylinder
    // @param radialDivision - number of subdivisions on the radial base
    // @param heightDivisions - number of subdivisions along the height
    //
    // Can only use calls to addTriangle()
    ///
    public void makeCylinder (float radius, int radialDivisions, int heightDivisions)
    {
        if( radialDivisions < 3 )
            radialDivisions = 3;

        if( heightDivisions < 1 )
            heightDivisions = 1;

        // YOUR IMPLEMENTATION HERE
        float pi = 3.14159265359f;
        double theta = 2*pi / radialDivisions;
        double rotation = theta;
        Point []radPoint1 = new Point[radialDivisions];
        Point []radPoint2 = new Point[radialDivisions];
        Quadrilateral []quad = new Quadrilateral[radialDivisions];
        // initial x and z position.
        float x = (float)(radius*Math.cos(0));
        float z = (float)(radius*Math.sin(0));
        radPoint1[0] = new Point(x, 0.5f ,z);
        radPoint2[0] = new Point(x, -0.5f ,z);
        // temp point holders.
        Point p1,p2,p3,p4;

        // draw the top.
        for(int i = 1; i < radialDivisions;++i){
            x = (float)(radius*Math.cos(theta));
            z = (float)(radius*Math.sin(theta));
            quad[i-1] = new Quadrilateral();
            radPoint1[i] = new Point(x, 0.5f ,z);
            radPoint2[i] = new Point(x, -0.5f ,z);
            p2 = radPoint1[i-1];
            p1 = radPoint1[i];
            quad[i-1].p1 = p1;
            quad[i-1].p2 = p2;
            addTriangle(0f,0.5f,0f,p1.x,p1.y,p1.z,p2.x,p2.y,p2.z);
            p1 = radPoint2[i-1];
            p2 = radPoint2[i];
            quad[i-1].p3 = p2;
            quad[i-1].p4 = p1;
            addTriangle(0f,-0.5f,0f,p1.x,p1.y,p1.z,p2.x,p2.y,p2.z);
            theta += rotation;
        }
        p2 = radPoint1[radialDivisions-1];
        p1 = radPoint1[0];
        quad[radialDivisions-1] = new Quadrilateral();
        quad[radialDivisions-1].p1 = p1;
        quad[radialDivisions-1].p2 = p2;
        addTriangle(0f, 0.5f, 0f, p1.x, p1.y, p1.z, p2.x, p2.y, p2.z);
        p1 = radPoint2[radialDivisions-1];
        p2 = radPoint2[0];
        quad[radialDivisions-1].p3 = p2;
        quad[radialDivisions-1].p4 = p1;
        addTriangle(0f,-0.5f,0f,p1.x,p1.y,p1.z,p2.x,p2.y,p2.z);

        float len = 1.0f / heightDivisions;
        float v;
        Quadrilateral q;
        Point uv,s1;

        // draw all the squares.
        for(int r = 0; r < radialDivisions;++r){
            p1 = quad[r].p1;
            p2 = quad[r].p2;
            p3 = quad[r].p3;
            p4 = quad[r].p4;
            s1 = p1;
            v = len;
            for (int c = 0; c < heightDivisions;++c) {
                q = new Quadrilateral();
                uv = new Point(p1.x, (1 - v) * p1.y + v * p3.y, p1.z);
                q.p1 = s1;
                q.p2 = new Point(p2.x,s1.y,p2.z);
                q.p3 = new Point(p3.x,uv.y,p3.z);
                q.p4 = new Point(p4.x,uv.y,p4.z);
                q.draw();
                s1 = new Point(q.p3.x,q.p3.y,p3.z);
                v += len;
            }
        }
    }

    ///
    // makeCone - Create polygons for a cone with unit height, centered at the
    // origin, with separate number of radial subdivisions and height
    // subdivisions.
    //
    // @param radius - Radius of the base of the cone
    // @param radialDivision - number of subdivisions on the radial base
    // @param heightDivisions - number of subdivisions along the height
    //
    // Can only use calls to addTriangle()
    ///
    public void makeCone (float radius, int radialDivisions, int heightDivisions)
    {
        if( radialDivisions < 3 )
            radialDivisions = 3;

        if( heightDivisions < 1 )
            heightDivisions = 1;

        // YOUR IMPLEMENTATION HERE
        float pi = 3.14159265359f;
        double theta = 2*pi / radialDivisions;
        double rotation = theta;
        Point p1, p2, p3;
        float x, z;
        float y = .5f;
        float segments = 1f / heightDivisions;
        float radiusInc =  radius / heightDivisions;
        radius = radiusInc;
        Quadrilateral q;
        Point [][] circle = new Point[heightDivisions][];

        // draw the outline of the triangle.
        for (int i = 0; i < heightDivisions;i++ ) {
            y -= segments;
            circle[i] = new Point[radialDivisions];
            int k = 0;
            for (; k < radialDivisions; ++k) {
                x = (float) (radius * Math.cos(theta));
                z = (float) (radius * Math.sin(theta));
                circle[i][k] = new Point(x, y, z);
                theta += rotation;
                if(i == 0 && k > 0){
                    p2 = circle[i][k-1];
                    p3 = circle[i][k];
                    p1 = new Point(0f,.5f,0f);;
                    addTriangle(p3.x,p3.y,p3.z,p1.x,p1.y,p1.z,p2.x,p2.y,p2.z);
                }else if (k > 0){
                    //polygon segments.
                    q = new Quadrilateral();
                    q.p1 = circle[i-1][k-1];
                    q.p2 = circle[i-1][k];
                    q.p3 = circle[i][k-1];
                    q.p4 = circle[i][k];
                    q.draw();
                }
            }
            if(i == 0 && k > 0){
                p1 = new Point(0f,.5f,0f);;
                p3 = circle[i][0];
                p2 = circle[i][k-1];
                addTriangle(p3.x,p3.y,p3.z,p1.x,p1.y,p1.z,p2.x,p2.y,p2.z);
            }else if (k > 0){
                //polygon segments.
                q = new Quadrilateral();
                q.p1 = circle[i-1][k-1];
                q.p2 = circle[i-1][0];
                q.p3 = circle[i][k-1];
                q.p4 = circle[i][0];
                q.draw();
            }
            radius += radiusInc;
            theta = rotation;
        }

        int k = circle.length-1;
        int i = 1;
        // draw the last row.
        for (; i < radialDivisions; ++i){
            p2 = circle[k][i-1];
            p1 = circle[k][i];
            addTriangle(0f,-0.5f,0f,p1.x,p1.y,p1.z,p2.x,p2.y,p2.z);
        }
        p1 = circle[k][0];
        p2 = circle[k][i-1];
        addTriangle(0f,-0.5f,0f,p1.x,p1.y,p1.z,p2.x,p2.y,p2.z);


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
    public void makeSphere (float radius, int slices, int stacks) {
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
        float x, z, y;
        float radiusInc =  radius / stacks;
        Quadrilateral q;
        Point [][] circle = new Point[stacks+1][slices];
        for (int i = 0; i < stacks+1;i++ ) {
            int k = 0;
            theta = thetaInc;
            for (; k < slices; ++k) {
                x = (float) (radius * Math.cos(theta) * Math.sin(phi));
                y = (float) (radius * Math.sin(theta) * Math.sin(phi));
                z = (float) (radius * Math.cos(phi));
                circle[i][k] = new Point(x, y, z);
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
            if (i > 0){
                //polygon segments.
                q = new Quadrilateral();
                q.p1 = circle[i-1][k-1];
                q.p2 = circle[i-1][0];
                q.p3 = circle[i][k-1];
                q.p4 = circle[i][0];
                q.draw();
            }
            radius += radiusInc;
            if(radius >= originalRad) radius = originalRad;
            phi += phiInc;
        }
    }


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
                   p1.x, p1.y, p1.z,
                   p3.x, p3.y, p3.z,
                   p2.x, p2.y, p2.z);
            addTriangle(
                    p3.x, p3.y, p3.z,
                    p4.x, p4.y, p4.z,
                    p2.x, p2.y, p2.z);
        }


    }

    public class Point {
        float x,y,z;
        Point(float x, float y, float z){
            this.x = x;
            this.y = y;
            this.z = z;
        }
        Point(){
            this(0,0,0);
        }
        public String toString(){
            return "("+x+","+y+","+z+")";
        }
    }
}
