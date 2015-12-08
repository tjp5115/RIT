#version 120

attribute vec3 vPosition;
attribute vec3 vNormal;
uniform vec3 theta;

varying vec4 color;

void main()
{
    // Compute the sines and cosines of each rotation
        // about each axis
        vec3 angles = radians( theta );
        vec3 c = cos( angles );
        vec3 s = sin( angles );

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

        gl_Position = rz * ry * rx * vec4(vPosition,1.0);
        color = vec4(.91,.81,.66,1.0);
}
