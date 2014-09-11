package blue3D;

import blue3D.type.BasicInstanceL;
import blue3D.type.Matrix4f;

public class OrthoCameraL extends BasicInstanceL implements Camera{
	
	private Matrix4f viewMatrix=new Matrix4f(), orthoMatrix=new Matrix4f();
	
	//private Vector3l renderOrigin=new Vector3l();//subtract this from every object's position before rendering
	
	private ViewPort viewPort;//if not null, the view will be resized to fit automatically.
	
	private int view_width, view_height;
	
	private float width, near_plane, far_plane;
	
	
	/**
	 * creates the camera, with default and valid sizing
	 * the defaults are from 1mm to 1m
	 */
	public OrthoCameraL(){
		near_plane=0.1f;
		far_plane=10000;
		width=800;
		resize(800,600);
	}
	
	
	/**
	 * @param right_side distance of near-plane used for rendering
	 * @param left_side distance of the far-plane used when rendering
	 * @param near_plane field of view
	 * @param width width of the display or canvas used
	 * @param height height of the display or canvas used
	 */
	public OrthoCameraL(float view_width, float near_plane, float far_plane, int width, int height){
		setupProjection(view_width, near_plane, far_plane, width, height);
	}
	
	
	/**
	 * @param near_plane distance of near-plane used for rendering
	 * @param far_plane distance of the far-plane used when rendering
	 * @param fov field of view
	 * @param viewPort view-port to tie this camera to. This camera will be resized if the view-port is
	 */
	OrthoCameraL(float view_width, float near_plane, float far_plane, ViewPort viewPort){
		this(view_width, near_plane, far_plane, viewPort.getWidth(), viewPort.getHeight());
	}
	
	
	/**
	 * get the camera's width in screen coordinates
	 */
	public int getWidth(){
		return view_width;
	}
	
	/**
	 * get the camera't height in screen coordinates
	 */
	public int getHeight(){
		return view_height;
	}
	
	
//	public Vector3l getRenderOffset(){
//		return renderOrigin;
//	}
	
	
	/**
	 * @param width width of the camera, in world coordinates
	 * @param near_plane distance of the near_plane used for rendering
	 * @param far_plane distance of the far-plane used when rendering
	 * @param view_width width of the display or canvas used
	 * @param view_height height of the display or canvas used
	 */
	void setupProjection(float width, float near_plane, float far_plane, int view_width, int view_height){
		this.width=width;
		this.near_plane=near_plane;
		this.far_plane=far_plane;
		resize(view_width, view_height);
	}
	
	
	/**
	 * @param near_plane distance of near-plane used for rendering
	 * @param far_plane distance of the far-plane used when rendering
	 * @param fov field of view
	 * @param newViewPort the view-port to tie this camera to
	 */
	void setupProjection(float view_width, float near_plane, float far_plane, ViewPort newViewPort){
		connectToViewPort(newViewPort);
		setupProjection(view_width, near_plane, far_plane, viewPort.getWidth(), viewPort.getHeight());
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
	public OrthoCameraL resize(int view_width, int view_height){
		//update width & height
		this.view_width=view_width;
		this.view_height=view_height;
		System.out.println(view_width+" "+view_height);
		float left=-width/2, right=width/2;
		float top=width*(view_height*1f/view_width)/2, bottom=-width*(view_height*1f/view_width)/2;
		
		orthoMatrix.setIdentity();
		
		orthoMatrix.m00=2/(right-left);
		orthoMatrix.m11=2/(top-bottom);
		orthoMatrix.m22=-2/(far_plane-near_plane);

//		orthoMatrix.m03=-(right+left)/(right-left);
//		orthoMatrix.m13=-(top+bottom)/(top-bottom);
		orthoMatrix.m23=-(far_plane+near_plane)/(far_plane-near_plane);
		return this;
	}
	
	
	/**
	 * Calculates the direction and location of the camera.
	 * The projection must be calculated first.
	 */
	public OrthoCameraL calculateMatrix(){
		//reset the matrix
		viewMatrix.setIdentity();
		//translate the camera
		position().calculateOffsetMatrix(viewMatrix);
		//rotate the camera
		orientation().calculateMatrix(viewMatrix);
		//since this is the camera matrix, it needs to be inverted (as we are actually moving the world around the camera).
		viewMatrix.invert();
		Matrix4f.mul(orthoMatrix, viewMatrix, viewMatrix);
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
	public OrthoCameraL use(){
		MatrixHandler.setCamera(this);
		return this;
	}
	
	//the camera should be close to the origin, relative to everything else. almost.
	
}
