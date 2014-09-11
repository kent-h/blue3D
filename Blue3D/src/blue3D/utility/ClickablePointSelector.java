package blue3D.utility;

import java.util.Iterator;
import java.util.LinkedList;

import blue3D.Camera;
import blue3D.CameraD;
import blue3D.CameraL;
import blue3D.MatrixHandler;
import blue3D.type.Instance;
import blue3D.type.Vector3d;
import blue3D.type.Vector3f;
import blue3D.type.Vector3l;


/**
 * Given a list of points, this class determines which is the closest to the centre of the camera view.
 * @author Kent
 */

public class ClickablePointSelector{
	
	//the maximum angle between the point's angle and the camera's angle
	//3 degrees by default
	static float maxDottedAngle=0.99862953475f;
	
	//all connected points
	LinkedList<ClickablePoint> allPoints=new LinkedList<ClickablePoint>();
	
	
	/**
	 * add an absolute point to the snap-list
	 * @param position
	 */
	public void addPoint(Vector3l position){
		allPoints.add(new ClickablePoint(position));
	}
	
	
	/**
	 * add a point which is connected to an object to the list
	 * @param offset the position of the point, relative to the instance
	 * @param relativeTo the instance this point is connected to
	 */
	public void addPoint(Vector3l offset, Instance relativeTo){
		allPoints.add(new ClickablePoint(offset, relativeTo));
	}
	
	
	/**
	 * Internal comparison used to determine the best point
	 * @param point point to compare, NOT null
	 * @param betterThan second point to compare, or null
	 * @return the closest valid point, or, if neither is valid, p2.
	 */
	private static ClickablePoint isBetter(ClickablePoint point, ClickablePoint betterThan){
		if (betterThan==null)return point.isPointedAt?point:null;
		
		if (point.isPointedAt){
			if (betterThan.isPointedAt){
				return point.distanceSquared<betterThan.distanceSquared?point:betterThan;
			}else{
				return point;
			}
		}else{
			return betterThan;
		}
	}
	
	
	/**
	 * determines the best out of all the points, must be called before bestPosition() or bestInstance() will return valid values.
	 */
	public void calculateBest() {
Iterator<ClickablePoint> point=allPoints.iterator();
		
		ClickablePoint current;
		best=null;
		while(point.hasNext()){
			current=point.next();
			current.calculate();
			best=isBetter(current, best);
		}
	}
	
	ClickablePoint best;
	
	
	/**
	 * @return the best Point's position (relative or absolute), or null if none are in front of the camera
	 */
	public Vector3l bestPosition(){
		if (best==null){
			return null;
		}
		return best.position;
	}
	
	
	/**
	 * @return instance the best point is relative to, or null, if the best point is null or absolute
	 */
	public Instance bestInstance(){
		if (best==null){
			return null;
		}
		return best.relativeTo;
	}
	
	
	
	public Iterator<Vector3l> positionIterator() {
		return new Iterator<Vector3l>(){
			private Iterator<ClickablePoint> iter;
			
			{iter=allPoints.iterator();}

			@Override
			public boolean hasNext() {
				return iter.hasNext();
			}

			@Override
			public Vector3l next() {
				return iter.next().position;
			}

			@Override
			public void remove() {
				iter.remove();
			}
		};
	}


	public Iterator<Instance> instanceIterator() {
		return new Iterator<Instance>(){
			private Iterator<ClickablePoint> iter;
			
			{iter=allPoints.iterator();}

			@Override
			public boolean hasNext() {
				return iter.hasNext();
			}

			@Override
			public Instance next() {
				return iter.next().relativeTo;
			}

			@Override
			public void remove() {
				iter.remove();
			}
		};
	}
	
	
	/**
	 * set the angle the camera centre has to be from a point for it to be "moused over"
	 * @param angle
	 */
	public void setMaxAngle(float angle){
		maxDottedAngle=(float)Math.cos(angle);
	}
	
	
	/**
	 * remove all ClickablePoints with the given location
	 * @param point
	 */
	public void remove(Vector3l point) {
		Iterator<ClickablePoint> iter=allPoints.iterator();
		LinkedList<ClickablePoint> toRemove=new LinkedList<ClickablePoint>();
		while (iter.hasNext()){
			ClickablePoint current=iter.next();
			if (point==current.getPosition()){
				toRemove.add(current);
			}
		}
		//remove every element in the generated list
		while (!toRemove.isEmpty()){
			allPoints.remove(toRemove.remove());
		}
	}
	
	
	/**
	 * remove all ClickablePoints which are relative to the given instance
	 * @param instance
	 */
	public void remove(Instance instance) {
		Iterator<ClickablePoint> iter=allPoints.iterator();
		LinkedList<ClickablePoint> toRemove=new LinkedList<ClickablePoint>();
		while (iter.hasNext()){
			ClickablePoint current=iter.next();
			if (instance==current.getRelativeTo()){
				toRemove.add(current);
			}
		}
		//remove every element in the generated list
		while (!toRemove.isEmpty()){
			allPoints.remove(toRemove.remove());
		}
	}
	
	
	/**
	 * clear all points
	 */
	public void clear() {
		allPoints.clear();
	}
	
	
	//internal class
	
	
	private static class ClickablePoint{
		
		//direction the camera points
		static Vector3f cameraDirection=new Vector3f();
		
		static Vector3d tmp=new Vector3d();
		
		private Vector3l position;
		
		//if the point is part of an object, it should rotate and move relative to the object
		Instance relativeTo;
		
		public boolean isPointedAt;
		double distanceSquared;
		
		/**
		 * constructor for a (non-relative) point
		 * @param pos
		 */
		ClickablePoint(Vector3l pos){
			position=pos;
		}
		
		
		/**
		 * constructor for a point which is relative to an instance
		 * @param pos
		 * @param rotateAround
		 */
		ClickablePoint(Vector3l pos, Instance rotateAround){
			position=pos;
			relativeTo=rotateAround;
		}


		public Vector3l getPosition() {
			return position;
		}
		
		
		public Instance getRelativeTo(){
			return relativeTo;
		}
		
	  boolean calculate(){
	  	Camera cam=MatrixHandler.getCamera();
	  	if (cam instanceof CameraL){
		  	Vector3l cameraPosition=((CameraL)cam).position();
		  	((CameraL)cam).orientation().getVector(cameraDirection);
		  	
		  	if (relativeTo==null){//if this point is absolute
		  		tmp.x=position.x-cameraPosition.x;
		  		tmp.y=position.y-cameraPosition.y;
		  		tmp.z=position.z-cameraPosition.z;
		  	}else{//if the point is relative the an instance
		  		relativeTo.orientation().rotateVector(position, tmp);//rotate point around instance
		  		Vector3l relativeToPos=(Vector3l) (relativeTo.position());
		  		tmp.x=(relativeToPos.x+tmp.x)-cameraPosition.x;//translate the now rotated position by the relative instance and camera
		  		tmp.y=(relativeToPos.y+tmp.y)-cameraPosition.y;
		  		tmp.z=(relativeToPos.z+tmp.z)-cameraPosition.z;
		  	}
		  	
	  	}else if (cam instanceof CameraD){
	  		
	  		Vector3d cameraPosition=((CameraD)cam).position();
		  	((CameraD)cam).orientation().getVector(cameraDirection);
	  		
	  		if (relativeTo==null){//if this point is absolute
		  		tmp.x=position.x-cameraPosition.x;
		  		tmp.y=position.y-cameraPosition.y;
		  		tmp.z=position.z-cameraPosition.z;
		  	}else{//if the point is relative the an instance
		  		relativeTo.orientation().rotateVector(position, tmp);//rotate point around instance
		  		Vector3l relativeToPos=(Vector3l) (relativeTo.position());
		  		tmp.x=(relativeToPos.x+tmp.x)-cameraPosition.x;//translate the now rotated position by the relative instance and camera
		  		tmp.y=(relativeToPos.y+tmp.y)-cameraPosition.y;
		  		tmp.z=(relativeToPos.z+tmp.z)-cameraPosition.z;
		  	}
	  	}
	  	
	  	
	  	
	  	distanceSquared=tmp.lengthSquared();
  		tmp.normalize();
	  	return isPointedAt=cameraDirection.dot(tmp)>maxDottedAngle;
	  }
		
	}


	public void removeAll() {
		allPoints.clear();
	}
	
}






























