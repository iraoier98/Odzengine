package render;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.*;

import java.util.ArrayList;
import java.util.List;

import entities.Entity;
import entities.Model;
import shaders.GuiTextShader;
import shaders.Shader;
import text.Letter;
import text.Text;

import static global.GlobalVariables.*;


public class Renderer {
	
	private static List<Entity> entities;
	private static List<Text> texts;
	
	public static void init() {
		entities = new ArrayList<Entity>();
		texts = new ArrayList<Text>();
		Shader.init("shaders/vertex.shader", "shaders/fragment.shader");
		GuiTextShader.init("shaders/vertex2.shader", "shaders/fragment2.shader");
	}
	
	public static void render(Entity e) {
		entities.add(e);
	}

	public static void render(Text t) {
		texts.add(t);
	}
	
	public static void render() {
		for (Entity e : entities) {
			Shader.transf = e.getTransformationMatrix();
			Shader.activate();
			Model model = e.getModel();
			glBindVertexArray(model.getVAO());
			glEnableVertexAttribArray(POSITION_ATTRIB_INDEX);
			glEnableVertexAttribArray(TEXTURE_COORDS_ATTRIB_INDEX);
			glActiveTexture(GL_TEXTURE0);
			glBindTexture(GL_TEXTURE_2D, model.getTexture());
			glDrawElements(GL_TRIANGLES, model.getVertexCount(), GL_UNSIGNED_INT, 0);
			glDisableVertexAttribArray(POSITION_ATTRIB_INDEX);
			glDisableVertexAttribArray(TEXTURE_COORDS_ATTRIB_INDEX);
		}
		for (Text t : texts) {
			for (Letter l : t.getLetters()) {
				GuiTextShader.transf = l.getTransformationMatrix();
				GuiTextShader.activate();
				glBindVertexArray(l.getModel().getVAO());
				glEnableVertexAttribArray(POSITION_ATTRIB_INDEX);
				glEnableVertexAttribArray(TEXTURE_COORDS_ATTRIB_INDEX);
				glActiveTexture(GL_TEXTURE0);
				glBindTexture(GL_TEXTURE_2D, l.getModel().getTexture());
				glDrawElements(GL_TRIANGLES, 6, GL_UNSIGNED_INT, 0);
				glDisableVertexAttribArray(POSITION_ATTRIB_INDEX);
				glDisableVertexAttribArray(TEXTURE_COORDS_ATTRIB_INDEX);
			}
		}
	}
	
	public static void dispose() {
		entities = null;
		texts = null;
	}

}
