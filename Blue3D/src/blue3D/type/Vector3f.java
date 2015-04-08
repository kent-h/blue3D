package blue3D.type;


/**
 * A 3-element vector that is represented by single-precision floating point
 * x,y,z coordinates. If this value represents a normal, then it should be
 * normalized.
 * 
 */
public class Vector3f implements Vector3, java.io.Serializable {

	// Combatible with 1.1
	static final long serialVersionUID = -7031930069184524614L;
	/**
	 * The x coordinate.
	 */
	public float x;
	/**
	 * The y coordinate.
	 */
	public float y;
	/**
	 * The z coordinate.
	 */
	public float z;

	/**
	 * Constructs and initializes a Vector3f from the specified xyz coordinates.
	 * 
	 * @param x
	 *          the x coordinate
	 * @param y
	 *          the y coordinate
	 * @param z
	 *          the z coordinate
	 */
	public Vector3f(float x, float y, float z) {
		// super(x,y,z);
		this.x = x;
		this.y = y;
		this.z = z;
	}

	/**
	 * Constructs and initializes a Vector3f from the array of length 3.
	 * 
	 * @param v
	 *          the array of length 3 containing xyz in order
	 */
	public Vector3f(float[] v) {
		// super(v);
		x = v[0];
		y = v[1];
		z = v[2];
	}

	/**
	 * Constructs and initializes a Vector3f from the specified Vector3f.
	 * 
	 * @param v0
	 *          the Vector3f containing the initialization x y z data
	 */
	public Vector3f(Vector3f v0) {
		// super(v1);
		x = v0.x;
		y = v0.y;
		z = v0.z;
	}

	/**
	 * Constructs and initializes a Vector3f from the specified Vector3d.
	 * 
	 * @param v0
	 *          the Vector3d containing the initialization x y z data
	 */
	public Vector3f(Vector3d v0) {
		// super(v1);
		x = (float) v0.x;
		y = (float) v0.y;
		z = (float) v0.z;
	}

	/**
	 * Constructs and initializes a Vector3f from the specified Vector3l.
	 * 
	 * @param v0
	 *          the Vector3l containing the initialization x y z data
	 */
	public Vector3f(Vector3l v0) {
		x = v0.x;
		y = v0.y;
		z = v0.z;
	}

	/**
	 * Constructs and initializes a Vector3f to (0,0,0).
	 */
	public Vector3f() {
//		super();
	}

	/**
	 * Returns the squared length of this vector.
	 * 
	 * @return the squared length of this vector
	 */
	public final float lengthSquared() {
		return (this.x * this.x + this.y * this.y + this.z * this.z);
	}

	/**
	 * Returns the length of this vector.
	 * 
	 * @return the length of this vector
	 */
	public final float length() {
		return (float) Math.sqrt(this.x * this.x + this.y * this.y + this.z
				* this.z);
	}
	
	
	public float distanceSquared(Vector3f other){
		final float dx=other.x-x,
				dy=other.y-y,
				dz=other.z-z;
		return dx*dx+dy*dy+dz*dz;
	}
	
	
	public float distance(Vector3f other){
		double dx=other.x-x,
				dy=other.y-y,
				dz=other.z-z;
		return (float) Math.sqrt(dx*dx+dy*dy+dz*dz);
	}
	
	
	/**
	 * Sets this vector to be the vector cross product of vectors v1 and v2.
	 * 
	 * @param v1
	 *          the first vector
	 * @param v2
	 *          the second vector
	 */
	public final Vector3f cross(Vector3f v1, Vector3f v2) {
		float x = v1.y * v2.z - v1.z * v2.y,
					y = v2.x * v1.z - v2.z * v1.x;
		this.z = v1.x * v2.y - v1.y * v2.x;
		this.y = y;
		this.x = x;
		return this;
	}
	
	/**
	 * Sets this vector to be the cross product of the vectors v1-v0 and v2-v0.
	 * 
	 * @param v1
	 *          the first vector
	 * @param v2
	 *          the second vector
	 */
	public final Vector3f perpendicular(Vector3f v0, Vector3f v1, Vector3f v2) {
		Vector3f s1=new Vector3f().sub(v1, v0);
		Vector3f s2=new Vector3f().sub(v2, v0);
		
		float x = s1.y * s2.z - s1.z * s2.y,
					y = s2.x * s1.z - s2.z * s1.x;
		this.z = s1.x * s2.y - s1.y * s2.x;
		this.y = y;
		this.x = x;
		return this;
	}
	
	/**
	 * Sets this vector to be the cross product of the vectors v1-v0 and v2-v0.
	 * 
	 * @param v1
	 *          the first vector
	 * @param v2
	 *          the second vector
	 */
	public final Vector3f perpendicular(Vector3l v0, Vector3l v1, Vector3l v2) {
		Vector3l s1=new Vector3l(v1).sub(v0);
		Vector3l s2=new Vector3l(v2).sub(v0);
		
		float x = s1.y * s2.z - s1.z * s2.y,
					y = s2.x * s1.z - s2.z * s1.x;
		this.z = s1.x * s2.y - s1.y * s2.x;
		this.y = y;
		this.x = x;
		return this;
	}

	/**
	 * Computes the dot product of this vector and vector v1.
	 * 
	 * @param v1
	 *          the other vector
	 * @return the dot product of this vector and v1
	 */
	public final float dot(Vector3f v1) {
		return x * v1.x + y * v1.y + z * v1.z;
	}
	
	/**
	 * Computes the dot product of this vector and vector v1.
	 * 
	 * @param v1
	 *          the other vector
	 * @return the dot product of this vector and v1
	 */
	public final float dot(Vector3d v1) {
		return (float) (x * v1.x + y * v1.y + z * v1.z);
	}

	/**
	 * Sets the value of this vector to the normalization of vector v1.
	 * 
	 * @param v1
	 *          the un-normalized vector
	 */
	public final Vector3f normalize(Vector3f v1) {
		float norm;

		norm = (float) (1.0 / Math.sqrt(v1.x * v1.x + v1.y * v1.y + v1.z * v1.z));
		this.x = v1.x * norm;
		this.y = v1.y * norm;
		this.z = v1.z * norm;
		return this;
	}

	/**
	 * Normalizes this vector in place.
	 */
	public final Vector3f normalize() {
		float norm;

		norm = (float) (1.0 / Math.sqrt(this.x * this.x + this.y * this.y + this.z
				* this.z));
		this.x *= norm;
		this.y *= norm;
		this.z *= norm;
		return this;
	}

	/**
	 * Returns the angle in radians between this vector and the vector parameter;
	 * the return value is constrained to the range [0,PI].
	 * 
	 * @param v1
	 *          the other vector
	 * @return the angle in radians in the range [0,PI]
	 */
	public final float angle(Vector3f v1) {
		double vDot = this.dot(v1) / (this.length() * v1.length());
		if (vDot < -1.0)
			vDot = -1.0;
		if (vDot > 1.0)
			vDot = 1.0;
		return ((float) (Math.acos(vDot)));
	}

	/**
	 * Returns a string that contains the values of this Vector3f. The form is
	 * (x,y,z).
	 * 
	 * @return the String representation
	 */
	public String toString() {
		return "(" + this.x + ", " + this.y + ", " + this.z + ")";
	}

	/**
	 * Sets the value of this tuple to the specified xyz coordinates.
	 * 
	 * @param x
	 *          the x coordinate
	 * @param y
	 *          the y coordinate
	 * @param z
	 *          the z coordinate
	 */
	public final Vector3f set(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
		return this;
	}

	/**
	 * Sets the value of this tuple to the xyz coordinates specified in the array
	 * of length 3.
	 * 
	 * @param t
	 *          the array of length 3 containing xyz in order
	 */
	public final Vector3f set(float[] t) {
		this.x = t[0];
		this.y = t[1];
		this.z = t[2];
		return this;
	}

	/**
	 * Sets the value of this tuple to the value of tuple t1.
	 * 
	 * @param t
	 *          the tuple to be copied
	 */
	public final Vector3f set(Vector3f t) {
		this.x = t.x;
		this.y = t.y;
		this.z = t.z;
		return this;
	}
	
	/**
	 * Sets the value of this tuple to the value of tuple t1.
	 * 
	 * @param t
	 *          the tuple to be copied
	 */
	public final Vector3f set(Vector3l t) {
		this.x = t.x;
		this.y = t.y;
		this.z = t.z;
		return this;
	}

	/**
	 * Gets the value of this tuple and copies the values into t.
	 * 
	 * @param t
	 *          the array of length 3 into which the values are copied
	 */
	public final float[] get(float[] t) {
		t[0] = this.x;
		t[1] = this.y;
		t[2] = this.z;
		return t;
	}

	/**
	 * Gets the value of this tuple and copies the values into t.
	 * 
	 * @param t
	 *          the Vector3f object into which the values of this object are
	 *          copied
	 */
	public final Vector3f get(Vector3f t) {
		t.x = this.x;
		t.y = this.y;
		t.z = this.z;
		return t;
	}

	/**
	 * Sets the value of this tuple to the vector sum of tuples t1 and t2.
	 * 
	 * @param t1
	 *          the first tuple
	 * @param t2
	 *          the second tuple
	 */
	public final Vector3f add(Vector3f t1, Vector3f t2) {
		this.x = t1.x + t2.x;
		this.y = t1.y + t2.y;
		this.z = t1.z + t2.z;
		return this;
	}

	/**
	 * Sets the value of this tuple to the vector sum of itself and tuple t1.
	 * 
	 * @param t1
	 *          the other tuple
	 */
	public final Vector3f add(Vector3f t1) {
		this.x += t1.x;
		this.y += t1.y;
		this.z += t1.z;
		return this;
	}

	/**
	 * Sets the value of this tuple to the vector sum of itself and tuple t1.
	 * 
	 * @param t1
	 *          the other tuple
	 */
	public final Vector3f add(Vector3d t1) {
		this.x += t1.x;
		this.y += t1.y;
		this.z += t1.z;
		return this;
	}
	
	/**
	 * Sets the value of this tuple to the vector sum of itself and tuple t1.
	 * 
	 * @param t1
	 *          the other tuple
	 */
	public final Vector3f add(Vector3l t1) {
		this.x += t1.x;
		this.y += t1.y;
		this.z += t1.z;
		return this;
	}
	
	/**
	 * Sets the value of this tuple to the vector difference of tuples t1 and t2
	 * (this = t1 - t2).
	 * 
	 * @param t1
	 *          the first tuple
	 * @param t2
	 *          the second tuple
	 */
	public final Vector3f sub(Vector3f t1, Vector3f t2) {
		this.x = t1.x - t2.x;
		this.y = t1.y - t2.y;
		this.z = t1.z - t2.z;
		return this;
	}

	/**
	 * Sets the value of this tuple to the vector difference of itself and tuple
	 * t1 (this = this - t1) .
	 * 
	 * @param t1
	 *          the other tuple
	 */
	public final Vector3f sub(Vector3f t1) {
		x -= t1.x;
		y -= t1.y;
		z -= t1.z;
		return this;
	}
	
	/**
	 * Sets the value of this tuple to the vector difference of itself and tuple
	 * t1 (this = this - t1) .
	 * 
	 * @param t1
	 *          the other tuple
	 */
	public final Vector3f sub(Vector3d t1) {
		x -= t1.x;
		y -= t1.y;
		z -= t1.z;
		return this;
	}
	
	/**
	 * Sets the value of this tuple to the vector difference of itself and tuple
	 * t1 (this = this - t1) .
	 * 
	 * @param t1
	 *          the other tuple
	 */
	public final Vector3f sub(Vector3l t1) {
		this.x -= t1.x;
		this.y -= t1.y;
		this.z -= t1.z;
		return this;
	}

	/**
	 * Sets the value of this tuple to the negation of tuple t1.
	 * 
	 * @param t1
	 *          the source tuple
	 */
	public final Vector3f setNegation(Vector3f t1) {
		this.x = -t1.x;
		this.y = -t1.y;
		this.z = -t1.z;
		return this;
	}

	/**
	 * Negates the value of this tuple in place.
	 */
	public final Vector3f negate() {
		this.x = -this.x;
		this.y = -this.y;
		this.z = -this.z;
		return this;
	}

	/**
	 * Sets the value of this vector to the scalar multiplication of tuple t1.
	 * 
	 * @param s
	 *          the scalar value
	 * @param t1
	 *          the source tuple
	 */
	public final Vector3f scale(float s, Vector3f t1) {
		this.x = s * t1.x;
		this.y = s * t1.y;
		this.z = s * t1.z;
		return this;
	}

	/**
	 * Sets the value of this tuple to the scalar multiplication of the scale
	 * factor with this.
	 * 
	 * @param s
	 *          the scalar value
	 */
	public final Vector3f scale(float s) {
		this.x *= s;
		this.y *= s;
		this.z *= s;
		return this;
	}

	/**
	 * Sets the value of this tuple to the scalar multiplication of tuple t1 and
	 * then adds tuple t2 (this = s*t1 + t2).
	 * 
	 * @param s
	 *          the scalar value
	 * @param t1
	 *          the tuple to be scaled and added
	 * @param t2
	 *          the tuple to be added without a scale
	 */
	public final Vector3f scaleAdd(float s, Vector3f t1, Vector3f t2) {
		this.x = s * t1.x + t2.x;
		this.y = s * t1.y + t2.y;
		this.z = s * t1.z + t2.z;
		return this;
	}

	/**
	 * Sets the value of this tuple to the scalar multiplication of itself and
	 * then adds tuple t1 (this = s*this + t1).
	 * 
	 * @param s
	 *          the scalar value
	 * @param t1
	 *          the tuple to be added
	 */
	public final Vector3f scaleAdd(float s, Vector3f t1) {
		this.x = s * this.x + t1.x;
		this.y = s * this.y + t1.y;
		this.z = s * this.z + t1.z;
		return this;
	}

	/**
	 * Returns true if the Object t1 is of type Vector3f and all of the data
	 * members of t1 are equal to the corresponding data members in this Vector3f.
	 * 
	 * @param t1
	 *          the vector with which the comparison is made
	 * @return true or false
	 */
	public boolean equals(Vector3f t1) {
		try {
			return (this.x == t1.x && this.y == t1.y && this.z == t1.z);
		} catch (NullPointerException e2) {
			return false;
		}
	}

	/**
	 * Returns true if the Object t1 is of type Vector3f and all of the data
	 * members of t1 are equal to the corresponding data members in this Vector3f.
	 * 
	 * @param t1
	 *          the Object with which the comparison is made
	 * @return true or false
	 */
	public boolean equals(Object t1) {
		try {
			Vector3f t2 = (Vector3f) t1;
			return (this.x == t2.x && this.y == t2.y && this.z == t2.z);
		} catch (NullPointerException e2) {
			return false;
		} catch (ClassCastException e1) {
			return false;
		}
	}

	/**
	 * Returns true if the L-infinite distance between this tuple and tuple t1 is
	 * less than or equal to the epsilon parameter, otherwise returns false. The
	 * L-infinite distance is equal to MAX[abs(x1-x2), abs(y1-y2), abs(z1-z2)].
	 * 
	 * @param t1
	 *          the tuple to be compared to this tuple
	 * @param epsilon
	 *          the threshold value
	 * @return true or false
	 */
	public boolean epsilonEquals(Vector3f t1, float epsilon) {
		float diff;

		diff = x - t1.x;
		if (Float.isNaN(diff))
			return false;
		if ((diff < 0 ? -diff : diff) > epsilon)
			return false;

		diff = y - t1.y;
		if (Float.isNaN(diff))
			return false;
		if ((diff < 0 ? -diff : diff) > epsilon)
			return false;

		diff = z - t1.z;
		if (Float.isNaN(diff))
			return false;
		if ((diff < 0 ? -diff : diff) > epsilon)
			return false;

		return true;

	}

	/**
	 * Clamps the tuple parameter to the range [low, high] and places the values
	 * into this tuple.
	 * 
	 * @param min
	 *          the lowest value in the tuple after clamping
	 * @param max
	 *          the highest value in the tuple after clamping
	 * @param t
	 *          the source tuple, which will not be modified
	 */
	public final Vector3f clamp(float min, float max, Vector3f t) {
		if (t.x > max) {
			x = max;
		} else if (t.x < min) {
			x = min;
		} else {
			x = t.x;
		}

		if (t.y > max) {
			y = max;
		} else if (t.y < min) {
			y = min;
		} else {
			y = t.y;
		}

		if (t.z > max) {
			z = max;
		} else if (t.z < min) {
			z = min;
		} else {
			z = t.z;
		}
		return this;
	}

	/**
	 * Clamps the minimum value of the tuple parameter to the min parameter and
	 * places the values into this tuple.
	 * 
	 * @param min
	 *          the lowest value in the tuple after clamping
	 * @param t
	 *          the source tuple, which will not be modified
	 */
	public final Vector3f clampMin(float min, Vector3f t) {
		x = t.x<min?min:t.x;
		y = t.y<min?min:t.y;
		z = t.z<min?min:t.z;
		return this;
	}

	/**
	 * Clamps the maximum value of the tuple parameter to the max parameter and
	 * places the values into this tuple.
	 * 
	 * @param max
	 *          the highest value in the tuple after clamping
	 * @param t
	 *          the source tuple, which will not be modified
	 */
	public final Vector3f clampMax(float max, Vector3f t) {
		x = t.x>max?max:t.x;
		y = t.y>max?max:t.y;
		z = t.z>max?max:t.z;
		return this;
	}

	/**
	 * Sets each component of the tuple parameter to its absolute value and places
	 * the modified values into this tuple.
	 * 
	 * @param t
	 *          the source tuple, which will not be modified
	 */
	public final Vector3f absolute(Vector3f t) {
		x = Math.abs(t.x);
		y = Math.abs(t.y);
		z = Math.abs(t.z);
		return this;
	}

	/**
	 * Clamps this tuple to the range [low, high].
	 * 
	 * @param min
	 *          the lowest value in this tuple after clamping
	 * @param max
	 *          the highest value in this tuple after clamping
	 */
	public final Vector3f clamp(float min, float max) {
		if (x > max) {
			x = max;
		} else if (x < min) {
			x = min;
		}

		if (y > max) {
			y = max;
		} else if (y < min) {
			y = min;
		}

		if (z > max) {
			z = max;
		} else if (z < min) {
			z = min;
		}
		return this;
	}

	/**
	 * Clamps the minimum value of this tuple to the min parameter.
	 * 
	 * @param min
	 *          the lowest value in this tuple after clamping
	 */
	public final Vector3f clampMin(float min) {
		if (x < min)
			x = min;
		if (y < min)
			y = min;
		if (z < min)
			z = min;
		return this;
	}

	/**
	 * Clamps the maximum value of this tuple to the max parameter.
	 * 
	 * @param max
	 *          the highest value in the tuple after clamping
	 */
	public final Vector3f clampMax(float max) {
		if (x > max)
			x = max;
		if (y > max)
			y = max;
		if (z > max)
			z = max;
		return this;
	}

	/**
	 * Sets each component of this tuple to its absolute value.
	 */
	public final Vector3f absolute() {
		x = Math.abs(x);
		y = Math.abs(y);
		z = Math.abs(z);
		return this;
	}

	/**
	 * Linearly interpolates between tuples t1 and t2 and places the result into
	 * this tuple: this = (1-alpha)*t1 + alpha*t2.
	 * 
	 * @param t1
	 *          the first tuple
	 * @param t2
	 *          the second tuple
	 * @param alpha
	 *          the alpha interpolation parameter
	 */
	public final Vector3f interpolate(Vector3f t1, Vector3f t2, float alpha) {
		this.x = (1 - alpha) * t1.x + alpha * t2.x;
		this.y = (1 - alpha) * t1.y + alpha * t2.y;
		this.z = (1 - alpha) * t1.z + alpha * t2.z;
		return this;
	}

	/**
	 * Linearly interpolates between this tuple and tuple t1 and places the result
	 * into this tuple: this = (1-alpha)*this + alpha*t1.
	 * 
	 * @param t1
	 *          the first tuple
	 * @param alpha
	 *          the alpha interpolation parameter
	 */
	public final Vector3f interpolate(Vector3f t1, float alpha) {
		this.x = (1 - alpha) * this.x + alpha * t1.x;
		this.y = (1 - alpha) * this.y + alpha * t1.y;
		this.z = (1 - alpha) * this.z + alpha * t1.z;
		return this;
	}

	/**
	 * Creates a new object of the same class as this object.
	 * 
	 * @return a clone of this instance.
	 * @exception OutOfMemoryError
	 *              if there is not enough memory.
	 * @see java.lang.Cloneable
	 * @since vecmath 1.3
	 */
	public Object clone() {
		// Since there are no arrays we can just use Object.clone()
		try {
			return super.clone();
		} catch (CloneNotSupportedException e) {
			// this shouldn't happen, since we are Cloneable
			throw new InternalError();
		}
	}

	/**
	 * Get the <i>x</i> coordinate.
	 * 
	 * @return the <i>x</i> coordinate.
	 * 
	 * @since vecmath 1.5
	 */
	public final float getX() {
		return x;
	}

	/**
	 * Set the <i>x</i> coordinate.
	 * 
	 * @param x
	 *          value to <i>x</i> coordinate.
	 * 
	 * @since vecmath 1.5
	 */
	public final Vector3f setX(float x) {
		this.x = x;
		return this;
	}

	/**
	 * Get the <i>y</i> coordinate.
	 * 
	 * @return the <i>y</i> coordinate.
	 * 
	 * @since vecmath 1.5
	 */
	public final float getY() {
		return y;
	}

	/**
	 * Set the <i>y</i> coordinate.
	 * 
	 * @param y
	 *          value to <i>y</i> coordinate.
	 * 
	 * @since vecmath 1.5
	 */
	public final Vector3f setY(float y) {
		this.y = y;
		return this;
	}

	/**
	 * Get the <i>z</i> coordinate.
	 * 
	 * @return the <i>z</i> coordinate
	 * 
	 * @since vecmath 1.5
	 */
	public final float getZ() {
		return z;
	}

	/**
	 * Set the <i>Z</i> coordinate.
	 * 
	 * @param z
	 *          value to <i>z</i> coordinate.
	 * 
	 * @since vecmath 1.5
	 */
	public final Vector3f setZ(float z) {
		this.z = z;
		return this;
	}

	
	public float getFloatX() {
		return x;
	}

	
	public float getFloatY() {
		return y;
	}

	
	public float getFloatZ() {
		return z;
	}

	
	public Vector3f setLength(double length) {
		float div=(float) (Math.sqrt(x*x+y*y+z*z)/length);
		x/=div;
		y/=div;
		z/=div;
		return this;
	}
	
	
	/**
	 * update the given matrix with the offset caused by this vector, counter-offset by the given offset (assumed to be the camera offset).
	 * @param matrix matrix to update
	 * @param offset 
	 */
	public Matrix4f calculateOffsetMatrix(Matrix4f matrix){
		return matrix.translate(this);
	}

}

// public class Vector3f extends org.lwjgl.util.vector.Vector3f implements
// Vector3{
//
//
// public Vector3f(){
//
// }
//
// public Vector3f(float x, float y, float z) {
// this.x=x;
// this.y=y;
// this.z=z;
// }
//
//
// public Vector3f(Vector3f other) {
// x=other.x;
// y=other.y;
// z=other.z;
// }
//
//
// public Vector3f add(Vector3f other){
// x+=other.x;
// y+=other.y;
// z+=other.z;
// return this;
// }
//
//
// /**
// * set this vector to the sum of t0 and t1
// * @param t0
// * @param t1
// * @return this
// */
// public Vector3f add(Vector3f t0, Vector3f t1){
// x=t0.x+t1.x;
// y=t0.y+t1.y;
// z=t0.z+t1.z;
// return this;
// }
//
//
// public Vector3f sub(Vector3f other){
// x-=other.x;
// y-=other.y;
// z-=other.z;
// return this;
// }
//
//
// /**
// * set this vector to the sum of t0 and t1
// * @param t0
// * @param t1
// * @return this
// */
// public Vector3f sub(Vector3f t0, Vector3f t1){
// x=t0.x-t1.x;
// y=t0.y-t1.y;
// z=t0.z-t1.z;
// return this;
// }
//
//
// public Vector3f scale(float scale, Vector3f other){
// x=other.x*scale;
// y=other.y*scale;
// z=other.z*scale;
// return this;
// }
//
//
// //this = s*t1 + t2
// public Vector3f scaleAdd(float multiplier, Vector3f scale, Vector3f add){
// x=scale.x*multiplier+add.x;
// y=scale.y*multiplier+add.y;
// z=scale.z*multiplier+add.z;
// return this;
// }
//
//
// public Vector3f absolute(){
// x=Math.abs(x);
// y=Math.abs(y);
// z=Math.abs(z);
// return this;
// }
//
//
// public Vector3f absolute(Vector3f absoluteOf){
// x=Math.abs(absoluteOf.x);
// y=Math.abs(absoluteOf.y);
// z=Math.abs(absoluteOf.z);
// return this;
// }
//
//
// public Vector3f interpolate(Vector3f vec0, Vector3f vec1, float alpha){
// float invAlpha=1-alpha;
// x=invAlpha*vec0.x+alpha*vec1.x;
// y=invAlpha*vec0.y+alpha*vec1.y;
// z=invAlpha*vec0.z+alpha*vec1.z;
// return this;
// }
//
//
// public Vector3f normalize(){
// float div=(float) Math.sqrt(x*x+y*y+z*z);
// // if (div==0){
// // new RuntimeException().printStackTrace();
// // System.out.println("found!!!!!!!!!!!!!!!!!!!!!!!x"+x+"y"+y+"z"+z);
// // }else{
// // System.out.println("not found........................");
// // }
// x/=div;
// y/=div;
// z/=div;
// return this;
// }
//
//
// /**
// * sets the value of this vector to the normalization of vector other.
// * THIS IS DIFFERENT THAN normalise()!!!
// * CHANGE THE NAME OF THIS LATER!!!
// * @param other
// * @return
// */
// public Vector3f normalize(Vector3f other){
// float div=(float) Math.sqrt(other.x*other.x+other.y*other.y+other.z*other.z);
// x=other.x/div;
// y=other.y/div;
// z=other.z/div;
// return this;
// }
//
//
// public double dot(Vector3d other){
// return x*other.x+y*other.y+z*other.z;
// }
//
//
// public Vector3f setLength(double length) {
// float dist=(float) (StrictMath.sqrt(x*x+y*y+z*z)/length);
// x=x/dist;
// y=y/dist;
// z=z/dist;
// return this;
// }
//
//
// /**
// * set this vector to the cross product of v0 and v1
// * @param v0
// * @param v1
// * @return this
// */
// public Vector3f cross(Vector3f v0, Vector3f v1){
// float tmpx=v0.y*v1.z-v0.z*v1.y;
// float tmpy=v0.z*v1.x-v0.x*v1.z;
// z =v0.x*v1.y-v0.y*v1.x;
// y=tmpy;
// x=tmpx;
// // System.out.println(v0.toString()+v1);
// return this;
// }
//
//
// public float dot(Vector3f other){
// return x*other.x+y*other.y+z*other.z;
// }
//
//
// /**
// * this is a utility method, it is not recommended to over-use it, as both
// vectors must both be normalized first (which is a slow operation).
// * @param other
// * @return the angle between this and other
// */
// public float angle(Vector3f other){
// return (float) Math.acos(dot(other)/(length()*other.length()));
// }
//
//
// public Vector3f setNegation(Vector3f toNegate){
// x=-toNegate.x;
// y=-toNegate.y;
// z=-toNegate.z;
// return this;
// }
//
// }

