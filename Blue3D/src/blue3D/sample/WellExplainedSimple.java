package blue3D.sample;

import java.nio.ByteBuffer;

import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWFramebufferSizeCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWWindowSizeCallback;
import org.lwjgl.glfw.GLFWvidmode;
import org.lwjgl.opengl.ContextCapabilities;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GLContext;

import blue3D.CameraD;
import blue3D.MatrixHandler;
import blue3D.Shader;
import blue3D.ViewPort;
import blue3D.type.baseEntities.MovingInstanceD;
import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.*;




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
	
	//We need to strongly reference callback instances.
  @SuppressWarnings("unused")
	private static GLFWErrorCallback errorCallback;
  @SuppressWarnings("unused")
	private static GLFWKeyCallback   keyCallback;
  @SuppressWarnings("unused")
	private static GLFWWindowSizeCallback windowSizeCallBack;
  @SuppressWarnings("unused")
	private static GLFWFramebufferSizeCallback framebufferSizeCallBack;

  // The window handle
  private static long window;
  
  static final int START_WIDTH = 800;
  static final int START_HEIGHT = 600;
	
	private static void init() {
		// Setup an error callback. The default implementation
		// will print the error message in System.err.
		glfwSetErrorCallback(errorCallback = errorCallbackPrint(System.err));

		// Initialize GLFW. Most GLFW functions will not work before doing this.
		if (glfwInit() != GL11.GL_TRUE)
			throw new IllegalStateException("Unable to initialize GLFW");

		// Configure our window
		glfwDefaultWindowHints(); // optional, the current window hints are already the default
		glfwWindowHint(GLFW_VISIBLE, GL_FALSE); // the window will stay hidden after creation
		glfwWindowHint(GLFW_RESIZABLE, GL_TRUE); // the window will be resizable

		// Create the window
		window = glfwCreateWindow(START_WIDTH, START_HEIGHT, "Blue3D", NULL, NULL);
		if (window == NULL)
			throw new RuntimeException("Failed to create the GLFW window");

		// Setup a key callback. It will be called every time a key is pressed, repeated or released.
		glfwSetKeyCallback(window, keyCallback = new GLFWKeyCallback() {
			@Override
			public void invoke(long window, int key, int scancode, int action,
					int mods) {
				if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE)
					glfwSetWindowShouldClose(window, GL_TRUE); // We will detect this in our rendering loop
			}
		});

		// Get the resolution of the primary monitor
		ByteBuffer vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
		// Center our window
		glfwSetWindowPos(window, 
				(GLFWvidmode.width(vidmode) - START_WIDTH) / 2,
				(GLFWvidmode.height(vidmode) - START_HEIGHT) / 2);

		// Make the OpenGL context current
		glfwMakeContextCurrent(window);
		// Enable v-sync
		glfwSwapInterval(1);

		// Make the window visible
		glfwShowWindow(window);

		GLContext.createFromCurrent();
	}
	
	public static void main(String[] args){
		
		//create screen, initialize opengl
		init();
		
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
		final ViewPort viewPort=new ViewPort(800, 600).connectCamera(camera);
		
		//ensure the camera is resized to when the frame buffer/window size is changed.
    glfwSetFramebufferSizeCallback(window, new GLFWFramebufferSizeCallback(){
			@Override
			public void invoke(long window, int width, int height) {
				GL11.glViewport(0, 0, width, height);
				viewPort.resize(width, height);
				
			}
    });
		
		//default 3d shader only supports location and basic color
		new Shader().use();
		
		//being the main loop, continue until the window is closed
		while (glfwWindowShouldClose(window) == GL_FALSE){
			
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
			
			glfwSwapBuffers(window); // swap the color buffers
			
			//also poll the mouse and keyboard
			glfwPollEvents();
			
		}
		
	}
	
	
	
	
	//entity with (inefficient) draw code
	static class MyEntity extends MovingInstanceD{
		void draw(){
			
			//draw a 2x2x2m cube, with a different color on each side
			GL20.glUniform4f(0, 1, 0.5f, 1, 1);
			GL11.glBegin(GL11.GL_QUADS);
			
			GL11.glColor3f(0,1,1);
			GL11.glVertex3f(-1, 1, 1);
			GL11.glVertex3f(-1, -1, 1);
			GL11.glVertex3f(1, -1, 1);
			GL11.glVertex3f(1, 1, 1);
			
			GL11.glEnd();
			GL20.glUniform4f(0, 0, 0.5f, 1, 1);
			GL11.glBegin(GL11.GL_QUADS);
			
			GL11.glColor3f(1,1,0);
			GL11.glVertex3f(-1, 1, -1);
			GL11.glVertex3f(-1, 1, 1);
			GL11.glVertex3f(1, 1, 1);
			GL11.glVertex3f(1, 1, -1);
			
			GL11.glEnd();
			GL20.glUniform4f(0, 0, 0.5f, 0, 1);
			GL11.glBegin(GL11.GL_QUADS);
			
			GL11.glColor3f(1,0,1);
			GL11.glVertex3f(1, -1, 1);
			GL11.glVertex3f(1, -1, -1);
			GL11.glVertex3f(1, 1, -1);
			GL11.glVertex3f(1, 1, 1);
			
			GL11.glEnd();
			GL20.glUniform4f(0, 0, 1, 1, 1);
			GL11.glBegin(GL11.GL_QUADS);
			
			GL11.glColor3f(0,0,1);
			GL11.glVertex3f(-1, 1, 1);
			GL11.glVertex3f(-1, 1, -1);
			GL11.glVertex3f(-1, -1, -1);
			GL11.glVertex3f(-1, -1, 1);
			
			GL11.glEnd();
			GL20.glUniform4f(0, 1, 0.5f, 0, 1);
			GL11.glBegin(GL11.GL_QUADS);
			
			GL11.glColor3f(0,1,0);
			GL11.glVertex3f(1, -1, -1);
			GL11.glVertex3f(1, -1, 1);
			GL11.glVertex3f(-1, -1, 1);
			GL11.glVertex3f(-1, -1, -1);
			
			GL11.glEnd();
			GL20.glUniform4f(0, 1, 1, 1, 1);
			GL11.glBegin(GL11.GL_QUADS);
			
			GL11.glColor3f(1,0,0);
			GL11.glVertex3f(1, 1, -1);
			GL11.glVertex3f(1, -1, -1);
			GL11.glVertex3f(-1, -1, -1);
			GL11.glVertex3f(-1, 1, -1);
			
			GL11.glEnd();
		}
	}
	
}
