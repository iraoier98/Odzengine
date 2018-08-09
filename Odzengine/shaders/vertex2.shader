#version 150 core

in vec3 position;
in vec2 textureCoords;

out vec2 pipe_textureCoords;

uniform mat4 model;
uniform mat4 proj;

void main() {
	pipe_textureCoords = textureCoords;
    mat4 mvp = proj * model;
    gl_Position = mvp * vec4(position.xy, 0.0, 1.0);
}