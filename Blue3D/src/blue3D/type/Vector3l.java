package blue3D.type;

import blue3D.incomplete.network.Sendable;
import blue3D.incomplete.network.SendableHelper;

public class Vector3l implements Sendable, Vector3{
	
	
	//do not implement getters and setters for these
	public long x;
	public long y;
	public long z;
	
	private static Vector3f tmp=new Vector3f();//must not be required across functions
	
	
	/**
	 * creates a vector with values 0,0,0
	 */
	public Vector3l(){
		
	}
	
	/**
	 * create a vector with the given values
	 * @param nx
	 * @param ny
	 * @param nz
	 */
	public Vector3l(long nx, long ny, long nz){
		x = nx;
		y = ny;
		z = nz;
	}
	
	
	/**
	 * copy constructor
	 * @param other
	 */
	public Vector3l(Vector3l other){
		x = other.x;
		y = other.y;
		z = other.z;
	}
	
	
	/**
	 * update the given matrix with the offset caused by this vector.
	 * @param matrix matrix to update
	 */
	public Matrix4f calculateMatrix(Matrix4f matrix){
		if (matrix == null)
			matrix = new Matrix4f();
		
		matrix.m00 += x * matrix.m30;
		matrix.m01 += x * matrix.m31;
		matrix.m02 += x * matrix.m32;
		matrix.m03 += x * matrix.m33;
		matrix.m10 += y * matrix.m30;
		matrix.m11 += y * matrix.m31;
		matrix.m12 += y * matrix.m32;
		matrix.m13 += y * matrix.m33;
		matrix.m20 += z * matrix.m30;
		matrix.m21 += z * matrix.m31;
		matrix.m22 += z * matrix.m32;
		matrix.m23 += z * matrix.m33;
		
		//		dest.m03 += src.m00 * vec.x + src.m01 * vec.y + src.m02 * vec.z;
		//		dest.m13 += src.m10 * vec.x + src.m11 * vec.y + src.m12 * vec.z;
		//		dest.m23 += src.m20 * vec.x + src.m21 * vec.y + src.m22 * vec.z;
		//		dest.m33 += src.m30 * vec.x + src.m31 * vec.y + src.m32 * vec.z;
		
				return matrix;
	}
	
	
	/**
	 * update the given matrix with the offset caused by this vector, counter-offset by the given offset (assumed to be the camera offset).
	 * @param matrix matrix to update
	 * @param offset 
	 */
	public void calculateOffsetMatrix(Matrix4f matrix){
		tmp.x=x;
		tmp.y=y;
		tmp.z=z;
		matrix.translate(tmp);
	}
	
	
	/**
	 * add the value of the other vector to the value of this vector, and store in this vector.
	 * @param other
	 */
	public Vector3l add(Vector3f other){
		x += other.x;
		y += other.y;
		z += other.z;
		return this;
	}
	
	
	/**
	 * add the value of the other vector to the value of this vector, and store in this vector.
	 * @param other
	 */
	public Vector3l add(Vector3d other){
		x += other.x;
		y += other.y;
		z += other.z;
		return this;
	}
	
	
	/**
	 * add the value of the other vector to the value of this vector, and store in this vector.
	 * @param other
	 * @return 
	 */
	public Vector3l add(Vector3l other){
		x += other.x;
		y += other.y;
		z += other.z;
		
		return this;
	}
	
	public Vector3l sub(Vector3f other) {
		x -= other.x;
		y -= other.y;
		z -= other.z;
		return this;
	}
	
	public Vector3l sub(Vector3d other) {
		x -= other.x;
		y -= other.y;
		z -= other.z;
		return this;
	}
	
	public Vector3l sub(Vector3l other) {
		x -= other.x;
		y -= other.y;
		z -= other.z;
		return this;
	}
	
	/**
	 * sets this vector to (left-right)
	 * @param left
	 * @param right
	 * @return
	 */
	public Vector3l sub(Vector3l left, Vector3l right) {
		x = left.x-right.x;
		y = left.y-right.y;
		z = left.z-right.z;
		return this;
	}
	
	
	/**
	 * make this vector equal to the given vector
	 * @param other given vector
	 * @return 
	 */
	public Vector3l set(Vector3l other){
		x = other.x;
		y = other.y;
		z = other.z;
		return this;
	}
	
	
	/**
	 * make this vector equal to the given vector
	 * @param other given vector
	 * @return 
	 */
	public Vector3l set(Vector3f other) {
		x = (long) other.x;
		y = (long) other.y;
		z = (long) other.z;
		return this;
	}
	
	
	/**
	 * set vector to the given value.
	 * @param x the x value
	 * @param y the y value
	 * @param z the z value
	 */
	public void set(long x, long y, long z){
		this.x=x;
		this.y=y;
		this.z=z;
	}
	
	
	public Vector3l perpendicular(Vector3l v0, Vector3l v1, Vector3l v2){
		Vector3l s1=new Vector3l().sub(v1, v0);
		Vector3l s2=new Vector3l().sub(v2, v0);
		
		long x = s1.y * s2.z - s1.z * s2.y,
					y = s2.x * s1.z - s2.z * s1.x;
		this.z = s1.x * s2.y - s1.y * s2.x;
		this.y = y;
		this.x = x;
		return this;
	}
	
	
	public String toString(){
		return "("+x+", "+y+", "+z+")";
	}


	public boolean equals(Vector3l other) {
		return x==other.x && y==other.y && z==other.z;
	}


	public int getByteRepresentation(byte[] array, int start) {
		return SendableHelper.getByteRep(array, SendableHelper.getByteRep(array, SendableHelper.getByteRep(array, start, x),y),z);
		//adds x, then y, then z, returns the new end of the array
	}
	
	
	public int updateFromByteRep(byte[] array, int start) {
		x=SendableHelper.getLongRep(array, start);
		y=SendableHelper.getLongRep(array, start+8);
		z=SendableHelper.getLongRep(array, start+16);
		return start+24;//8*3 bytes removed
	}
	
	
	/**
	 * compute the cross product
	 * @param left
	 * @param right
	 * @param dest where to store the result
	 * @return dest
	 */
	public static Vector3l cross(Vector3l left, Vector3l right, Vector3l dest){
		long
			destx= left.y*right.z-left.z*right.y,
			desty= left.z*right.x-left.x*right.z;
		dest.z = left.x*right.y-left.y*right.x;
		dest.y = desty;
		dest.x = destx;
		return dest;
	}
	
	
	public final Vector3l cross(Vector3l v1, Vector3l v2) {
		long x = v1.y * v2.z - v1.z * v2.y,
					y = v2.x * v1.z - v2.z * v1.x;
		this.z = v1.x * v2.y - v1.y * v2.x;
		this.y = y;
		this.x = x;
		return this;
	}
	
	
	/**
	 * the dot product of vector3l
	 */
	public static float dot(Vector3l left, Vector3l right){
		return left.x*right.x+left.y*right.y+left.z*right.z;
	}
	
	
	public Vector3l scale(double amount) {
		x*=amount;
		y*=amount;
		z*=amount;
		return this;
	}

	public Vector3l setLength(double length) {
		double dist=StrictMath.sqrt(x*x+y*y+z*z)/length;
		x/=dist;
		y/=dist;
		z/=dist;
		return this;
	}
	
	
	public Vector3l normalize() {
		double dist=StrictMath.sqrt(x*x+y*y+z*z)*1000;
		x=(long) (x/dist);
		y=(long) (y/dist);
		z=(long) (z/dist);
		return this;
	}
	
	
	public float getLength() {
		return (float)StrictMath.sqrt(x*x+y*y+z*z);
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

	public float distanceSquared(Vector3l other){
		final float dx=other.x-x,
				dy=other.y-y,
				dz=other.z-z;
		return dx*dx+dy*dy+dz*dz;
	}
	
	
	public float distance(Vector3l other){
		double dx=other.x-x,
				dy=other.y-y,
				dz=other.z-z;
		return (float) Math.sqrt(dx*dx+dy*dy+dz*dz);
	}
	
}
























