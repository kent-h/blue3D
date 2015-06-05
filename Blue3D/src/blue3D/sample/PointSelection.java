package blue3D.sample;

import java.awt.DisplayMode;
import java.util.Iterator;
import java.util.LinkedList;

import org.lwjgl.opengl.GL11;

import blue3D.CameraL;
import blue3D.MatrixHandler;
import blue3D.Shader;
import blue3D.ViewPort;
import blue3D.type.QuaternionF;
import blue3D.type.Vector3f;
import blue3D.type.Vector3l;
import blue3D.type.baseEntities.BasicInstanceL;
import blue3D.type.baseEntities.Instance;
import blue3D.utility.ClickablePointSelector;


public class PointSelection {
	
	
	static CameraL camera=new CameraL();
	
	
	public static void main(String[] args){
		
		int width=800, height=600;
		ViewPort vp=new ViewPort(width, height);
		vp.connectCamera(camera);
		
		
		try {
			Display.setDisplayMode(new DisplayMode(800,600));
			Display.setResizable(true);
			Display.create();
			GL11.glEnable(GL11.GL_DEPTH_TEST);
			GL11.glEnable(GL11.GL_CULL_FACE);
			GL11.glCullFace(GL11.GL_BACK);
		} catch (LWJGLException e) {
			
		}
		
		camera.use();
		
		new Shader().use();
		
		
		Mouse.setGrabbed(true);
		
		
		while (!Display.isCloseRequested() && !Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)){
			if (Display.wasResized()){
				width=Display.getWidth();
				height=Display.getHeight();
				GL11.glViewport(0, 0, width, height);
				vp.resize(width, height);
			}
			loop();
			Display.sync(60);
			Display.update();
			GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		}
		
	}
	
	
	static LinkedList<Instance> instanceQueue = new LinkedList<Instance>();
	static ClickablePointSelector clickablePointSelector= new ClickablePointSelector();
	
	
	public static void loop(){
		
		//it would be much better to get floating-point mouse coordinates, if possible
		QuaternionF.mul(new QuaternionF(new Vector3f(1,0,0),-Mouse.getDY()/1000f),camera.orientation(),camera.orientation());
		QuaternionF.mul(new QuaternionF(new Vector3f(0,1,0),Mouse.getDX()/1000f),camera.orientation(),camera.orientation());
		
		BasicInstanceL tmp=new BasicInstanceL();
		tmp.position().x=(long)(Math.random()*10000)-5000;
		tmp.position().y=(long)(Math.random()*10000)-5000;
		tmp.position().z=(long)(Math.random()*10000)-5000;
		instanceQueue.add(tmp);
		
		clickablePointSelector.addPoint(new Vector3l(0,0,0),tmp);
		
		MatrixHandler.calculateCamera();
		Iterator<Instance> iter=instanceQueue.iterator();
		
		clickablePointSelector.calculateBest();
		Vector3l bestPoint=clickablePointSelector.bestPosition();
		Instance bestInst=clickablePointSelector.bestInstance();
		
		
		while(iter.hasNext()){
			Instance current=iter.next();
			QuaternionF.mul(new QuaternionF(new Vector3f(1,0,0),0.01f), current.orientation(), current.orientation());
			draw(current, bestPoint, bestInst);
		}
		
		MatrixHandler.setInstance(null);
		MatrixHandler.calculateInstance();
		MatrixHandler.load();
		GL11.glBegin(GL11.GL_TRIANGLES);
		GL11.glVertex3f(0, 500, -5000);
		GL11.glVertex3f(-100, 0, -5000);
		GL11.glVertex3f(100, 0, -5000);
		GL11.glEnd();
		
	}
	
	
	static void draw(Instance i, Vector3l best, Instance bestInst){
		MatrixHandler.setInstance(i);
		MatrixHandler.calculateInstance();
		MatrixHandler.load();
		
		if (i.position()==best || bestInst==i){
			GL11.glColor3f(1, 0, 0);
		}else{
			GL11.glColor3f(0, 1, 0);
		}
		
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glVertex3f(-50, 50, 50);
		GL11.glVertex3f(-50, -50, 50);
		GL11.glVertex3f(50, -50, 50);
		GL11.glVertex3f(50, 50, 50);
		
		GL11.glVertex3f(-50, 50, -50);
		GL11.glVertex3f(-50, 50, 50);
		GL11.glVertex3f(50, 50, 50);
		GL11.glVertex3f(50, 50, -50);
		
		GL11.glVertex3f(50, -50, 50);
		GL11.glVertex3f(50, -50, -50);
		GL11.glVertex3f(50, 50, -50);
		GL11.glVertex3f(50, 50, 50);
		
		GL11.glVertex3f(-50, 50, 50);
		GL11.glVertex3f(-50, 50, -50);
		GL11.glVertex3f(-50, -50, -50);
		GL11.glVertex3f(-50, -50, 50);
		
		GL11.glVertex3f(50, -50, -50);
		GL11.glVertex3f(50, -50, 50);
		GL11.glVertex3f(-50, -50, 50);
		GL11.glVertex3f(-50, -50, -50);
		
		GL11.glVertex3f(50, 50, -50);
		GL11.glVertex3f(50, -50, -50);
		GL11.glVertex3f(-50, -50, -50);
		GL11.glVertex3f(-50, 50, -50);
		
		GL11.glEnd();
		
		
		
		MatrixHandler.setInstance(null);
		MatrixHandler.calculateInstance();
		MatrixHandler.load();
		GL11.glBegin(GL11.GL_LINES);
		GL11.glVertex3f(i.position().getFloatX(), i.position().getFloatY(), i.position().getFloatZ());
		Vector3f tmp=new Vector3f(0,0,-1000);
		i.orientation().rotateVector(tmp, tmp);
		GL11.glVertex3f(i.position().getFloatX()+tmp.x, i.position().getFloatY()+tmp.y, i.position().getFloatZ()+tmp.z);
		GL11.glEnd();
	}
	
}





























