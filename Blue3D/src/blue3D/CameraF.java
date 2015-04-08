package blue3D;

import blue3D.type.Matrix4f;
import blue3D.type.baseEntities.BasicInstanceF;

public class CameraF extends BasicInstanceF implements Camera{
	
	private Matrix4f viewMatrix=new Matrix4f();
	private Matrix4f projectionMatrix = new Matrix4f();
	
	private ViewPort viewPort;//if not null, the view will be resized to fit automatically.
	
	private int width, height;
	
	private float nearPlane;
	private float farPlane;
	private float fov;
	
	
	/**
	 * creates the camera, with default and valid sizing
	 * the defaults are from 1mm to 1m
	 */
	public CameraF(){
		nearPlane=0.01f;
		farPlane=10000;
		fov=60*180/(float)Math.PI;
		resize(800,600);
	}
	
	
	/**
	 * @param near_plane distance of near-plane used for rendering
	 * @param far_plane distance of the far-plane used when rendering
	 * @param fov field of view
	 * @param width width of the display or canvas used
	 * @param height height of the display or canvas used
	 */
	public CameraF(float near_plane, float far_plane, float fov, int width, int height){
		setupProjection(near_plane, far_plane, fov, width, height);
	}
	
	
	/**
	 * @param near_plane distance of near-plane used for rendering
	 * @param far_plane distance of the far-plane used when rendering
	 * @param fov field of view
	 * @param viewPort view-port to tie this camera to. This camera will be resized if the view-port is
	 */
	CameraF(float near_plane, float far_plane, float fov, ViewPort viewPort){
		setupProjection(near_plane, far_plane, fov, viewPort.getWidth(), viewPort.getHeight());
	}
	
	
	public int getWidth(){
		return width;
	}
	
	
	public int getHeight(){
		return height;
	}
	
	
	/**
	 * @param near_plane distance of near-plane used for rendering
	 * @param far_plane distance of the far-plane used when rendering
	 * @param fov field of view
	 * @param width width of the display or canvas used
	 * @param height height of the display or canvas used
	 */
	void setupProjection(float near_plane, float far_plane, float fov, int width, int height){
		nearPlane=near_plane;
		farPlane=far_plane;
		this.fov=fov;
		resize(width, height);
	}
	
	
	/**
	 * @param near_plane distance of near-plane used for rendering
	 * @param far_plane distance of the far-plane used when rendering
	 * @param fov field of view
	 * @param newViewPort the view-port to tie this camera to
	 */
	void setupProjection(float near_plane, float far_plane, float fov, ViewPort newViewPort){
		connectToViewPort(newViewPort);
		setupProjection(near_plane, far_plane, fov, viewPort.getWidth(), viewPort.getHeight());
	}
	
	
	public void connectToViewPort(ViewPort newViewPort){
		if (viewPort!=null){
			viewPort.disconnectCamera(this);
		}
		viewPort=newViewPort;
		viewPort.connectCamera(this);
	}
	
	
	/**
	 * Sets up perspective division for the camera.
	 * Will be called if the draw surface is resized.
	 * Note that the new data will not be used until calculateMatrix() is called. 
	 * For this reason, calculateMatrix() should be called at least once per frame.
	 */
	public CameraF resize(int width, int height){
		//update width & height
		this.width=width;
		this.height=height;
		//calculate the projection matrix
		projectionMatrix.setIdentity();
		float aspectRatio = (float)width / (float)height;
		float y_scale = (float)(1.0/Math.tan(fov/2));
		float x_scale = y_scale / aspectRatio;
		float frustum_length = (float)(farPlane - nearPlane);
		projectionMatrix.m00 = x_scale;
		projectionMatrix.m11 = y_scale;
		projectionMatrix.m22 = (float)-((farPlane + nearPlane) / frustum_length);
		projectionMatrix.m32 = -1;
		projectionMatrix.m23 = (float)-((2 * nearPlane * farPlane) / frustum_length);
		projectionMatrix.m33 = 0;
		return this;
	}
	
	
	/**
	 * Calculates the direction and location of the camera.
	 * The projection must be calculated first.
	 */
	public CameraF calculateMatrix(){
		//reset the matrix
		viewMatrix.setIdentity();
		//translate the camera
		position().calculateOffsetMatrix(viewMatrix);
		//rotate the camera
		orientation().calculateMatrix(viewMatrix);
		//since this is the camera matrix, it needs to be inverted (as we are actually moving the world around the camera).
		viewMatrix.invert();
		Matrix4f.mul(projectionMatrix, viewMatrix, viewMatrix);
		return this;
	}
	
	
	/**
	 * Writes all camera info to the given matrix.  
	 * Any transforms previously in the matrix will be discarded.
	 */
	public Matrix4f getMatrix(){
		return viewMatrix;
	}
	
	
	/**
	 * If not already the current camera:
	 * - recalculates the matrix
	 * - becomes the current camera
	 *  Note: If already the current camera, the matrix will NOT be recalculated, use calculateMatrix() instead.
	 */
	public CameraF use(){
		MatrixHandler.setCamera(this);
		return this;
	}
	
	//the camera should be close to the origin, relative to everything else. almost.
	
}