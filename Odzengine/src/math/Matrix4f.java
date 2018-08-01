package math;

import java.nio.FloatBuffer;

import org.lwjgl.system.MemoryStack;

public class Matrix4f {
	
	private float m11, m12, m13, m14;
	private float m21, m22, m23, m24;
	private float m31, m32, m33, m34;
	private float m41, m42, m43, m44;
	
	public Matrix4f() {
		m11 = m22 = m33 = m44 = 1;
	}
	
	
	/**
	 * column major
	 * @return
	 */
	public FloatBuffer toFloatBuffer() {
		
		MemoryStack stack = MemoryStack.stackPush();
		FloatBuffer buf = stack.mallocFloat(4 * 4);
		
		buf.put(m11).put(m21).put(m31).put(m41);
		buf.put(m12).put(m22).put(m32).put(m42);
		buf.put(m13).put(m23).put(m33).put(m43);
		buf.put(m14).put(m24).put(m34).put(m44);
		buf.flip();
		MemoryStack.stackPop();
		
		return buf;
	}
	
	
	public static Matrix4f ortographic(float left, float right, float bot, float top, float near, float far) {
		
		Matrix4f matrix = new Matrix4f();
		
		matrix.m11 = 2 / (right - left);
		matrix.m14 = (right + left) / (right - left);
		matrix.m22 = 2 / (top - bot);
		matrix.m24 = (top + bot) / (top - bot);
		matrix.m33 = -2 / (far - near);
		matrix.m34 = (far + near) / (far - near);
		
		return matrix;
	}
	
	public static Matrix4f perspective(float aspectRatio, float fieldOfView, float near, float far) {
		
		Matrix4f matrix = new Matrix4f();
		
		float zm = far - near;
		float zp = far + near;
		
		matrix.m11 = (float) ((1 / Math.tan(fieldOfView / 2)) / aspectRatio);
		matrix.m22 = (float) (1 / Math.tan(fieldOfView / 2));
		matrix.m33 = -zp / zm;
		matrix.m34 = -2 * far * near / zm;
		matrix.m43 = -1;
		matrix.m44 = 0;
		
		return matrix;
		
	}
	

}
