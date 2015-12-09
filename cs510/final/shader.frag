#version 120

//Author: Tyler Paulsen
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

// Phong shading fragment shader
varying vec3 N;
varying vec3 pos;

void main()
{
    // vectors for the phong illumination model
    vec3 L = normalize(ls_position.xyz - pos.xyz);
    vec3 V = normalize(-pos.xyz);
    vec3 R = reflect(-L,N);

    // ambient light
    vec4 ambient = Ax * Ka * a_color;
    // diffuse light
    float diffReflection = max(dot(L,N),0.0);
    vec4 diffuse =   Dx * Kd * diffReflection;

    // specular light
    vec4 spec = vec4(0.0);
    if (diffReflection > 0.0 ){
        vec4 spec = Sx * Ks * pow(max(dot(R,V),0.0), n);
        gl_FragColor = ambient + Lx*(diffuse + spec );
    }else{
        gl_FragColor = ambient + Lx*(diffuse);
    }

}

