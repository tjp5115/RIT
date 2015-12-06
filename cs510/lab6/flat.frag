#version 120
//Author: Tyler Paulsen
// Flat shading fragment shader
varying vec4 flatShading;
void main()
{
    gl_FragColor = flatShading ;
}
