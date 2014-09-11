package blue3D.type;


public interface Instance {
	
	/**
	 * transform the given matrix by this instance's rotation and location
	 * @param modelMatrix
	 * @param renderOffset
	 */
	public void calculateMatrix(Matrix4f modelMatrix);
	
	
	/**
	 * there is no setter for this variable, to modify it, use getOrientation(), then modify the returned Quaternion object
	 * @return the object's orientation object
	 */
	public QuaternionF orientation();
	
	/**
	 * there is no setter for this variable, to modify it, use getPosition(), then modify the returned Vector3l object
	 * @return the object's position object
	 */
	public Vector3 position();
	
	
	/**
	 * allows the object to be updated in some way, presumably once per frame
	 * the the Moving* objects, update the position and orientation.
	 */
	public void tick();
	
	
	/**
	 * Transform the given vertex. (Convert from model coordinates to world coordinates, using the current position and orientation.)
	 * @param toTransform the vertex to transform
	 * @return
	 */
	public Vector3l transform(Vector3l toTransform);
	
	
	/**
	 * Transform the given vertex. (Convert from model coordinates to world coordinates, using the current position and orientation.)
	 * @param toTransform the vertex to transform
	 * @return
	 */
	public Vector3f transform(Vector3f toTransform);
	
	
	/**
	 * Transform the given vertex. (Convert from model coordinates to world coordinates, using the current position and orientation.)
	 * @param toTransform the vertex to transform
	 * @return
	 */
	public Vector3d transform(Vector3d toTransform);
	
	
	/**
	 * Transform the given vertex. (Convert from world coordinates to model coordinates, using the current position and orientation.)
	 * @param toTransform the vertex to transform
	 * @return
	 */
	public Vector3l inverseTransform(Vector3l toTransform);
	
	
	/**
	 * Transform the given vertex. (Convert from world coordinates to model coordinates, using the current position and orientation.)
	 * @param toTransform the vertex to transform
	 * @return
	 */
	public Vector3f inverseTransform(Vector3f toTransform);
	
	
	/**
	 * Transform the given vertex. (Convert from world coordinates to model coordinates, using the current position and orientation.)
	 * @param toTransform the vertex to transform
	 * @return
	 */
	public Vector3d inverseTransform(Vector3d toTransform);
	
}









































