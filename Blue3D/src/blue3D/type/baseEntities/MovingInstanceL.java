package blue3D.type.baseEntities;

import blue3D.type.QuaternionF;
import blue3D.type.Vector3l;

public class MovingInstanceL extends BasicInstanceL{
	
	
	/**
	 * the rotation/tick of this instance
	 * note that rotation is applied to the orientation every tick.
	 * not rotating by default
	 */
	private QuaternionF rotation=new QuaternionF();
	
	
	/**
	 * the velocity this instance is moving at
	 */
	private Vector3l velocity=new Vector3l();
	
	
	/**
	 * access to the rotation
	 * @return pointer to the rotation Quaternion
	 */
	public QuaternionF rotation(){
		return rotation;
	}
	
	
	/**
	 * access to the velocity
	 * @return pointer to the velocity vector
	 */
	public Vector3l velocity(){
		return velocity;
	}
	
	
	/**
	 * update the position and orientation with the velocity and rotation
	 */
	public MovingInstanceL tick(){
		QuaternionF.mul(rotation,orientation(),orientation());
		position().add(velocity);
		return this;
	}
	
}
