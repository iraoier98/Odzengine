package math;

public class Vector3f {
	
	public float x, y, z;
	
	public Vector3f() {
		x = 0;
		y = 0;
		z = 0;
	}
	
	public Vector3f(Vector2f xy, float z) {
		this.x = xy.x;
		this.y = xy.y;
		this.z = z;
	}
	
	public Vector3f(Vector2f xy) {
		this.x = xy.x;
		this.y = xy.y;
		this.z = 0;
	}

	public Vector3f(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public void add(Vector3f other) {
		x += other.x;
		y += other.y;
		z += other.z;
	}

	public float length() {
		return (float) Math.sqrt(x * x + y * y + z * z);
	}

	public void normalize() {
		float length = length();
		x /= length;
		y /= length;
		z /= length;
		
	}

}
