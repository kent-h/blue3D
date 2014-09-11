package blue3D.type;

import java.nio.FloatBuffer;

public class Matrix4f implements Matrix {

	public float 
		m00=1, 
		m01=0,
		m02=0, 
		m03=0, 
		m10=0, 
		m11=1, 
		m12=0, 
		m13=0, 
		m20=0, 
		m21=0, 
		m22=1, 
		m23=0, 
		m30=0, 
		m31=0, 
		m32=0, 
		m33=1;

	/**
	 * Construct a new matrix, initialized to the identity.
	 */
	public Matrix4f() {
		
	}

	public Matrix4f(final Matrix4f src) {
		set(src);
	}

	/**
	 * Returns a string representation of this matrix
	 */
	public String toString() {
		StringBuilder buf = new StringBuilder();
		buf.append(m00).append(' ').append(m01).append(' ').append(m02).append(' ').append(m03).append('\n');
		buf.append(m10).append(' ').append(m11).append(' ').append(m12).append(' ').append(m13).append('\n');
		buf.append(m20).append(' ').append(m21).append(' ').append(m22).append(' ').append(m23).append('\n');
		buf.append(m30).append(' ').append(m31).append(' ').append(m32).append(' ').append(m33).append('\n');
		return buf.toString();
	}

	/**
	 * Set this matrix to be the identity matrix.
	 * @return this
	 */
	public Matrix4f setIdentity() {
		m01=m02=m03=m10=m12=m13=m20=m21=m23=m30=m31=m32=0;
		m00=m11=m22=m33=1;
		return this;
	}

	/**
	 * Set the given matrix to be the identity matrix.
	 * @param m The matrix to set to the identity
	 * @return m
	 */
	public static Matrix4f setIdentity(Matrix4f m) {
		return m.setIdentity();
	}

	/**
	 * Set this matrix to 0.
	 * @return this
	 */
	public Matrix4f setZero() {
		m00=m01=m02=m03=m10=m11=m12=m13=m20=m21=m22=m23=m30=m31=m32=m33=0;
		return this;
	}

	/**
	 * Set the given matrix to 0.
	 * @param m The matrix to set to 0
	 * @return m
	 */
	public static Matrix4f setZero(Matrix4f m) {
		return m.setZero();
	}

	/**
	 * Load from another matrix4f
	 * @param src The source matrix
	 * @return this
	 */
	public Matrix4f set(Matrix4f src) {
		m00=src.m00;
		m01=src.m01;
		m02=src.m02;
		m03=src.m03;
		m10=src.m10;
		m11=src.m11;
		m12=src.m12;
		m13=src.m13;
		m20=src.m20;
		m21=src.m21;
		m22=src.m22;
		m23=src.m23;
		m30=src.m30;
		m31=src.m31;
		m32=src.m32;
		m33=src.m33;
		return this;
	}

	/**
	 * Copy the source matrix to the destination matrix
	 * @param src The source matrix
	 * @param dest The destination matrix, or null of a new one is to be created
	 * @return The copied matrix
	 */
	public static Matrix4f set(Matrix4f src, Matrix4f dest) {
		if (dest == null)
			dest = new Matrix4f();
		return dest.set(src);
	}

	/**
	 * Load from a float buffer. The buffer stores the matrix in column major
	 * (OpenGL) order.
	 *
	 * @param buf A float buffer to read from
	 * @return this
	 */
	public Matrix4f load(FloatBuffer buf) {
		m00 = buf.get();
		m10 = buf.get();
		m20 = buf.get();
		m30 = buf.get();
		m01 = buf.get();
		m11 = buf.get();
		m21 = buf.get();
		m31 = buf.get();
		m02 = buf.get();
		m12 = buf.get();
		m22 = buf.get();
		m32 = buf.get();
		m03 = buf.get();
		m13 = buf.get();
		m23 = buf.get();
		m33 = buf.get();
		return this;
	}

	/**
	 * Load from a float buffer. The buffer stores the matrix in row major
	 * (maths) order.
	 *
	 * @param buf A float buffer to read from
	 * @return this
	 */
	public Matrix4f loadTranspose(FloatBuffer buf) {
		m00 = buf.get();
		m01 = buf.get();
		m02 = buf.get();
		m03 = buf.get();
		m10 = buf.get();
		m11 = buf.get();
		m12 = buf.get();
		m13 = buf.get();
		m20 = buf.get();
		m21 = buf.get();
		m22 = buf.get();
		m23 = buf.get();
		m30 = buf.get();
		m31 = buf.get();
		m32 = buf.get();
		m33 = buf.get();
		return this;
	}

	/**
	 * Store this matrix in a float buffer. The matrix is stored in column
	 * major (openGL) order.
	 * @param buf The buffer to store this matrix in
	 */
	public Matrix4f store(FloatBuffer buf) {
		buf.put(m00);
		buf.put(m10);
		buf.put(m20);
		buf.put(m30);
		buf.put(m01);
		buf.put(m11);
		buf.put(m21);
		buf.put(m31);
		buf.put(m02);
		buf.put(m12);
		buf.put(m22);
		buf.put(m32);
		buf.put(m03);
		buf.put(m13);
		buf.put(m23);
		buf.put(m33);
		return this;
	}

	/**
	 * Store this matrix in a float buffer. The matrix is stored in row
	 * major (maths) order.
	 * @param buf The buffer to store this matrix in
	 */
	public Matrix4f storeTranspose(FloatBuffer buf) {
		buf.put(m00);
		buf.put(m01);
		buf.put(m02);
		buf.put(m03);
		buf.put(m10);
		buf.put(m11);
		buf.put(m12);
		buf.put(m13);
		buf.put(m20);
		buf.put(m21);
		buf.put(m22);
		buf.put(m23);
		buf.put(m30);
		buf.put(m31);
		buf.put(m32);
		buf.put(m33);
		return this;
	}

	/**
	 * Store the rotation portion of this matrix in a float buffer. The matrix is stored in column
	 * major (openGL) order.
	 * @param buf The buffer to store this matrix in
	 */
	public Matrix4f store3f(FloatBuffer buf) {
		buf.put(m00);
		buf.put(m10);
		buf.put(m20);
		buf.put(m01);
		buf.put(m11);
		buf.put(m21);
		buf.put(m02);
		buf.put(m12);
		buf.put(m22);
		return this;
	}

	/**
	 * Add two matrices together and place the result in a third matrix.
	 * @param left The left source matrix
	 * @param right The right source matrix
	 * @param dest The destination matrix, or null if a new one is to be created
	 * @return the destination matrix
	 */
	public static Matrix4f add(Matrix4f left, Matrix4f right, Matrix4f dest) {
		if (dest == null)
			dest = new Matrix4f();
		dest.m00 = left.m00 + right.m00;
		dest.m10 = left.m10 + right.m10;
		dest.m20 = left.m20 + right.m20;
		dest.m30 = left.m30 + right.m30;
		dest.m01 = left.m01 + right.m01;
		dest.m11 = left.m11 + right.m11;
		dest.m21 = left.m21 + right.m21;
		dest.m31 = left.m31 + right.m31;
		dest.m02 = left.m02 + right.m02;
		dest.m12 = left.m12 + right.m12;
		dest.m22 = left.m22 + right.m22;
		dest.m32 = left.m32 + right.m32;
		dest.m03 = left.m03 + right.m03;
		dest.m13 = left.m13 + right.m13;
		dest.m23 = left.m23 + right.m23;
		dest.m33 = left.m33 + right.m33;
		return dest;
	}
	
	/**
	 * add the values of the other matrix to the values of this matrix
	 * @param other the addition source matrix
	 * @return this
	 */
	public Matrix4f add(Matrix4f other){
		m00+=other.m00;
		m01+=other.m01;
		m02+=other.m02;
		m03+=other.m03;
		m10+=other.m10;
		m11+=other.m11;
		m12+=other.m12;
		m13+=other.m13;
		m20+=other.m20;
		m21+=other.m21;
		m22+=other.m22;
		m23+=other.m23;
		m30+=other.m30;
		m31+=other.m31;
		m32+=other.m32;
		m33+=other.m33;
		return this;
	}

	/**
	 * Subtract the right matrix from the left and place the result in a third matrix.
	 * @param left The left source matrix
	 * @param right The right source matrix
	 * @param dest The destination matrix, or null if a new one is to be created
	 * @return the destination matrix
	 */
	public static Matrix4f sub(Matrix4f left, Matrix4f right, Matrix4f dest) {
		if (dest == null)
			dest = new Matrix4f();
		dest.m00 = left.m00 - right.m00;
		dest.m10 = left.m10 - right.m10;
		dest.m20 = left.m20 - right.m20;
		dest.m30 = left.m30 - right.m30;
		dest.m01 = left.m01 - right.m01;
		dest.m11 = left.m11 - right.m11;
		dest.m21 = left.m21 - right.m21;
		dest.m31 = left.m31 - right.m31;
		dest.m02 = left.m02 - right.m02;
		dest.m12 = left.m12 - right.m12;
		dest.m22 = left.m22 - right.m22;
		dest.m32 = left.m32 - right.m32;
		dest.m03 = left.m03 - right.m03;
		dest.m13 = left.m13 - right.m13;
		dest.m23 = left.m23 - right.m23;
		dest.m33 = left.m33 - right.m33;
		return dest;
	}
	
	
	/**
	 * subtract the values in the other matrix from the values in this matrix
	 * @param other the right source matrix
	 * @return this
	 */
	public Matrix4f sub(Matrix4f other){
		m00-=other.m00;
		m01-=other.m01;
		m02-=other.m02;
		m03-=other.m03;
		m10-=other.m10;
		m11-=other.m11;
		m12-=other.m12;
		m13-=other.m13;
		m20-=other.m20;
		m21-=other.m21;
		m22-=other.m22;
		m23-=other.m23;
		m30-=other.m30;
		m31-=other.m31;
		m32-=other.m32;
		m33-=other.m33;
		return this;
	}
	
	
	/**
	 * Multiply the right matrix by the left and place the result in a third matrix.
	 * @param left The left source matrix
	 * @param right The right source matrix
	 * @param dest The destination matrix, or null if a new one is to be created
	 * @return the destination matrix
	 */
	public static Matrix4f mul(Matrix4f left, Matrix4f right, Matrix4f dest) {
		if (dest == null)
			dest = new Matrix4f();
		float m00 = left.m00 * right.m00 + left.m01 * right.m10 + left.m02 * right.m20 + left.m03 * right.m30;
		float m01 = left.m00 * right.m01 + left.m01 * right.m11 + left.m02 * right.m21 + left.m03 * right.m31;
		float m02 = left.m00 * right.m02 + left.m01 * right.m12 + left.m02 * right.m22 + left.m03 * right.m32;
		float m03 = left.m00 * right.m03 + left.m01 * right.m13 + left.m02 * right.m23 + left.m03 * right.m33;
		float m10 = left.m10 * right.m00 + left.m11 * right.m10 + left.m12 * right.m20 + left.m13 * right.m30;
		float m11 = left.m10 * right.m01 + left.m11 * right.m11 + left.m12 * right.m21 + left.m13 * right.m31;
		float m12 = left.m10 * right.m02 + left.m11 * right.m12 + left.m12 * right.m22 + left.m13 * right.m32;
		float m13 = left.m10 * right.m03 + left.m11 * right.m13 + left.m12 * right.m23 + left.m13 * right.m33;
		float m20 = left.m20 * right.m00 + left.m21 * right.m10 + left.m22 * right.m20 + left.m23 * right.m30;
		float m21 = left.m20 * right.m01 + left.m21 * right.m11 + left.m22 * right.m21 + left.m23 * right.m31;
		float m22 = left.m20 * right.m02 + left.m21 * right.m12 + left.m22 * right.m22 + left.m23 * right.m32;
		float m23 = left.m20 * right.m03 + left.m21 * right.m13 + left.m22 * right.m23 + left.m23 * right.m33;
		float m30 = left.m30 * right.m00 + left.m31 * right.m10 + left.m32 * right.m20 + left.m33 * right.m30;
		float m31 = left.m30 * right.m01 + left.m31 * right.m11 + left.m32 * right.m21 + left.m33 * right.m31;
		float m32 = left.m30 * right.m02 + left.m31 * right.m12 + left.m32 * right.m22 + left.m33 * right.m32;
		dest.m33	= left.m30 * right.m03 + left.m31 * right.m13 + left.m32 * right.m23 + left.m33 * right.m33;
		dest.m32 = m32;
		dest.m31 = m31;
		dest.m30 = m30;
		dest.m23 = m23;
		dest.m22 = m22;
		dest.m21 = m21;
		dest.m20 = m20;
		dest.m13 = m13;
		dest.m12 = m12;
		dest.m11 = m11;
		dest.m10 = m10;
		dest.m03 = m03;
		dest.m02 = m02;
		dest.m01 = m01;
		dest.m00 = m00;
		return dest;
	}
	

	/**
	 * Transform a Vector by a matrix and return the result in a destination
	 * vector.
	 * @param left The left matrix
	 * @param right The right vector
	 * @param dest The destination vector, or null if a new one is to be created
	 * @return the destination vector
	 */
	public static Vector4f transform(Matrix4f left, Vector4f right, Vector4f dest) {
		if (dest == null)
			dest = new Vector4f();

		float x = left.m00 * right.x + left.m01 * right.y + left.m02 * right.z + left.m03 * right.w;
		float y = left.m10 * right.x + left.m11 * right.y + left.m12 * right.z + left.m13 * right.w;
		float z = left.m20 * right.x + left.m21 * right.y + left.m22 * right.z + left.m23 * right.w;
		float w = left.m30 * right.x + left.m31 * right.y + left.m32 * right.z + left.m33 * right.w;

		dest.x = x;
		dest.y = y;
		dest.z = z;
		dest.w = w;

		return dest;
	}

	/**
	 * Transpose this matrix
	 * @return this
	 */
	public Matrix4f transpose() {
		return transpose(this);
	}

//	/**
//	 * Translate this matrix
//	 * @param vec The vector to translate by
//	 * @return this
//	 */
//	public Matrix4f translate(Vector2f vec) {
//		return translate(vec, this);
//	}

	/**
	 * Translate this matrix
	 * @param vec The vector to translate by
	 * @return this
	 */
	public Matrix4f translate(Vector3f vec) {
		return translate(vec, this);
	}

	/**
	 * Scales this matrix
	 * @param vec The vector to scale by
	 * @return this
	 */
	public Matrix4f scale(Vector3f vec) {
		return scale(vec, this, this);
	}

	/**
	 * Scales the source matrix and put the result in the destination matrix
	 * @param vec The vector to scale by
	 * @param src The source matrix
	 * @param dest The destination matrix, or null if a new matrix is to be created
	 * @return The scaled matrix
	 */
	public static Matrix4f scale(Vector3f vec, Matrix4f src, Matrix4f dest) {
		if (dest == null)
			dest = new Matrix4f();
		dest.m00 = src.m00 * vec.x;
		dest.m10 = src.m10 * vec.x;
		dest.m20 = src.m20 * vec.x;
		dest.m30 = src.m30 * vec.x;
		dest.m01 = src.m01 * vec.y;
		dest.m11 = src.m11 * vec.y;
		dest.m21 = src.m21 * vec.y;
		dest.m31 = src.m31 * vec.y;
		dest.m02 = src.m02 * vec.z;
		dest.m12 = src.m12 * vec.z;
		dest.m22 = src.m22 * vec.z;
		dest.m32 = src.m32 * vec.z;
		return dest;
	}

	/**
	 * Rotates the matrix around the given axis the specified angle
	 * @param angle the angle, in radians.
	 * @param axis The vector representing the rotation axis. Must be normalized.
	 * @return this
	 */
	public Matrix4f rotate(float angle, Vector3f axis) {
		return rotate(angle, axis, this);
	}

	/**
	 * Rotates the matrix around the given axis the specified angle
	 * @param angle the angle, in radians.
	 * @param axis The vector representing the rotation axis. Must be normalized.
	 * @param dest The matrix to put the result, or null if a new matrix is to be created
	 * @return The rotated matrix
	 */
	public Matrix4f rotate(float angle, Vector3f axis, Matrix4f dest) {
		return rotate(angle, axis, this, dest);
	}

	/**
	 * Rotates the source matrix around the given axis the specified angle and
	 * put the result in the destination matrix.
	 * @param angle the angle, in radians.
	 * @param axis The vector representing the rotation axis. Must be normalized.
	 * @param src The matrix to rotate
	 * @param dest The matrix to put the result, or null if a new matrix is to be created
	 * @return The rotated matrix
	 */
	public static Matrix4f rotate(float angle, Vector3f axis, Matrix4f src, Matrix4f dest) {
		if (dest == null)
			dest = new Matrix4f();
		float c = (float) Math.cos(angle);
		float s = (float) Math.sin(angle);
		float oneminusc = 1.0f - c;
		float xy = axis.x*axis.y;
		float yz = axis.y*axis.z;
		float xz = axis.x*axis.z;
		float xs = axis.x*s;
		float ys = axis.y*s;
		float zs = axis.z*s;

		float f00 = axis.x*axis.x*oneminusc+c;
		float f01 = xy*oneminusc+zs;
		float f02 = xz*oneminusc-ys;
		// n[3] not used
		float f10 = xy*oneminusc-zs;
		float f11 = axis.y*axis.y*oneminusc+c;
		float f12 = yz*oneminusc+xs;
		// n[7] not used
		float f20 = xz*oneminusc+ys;
		float f21 = yz*oneminusc-xs;
		float f22 = axis.z*axis.z*oneminusc+c;

		float t00 = src.m00 * f00 + src.m01 * f01 + src.m02 * f02;
		float t01 = src.m10 * f00 + src.m11 * f01 + src.m12 * f02;
		float t02 = src.m20 * f00 + src.m21 * f01 + src.m22 * f02;
		float t03 = src.m30 * f00 + src.m31 * f01 + src.m32 * f02;
		float t10 = src.m00 * f10 + src.m01 * f11 + src.m02 * f12;
		float t11 = src.m10 * f10 + src.m11 * f11 + src.m12 * f12;
		float t12 = src.m20 * f10 + src.m21 * f11 + src.m22 * f12;
		float t13 = src.m30 * f10 + src.m31 * f11 + src.m32 * f12;
		dest.m02 = src.m00 * f20 + src.m01 * f21 + src.m02 * f22;
		dest.m12 = src.m10 * f20 + src.m11 * f21 + src.m12 * f22;
		dest.m22 = src.m20 * f20 + src.m21 * f21 + src.m22 * f22;
		dest.m32 = src.m30 * f20 + src.m31 * f21 + src.m32 * f22;
		dest.m00 = t00;
		dest.m10 = t01;
		dest.m20 = t02;
		dest.m30 = t03;
		dest.m01 = t10;
		dest.m11 = t11;
		dest.m21 = t12;
		dest.m31 = t13;
		return dest;
	}

	/**
	 * Translate this matrix and stash the result in another matrix
	 * @param vec The vector to translate by
	 * @param dest The destination matrix or null if a new matrix is to be created
	 * @return the translated matrix
	 */
	public Matrix4f translate(Vector3f vec, Matrix4f dest) {
		return translate(vec, this, dest);
	}

	/**
	 * Translate the source matrix and stash the result in the destination matrix
	 * @param vec The vector to translate by
	 * @param src The source matrix
	 * @param dest The destination matrix or null if a new matrix is to be created
	 * @return The translated matrix
	 */
	public static Matrix4f translate(Vector3f vec, Matrix4f src, Matrix4f dest) {
		if (dest == null)
			dest = new Matrix4f();
		
		dest.m00 += vec.x * src.m30;
		dest.m01 += vec.x * src.m31;
		dest.m02 += vec.x * src.m32;
		dest.m03 += vec.x * src.m33;
		dest.m10 += vec.y * src.m30;
		dest.m11 += vec.y * src.m31;
		dest.m12 += vec.y * src.m32;
		dest.m13 += vec.y * src.m33;
		dest.m20 += vec.z * src.m30;
		dest.m21 += vec.z * src.m31;
		dest.m22 += vec.z * src.m32;
		dest.m23 += vec.z * src.m33;
		
//		dest.m03 += src.m00 * vec.x + src.m01 * vec.y + src.m02 * vec.z;
//		dest.m13 += src.m10 * vec.x + src.m11 * vec.y + src.m12 * vec.z;
//		dest.m23 += src.m20 * vec.x + src.m21 * vec.y + src.m22 * vec.z;
//		dest.m33 += src.m30 * vec.x + src.m31 * vec.y + src.m32 * vec.z;

		return dest;
	}

//	/**
//	 * Translate this matrix and stash the result in another matrix
//	 * @param vec The vector to translate by
//	 * @param dest The destination matrix or null if a new matrix is to be created
//	 * @return the translated matrix
//	 */
//	public Matrix4f translate(Vector2f vec, Matrix4f dest) {
//		return translate(vec, this, dest);
//	}
//
//	/**
//	 * Translate the source matrix and stash the result in the destination matrix
//	 * @param vec The vector to translate by
//	 * @param src The source matrix
//	 * @param dest The destination matrix or null if a new matrix is to be created
//	 * @return The translated matrix
//	 */
//	public static Matrix4f translate(Vector2f vec, Matrix4f src, Matrix4f dest) {
//		if (dest == null)
//			dest = new Matrix4f();
//
//		dest.m30 += src.m00 * vec.x + src.m10 * vec.y;
//		dest.m31 += src.m01 * vec.x + src.m11 * vec.y;
//		dest.m32 += src.m02 * vec.x + src.m12 * vec.y;
//		dest.m33 += src.m03 * vec.x + src.m13 * vec.y;
//
//		return dest;
//	}

	/**
	 * Transpose this matrix and place the result in another matrix
	 * @param dest The destination matrix or null if a new matrix is to be created
	 * @return the transposed matrix
	 */
	public Matrix4f transpose(Matrix4f dest) {
		return transpose(this, dest);
	}

	/**
	 * Transpose the source matrix and place the result in the destination matrix
	 * @param src The source matrix
	 * @param dest The destination matrix or null if a new matrix is to be created
	 * @return the transposed matrix
	 */
	public static Matrix4f transpose(Matrix4f src, Matrix4f dest) {
		if (dest == null)
		   dest = new Matrix4f();
		float m00 = src.m00;
		float m01 = src.m01;
		float m02 = src.m02;
		float m03 = src.m03;
		float m10 = src.m10;
		float m11 = src.m11;
		float m12 = src.m12;
		float m13 = src.m13;
		float m20 = src.m20;
		float m21 = src.m21;
		float m22 = src.m22;
		float m23 = src.m23;
		float m30 = src.m30;
		float m31 = src.m31;
		float m32 = src.m32;
		float m33 = src.m33;

		dest.m00 = m00;
		dest.m10 = m01;
		dest.m20 = m02;
		dest.m30 = m03;
		dest.m01 = m10;
		dest.m11 = m11;
		dest.m21 = m12;
		dest.m31 = m13;
		dest.m02 = m20;
		dest.m12 = m21;
		dest.m22 = m22;
		dest.m32 = m23;
		dest.m03 = m30;
		dest.m13 = m31;
		dest.m23 = m32;
		dest.m33 = m33;

		return dest;
	}

	/**
	 * @return the determinant of the matrix
	 */
	public float determinant() {
		float f =
			m00
				* ((m11 * m22 * m33 + m21 * m32 * m13 + m31 * m12 * m23)
					- m31 * m22 * m13
					- m11 * m32 * m23
					- m21 * m12 * m33);
		f -= m10
			* ((m01 * m22 * m33 + m21 * m32 * m03 + m31 * m02 * m23)
				- m31 * m22 * m03
				- m01 * m32 * m23
				- m21 * m02 * m33);
		f += m20
			* ((m01 * m12 * m33 + m11 * m32 * m03 + m31 * m02 * m13)
				- m31 * m12 * m03
				- m01 * m32 * m13
				- m11 * m02 * m33);
		f -= m30
			* ((m01 * m12 * m23 + m11 * m22 * m03 + m21 * m02 * m13)
				- m21 * m12 * m03
				- m01 * m22 * m13
				- m11 * m02 * m23);
		return f;
	}

	/**
	 * Calculate the determinant of a 3x3 matrix
	 * @return result
	 */

	private static float determinant3x3(float t00, float t01, float t02,
				     float t10, float t11, float t12,
				     float t20, float t21, float t22)
	{
		return   t00 * (t11 * t22 - t12 * t21)
		       + t01 * (t12 * t20 - t10 * t22)
		       + t02 * (t10 * t21 - t11 * t20);
	}

	/**
	 * Invert this matrix
	 * @return this if successful, null otherwise
	 */
	public Matrix4f invert() {
		return invert(this, this);
	}

	/**
	 * Invert the source matrix and put the result in the destination
	 * @param src The source matrix
	 * @param dest The destination matrix, or null if a new matrix is to be created
	 * @return The inverted matrix if successful, null otherwise
	 */
	public static Matrix4f invert(Matrix4f src, Matrix4f dest) {
		float determinant = src.determinant();

		if (determinant != 0) {
			/*
			 * m00 m01 m02 m03
			 * m10 m11 m12 m13
			 * m20 m21 m22 m23
			 * m30 m31 m32 m33
			 */
			if (dest == null)
				dest = new Matrix4f();
			float determinant_inv = 1f/determinant;

			// first row
			float t00 =  determinant3x3(src.m11, src.m21, src.m31, src.m12, src.m22, src.m32, src.m13, src.m23, src.m33);
			float t01 = -determinant3x3(src.m01, src.m21, src.m31, src.m02, src.m22, src.m32, src.m03, src.m23, src.m33);
			float t02 =  determinant3x3(src.m01, src.m11, src.m31, src.m02, src.m12, src.m32, src.m03, src.m13, src.m33);
			float t03 = -determinant3x3(src.m01, src.m11, src.m21, src.m02, src.m12, src.m22, src.m03, src.m13, src.m23);
			// second row
			float t10 = -determinant3x3(src.m10, src.m20, src.m30, src.m12, src.m22, src.m32, src.m13, src.m23, src.m33);
			float t11 =  determinant3x3(src.m00, src.m20, src.m30, src.m02, src.m22, src.m32, src.m03, src.m23, src.m33);
			float t12 = -determinant3x3(src.m00, src.m10, src.m30, src.m02, src.m12, src.m32, src.m03, src.m13, src.m33);
			float t13 =  determinant3x3(src.m00, src.m10, src.m20, src.m02, src.m12, src.m22, src.m03, src.m13, src.m23);
			// third row
			float t20 =  determinant3x3(src.m10, src.m20, src.m30, src.m11, src.m21, src.m31, src.m13, src.m23, src.m33);
			float t21 = -determinant3x3(src.m00, src.m20, src.m30, src.m01, src.m21, src.m31, src.m03, src.m23, src.m33);
			float t22 =  determinant3x3(src.m00, src.m10, src.m30, src.m01, src.m11, src.m31, src.m03, src.m13, src.m33);
			float t23 = -determinant3x3(src.m00, src.m10, src.m20, src.m01, src.m11, src.m21, src.m03, src.m13, src.m23);
			// fourth row
			float t30 = -determinant3x3(src.m10, src.m20, src.m30, src.m11, src.m21, src.m31, src.m12, src.m22, src.m32);
			float t31 =  determinant3x3(src.m00, src.m20, src.m30, src.m01, src.m21, src.m31, src.m02, src.m22, src.m32);
			float t32 = -determinant3x3(src.m00, src.m10, src.m30, src.m01, src.m11, src.m31, src.m02, src.m12, src.m32);
			float t33 =  determinant3x3(src.m00, src.m10, src.m20, src.m01, src.m11, src.m21, src.m02, src.m12, src.m22);

			// transpose and divide by the determinant
			dest.m00 = t00*determinant_inv;
			dest.m11 = t11*determinant_inv;
			dest.m22 = t22*determinant_inv;
			dest.m33 = t33*determinant_inv;
			dest.m10 = t10*determinant_inv;
			dest.m01 = t01*determinant_inv;
			dest.m02 = t02*determinant_inv;
			dest.m20 = t20*determinant_inv;
			dest.m21 = t21*determinant_inv;
			dest.m12 = t12*determinant_inv;
			dest.m30 = t30*determinant_inv;
			dest.m03 = t03*determinant_inv;
			dest.m31 = t31*determinant_inv;
			dest.m13 = t13*determinant_inv;
			dest.m23 = t23*determinant_inv;
			dest.m32 = t32*determinant_inv;
			return dest;
		} else
			return null;
	}

	/**
	 * Negate this matrix
	 * @return this
	 */
	public Matrix negate() {
		return negate(this);
	}

	/**
	 * Negate this matrix and place the result in a destination matrix.
	 * @param dest The destination matrix, or null if a new matrix is to be created
	 * @return the negated matrix
	 */
	public Matrix4f negate(Matrix4f dest) {
		return negate(this, dest);
	}

	/**
	 * Negate this matrix and place the result in a destination matrix.
	 * @param src The source matrix
	 * @param dest The destination matrix, or null if a new matrix is to be created
	 * @return The negated matrix
	 */
	public static Matrix4f negate(Matrix4f src, Matrix4f dest) {
		if (dest == null)
			dest = new Matrix4f();

		dest.m00 = -src.m00;
		dest.m10 = -src.m10;
		dest.m20 = -src.m20;
		dest.m30 = -src.m30;
		dest.m01 = -src.m01;
		dest.m11 = -src.m11;
		dest.m21 = -src.m21;
		dest.m31 = -src.m31;
		dest.m02 = -src.m02;
		dest.m12 = -src.m12;
		dest.m22 = -src.m22;
		dest.m32 = -src.m32;
		dest.m03 = -src.m03;
		dest.m13 = -src.m13;
		dest.m23 = -src.m23;
		dest.m33 = -src.m33;

		return dest;
	}
	
	// start of changes
	
	public Matrix4f set(Matrix3f other){
		m00=other.m00;
		m10=other.m01;
		m20=other.m02;
		m01=other.m10;
		m11=other.m11;
		m21=other.m12;
		m02=other.m20;
		m12=other.m21;
		m22=other.m22;
		m30=m31=m32=m03=m13=m23=0;
		m33=1;
		return this;
	}
	
	
	public Matrix3f getRotationScale(Matrix3f dest){
		dest.m00=m00;
		dest.m01=m10;
		dest.m02=m20;
		
		dest.m10=m01;
		dest.m11=m11;
		dest.m12=m21;
		
		dest.m20=m02;
		dest.m21=m12;
		dest.m22=m22;
		return dest;
	}
	
//	public String toString(){
//		return m00+" "+m01+" "+m02+" "+m03+"\n"+
//				m10+" "+m11+" "+m12+" "+m13+"\n"+
//				m20+" "+m21+" "+m22+" "+m23+"\n"+
//				m30+" "+m31+" "+m32+" "+m33;
//	}
	
}
