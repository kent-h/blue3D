package blue3D.sample;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

import blue3D.CameraD;
import blue3D.MatrixHandler;
import blue3D.Shader;
import blue3D.ViewPort;
import blue3D.type.baseEntities.MovingInstanceD;




/**
 * | Z
 * |
 * |																												__	(0,-10,5)
 * |																	camera				____----	/
 * |																				____----				/
 * |																				\							/
 * |																				 \					/
 * |																					\				/
 * |																					 \		/
 * |		 myInstance															\	/
 * |
 * |		____________
 * |		|						|
 * |		|						|
 * |		|	 (0,0,0)	|
 * |		|						|
 * |		|___________|
 * |
 * |																																								Y
 * |________________________________________________________________________________
 */


//the box is rotating around the z axis




public class WellExplainedSimple {
	
	public static void main(String[] args){
		
		//setup and create the window
		try {
			//allow the user to resize the window
			Display.setResizable(true);
			//set the default size of the window
			Display.setDisplayMode(new DisplayMode(800,600));
			//actually create the window
			Display.create();
		} catch (LWJGLException e) {
			//this is extremely unlikely
			//only reachable if LWJGL was unable to create the window
			e.printStackTrace();
			System.exit(0);
		}
		
		//remove the back side of all triangles (only for optimization)
		GL11.glCullFace(GL11.GL_BACK);
		
		//ensure that the closest object is always on top
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		
		
		//create a single instance of our sample object
		//in practice, many of the same object can be created
		MyEntity myInstance=new MyEntity();
		
		//have the instance rotate around its z axis by PI/100 radians per tick.
		myInstance.rotation().rotate(0, 0, 1, (float)Math.PI/100);
		
		
		//create a Camera (double-precision), and set it as the current camera
		//by default, the camera sits at (0,0,0), looks toward (0,0,-1) "-z", with the top of the camera facing (0,1,0) "+y"
		CameraD camera=new CameraD().use();
		
		//rotate the camera by -PI/3 radians around the x axis 
		camera.orientation().rotate(1, 0, 0, -(float)Math.PI/3);
		
		//move the camera to (0,-10,5)
		camera.position().set(0,-15, 5);
		
		//create a ViewPort, and and give it the camera
		//a ViewPort allows a camera's width and height to be easily resized (the actual number of pixels it needs to render to).
		ViewPort viewPort=new ViewPort(800, 600).connectCamera(camera);;
		
		
		//default 3d shader only supports location and basic color
		new Shader().use();
		
		
		//being the main loop, continue until the window is closed
		while (!Display.isCloseRequested()){
			
			if (Display.wasResized()){
				GL11.glViewport(0, 0, Display.getWidth(), Display.getHeight());
					viewPort.resize(Display.getWidth(), Display.getHeight());
			}
			
			//clear the screen color and the depth buffer
			GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
			
			//move the animation forward one tick (may also be called a frame)
			myInstance.tick();
			
			//recalculate the camera's position & rotation
			MatrixHandler.calculateCamera();
			
			//use the position & rotation of the instance "myInstance"
			MatrixHandler.setInstance(myInstance);
			MatrixHandler.calculateInstance();
			
			//load the mixed camera & instance data
			//this includes their locations and orientations
			//all transformations on the previously calculated instance and camera will now apply to drawn elements
			MatrixHandler.load();
			
			//draw our simple shape
			myInstance.draw();
			
			//flip the buffers, making the drawn image visible
			//although it is not used here, this also polls the mouse and keyboard
			Display.update();
			
			//don't allow more than 60 frames per second
			//(wait until 1/60th of a second has passed since this method was last called)
			Display.sync(60);
		}
		
	}
	
	
	//entity with (inefficient) draw code
	static class MyEntity extends MovingInstanceD{
		void draw(){
			
			//draw a 2x2x2m cube, with a different color on each side
			GL11.glBegin(GL11.GL_QUADS);
			
			GL11.glColor3f(0,1,1);
			GL11.glVertex3f(-1, 1, 1);
			GL11.glVertex3f(-1, -1, 1);
			GL11.glVertex3f(1, -1, 1);
			GL11.glVertex3f(1, 1, 1);
			
			GL11.glColor3f(1,1,0);
			GL11.glVertex3f(-1, 1, -1);
			GL11.glVertex3f(-1, 1, 1);
			GL11.glVertex3f(1, 1, 1);
			GL11.glVertex3f(1, 1, -1);
			
			GL11.glColor3f(1,0,1);
			GL11.glVertex3f(1, -1, 1);
			GL11.glVertex3f(1, -1, -1);
			GL11.glVertex3f(1, 1, -1);
			GL11.glVertex3f(1, 1, 1);
			
			GL11.glColor3f(0,0,1);
			GL11.glVertex3f(-1, 1, 1);
			GL11.glVertex3f(-1, 1, -1);
			GL11.glVertex3f(-1, -1, -1);
			GL11.glVertex3f(-1, -1, 1);
			
			GL11.glColor3f(0,1,0);
			GL11.glVertex3f(1, -1, -1);
			GL11.glVertex3f(1, -1, 1);
			GL11.glVertex3f(-1, -1, 1);
			GL11.glVertex3f(-1, -1, -1);
			
			GL11.glColor3f(1,0,0);
			GL11.glVertex3f(1, 1, -1);
			GL11.glVertex3f(1, -1, -1);
			GL11.glVertex3f(-1, -1, -1);
			GL11.glVertex3f(-1, 1, -1);
			
			GL11.glEnd();
		}
	}
	
}
