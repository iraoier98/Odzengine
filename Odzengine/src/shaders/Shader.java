package shaders;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import static global.GlobalVariables.*;
import math.Matrix4f;

public class Shader {
	
	private int shaderProgram;
	
	public Shader(String vertexPath, String fragmentPath) {
		
		
		int vertexShader = glCreateShader(GL_VERTEX_SHADER);
		String vertexSource = readShader("shaders/vertex.shader");
		glShaderSource(vertexShader, vertexSource);
		
		glCompileShader(vertexShader);
		int status = glGetShaderi(vertexShader, GL_COMPILE_STATUS);
		if (status != GL_TRUE) {
		    throw new RuntimeException(glGetShaderInfoLog(vertexShader));
		}
		
		
		int fragmentShader = glCreateShader(GL_FRAGMENT_SHADER);
		String fragmentSource = readShader("shaders/fragment.shader");
		glShaderSource(fragmentShader, fragmentSource);
		
		glCompileShader(fragmentShader);
		status = glGetShaderi(fragmentShader, GL_COMPILE_STATUS);
		if (status != GL_TRUE) {
		    throw new RuntimeException(glGetShaderInfoLog(fragmentShader));
		}
		
		
		shaderProgram = glCreateProgram();
		glAttachShader(shaderProgram, vertexShader);
		glAttachShader(shaderProgram, fragmentShader);
		glBindAttribLocation(shaderProgram, POSITION_ATTRIB_INDEX, "position");
		glBindFragDataLocation(shaderProgram, 0, "fragColor");
		
		glLinkProgram(shaderProgram);
		status = glGetProgrami(shaderProgram, GL_LINK_STATUS);
		if (status != GL_TRUE) {
		    throw new RuntimeException(glGetProgramInfoLog(shaderProgram));
		}
		
		glUseProgram(shaderProgram);
	}
	
	
	public void bindUniforms() {
		int uniModel = glGetUniformLocation(shaderProgram, "model");
		Matrix4f model = new Matrix4f();
		glUniformMatrix4fv(uniModel, false, model.toFloatBuffer());

		int uniView = glGetUniformLocation(shaderProgram, "view");
		Matrix4f view = new Matrix4f();
		glUniformMatrix4fv(uniView, false, view.toFloatBuffer());

		int uniProjection = glGetUniformLocation(shaderProgram, "projection");
		Matrix4f projection = Matrix4f.ortographic(-16, 16, -9, 9, 0, 100);
		glUniformMatrix4fv(uniProjection, false, projection.toFloatBuffer());
	}
	
	
	private static String readShader(String filePath) {
		File file = new File(filePath);
		String result = "";
		try {
			Scanner scanner = new Scanner(file);
			while (scanner.hasNextLine()) {
				result += scanner.nextLine() + "\n";
			}
			scanner.close();
			return result;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}


	public int getShaderProgram() {
		return shaderProgram;
	}
	
	
	
//	public static void main(String[] args) {
//			//Tests the readShader method
//		System.out.println(readShader("shaders/vertex.shader"));
//	}

}
