package entities;

import math.Matrix4f;
import math.Vector3f;

public class Entity {
	
	private Model model;
	
	private Vector3f position;
	private Vector3f rotation;
	private Vector3f scale;
	
	
	public Entity(Model model) {
		this.model = model;
		this.position = new Vector3f();
		this.rotation = new Vector3f();
		this.scale = new Vector3f(1, 1, 1);
	}
	
	public Entity(Model model, Vector3f position, Vector3f rotation, Vector3f scale) {
		this.model = model;
		this.position = position;
		this.rotation = rotation;
		this.scale = scale;
	}
	
	
	public Model getModel() {
		return model;
	}
	
	public Vector3f getPosition() {
		return position;
	}
	
	public Vector3f getRotation() {
		return rotation;
	}
	
	public Vector3f getScale() {
		return scale;
	}
	
	public Matrix4f getTransformationMatrix() {
		return Matrix4f.transformation(position, rotation, scale);
	}
	
	
	public void a() {
		position.x = -5;
		rotation.z = 180;
		scale.y = 2;
	}
	
	
	
	

	

}
