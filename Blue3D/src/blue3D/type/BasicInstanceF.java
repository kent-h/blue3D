package blue3D.type;


public class BasicInstanceF implements Instance{
	private Vector3f position=new Vector3f();
	//protected Vector3l velocity=new Vector3l();//should this be part of this object? unused for now
	private QuaternionF orientation=new QuaternionF();
	
	
	public void calculateMatrix(Matrix4f modelMatrix) {
		position.calculateOffsetMatrix(modelMatrix);
		orientation.calculateMatrix(modelMatrix);
		//orientation.calculateMatrix(modelMatrix);
	}
	
	
	/**
	 * there is no setter for this variable, to modify it, use orientation(), then modify the returned Quaternion object
	 * @return the object's orientation object
	 */
	public QuaternionF orientation(){
		return orientation;
	}
	
	
	/**
	 * there is no setter for this variable, to modify it, use position(), then modify the returned Vector3d object
	 * @return the object's position object
	 */
	public Vector3f position() {
		return position;
	}
	
	/**
	 * nothing to do for this class
	 */
	public BasicInstanceF tick(){
		return this;
	}
	
	public Vector3l transform(Vector3l toTransform) {
		orientation.rotateVector(toTransform);//rotate
		toTransform.add(position);//then translate
		return toTransform;
	}


	public Vector3f transform(Vector3f toTransform) {
		orientation.rotateVector(toTransform);//rotate
		toTransform.add(position);//then translate
		return toTransform;
	}


	public Vector3d transform(Vector3d toTransform) {
		orientation.rotateVector(toTransform);//rotate
		toTransform.add(position);//then translate
		return toTransform;
	}


	public Vector3l inverseTransform(Vector3l toTransform) {
		toTransform.sub(position);//translate
		orientation.inverseRotateVector(toTransform);//then rotate
		return toTransform;
	}


	public Vector3f inverseTransform(Vector3f toTransform) {
		toTransform.sub(position);//translate
		orientation.inverseRotateVector(toTransform);//then rotate
		return toTransform;
	}


	public Vector3d inverseTransform(Vector3d toTransform) {
		toTransform.sub(position);//translate
		orientation.inverseRotateVector(toTransform);//then rotate
		return toTransform;
	}
}
