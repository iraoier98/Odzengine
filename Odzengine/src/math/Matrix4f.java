package math;

import java.nio.FloatBuffer;

import org.lwjgl.system.MemoryStack;

public class Matrix4f {
	
	private float m11, m12, m13, m14;
	private float m21, m22, m23, m24;
	private float m31, m32, m33, m34;
	private float m41, m42, m43, m44;
	
	public Matrix4f() {
		m11 = 1;
		m22 = 1;
		m33 = 1;
		m44 = 1;
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
	
	public static Matrix4f transformation(Vector3f position, Vector3f rotation, Vector3f scale) {
		Matrix4f trans = new Matrix4f();
		trans.translate(position);
		trans.rotate(rotation.x, new Vector3f(1, 0, 0));
		trans.rotate(rotation.y, new Vector3f(0, 1, 0));
		trans.rotate(rotation.z, new Vector3f(0, 0, 1));
		trans.scale(scale);
		return trans;
	}
	
	
	/**
	 * Scales the source matrix and put the result in the destination matrix
	 * @param vec The vector to scale by
	 * @param src The source matrix
	 * @param dest The destination matrix, or null if a new matrix is to be created
	 * @return The scaled matrix
	 */
	public void scale(Vector3f vec) {
		m11 *= vec.x;
		m22 *= vec.y;
		m33 *= vec.z;
	}
	
	//m00

	/**
	 * Rotates the source matrix around the given axis the specified angle and
	 * put the result in the destination matrix.
	 * @param angle the angle, in radians.
	 * @param axis The vector representing the rotation axis. Must be normalized.
	 * @param src The matrix to rotate
	 * @param dest The matrix to put the result, or null if a new matrix is to be created
	 * @return The rotated matrix
	 */
	public void rotate(float angle, Vector3f axis) {
		float c = (float) Math.cos(Math.toRadians(angle));
        float s = (float) Math.sin(Math.toRadians(angle));
        if (axis.length() != 1f) {
            axis.normalize();
        }

        m11 = axis.x * axis.x * (1f - c) + c;
        m21 = axis.y * axis.x * (1f - c) + axis.z * s;
        m31 = axis.x * axis.z * (1f - c) - axis.y * s;
        m12 = axis.x * axis.y * (1f - c) - axis.z * s;
        m22 = axis.y * axis.y * (1f - c) + c;
        m32 = axis.y * axis.z * (1f - c) + axis.x * s;
        m13 = axis.x * axis.z * (1f - c) + axis.y * s;
        m23 = axis.y * axis.z * (1f - c) - axis.x * s;
        m33 = axis.z * axis.z * (1f - c) + c;
	}

	/**
	 * Translate the source matrix and stash the result in the destination matrix
	 * @param vec The vector to translate by
	 * @param src The source matrix
	 * @param dest The destination matrix or null if a new matrix is to be created
	 * @return The translated matrix
	 */
	public void translate(Vector3f vec) {
		m14 += vec.x;
		m24 += vec.y;
		m34 += vec.z;
	}


	@Override
	public String toString() {
		return	m11 + " " + m12 + " " + m13 + " " + m14 + "\n" + 
				m21 + " " + m22 + " " + m23 + " " + m24 + "\n" + 
				m31 + " " + m32 + " " + m33 + " " + m43 + "\n" + 
				m41 + " " + m42 + " " + m43 + " " + m44;
		
	}





}
