package blue3D.type;

public class MovingInstanceD extends BasicInstanceD{
	
	
	/**
	 * the rotation/tick of this instance
	 * note that rotation is applied to the orientation every tick.
	 * not rotating by default
	 */
	private QuaternionF rotation=new QuaternionF();
	
	
	/**
	 * the velocity this instance is moving at
	 */
	private Vector3d velocity=new Vector3d();
	
	
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
	public Vector3d velocity(){
		return velocity;
	}
	
	
	/**
	 * update the position and orientation with the velocity and rotation
	 */
	public void tick(){
		QuaternionF.mul(rotation,orientation(),orientation());
		position().add(velocity);
	}
	
}
