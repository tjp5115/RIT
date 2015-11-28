#version 120
uniform vec4 ls_position;
// Gouraud shading fragment shader
varying vec4 color;
varying vec3 normal;
varying vec3 position;

uniform vec4 a_color;
void main()
{
    float dist = length(vec3(ls_position) - position);
    vec3 L = normalize(vec3(ls_position) - position);
    gl_FragColor = color *dot(normal,L);
}

