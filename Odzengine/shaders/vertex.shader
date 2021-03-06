#version 150 core

in vec3 position;
in vec2 textureCoords;

out vec2 pipe_textureCoords;

uniform mat4 model;
uniform mat4 view;
uniform mat4 projection;

void main() {
	pipe_textureCoords = textureCoords;
    mat4 mvp = projection * view * model;
    gl_Position = mvp * vec4(position, 1.0);
}