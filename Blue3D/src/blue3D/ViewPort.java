package blue3D;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;


public class ViewPort {
	
	private int width;
	private int height;
	
	private List<Camera> connectedCameras=new LinkedList<Camera>();
	
	
	public ViewPort(int w, int h){
		width=w;
		height=h;
	}
	
	
	public ViewPort connectCamera(Camera cam){
		connectedCameras.add(cam);
		cam.resize(width, height);
		return this;
	}
	
	
	int getWidth(){
		return width;
	}
	
	
	int getHeight(){
		return height;
	}
	
	
	public void resize(int w, int h){
		if (width!=w || height!=h){
			width=w;
			height=h;
			
			Iterator<Camera> iter = connectedCameras.iterator();
			while(iter.hasNext()){
				iter.next().resize(width, height);
			}
		}
	}
	
	
	public void disconnectCamera(Camera cam) {
		connectedCameras.remove(cam);
	}
	
}
