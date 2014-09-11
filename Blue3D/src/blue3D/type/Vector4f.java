package blue3D.type;

public class Vector4f extends org.lwjgl.util.vector.Vector4f{
	
	public Vector4f(){
		
	}
	
	public Vector4f(Vector4f other){
		w=other.w;
		x=other.x;
		y=other.y;
		z=other.z;
	}
	
	public Vector4f set(Vector3f other){
		w=0;
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
	
}
