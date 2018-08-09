package entities;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.stb.STBImage;
import org.lwjgl.system.MemoryStack;

import exceptions.LoaderException;
import text.Letter;
import text.Letters;

import static global.GlobalVariables.*;

public class Loader {
	
	private static final String DEFAULT_TEXTURE_PATH = "res/default_32x32.png";
	
	private static List<Integer> vaos;
	private static List<Integer> vbos;
	private static List<Integer> textures;
	
	
	public static void init() {
		vaos = new ArrayList<Integer>();
		vbos = new ArrayList<Integer>();
		textures = new ArrayList<Integer>();
	}
	
	public static void dispose() {
		for (Integer vao : vaos) {
			glDeleteVertexArrays(vao);
		}
		for (Integer vbo : vbos) {
			glDeleteBuffers(vbo);
		}
		for (Integer texture : textures) {
			glDeleteTextures(texture);
		}
		
		vaos = null;
		vbos = null;
		textures = null;
	}
	
	public static Model loadModel(float[] positions, int[] indices, float[] textureCoords, String texturePath) throws LoaderException {
		
		int vao = glGenVertexArrays();
		glBindVertexArray(vao);
		vaos.add(vao);
		
		int indVBO = glGenBuffers();
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, indVBO);
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, intArrayToBuffer(indices), GL_STATIC_DRAW);
		vbos.add(indVBO);

		loadDataToVBO(positions, 3, POSITION_ATTRIB_INDEX);
		loadDataToVBO(textureCoords, 2, TEXTURE_COORDS_ATTRIB_INDEX);

		int textureID;
		if (texturePath == null) {
			textureID = -1;
		}else {
			textureID = loadTexture(texturePath);
			textures.add(textureID);
		}
		
		return new Model(vao, indices.length, textureID);
	}
	
	
	public static Model loadLetter(int x, int y, int width, int height) throws LoaderException {
		float xx = (float) x / Letters.FONT_IMAGE_WIDTH;
		float yy = (float) y / Letters.FONT_IMAGE_HEIGHT;
		float ww = (float) width / Letters.FONT_IMAGE_WIDTH;
		float hh = (float) height / Letters.FONT_IMAGE_HEIGHT;
		
		float[] positions = {	-ww / 2, hh / 2, 0,		//		top left
								ww / 2, hh / 2, 0,		//		top right
								-ww / 2, -hh / 2, 0,	//		bot left
								ww / 2, -hh / 2, 0		//		bot right
		};
//		float[] positions = {	-ww, hh, 0,		//		top left
//				ww, hh, 0,		//		top right
//				-ww, -hh, 0,	//		bot left
//				ww, -hh, 0		//		bot right
//		};


		int[] indices = {1, 0, 2, 1, 2, 3};
		
		float[] textureCoords = {	xx, yy,					//		center in top left
									xx + ww, yy,
									xx, yy + hh,
									xx + ww, yy + hh
		};
		
		return loadModel(positions, indices, textureCoords, null);
	}


	
	
	
	public static int loadTexture(String texturePath) throws LoaderException {
		int textureID = glGenTextures();
		glBindTexture(GL_TEXTURE_2D, textureID);
		
		float color[] = {1, 0.3f, 1, 1};
		glTexParameterfv(GL_TEXTURE_2D, GL_TEXTURE_BORDER_COLOR, color);
	    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_BORDER);
	    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_BORDER);
	    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
	    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
		
		MemoryStack stack = MemoryStack.stackPush();
		IntBuffer width = stack.mallocInt(1);
		IntBuffer height = stack.mallocInt(1);
		IntBuffer channels = stack.mallocInt(1);
		
		FloatBuffer image = STBImage.stbi_loadf(texturePath, width, height, channels, 4);
		if (image == null) {
			image = STBImage.stbi_loadf(DEFAULT_TEXTURE_PATH, width, height, channels, 4);
			if (image == null) {
				throw new LoaderException("Error at loading the texture '" + texturePath + "'\n" + STBImage.stbi_failure_reason());
			}
		}
		MemoryStack.stackPop();

		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, width.get(0), height.get(0), 0, GL_RGBA, GL_FLOAT, image);
		
		textures.add(textureID);
		
		return textureID;
	}
	
	public static Model loadQuad() throws LoaderException {
		float[] positions = {	-0.5f, 0.5f, 1,			//		top left
								0.5f, 0.5f, 1,			//		top right
								-0.5f, -0.5f, 1,		//		bot left
								0.5f, -0.5f, 1			//		bot right
		};
		
		int[] indices = {1, 0, 2, 1, 2, 3};
		
		float[] texCoords = {	0, 0,					//		center in top left
								1, 0,
								0, 1,
								1, 1
		};
		
		return loadModel(positions, indices, texCoords, DEFAULT_TEXTURE_PATH);
	}
	
	

	
	private static void loadDataToVBO(float[] data, int length, int VBOindex) {
		int vboID = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, vboID);
		glBufferData(GL_ARRAY_BUFFER, floatArrayToBuffer(data), GL_STATIC_DRAW);
		glVertexAttribPointer(VBOindex, length, GL_FLOAT, false, 0, 0);
		vbos.add(vboID);
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
