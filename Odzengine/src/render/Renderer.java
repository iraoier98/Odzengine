package render;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.*;

import java.util.ArrayList;
import java.util.List;

import entities.Entity;
import shaders.Shader;
import static global.GlobalVariables.*;


public class Renderer {
	
	private static List<Entity> entities;
	private static Shader shader;
	
	public static void init() {
		entities = new ArrayList<Entity>();
		shader = new Shader("shaders/vertex.shader", "shaders/fragment.shader");
	}
	
	public static void render(Entity e) {
		entities.add(e);
	}
	
	public static void render() {
		for (Entity e : entities) {
			glBindVertexArray(e.getVAO());
			glEnableVertexAttribArray(POSITION_ATTRIB_INDEX);
			shader.bindUniforms();
			glDrawElements(GL_TRIANGLES, e.getVertexCount(), GL_UNSIGNED_INT, 0);
			glDisableVertexAttribArray(POSITION_ATTRIB_INDEX);
		}
	}
	
	public static void dispose() {
		entities = null;
	}

}
