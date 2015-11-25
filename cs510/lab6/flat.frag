#version 120

// Flat shading fragment shader
varying vec4 flatShading;
void main()
{
    gl_FragColor = flatShading;
}
