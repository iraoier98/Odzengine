#version 150 core

in vec3 position;

out vec3 vertexColor;

uniform mat4 model;
uniform mat4 view;
uniform mat4 projection;

void main() {
    vertexColor = vec3(position.x + 0.5, 1.0, position.y + 0.5);
    mat4 mvp = projection * view * model;
    gl_Position = mvp * vec4(position, 1.0);
}