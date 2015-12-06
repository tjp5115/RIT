#version 120
//Author: Tyler Paulsen
// Flat shading vertex shader

// INCOMING DATA

// Vertex location (in model space)
attribute vec4 vPosition;

// Normal vector at vertex (in model space)
attribute vec3 vNormal;

// Model transformations
uniform vec3 theta;
uniform vec3 trans;
uniform vec3 scale;

// Camera parameters
uniform vec3 cPosition;
uniform vec3 cLookAt;
uniform vec3 cUp;

// View volume boundaries
uniform float left;
uniform float right;
uniform float top;
uniform float bottom;
uniform float near;
uniform float far;

// Lighting
uniform vec4 Ax;
uniform float Ka;

uniform vec4 Dx;
uniform float Kd;

uniform vec4 Sx;
uniform float n;
uniform float Ks;

// Light source properties
uniform vec4 Lx;
uniform vec4 ls_position;

// Ambient light properties
uniform vec4 a_color;

// OUTGOING DATA
varying vec4 flatShading;
// Inverse matrix function for a 4x4 matrix. Only returns the upper 3x3 matrix.
// used for the normal vector to put it in clip space
mat3 upperInverse(mat4 a){
    mat3 inv;
    float det =
    a[0][0]*a[1][1]*a[2][2]*a[3][3] + a[0][0]*a[1][2]*a[2][3]*a[3][1] + a[0][0]*a[1][3]*a[2][1]*a[3][2]+
    a[0][1]*a[1][0]*a[2][3]*a[3][2] + a[0][1]*a[1][2]*a[2][0]*a[3][3] + a[0][1]*a[1][3]*a[2][2]*a[3][0]+
    a[0][2]*a[1][0]*a[2][1]*a[3][3] + a[0][2]*a[1][1]*a[2][3]*a[3][1] + a[0][2]*a[1][3]*a[2][0]*a[3][1]+
    a[0][3]*a[1][0]*a[2][2]*a[3][1] + a[0][3]*a[1][1]*a[2][0]*a[3][2] + a[0][3]*a[1][2]*a[2][1]*a[3][0]-
    a[0][0]*a[1][1]*a[2][3]*a[3][2] - a[0][0]*a[1][2]*a[2][1]*a[3][3] - a[0][0]*a[1][3]*a[2][2]*a[3][1]-
    a[0][1]*a[1][0]*a[2][2]*a[3][3] - a[0][1]*a[1][2]*a[2][3]*a[3][0] - a[0][1]*a[1][3]*a[2][0]*a[3][2]-
    a[0][2]*a[1][0]*a[2][3]*a[3][1] - a[0][2]*a[1][1]*a[2][0]*a[3][3] - a[0][2]*a[1][3]*a[2][1]*a[3][0]-
    a[0][3]*a[1][0]*a[2][1]*a[3][2] - a[0][3]*a[1][1]*a[2][1]*a[3][0] - a[0][3]*a[1][2]*a[2][0]*a[3][1];

    inv[0][0] = a[1][1]*a[2][2]*a[3][3] + a[1][2]*a[2][3]*a[3][1] + a[1][3]*a[2][1]*a[3][2] - a[1][1]*a[2][3]*a[3][2] -a[1][2]*a[2][1]*a[3][3] - a[1][3]*a[2][2]*a[3][1];
    inv[0][1] = a[0][1]*a[2][3]*a[3][2] + a[1][2]*a[2][1]*a[3][3] + a[1][3]*a[2][2]*a[3][1] - a[0][1]*a[2][2]*a[3][3] -a[0][2]*a[2][3]*a[3][1] - a[0][3]*a[2][1]*a[3][2];
    inv[0][2] = a[0][1]*a[1][2]*a[3][3] + a[0][2]*a[1][3]*a[3][1] + a[0][3]*a[1][1]*a[3][2] - a[0][1]*a[1][3]*a[3][2] -a[0][2]*a[1][1]*a[3][3] - a[0][3]*a[1][2]*a[3][1];

    inv[1][0] = a[1][0]*a[2][3]*a[3][2] + a[1][2]*a[2][0]*a[3][3] + a[1][3]*a[2][2]*a[3][0] - a[1][0]*a[2][2]*a[3][3] -a[1][2]*a[2][3]*a[3][0] - a[1][3]*a[2][0]*a[3][2];
    inv[1][1] = a[0][0]*a[2][2]*a[3][3] + a[0][2]*a[2][3]*a[3][0] + a[0][3]*a[2][0]*a[3][2] - a[0][0]*a[2][3]*a[3][2] -a[0][2]*a[2][0]*a[3][3] - a[0][3]*a[2][2]*a[3][0];
    inv[1][2] = a[0][0]*a[1][3]*a[3][2] + a[0][2]*a[1][0]*a[3][3] + a[0][3]*a[1][2]*a[3][0] - a[0][0]*a[1][2]*a[3][3] -a[0][2]*a[1][3]*a[3][0] - a[0][3]*a[2][0]*a[3][2];

    inv[2][0] = a[1][0]*a[2][1]*a[3][3] + a[1][1]*a[2][3]*a[3][0] + a[1][3]*a[2][0]*a[3][1] - a[1][0]*a[2][3]*a[3][1] -a[1][1]*a[2][0]*a[3][3] - a[1][3]*a[3][2]*a[3][1];
    inv[2][1] = a[0][0]*a[2][3]*a[3][1] + a[0][1]*a[2][0]*a[3][3] + a[0][3]*a[2][1]*a[3][0] - a[0][0]*a[2][1]*a[3][3] -a[0][1]*a[2][3]*a[3][0] - a[0][3]*a[2][0]*a[3][1];
    inv[2][2] = a[0][0]*a[1][1]*a[3][3] + a[0][1]*a[1][3]*a[3][0] + a[0][3]*a[1][0]*a[3][1] - a[0][0]*a[1][3]*a[3][1] -a[0][1]*a[1][0]*a[3][3] - a[0][3]*a[1][1]*a[3][0];


    return 1/det * inv;
}

void main()
{
    // Compute the sines and cosines of each rotation about each axis
    vec3 angles = radians( theta );
    vec3 c = cos( angles );
    vec3 s = sin( angles );

    // Create rotation matrices
    mat4 rxMat = mat4( 1.0,  0.0,  0.0,  0.0,
                       0.0,  c.x,  s.x,  0.0,
                       0.0,  -s.x, c.x,  0.0,
                       0.0,  0.0,  0.0,  1.0 );

    mat4 ryMat = mat4( c.y,  0.0,  -s.y, 0.0,
                       0.0,  1.0,  0.0,  0.0,
                       s.y,  0.0,  c.y,  0.0,
                       0.0,  0.0,  0.0,  1.0 );

    mat4 rzMat = mat4( c.z,  s.z,  0.0,  0.0,
                       -s.z, c.z,  0.0,  0.0,
                       0.0,  0.0,  1.0,  0.0,
                       0.0,  0.0,  0.0,  1.0 );

    mat4 xlateMat = mat4( 1.0,     0.0,     0.0,     0.0,
                          0.0,     1.0,     0.0,     0.0,
                          0.0,     0.0,     1.0,     0.0,
                          trans.x, trans.y, trans.z, 1.0 );

    mat4 scaleMat = mat4( scale.x,  0.0,     0.0,     0.0,
                          0.0,      scale.y, 0.0,     0.0,
                          0.0,      0.0,     scale.z, 0.0,
                          0.0,      0.0,     0.0,     1.0 );

    // Create view matrix
    vec3 nVec = normalize( cPosition - cLookAt );
    vec3 uVec = normalize( cross (normalize(cUp), nVec) );
    vec3 vVec = normalize( cross (nVec, uVec) );

    mat4 viewMat = mat4( uVec.x, vVec.x, nVec.x, 0.0,
                         uVec.y, vVec.y, nVec.y, 0.0,
                         uVec.z, vVec.z, nVec.z, 0.0,
                         -1.0*(dot(uVec, cPosition)),
                         -1.0*(dot(vVec, cPosition)),
                         -1.0*(dot(nVec, cPosition)), 1.0 );

    // Create projection matrix
    mat4 projMat = mat4( (2.0*near)/(right-left), 0.0, 0.0, 0.0,
                         0.0, ((2.0*near)/(top-bottom)), 0.0, 0.0,
                         ((right+left)/(right-left)),
                         ((top+bottom)/(top-bottom)),
                         ((-1.0*(far+near)) / (far-near)), -1.0,
                         0.0, 0.0, ((-2.0*far*near)/(far-near)), 0.0 );

    // Transformation order:
    //    scale, rotate Z, rotate Y, rotate X, translate
    mat4 modelMat = xlateMat * rxMat * ryMat * rzMat * scaleMat;
    mat4 modelViewMat = viewMat * modelMat;

    // Transform the vertex location into clip space
    gl_Position =  projMat * viewMat  * modelMat * vPosition;

    //lighting calculations

    // position in clip space
    vec3 pos =  vec3(modelViewMat * vPosition);

    // vectors used in the phong illumination model
    vec3 N = normalize(transpose(upperInverse(modelViewMat)) * vNormal);
    vec3 L = normalize(ls_position.xyz - pos.xyz);
    vec3 V = normalize(-pos.xyz);
    vec3 R = reflect(-L,N);

    //ambient light
    vec4 ambient = Ax * Ka * a_color;

    // diffuse light
    float diffReflection = max(dot(L,N),0.0);
    vec4 diffuse =   Dx * Kd * diffReflection;

    // specular light
    vec4 spec = vec4(0.0);
    if (diffReflection > 0.0 ){
        vec4 spec = Sx * Ks * pow(max(dot(R,V),0.0), n);
    }
    flatShading = ambient + Lx*(diffuse + spec );
}

