package blue3D.type.baseEntities;

import blue3D.type.Matrix4f;
import blue3D.type.QuaternionF;
import blue3D.type.Vector3d;
import blue3D.type.Vector3f;
import blue3D.type.Vector3l;


public class BasicInstanceD implements Instance{
	private Vector3d position=new Vector3d();
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
	public Vector3d position() {
		return position;
	}
	
	/**
	 * nothing to do for this class
	 * @return 
	 */
	public BasicInstanceD tick(){
		return this;
	}
	
	@Override
	public Vector3l transform(Vector3l toTransform) {
		orientation.rotateVector(toTransform);//rotate
		toTransform.add(position);//then translate
		return toTransform;
	}

	@Override
	public Vector3f transform(Vector3f toTransform) {
		orientation.rotateVector(toTransform);//rotate
		toTransform.add(position);//then translate
		return toTransform;
	}

	@Override
	public Vector3d transform(Vector3d toTransform) {
		orientation.rotateVector(toTransform);//rotate
		toTransform.add(position);//then translate
		return toTransform;
	}

	@Override
	public Vector3l inverseTransform(Vector3l toTransform) {
		toTransform.sub(position);//translate
		orientation.inverseRotateVector(toTransform);//then rotate
		return toTransform;
	}

	@Override
	public Vector3f inverseTransform(Vector3f toTransform) {
		toTransform.sub(position);//translate
		orientation.inverseRotateVector(toTransform);//then rotate
		return toTransform;
	}

	@Override
	public Vector3d inverseTransform(Vector3d toTransform) {
		toTransform.sub(position);//translate
		orientation.inverseRotateVector(toTransform);//then rotate
		return toTransform;
	}
}
