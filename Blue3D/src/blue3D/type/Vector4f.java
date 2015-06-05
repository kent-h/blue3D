package blue3D.type;

public class Vector4f{
	
	public float x,y,z,w;
	
	public Vector4f(){
		
	}
	
	public Vector4f(Vector4f other){
		w=other.w;
		x=other.x;
		y=other.y;
		z=other.z;
	}
	
	public Vector4f(float ix, float iy, float iz, float iw) {
		w=iw;
		x=ix;
		y=iy;
		z=iz;
	}

	public Vector4f set(Vector3f other){
		w=1;
		x=other.x;
		y=other.y;
		z=other.z;
		return this;
	}
	
	public Vector4f absolute(){
		w=Math.abs(w);
		x=Math.abs(x);
		y=Math.abs(y);
		z=Math.abs(z);
		return this;
	}
	
	/**
	 * Returns a string that contains the values of this Vector3f. The form is
	 * (x,y,z).
	 * 
	 * @return the String representation
	 */
	public String toString() {
		return "(" + x + ", " + y + ", " + z + "," + w + ")";
	}

	public void set(float ix, float iy, float iz, float iw) {
		w=iw;
		x=ix;
		y=iy;
		z=iz;
	}
	
}
