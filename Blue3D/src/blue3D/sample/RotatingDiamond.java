package blue3D.sample;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

import blue3D.CameraL;
import blue3D.MatrixHandler;
import blue3D.Shader;
import blue3D.ViewPort;
import blue3D.type.QuaternionF;
import blue3D.type.baseEntities.BasicInstanceL;

public class RotatingDiamond {
	
	static boolean systemRunning=true;
	
	static CameraL testCamera;
	static ViewPort screenCameraHandler;
	
	public static void main(String[] args){
		displayStartup();
		cameraSetup();
		MatrixHandler.setInstance(new BasicInstanceL());
		while (systemRunning){
			physicsCycle();
			drawCycle();
		}
	}
	
	
	private static void cameraSetup() {
		screenCameraHandler=new ViewPort(800, 600);
		new Shader(
				"#version 330 core\n" +
				"#extension GL_ARB_explicit_uniform_location : require\n" +
				"layout(location=8) uniform mat4 worldMatrix;" +
				"in vec4 gl_Vertex;" +
				"in vec4 gl_Color;" +
				"out vec4 outcol;" +
				"void main() {" +
				"  gl_Position = worldMatrix * gl_Vertex;" +
				"  outcol=gl_Color;" +
				"}"
				, 
				"#version 330 core\n" +
				"in vec4 outcol;" +
				"out vec4 gl_FragColor;" +
				"void main() {" +
				"gl_FragColor = outcol;\n" +
				"}"
				).use();
		testCamera = new CameraL(10, 10000000, 60*180/(float)Math.PI, 800, 600);
		testCamera.use();
		screenCameraHandler.connectCamera(testCamera);
	}


	public static void displayStartup(){
		try {
			Display.setResizable(true);
			Display.setDisplayMode(new DisplayMode(800,600));
			Display.create();
			resizeDrawSurface(800,600);
			//note: the depth buffer is not enabled
			//GL11.glEnable(GL11.GL_CULL_FACE);
			GL11.glCullFace(GL11.GL_BACK);
		} catch (LWJGLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	public static void physicsCycle(){
		
	}
	
	
	public static void drawCycle(){
		Display.update();
		if (systemRunning=!Display.isCloseRequested()){
			//if the display was resized, update cameras and canvases to account for it.
			if (Display.wasResized()){
				resizeDrawSurface(Display.getWidth(), Display.getHeight());
			}
			//clear the screen
			GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
			
			//****************************
			//test code
			//****************************
			
			//recalculate the camera
			testCamera.position().z=200000;
			testCamera.position().z-=10000;
			//MatrixHandler.getInstance().getPosition().x+=100;
			QuaternionF.mul(new QuaternionF(0.99950656036f,0,0.03141075907f,0),MatrixHandler.getInstance().orientation(),MatrixHandler.getInstance().orientation());
			//Quaternion.mul(new Quaternion(0.9999950652f,0.00314158748f,0,0),MatrixHandler.getCamera().getOrientation(),MatrixHandler.getCamera().getOrientation());
			//System.out.println(MatrixHandler.getInstance().getOrientation());
			MatrixHandler.calculateInstance();
			MatrixHandler.calculateCamera();
			MatrixHandler.load();
			
			
			GL11.glBegin(GL11.GL_TRIANGLES);
			
			GL11.glColor3f(.5f, 0, 0);//top left
			GL11.glVertex3f(0,10000,0);
			
			GL11.glColor3f(1,0,0);
			GL11.glVertex3f(0,20000,0);
			
			GL11.glColor3f(0, 0, 0);
			GL11.glVertex3f(-10000,0,0);
			
			
			GL11.glColor3f(1,0,0);//top right
			GL11.glVertex3f(0,20000,0);
			
			GL11.glColor3f(.5f,0,0);
			GL11.glVertex3f(0,10000,0);
			
			GL11.glColor3f(0, 0, 0);
			GL11.glVertex3f(10000,0,0);
			
			
			
			GL11.glColor3f(.5f, 0, 0);
			GL11.glVertex3f(0,-10000,0);
			
			GL11.glColor3f(1,0,0);
			GL11.glVertex3f(0,-20000,0);
			
			GL11.glColor3f(0, 0, 0);
			GL11.glVertex3f(10000,0,0);
			
			
			GL11.glColor3f(1,0,0);
			GL11.glVertex3f(0,-20000,0);
			
			GL11.glColor3f(.5f,0,0);
			GL11.glVertex3f(0,-10000,0);
			
			GL11.glColor3f(0, 0, 0);
			GL11.glVertex3f(-10000,0,0);
			
			GL11.glEnd();
			
			
			//****************************
			//end of test code
			//****************************
			
			
		}else{
			//shutting down
			Display.destroy();
			try {
				Thread.sleep(4000);
			} catch (InterruptedException e) {
				//nothing to do
			}
		}
		Display.sync(60);
	}
	
	
	static void resizeDrawSurface(int width, int height){
		GL11.glViewport(0, 0, width, height);
		if (screenCameraHandler!=null){
			screenCameraHandler.resize(width, height);
		}
	}
	
	
}
