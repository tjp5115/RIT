#version 120
//Author: Tyler Paulsen
uniform vec4 ls_position;
// Gouraud shading fragment shader
varying vec4 color;
varying vec3 normal;
varying vec3 position;
void main()
{
    gl_FragColor = color;
}

