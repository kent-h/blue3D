package blue3D;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL20;

import blue3D.type.Matrix4f;
import blue3D.type.baseEntities.Instance;

public class MatrixHandler {
	
	//position, orientation, and perspective;
	static Camera currentCamera=new CameraL();//Initialised with a dummy camera
	
  //position and orientation
	static Instance currentInstance=null;
	
	static Matrix4f modelMatrix = new Matrix4f();
	static Matrix4f worldMatrix = new Matrix4f();
	static Matrix4f cameraMatrix = new Matrix4f();//the camera which will eventually be loaded
	
	//buffer to use when loading any matrix;
	static FloatBuffer matrix44Buffer = BufferUtils.createFloatBuffer(16);
	
	
	/**
	 * @return the camera currently in use.
	 */
	public static Camera getCamera(){
		return currentCamera;
	}
	
	
	/**
	 * switches to the selected camera, does not recalculate it though.
	 * @param cam the camera to switch to
	 */
	public static void setCamera(Camera cam){
		currentCamera=cam;
	}
	
	
	/**
	 * get the current instance
	 * @return the current instance
	 */
	public static Instance getInstance(){
		return currentInstance;
	}
	
	
	/**
	 * set the current instance. This does not calculate the matrix for the instance, use calculateInstance() after/instead.
	 * @param instance the instance to make current
	 */
	public static void setInstance(Instance instance) {
		currentInstance=instance;
	}
	
	
	/**
	 * calculates or re-calculates the camera matrix
	 */
	public static void calculateCamera(){
		currentCamera.calculateMatrix();
	}
	
	
	/**
	 * calculates or re-calculates the current model's matrix, this is only valid for the current camera
	 */
	public static void calculateInstance(){
		modelMatrix.setIdentity();
		if (currentInstance!=null){
			currentInstance.calculateMatrix(modelMatrix);
		}
	}
	
	
	/**
	 * calculates model matrix for the given instance, and uses it for further rendering
	 * does NOT set the given instance as current
	 * this is only valid for the current camera
	 */
	public static void calculateInstance(Instance givenInst){
		modelMatrix.setIdentity();
		if (givenInst!=null)
			givenInst.calculateMatrix(modelMatrix);
	}
	
	
	public static void setInstanceMatrix(Matrix4f matrix){
		modelMatrix.set(matrix);
	}
	
	
	/**
	 * calculates or re-calculates the bone positions only, if anything else was modified before this call, behaviour is undefined.
	 * unimplemented, may be included in a future release
	 */
	public static void calculateBones(){
		
	}
	
	
	/**
	 * calculates and returns the matrix for all active transformations.
	 */
	public static Matrix4f getWorldMatrix(){
		Matrix4f.mul(currentCamera.getMatrix(), modelMatrix, worldMatrix);
		return worldMatrix;
	}
	
	
	/**
	 * recalculate world matrix and pass to the gpu
	 * the matrix will be stored to location 8. (use "#extension GL_ARB_explicit_uniform_location : require" with "layout(location=8)" to access.
	 */
	public static void load() {
		Matrix4f.mul(currentCamera.getMatrix(), modelMatrix, worldMatrix);
		worldMatrix.store(matrix44Buffer);
		matrix44Buffer.flip();
		GL20.glUniformMatrix4(8, false, matrix44Buffer);
	}
	
}
