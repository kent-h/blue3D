package blue3D.sample;

import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;
import java.util.Iterator;
import java.util.LinkedList;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.PixelFormat;

import blue3D.CameraL;
import blue3D.MatrixHandler;
import blue3D.Shader;
import blue3D.Texture;
import blue3D.ViewPort;
import blue3D.type.BasicInstanceL;
import blue3D.type.QuaternionF;
import blue3D.type.Vector3f;


public class SquareField {
	
	protected static Dimension newCanvasSize;
	protected static boolean closeRequested;

	public static void main(String[] args){
		
		
		int width=800;
		int height=600;
		
		
		ByteBuffer byteImg = screenCapture(width, height);
		
		
		//setup display
			
		Frame frame = new Frame("Game Frame");
		frame.setLayout(new BorderLayout());
		final Canvas canvas = new Canvas();
		
		
		canvas.addComponentListener(new ComponentAdapter() {
        @Override
        public void componentResized(ComponentEvent e)
        {
        	newCanvasSize=canvas.getSize(); 
        }
     });
     
    frame.addWindowFocusListener(new WindowAdapter() {
        @Override
        public void windowGainedFocus(WindowEvent e)
        {
        	canvas.requestFocusInWindow(); 
        }
     });
     
    frame.addWindowListener(new WindowAdapter() {
        @Override
        public void windowClosing(WindowEvent e)
        {
        	closeRequested = true;
        }
     });
		
		frame.add(canvas, BorderLayout.CENTER);
			
		
		
		
		try {
			
			
			
			/*Display.setParent(canvas);
			Display.setVSyncEnabled(true);
			frame.setUndecorated(true);
			//frame.setBackground(new Color(1,1,1,0));
			//frame.setBackground(new Color(0,0,0,0.5f));
			//frame.setOpacity(0.2f);
			frame.setPreferredSize(new Dimension(width, height));
			frame.setVisible(true);
			frame.setMinimumSize(new Dimension(400, 300));
      frame.pack();
      frame.setVisible(true);
      //frame.setBackground(new Color(1,1,1,0.5f));
      
      System.out.println(frame.isOpaque());*/
      
			Display.setDisplayMode(new DisplayMode(width,height));
			Display.setResizable(true);
			
      Display.create(new PixelFormat());
			
      
			/*Display.setTitle("shatter");
			Display.setResizable(true);
			Display.setDisplayMode(new DisplayMode(width,height));
			
			Display.setParent(canvas);
			
			Display.create();*/
      
      
			GL11.glEnable(GL11.GL_DEPTH_TEST);
			//no face culling
		} catch (LWJGLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		Texture t = new Texture(width, height, byteImg);
		t.use(10);
		
		
	  CameraL camera = new CameraL().use();//create camera, change it's location, and pass it to the MatrixHandler
		camera.position().z=10000;
		camera.connectToViewPort(screenCameraHandler);
	  
	  
	  
		Shader s=new Shader(
				"#version 330 core\n" +
				"#extension GL_ARB_explicit_uniform_location : require\n" +
				"layout(location=8) uniform mat4 worldMatrix;\n" +
				"in vec4 gl_Vertex;\n" +
				"in vec4 gl_Color;\n" +
				"in vec2 textureCoord;\n" +
				"out vec4 outcol;\n" +
				"out vec2 texCoord;\n" +
				"void main() {\n" +
				"  gl_Position = worldMatrix * gl_Vertex;\n" +
				"  outcol=gl_Color;\n" +
				"  texCoord=textureCoord;\n" +
				"}"
				, 
				/*"#version 330 core\n" +
				"in vec4 outcol;" +
				"out vec4 gl_FragColor;" +
				"void main() {" +
				"gl_FragColor = outcol;\n" +
				"}"*/
				
				"#version 330 core\n" +
				"#extension GL_ARB_explicit_uniform_location : require\n" +
				"in vec2 texCoord;\n" +
				"in vec4 outcol;\n" +
				"out vec4 gl_FragColor;\n" +
				"uniform sampler2D texSampler;\n" +
				"void main() {\n" +
				"  gl_FragColor = texture(texSampler, outcol.xy);\n" +
				"}"
				);
		s.use();
		
		
		
		LinkedList<Shape> allFlyers=new LinkedList<Shape>();
		
		
		int time=400;
		//main loop
		while(!Display.isCloseRequested()){
			
			if (Display.wasResized()){
				resizeDrawSurface(Display.getWidth(), Display.getHeight());
			}
			
			Shape newMovingShape=new Shape((long)(Math.random()*8000-4000),(long)(Math.random()*8000-4000),0);
			allFlyers.addFirst(newMovingShape);
			
			newMovingShape=new Shape((long)(Math.random()*8000-4000),(long)(Math.random()*8000-4000),0);
			allFlyers.addFirst(newMovingShape);
			
			newMovingShape=new Shape((long)(Math.random()*8000-4000),(long)(Math.random()*8000-4000),0);
			allFlyers.addFirst(newMovingShape);
			
			newMovingShape=new Shape((long)(Math.random()*8000-4000),(long)(Math.random()*8000-4000),0);
			allFlyers.addFirst(newMovingShape);
			
			newMovingShape=new Shape((long)(Math.random()*8000-4000),(long)(Math.random()*8000-4000),0);
			allFlyers.addFirst(newMovingShape);
			
			newMovingShape=new Shape((long)(Math.random()*8000-4000),(long)(Math.random()*8000-4000),0);
			allFlyers.addFirst(newMovingShape);
			
			if (time==0){
				allFlyers.removeLast();
				allFlyers.removeLast();
				allFlyers.removeLast();
				allFlyers.removeLast();
				allFlyers.removeLast();
				allFlyers.removeLast();
			}else{
				time--;
			}
			
			
			GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
			MatrixHandler.calculateCamera();
			MatrixHandler.calculateInstance();
			MatrixHandler.load();
			
			
			Iterator<Shape> iter = allFlyers.iterator();
			
			while(iter.hasNext()){
				Shape current=iter.next();
				current.tick();
				current.draw();
			}
			
			Display.update();
			Display.sync(60);
		}
		Display.destroy();
		
	}
	
	
	static ByteBuffer screenCapture(int width, int height){
		Robot robot=null;
		try {
			robot = new Robot();
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		BufferedImage img=robot.createScreenCapture(new Rectangle(0,0,width,height));
		ByteBuffer byteImg=ByteBuffer.allocateDirect(width*height*3);
		
		for(int i=0;i<height;i++){
			for(int j=0;j<width;j++){
				
				int pixel=img.getRGB(j,i);
				byteImg.put((byte)(pixel>>16));
				byteImg.put((byte)(pixel>>8));
				byteImg.put((byte)pixel);
				
			}
		}
		byteImg.flip();
		return byteImg;
	}
	
	static ViewPort screenCameraHandler=new ViewPort(800,600);
	
	static void resizeDrawSurface(int width, int height){
		GL11.glViewport(0, 0, width, height);
		screenCameraHandler.resize(width, height);
	}
	
	
	
	/**
	 * to receive the matrix from the matrix handler, use:
	 * layout(location=8) uniform mat4 worldMatrix;
	 * note: this should extend MovingInstance rather than BasicInstance
	 * @author Kent
	 */
	static class Shape extends BasicInstanceL {
		
		QuaternionF rotation;
		
		float r=(float)Math.random(),g=(float)Math.random(),b=(float)Math.random();
		
		Shape(long x, long y, long z){
		  position().x=x;
		  position().y=y;
		  position().z=z;
		  Vector3f axis=new Vector3f((float)Math.random()-0.5f,(float)Math.random()-0.5f,(float)Math.random()-0.5f);
		  axis.normalize();
			rotation=new QuaternionF(axis,(float)Math.random()*0.04f+0.02f);
		}
		
		
		public void tick(){
			position().z+=30;
			QuaternionF.mul(rotation,orientation(),orientation());
			
			MatrixHandler.setInstance(this);
			MatrixHandler.calculateInstance();
			MatrixHandler.load();
		}
		
		
		Shape draw(){
			GL11.glBegin(GL11.GL_TRIANGLE_STRIP);
			GL11.glColor3f(r, g, b);//unused for now
			
			GL11.glColor3f(1,0,0);
			GL11.glVertex3f(75,-75,0);
			
			GL11.glColor3f(0,0,0);
			GL11.glVertex3f(-75,-75,0);
			
			GL11.glColor3f(1,1,0);
			GL11.glVertex3f(75,75,0);
			
			GL11.glColor3f(0,1,0);
			GL11.glVertex3f(-75,75,0);
			
			GL11.glEnd();
			
			return this;
		}
		
	}
}





























