package blue3D.type;

public interface Vector3 {
	public float getFloatX();
	public float getFloatY();
	public float getFloatZ();
	
	public Vector3 normalize();
	public Vector3 setLength(double length);
	
	public Vector3 add(Vector3f other);
	public Vector3 add(Vector3d other);
	public Vector3 add(Vector3l other);
	
	public Vector3 sub(Vector3f other);
	public Vector3 sub(Vector3d other);
	public Vector3 sub(Vector3l other);
	
}
