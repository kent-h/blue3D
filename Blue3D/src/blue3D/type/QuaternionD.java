package blue3D.type;



public class QuaternionD extends Quaternion{
	
	
	public double w=1,x=0,y=0,z=0;
	
	
	private static Matrix4f tmp=new Matrix4f();//may not be used across functions
	
	
	/**
	 * create identity quaternion
	 */
	public QuaternionD(){
		
	}
	
	
	/**
	 * create quaternion with the given orientation
	 */
	public QuaternionD(double qw, double qx, double qy, double qz){
		w=qw;
		x=qx;
		y=qy;
		z=qz;
	}
	
	
	/**
	 * create a quaternion, with rotation r around the axes given by x
	 * @param rotationAxis x
	 * @param rotation r
	 */
	public QuaternionD(Vector3f rotationAxis, double rotation){
		w=Math.cos(rotation);
		double sin=Math.sin(rotation);
		x=rotationAxis.x*sin;
		y=rotationAxis.y*sin;
		z=rotationAxis.z*sin;
	}
	
	
	//mathematical operations
	
	
	/**
	 * take a weighted average of q0, and q1, and store it in ret. 
	 */
	public static QuaternionD slerp(QuaternionD q0, QuaternionD q1, QuaternionD ret, double weight, boolean allowFlip){
		// Warning: this method should not normalize the Quaternion
		double cosAngle = QuaternionD.dot(q0, q1);
    
    double c1, c2;
    // Linear interpolation for close orientations
    if ((1.0 - Math.abs(cosAngle)) < 0.01) {
    	c1 = 1.0f - weight;
    	c2 = weight;
    }else{
    	// Spherical interpolation
    	double angle = Math.acos(Math.abs(cosAngle));
    	double sinAngle = Math.sin(angle);
    	c1 = Math.sin(angle * (1.0f - weight)) / sinAngle;
    	c2 = Math.sin(angle * weight) / sinAngle;
    }

    // Use the shortest path
    if (allowFlip && (cosAngle < 0.0))
    	c1 = -c1;
    
    double 
    		tmpx=c1 * q0.x + c2 * q1.x,
    		tmpy=c1 * q0.y + c2 * q1.y,
    		tmpz=c1 * q0.z + c2 * q1.z;
    ret.w = c1 * q0.w + c2 * q1.w;
    ret.z = tmpz;
    ret.y = tmpy;
    ret.x = tmpx;
    
    
    return ret;
	}
	
	
	/**
	 * calculates and returns the dot product of two vectors
	 * @param quart1
	 * @param quart2
	 * @return the dot product, between -1 and 1 (assuming the quaternions are properly normalised).
	 */
	public static double dot(QuaternionD quart1, QuaternionD quart2){
		return quart1.x * quart2.x + quart1.y * quart2.y + quart1.z * quart2.z + quart1.w * quart2.w;
	}
	
	
	/**
	 * returns 'angle' between two quaternions, around the hyper-sphere, in radians.
	 */
	 public static double angle(QuaternionD quart1, QuaternionD quart2){
		 return Math.acos(dot(quart1, quart2));
	 }
	 
	 
	 /**
	  * rotate this quaternion around the given vector, which is assumed to be a unit vector
	  * @param unitx unit vector's x component
	  * @param unity unit vector's y component
	  * @param unitz unit vecotr's z component
	  * @param angle amount to rotate
	  * @return this quaternion
	  */
	 public QuaternionD rotate(double unitx, double unity, double unitz, double angle){
		 //calculate rotation quaternion
		 angle/=2;
		 double tw=Math.cos(angle);
		 double sin=Math.sin(angle);
		 double tx=unitx*sin;
		 double ty=unity*sin;
		 double tz=unitz*sin;
		 
		 //add rotation, using quaternion multiplication
		 double tmpw = (w*tw - x*tx - y*ty - z*tz);
		 double tmpx = (w*tx + x*tw + y*tz - z*ty);
		 double tmpy = (w*ty - x*tz + y*tw + z*tx);
		 z = (w*tz + x*ty - y*tx + z*tw);
		 y=tmpy;
		 x=tmpx;
		 w=tmpw;
		 
		 return this;
	 }
	 
	 
	 public QuaternionD setIdentity(){
		 w=1;
		 x=y=z=0;
		 return this;
	 }
	 
	 
	 /**
	  * normalize the quaternion
	  */
	 public QuaternionD normalize(){
		 double dist=Math.sqrt(w*w+x*x+y*y+z*z);
		 w/=dist;
		 x/=dist;
		 y/=dist;
		 z/=dist;
		 return this;
	 }
	 
	 
	 /**
	  * make this quaternion equal to the given quaternion
	  * @param other
	  */
	 public void set(QuaternionD other){
		 w=other.w;
		 x=other.x;
		 y=other.y;
		 z=other.z;
	 }
	 
	 
		public Vector3l rotateVector(Vector3l in, Vector3l dest) {
			long tmpx = (long) ((1-2*(y*y + z*z))*in.x + (x*y + z*w)*2*in.y 		+ (x*z - y*w)*2*in.z);//(12+6)*3 = 54 operations
			long tmpy = (long) ((x*y - z*w)*2*in.x 		 + (1-2*(x*x + z*z))*in.y + (y*z + x*w)*2*in.z);
			dest.z = 		(long) ((x*z + y*w)*2*in.x 		 + (y*z - x*w)*2*in.y 		+ (1-2*(x*x + y*y))*in.z);
			dest.y=tmpy;
			dest.x=tmpx;
			 
			return dest;
		}
	 
	 
	 /**
	  * @param in the vector to rotate
	  * @param dest where to store the rotated vector
	  * @return dest
	  */
	 public Vector3d rotateVector(Vector3l in, Vector3d dest){
		 
		 /*Vector3f u=new Vector3f(x, y, z);
		 
		 double dot=Vector3l.dot(u, in);
		 
		 Vector3l cross=new Vector3l();
		 Vector3l.cross(u, in, cross);
		 
		 
		 
		 u.scale(2.0f * dot);
     in.scale(w*w - dot);
     cross.scale(2*w);
     
		 dest.x = u.x+in.x+cross.x;
		 dest.y = u.y+in.y+cross.y;
		 dest.z = u.z+in.z+cross.z;*/
		 
		/* (1 - 2*(y*y + z*z))*in.x+2*(x*y + z*w)*in.y
	    2*(x*y - z*w)*in.x+(1 - 2*(x*x + z*z))*in.y;
	    2*(x*z + y*w)*in.x+2*(y*z - x*w)*in.y;
		 
		 2*(x*y + z*w)*in.y;
	    (1 - 2*(x*x + z*z))*in.y;
	    2*(y*z - x*w)*in.y;*/
		
		double tmpx = (1-2*(y*y + z*z))*in.x + (x*y + z*w)*2*in.y 		+ (x*z - y*w)*2*in.z;//(12+6)*3 = 54 operations
		double tmpy = (x*y - z*w)*2*in.x 		 + (1-2*(x*x + z*z))*in.y + (y*z + x*w)*2*in.z;
		dest.z = 		(x*z + y*w)*2*in.x 		 + (y*z - x*w)*2*in.y 		+ (1-2*(x*x + y*y))*in.z;
		dest.y=tmpy;
		dest.x=tmpx;
		
		return dest;
	}
	 
	 //speed up with different operations?
	 public Vector3d rotateVector(Vector3d in, Vector3d dest){
		 double tmpx = (1-2*(y*y + z*z))*in.x + (x*y + z*w)*2*in.y 		+ (x*z - y*w)*2*in.z;//(12+6)*3 = 54 operations
		 double tmpy = (x*y - z*w)*2*in.x 		 + (1-2*(x*x + z*z))*in.y + (y*z + x*w)*2*in.z;
		 dest.z = 		(x*z + y*w)*2*in.x 		 + (y*z - x*w)*2*in.y 		+ (1-2*(x*x + y*y))*in.z;
		 dest.y=tmpy;
		 dest.x=tmpx;
		 return dest;
	 }
	 
	 
	 /**
	  * get the direction this quaternion points along, no transform => (0,0,-1)
	  * @param dest location to store the vector
	  * @return dest
	  */
	 public Vector3d getVector(Vector3d dest){
		 dest.x=(x*z - y*w)*-2;
		 dest.y=(y*z + x*w)*-2;
		 dest.z=(x*x + y*y)*2-1;
		 return dest;
	 }
	
	
	/**
	 * rotates the given matrix
	 * this assumes that the quaternion is a unit quaternion
	 * @param addToMatrix
	 */
	public void calculateMatrix(Matrix4f addToMatrix) {
		
		//this has been extremely optimized, expect it to be difficult to read.
		
		tmp.m00=(float) (1 - 2*(y*y + z*z));
    tmp.m01=(float) (2*(x*y - z*w));
    tmp.m02=(float) (2*(x*z + y*w));
    
    tmp.m10=(float) (2*(x*y + z*w));
    tmp.m11=(float) (1 - 2*(x*x + z*z));
    tmp.m12=(float) (2*(y*z - x*w));
    
    tmp.m20=(float) (2*(x*z - y*w));
    tmp.m21=(float) (2*(y*z + x*w));
    tmp.m22=(float) (1 - 2*(x*x + y*y));
    
    tmp.m03=tmp.m13=tmp.m23=//right column
    tmp.m30=tmp.m31=tmp.m32=0;//bottom row
    tmp.m33=1;//bottom right
    
    Matrix4f.mul(addToMatrix,tmp,addToMatrix);
	}
	
	
	public String toString(){
		return w+":"+x+":"+y+":"+z+" length="+Math.sqrt(w*w+x*x+y*y+z*z);
	}
	
}
