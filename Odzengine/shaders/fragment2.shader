#version 150 core

in vec2 pipe_textureCoords;

out vec4 color;

uniform sampler2D image;

void main() {
    color = texture(image, pipe_textureCoords);
    
	if (color.a < 0.5){
		//color = vec4(0.0, 0.0, 0.0, 1.0);
		discard;
	}
}