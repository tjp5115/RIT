//
// Vertex shader for the transformation assignment
//
// Author:  W. R. Carithers
//
// Contributor: Tyler Paulsen

#version 120

attribute vec4 vPosition;

// projection matrix
uniform mat4 projection;

// tansformation vec
uniform vec3 theta;
uniform vec3 scale;
uniform vec3 translate;

//camera vectorss
uniform vec3 eye;
uniform vec3 lookat;
uniform vec3 up;

void main()
{
    // rotation vectors for each axis
    vec3 c = cos( radians( theta ) );
    vec3 s = sin( radians( theta ) );

    // translate matrix
    mat4 translate = mat4 ( 1.0,  0.0,  0.0,   0.0,
                            0.0,  1.0,  0.0,   0.0,
                            0.0,  0.0,  1.0,   0.0,
                            translate.x, translate.y, translate.z, 1.0);
    // scaling matrix
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
    mat4 rotation = rx * ry * rz;

    // transform the matrix (scaling, rotation, and tranlation)
    vec4 transformation = translate * rotation * scale * vPosition;

    //viewing transformation
    vec3 n = normalize(eye - lookat);
    vec3 u = normalize(cross(up, n));
    vec3 v = normalize(cross(n, u));
    mat4 view = mat4( u.x, u.y, u.z, dot(-1.0*u, eye),
                      v.x, v.y, v.z, dot(-1.0*v, eye),
                      n.x, n.y, n.z, dot(-1.0*n, eye),
                      0.0, 0.0, 0.0, 1.0);
    // put us in camera cords
    vec4 worldToCamera = transformation * view;

    //put us in clip space
    gl_Position =   projection * worldToCamera;

}
