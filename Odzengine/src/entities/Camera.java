package entities;

import math.Matrix4f;
import math.Vector3f;

public class Camera {
	
	public static Vector3f position;
	
	
	public static void setUp() {
		position = new Vector3f(0, 0, 0);
		
		
	}
	
	
	public static void moveForward() {
		position.add(new Vector3f(0.1f, 0, 0));
	}

	public static void moveBackwards() {
		position.add(new Vector3f(-0.1f, 0, 0));
	}
	
	public static Matrix4f viewMatrix() {
		Matrix4f view = new Matrix4f();
		view.translate(position);
		return view;
	}

}
