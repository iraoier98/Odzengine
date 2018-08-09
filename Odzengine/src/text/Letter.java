package text;

import entities.Loader;
import entities.Model;
import exceptions.LoaderException;
import math.Matrix4f;
import math.Vector2f;
import math.Vector3f;

public class Letter {
	
	private Model model;
	
	private Vector2f position;
	private Vector2f dimension;
	
	private float xoff;
	private float yoff;
	private float adv;
	
	private Letter() {
		
	}
	
	public Letter(int xoff, int yoff, int adv, int textureX, int textureY, int textureW, int textureH) {
		try {
			this.model = Loader.loadLetter(textureX, textureY, textureW, textureH);
		} catch (LoaderException e) {
			e.printStackTrace();
		}
		this.model.setTexture(Letters.fontTexture);
		this.xoff = (float) xoff / Letters.FONT_IMAGE_WIDTH;
		this.yoff = (float) yoff / Letters.FONT_IMAGE_HEIGHT;
		this.adv = (float) adv / Letters.FONT_IMAGE_WIDTH;
		this.position = new Vector2f();
		this.dimension = new Vector2f((float) textureW / Letters.FONT_IMAGE_WIDTH, (float) textureH / Letters.FONT_IMAGE_HEIGHT);
	}
	
	


	public Model getModel() {
		return model;
	}

	public Vector2f getPosition() {
		return position;
	}

	public void setPosition(Vector2f position) {
		this.position = position;
	}

	public float getXoff() {
		return xoff;
	}

	public float getYoff() {
		return yoff;
	}

	public float getAdv() {
		return adv;
	}
	
	public Vector2f getDimension() {
		return dimension;
	}

	
	public Matrix4f getTransformationMatrix() {
		return Matrix4f.transformation(new Vector3f(position), new Vector3f(), new Vector3f(1, 1, 1));
	}

	public Letter getCopy() {
		Letter copy = new Letter();
		copy.xoff = xoff;
		copy.yoff = yoff;
		copy.adv = adv;
		copy.dimension = dimension;
		copy.model = model;
		copy.position = position;
		return copy;
	}


	public void print() {
		System.out.println(position.x);
		System.out.println(position.y);
		
	}
	
	
	


}
