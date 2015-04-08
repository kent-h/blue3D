package blue3D.type;

import blue3D.incomplete.network.Sendable;
import blue3D.incomplete.network.SendableHelper;

public class Vector3d implements Sendable, Vector3{

	public double x;
	public double y;
	public double z;
	
	public Vector3d(){
		//default for java is zero, so default=(0,0,0)
	}
	
	
	public Vector3d(double nx, double ny, double nz){
		x = nx;
		y = ny;
		z = nz;
	}
	
	
	public Vector3d(Vector3d toCopy){
		x = toCopy.x;
		y = toCopy.y;
		z = toCopy.z;
	}
	
	
	public void reset(){
		x=y=z=0;
	}
	
	
	public Vector3d add(Vector3f other){
		x += other.x;
		y += other.y;
		z += other.z;
		return this;
	}
	
	public Vector3d add(Vector3d other){
		x += other.x;
		y += other.y;
		z += other.z;
		return this;
	}
	
	public Vector3d add(Vector3l other){
		x += other.x;
		y += other.y;
		z += other.z;
		return this;
	}
	
	
	public Vector3d sub(Vector3f other){
		x-=other.x;
		y-=other.y;
		z-=other.z;
		return this;
	}
	
	public Vector3d sub(Vector3d other){
		x-=other.x;
		y-=other.y;
		z-=other.z;
		return this;
	}
	
	public Vector3d sub(Vector3l other){
		x-=other.x;
		y-=other.y;
		z-=other.z;
		return this;
	}
	
	
	public Vector3d set(Vector3d other){
		x = other.x;
		y = other.y;
		z = other.z;
		return this;
	}

	public Vector3d set(double x, double y, double z) {
		this.x=x;
		this.y=y;
		this.z=z;
		return this;
	}
	
	
	/**
	 * compute the cross product
	 * @param left
	 * @param right
	 * @param dest where to store the result
	 * @return dest
	 */
	public static Vector3d cross(Vector3d left, Vector3d right, Vector3d dest){
		double
			destx= left.y*right.z-left.z*right.y,
			desty= left.z*right.x-left.x*right.z;
		dest.z = left.x*right.y-left.y*right.x;
		dest.y = desty;
		dest.x = destx;
		return dest;
	}
	
	
	public String toString(){
		return "("+x+", "+y+", "+z+") @ "+super.toString();
	}


	public boolean equals(Vector3d other) {
		return x==other.x && y==other.y && z==other.z;
	}
	
	
	public double distanceSquared(Vector3d other){
		double dx=other.x-x,
				dy=other.y-y,
				dz=other.z-z;
		return dx*dx+dy*dy+dz*dz;
	}
	
	
	public double distance(Vector3d other){
		double dx=other.x-x,
				dy=other.y-y,
				dz=other.z-z;
		return Math.sqrt(dx*dx+dy*dy+dz*dz);
	}
	
	/**
	 * @param other
	 * @return the dot product
	 */
	public double dot(Vector3d other){
		return x*other.x+y*other.y+z*other.z;
	}
	
	static Vector3f tmp=new Vector3f();
	
	/**
	 * update the given matrix with the offset caused by this vector, counter-offset by the given offset (assumed to be the camera offset).
	 * @param matrix matrix to update
	 * @param offset 
	 */
	public void calculateOffsetMatrix(Matrix4f matrix){
		tmp.x=(float) x;
		tmp.y=(float) y;
		tmp.z=(float) z;
		matrix.translate(tmp);
	}
	
	
	public int getByteRepresentation(byte[] array, int start) {
		return SendableHelper.getByteRep(array, SendableHelper.getByteRep(array, SendableHelper.getByteRep(array, start, x),y),z);
		//adds x, then y, then z, returns the new end of the array
	}
	
	
	public int updateFromByteRep(byte[] array, int start) {
		x=SendableHelper.getDoubleRep(array, start);
		y=SendableHelper.getDoubleRep(array, start+8);
		z=SendableHelper.getDoubleRep(array, start+16);
		return start+24;//8*3 bytes removed
	}


	public Vector3d normalize() {
		double div=Math.sqrt(x*x+y*y+z*z);
		x/=div;
		y/=div;
		z/=div;
		return this;
	}


	public Vector3d setLength(double len) {
		double div=Math.sqrt(x*x+y*y+z*z)/len;
		x/=div;
		y/=div;
		z/=div;
		return this;
	}
	
	
	public Vector3d setLength(float length) {
		//re-route to double version 
		return setLength((double) length);
	}
	
	
	/**
	 * easier & faster to calculate than the length, it should only be used for comparison
	 * the length^2 is == the dot product this.itself
	 * @return |vector|^2.
	 */
	public double lengthSquared(){
		return x*x+y*y+z*z;
	}


	public double length() {
		return Math.sqrt(x*x+y*y+z*z);
	}


	public double angle(Vector3d other) {
		return Math.acos(dot(other)/(length()*other.length()));
	}


	public float getFloatX() {
		return (float) x;
	}


	public float getFloatY() {
		return (float) y;
	}


	public float getFloatZ() {
		return (float) z;
	}
	
}
