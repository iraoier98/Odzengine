package shaders;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import entities.Camera;

import static global.GlobalVariables.*;
import math.Matrix4f;

public class GuiTextShader {
	
	private static int shaderProgram;
	private static int vertexShader;
	private static int fragmentShader;
	
	public static Matrix4f transf = new Matrix4f();
	
	public static void init(String vertexPath, String fragmentPath) {
		
		
		vertexShader = glCreateShader(GL_VERTEX_SHADER);
		String vertexSource = readShader(vertexPath);
		glShaderSource(vertexShader, vertexSource);
		
		glCompileShader(vertexShader);
		int status = glGetShaderi(vertexShader, GL_COMPILE_STATUS);
		if (status != GL_TRUE) {
		    throw new RuntimeException(glGetShaderInfoLog(vertexShader));
		}
		
		
		fragmentShader = glCreateShader(GL_FRAGMENT_SHADER);
		String fragmentSource = readShader(fragmentPath);
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
		glBindAttribLocation(shaderProgram, TEXTURE_COORDS_ATTRIB_INDEX, "textureCoords");
		
		glLinkProgram(shaderProgram);
		glValidateProgram(shaderProgram);
		status = glGetProgrami(shaderProgram, GL_LINK_STATUS);
		if (status != GL_TRUE) {
		    throw new RuntimeException(glGetProgramInfoLog(shaderProgram));
		}
		
		glUseProgram(shaderProgram);
	}
	
	
	public static void bindUniforms() {
		int uniModel = glGetUniformLocation(shaderProgram, "model");
		glUniformMatrix4fv(uniModel, false, transf.toFloatBuffer());
		
		int uniProjection = glGetUniformLocation(shaderProgram, "proj");
		Matrix4f projection = Matrix4f.ortographic(-16f / 10, 16f / 10, -9f / 10, 9f / 10, 0, 10);
		glUniformMatrix4fv(uniProjection, false, projection.toFloatBuffer());
	}
	
	
	public static void activate() {
		glUseProgram(shaderProgram);
		bindUniforms();
	}
	
	
	public static void dispose() {
		glUseProgram(0);
		glDetachShader(shaderProgram, vertexShader);
		glDetachShader(shaderProgram, fragmentShader);
		glDeleteShader(vertexShader);
		glDeleteShader(fragmentShader);
		glDeleteProgram(shaderProgram);
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
	
	
//	public static void main(String[] args) {
//			//Tests the readShader method
//		System.out.println(readShader("shaders/vertex.shader"));
//	}

}
