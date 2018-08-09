package math;

public class Vector2f {
	
	public float x, y;
	
	public Vector2f() {
		x = 0;
		y = 0;
	}

	public Vector2f(float x, float y) {
		this.x = x;
		this.y = y;
	}

	public void add(Vector2f other) {
		x += other.x;
		y += other.y;
	}

	public float length() {
		return (float) Math.sqrt(x * x + y * y);
	}

	public void normalize() {
		float length = length();
		x /= length;
		y /= length;
		
	}

}
