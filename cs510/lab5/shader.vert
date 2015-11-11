//
// Vertex shader for the transformation assignment
//
// Author:  W. R. Carithers
//
// Contributor: Tyler Paulsen

#version 120

attribute vec4 vPosition;
uniform mat4 projection;

// tansformation vec
uniform vec3 theta;
uniform vec3 scale;
uniform vec3 translate;

//camera vecs
uniform vec3 eye;
uniform vec3 lookat;
uniform vec3 up;

void main()
{
    // Compute the sines and cosines of each rotation
    // about each axis
    vec3 c;
    c.x = cos( radians( theta.x ) );
    c.y = cos( radians( theta.y ) );
    c.z = cos( radians( theta.z ) );

    vec3 s;
    s.x = sin( radians( theta.x ) );
    s.y = sin( radians( theta.y ) );
    s.z = sin( radians( theta.z ) );

    mat4 translate = mat4 ( 1.0,  0.0,  0.0,  0.0,
                            0.0,  1.0,  0.0,  0.0,
                            0.0,  0.0,  1.0,  0.0,
                            translate.x, translate.y, translate.x, 1.0);

    mat4 scale = mat4 ( scale.x,  0.0,  0.0,  0.0,
                        0.0,  scale.y,  0.0,  0.0,
                        0.0,  0.0,  scale.z,  0.0,
                        0.0,  0.0,  0.0,  1.0);

    // rotation matrices
    mat4 rx = mat4 ( 1.0,  0.0,  0.0,  0.0,
                     0.0,  c.x,  s.x,  0.0,
                     0.0, -s.x,  c.x,  0.0,
                     0.0,  0.0,  0.0,  1.0 );

    mat4 ry = mat4 ( c.y,  0.0, -s.y,  0.0,
                     0.0,  1.0,  0.0,  0.0,
                     s.y,  0.0,  c.y,  0.0,
                     0.0,  0.0,  0.0,  1.0 );

    mat4 rz = mat4 ( c.z,  s.z,  0.0,  0.0,
                    -s.z,  c.z,  0.0,  0.0,
                     0.0,  0.0,  1.0,  0.0,
                     0.0,  0.0,  0.0,  1.0 );


    //viewing transformation
    vec3 n = normal(eye - lookat);
    vec3 u = normal(cross(up, n));
    vec3 v = cross(n, u);
    mat4 view = mat4( u.x, u.y, u.z, dot(-1.0*u, eye),
                      v.x, v.y, v.z, dot(-1.0*v, eye),
                      n.x, n.y, n.z, dot(-1.0*n, eye),
                      0.0, 0.0, 0.0, 1.0);

    gl_Position =  projection * view * translate * rz * ry * rx * scale  * vPosition ;

}
