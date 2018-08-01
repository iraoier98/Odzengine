package entities;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.system.MemoryStack;

import static global.GlobalVariables.*;

public class Loader {
	
	private static List<Integer> vaos;
	private static List<Integer> vbos;
	
	
	public static void init() {
		vaos = new ArrayList<Integer>();
		vbos = new ArrayList<Integer>();
	}
	
	public static void dispose() {
		for (Integer vao : vaos) {
			glDeleteVertexArrays(vao);
		}
		for (Integer vbo : vbos) {
			glDeleteVertexArrays(vbo);
		}
		vaos = vbos = null;
	}
	
	public static Entity loadEntity(float[] positions, int[] indices) {
		
		int vao = glGenVertexArrays();
		glBindVertexArray(vao);
		
		int posVBO = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, posVBO);
		glBufferData(GL_ARRAY_BUFFER, floatArrayToBuffer(positions), GL_STATIC_DRAW);
		glVertexAttribPointer(POSITION_ATTRIB_INDEX, 3, GL_FLOAT, false, 0, 0);
		
		int indVBO = glGenBuffers();
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, indVBO);
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, intArrayToBuffer(indices), GL_STATIC_DRAW);

		vaos.add(vao);
		vbos.add(posVBO);
		vbos.add(indVBO);
		return new Entity(vao, indices.length);
	}
	
	public static Entity loadQuad() {
		float[] positions = {	-0.5f, 0.5f, 1,
								0.5f, 0.5f, 1,
								-0.5f, -0.5f, 1,
								0.5f, -0.5f, 1
		};
		int[] indices = {1, 0, 2, 1, 2, 3};
		
		return loadEntity(positions, indices);
	}
	
	
	private static FloatBuffer floatArrayToBuffer(float[] data) {
		try (MemoryStack stack = MemoryStack.stackPush()) {
		    FloatBuffer buffer = stack.mallocFloat(data.length);
		    
		    for (float value : data) {
		    	buffer.put(value);
		    }
		    buffer.flip();
		    
			return buffer;
		}
	}
	
	private static IntBuffer intArrayToBuffer(int[] data) {
		try (MemoryStack stack = MemoryStack.stackPush()) {
			IntBuffer buffer = stack.mallocInt(data.length);
		    
		    for (int value : data) {
		    	buffer.put(value);
		    }
		    buffer.flip();
		    
			return buffer;
		}
	}

}
