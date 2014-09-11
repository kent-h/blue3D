package blue3D.type;



public class QuaternionF extends Quaternion{
	
	
	public float w=1,x=0,y=0,z=0;
	
	
	private static Matrix4f tmp=new Matrix4f();//may not be used across functions
	
	
	/**
	 * create identity quaternion
	 */
	public QuaternionF(){
		
	}
	
	/**
	 * copy constructor
	 * @param toCopy quaternion to be copied
	 */
	public QuaternionF(QuaternionF toCopy){
		w=toCopy.w;
		x=toCopy.x;
		y=toCopy.y;
		z=toCopy.z;
	}
	
	
	/**
	 * create quaternion with the given orientation
	 */
	public QuaternionF(float qw, float qx, float qy, float qz){
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
	public QuaternionF(Vector3f rotationAxis, float rotation){
		w=(float)Math.cos(rotation);
		float sin=(float)Math.sin(rotation);
		x=rotationAxis.x*sin;
		y=rotationAxis.y*sin;
		z=rotationAxis.z*sin;
	}
	
	
	//mathematical operations
	
	
	/**
	 * take a weighted average of q1, and q2, and store it in ret. 
	 */
	public static QuaternionF slerp(QuaternionF a, QuaternionF b, QuaternionF ret, float weight, boolean allowFlip){
		// Warning: this method should not normalize the Quaternion
		float cosAngle = QuaternionF.dot(a, b);
    
    float c1, c2;
    // Linear interpolation for close orientations
    if ((1.0 - Math.abs(cosAngle)) < 0.01) {
    	c1 = 1.0f - weight;
    	c2 = weight;
    }else{
    	// Spherical interpolation
    	float angle = (float) Math.acos(Math.abs(cosAngle));
    	float sinAngle = (float) Math.sin(angle);
    	c1 = (float) (Math.sin(angle * (1.0f - weight)) / sinAngle);
    	c2 = (float) (Math.sin(angle * weight) / sinAngle);
    }

    // Use the shortest path
    if (allowFlip && (cosAngle < 0.0))
    	c1 = -c1;
    
    ret.x = c1 * a.x + c2 * b.x;
    ret.y = c1 * a.y + c2 * b.y;
    ret.z = c1 * a.z + c2 * b.z;
    ret.w = c1 * a.w + c2 * b.w;
    
    return ret;
	}
	
	
	/**
	 * multiplication, this is NOT commutative
	 */
	public QuaternionF mul(QuaternionF right){
		float tw = (w*right.w - x*right.x - y*right.y - z*right.z);
		float tx = (w*right.x + x*right.w + y*right.z - z*right.y);
		float ty = (w*right.y - x*right.z + y*right.w + z*right.x);
		z = (w*right.z + x*right.y - y*right.x + z*right.w);
		y=ty;
		x=tx;
		w=tw;
		return this;
	}
	
	
	/**
	 * sets this quaternion to the multiplication of left and right (left*right)
	 * multiplication, this is NOT commutative
	 */
	public QuaternionF mul(QuaternionF left, QuaternionF right){
		float tw = (left.w*right.w - left.x*right.x - left.y*right.y - left.z*right.z);
		float tx = (left.w*right.x + left.x*right.w + left.y*right.z - left.z*right.y);
		float ty = (left.w*right.y - left.x*right.z + left.y*right.w + left.z*right.x);
		z = (left.w*right.z + left.x*right.y - left.y*right.x + left.z*right.w);
		y=ty;
		x=tx;
		w=tw;
		return this;
	}
	
	
	/**
	 * calculates and returns the dot product of two vectors
	 * @param quart1
	 * @param quart2
	 * @return the dot product, between -1 and 1 (assuming the quaternions are properly normalised).
	 */
	public static float dot(QuaternionF quart1, QuaternionF quart2){
		return quart1.x * quart2.x + quart1.y * quart2.y + quart1.z * quart2.z + quart1.w * quart2.w;
	}
	
	
	/**
	 * returns 'angle' between two quaternions, around the hyper-sphere, in radians.
	 */
	 public static float angle(QuaternionF quart1, QuaternionF quart2){
		 return (float)Math.acos(dot(quart1, quart2));
	 }
	 
	 
	 /**
	  * rotate this quaternion around the given vector, which is assumed to be a unit vector
	  * @param unitx unit vector's x component
	  * @param unity unit vector's y component
	  * @param unitz unit vecotr's z component
	  * @param angle amount to rotate
	  * @return this quaternion
	  */
	 public QuaternionF rotate(float unitx, float unity, float unitz, float angle){
		 //calculate rotation quaternion
		 angle/=2;
		 float tw=(float)Math.cos(angle);
		 float sin=(float)Math.sin(angle);
		 float tx=unitx*sin;
		 float ty=unity*sin;
		 float tz=unitz*sin;
		 
		 //add rotation, using quaternion multiplication
		 float tmpw = (w*tw - x*tx - y*ty - z*tz);
		 float tmpx = (w*tx + x*tw + y*tz - z*ty);
		 float tmpy = (w*ty - x*tz + y*tw + z*tx);
		 z = (w*tz + x*ty - y*tx + z*tw);
		 y=tmpy;
		 x=tmpx;
		 w=tmpw;
		 
		 return this;
	 }
	 
	 
	 public QuaternionF setIdentity(){
		 w=1;
		 x=y=z=0;
		 return this;
	 }
	 
	 
	 /**
	  * normalize the quaternion
	  */
	 public QuaternionF normalize(){
		 float dist=(float)Math.sqrt(w*w+x*x+y*y+z*z);
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
	 public void set(QuaternionF other){
		 w=other.w;
		 x=other.x;
		 y=other.y;
		 z=other.z;
	 }
	 
	 
	 /**
	  * note that the order here is x,y,z,w.  This should likely be changed.
	  * @param qx
	  * @param qy
	  * @param qz
	  * @param qw
	  */
	 public QuaternionF set(float qx, float qy, float qz, float qw){
		 w=qw;
		 x=qx;
		 y=qy;
		 z=qz;
		 return this;
	 }
	 
	 
		public Vector3l rotateVector(Vector3l in, Vector3l dest) {
			long tmpx = (long) ((1-2*(y*y + z*z))*in.x + (x*y + z*w)*2*in.y 		+ (x*z - y*w)*2*in.z);//(12+6)*3 = 54 operations
			long tmpy = (long) ((x*y - z*w)*2*in.x 		 + (1-2*(x*x + z*z))*in.y + (y*z + x*w)*2*in.z);
			dest.z = 		(long) ((x*z + y*w)*2*in.x 		 + (y*z - x*w)*2*in.y 		+ (1-2*(x*x + y*y))*in.z);
			dest.y=tmpy;
			dest.x=tmpx;
			return dest;
		}
		
		
		public Vector3d rotateVector(Vector3l in, Vector3d dest) {
			double tmpx = (1-2*(y*y + z*z))*in.x + (x*y + z*w)*2*in.y 		+ (x*z - y*w)*2*in.z;//(12+6)*3 = 54 operations
			double tmpy = (x*y - z*w)*2*in.x 		 + (1-2*(x*x + z*z))*in.y + (y*z + x*w)*2*in.z;
			dest.z = 			(x*z + y*w)*2*in.x 		 + (y*z - x*w)*2*in.y 		+ (1-2*(x*x + y*y))*in.z;
			dest.y=tmpy;
			dest.x=tmpx;
			return dest;
		}
		
		
		public Vector3d rotateVector(Vector3d in, Vector3d dest) {
			double tmpx = (1-2*(y*y + z*z))*in.x + (x*y + z*w)*2*in.y 		+ (x*z - y*w)*2*in.z;//(12+6)*3 = 54 operations
			double tmpy = (x*y - z*w)*2*in.x 		 + (1-2*(x*x + z*z))*in.y + (y*z + x*w)*2*in.z;
			dest.z = 			(x*z + y*w)*2*in.x 		 + (y*z - x*w)*2*in.y 		+ (1-2*(x*x + y*y))*in.z;
			dest.y=tmpy;
			dest.x=tmpx;
			return dest;
		}
	 
	 
	 /**
	  * @param in the vector to rotate
	  * @param dest where to store the rotated vector
	  * @return dest
	  */
	 public Vector3f rotateVector(Vector3l in, Vector3f dest){
		final float tmpx = (1-2*(y*y + z*z))*in.x + (x*y + z*w)*2*in.y 		 + (x*z - y*w)*2*in.z;//(12+6)*3 = 54 operations
		final float tmpy = (x*y - z*w)*2*in.x 		+ (1-2*(x*x + z*z))*in.y + (y*z + x*w)*2*in.z;
		dest.z = 		 (x*z + y*w)*2*in.x 		+ (y*z - x*w)*2*in.y 		 + (1-2*(x*x + y*y))*in.z;
		dest.y=tmpy;
		dest.x=tmpx;
		return dest;
	}
	 
	 /**
	  * @param in the vector to rotate
	  * @param dest where to store the rotated vector
	  * @return dest
	  */
	 public Vector3f rotateVector(Vector3f in, Vector3f dest){
		 final float xx=x*x, yy=y*y, zz=z*z,
				 xy=x*y, xz=x*z, xw=x*w,
				 yz=y*z, yw=y*w,
				 zw=z*w,
				 x2=in.x*2, y2=in.y*2, z2=in.z*2;
		 final float tmpx = (1-2*(yy + zz))*in.x + (xy + zw)*y2 				+ (xz - yw)*z2;//(12+6)*3 = 54 operations
		 final float tmpy = (xy - zw)*x2 				 + (1-2*(xx + zz))*in.y + (yz + xw)*z2;
		 dest.z = 		(xz + yw)*x2 				 + (yz - xw)*y2 				+ (1-2*(xx + yy))*in.z;
		 dest.y=tmpy;
		 dest.x=tmpx;
		 return dest;
	 }
	 
	 
	 /**
	  * rotate the vector around the inverse of this quaternion
	  * the inverse of a unit quaternion (w,x,y,z) is (w,-x,-y,-z)
	  * @param in the vector to rotate
	  * @param dest where to store the rotated vector
	  * @return dest
	  */
	 public Vector3f inverseRotateVector(Vector3f in, Vector3f dest){
		 float xx=x*x, yy=y*y, zz=z*z,
				 xy=x*y, xz=x*z, xw=x*w,
				 yz=y*z, yw=y*w,
				 zw=z*w,
				 x2=in.x*2, y2=in.y*2, z2=in.z*2;
		 float tmpx = (1-2*(yy + zz))*in.x + (xy - zw)*y2 				+ (xz + yw)*z2;//(12+6)*3 = 54 operations
		 float tmpy = (xy + zw)*x2 				 + (1-2*(xx + zz))*in.y + (yz - xw)*z2;
		 dest.z = 		(xz - yw)*x2 				 + (yz + xw)*y2 				+ (1-2*(xx + yy))*in.z;
		 dest.y=tmpy;
		 dest.x=tmpx;
		 return dest;
	 }
	 
	 
	 /**
	  * rotate the vector around the inverse of this quaternion
	  * the inverse of a unit quaternion (w,x,y,z) is (w,-x,-y,-z)
	  * @param in the vector to rotate
	  * @param dest where to store the rotated vector
	  * @return dest
	  */
	 public Vector3d inverseRotateVector(Vector3d in, Vector3d dest){
		 double xx=x*x, yy=y*y, zz=z*z,
				 xy=x*y, xz=x*z, xw=x*w,
				 yz=y*z, yw=y*w,
				 zw=z*w,
				 x2=in.x*2, y2=in.y*2, z2=in.z*2;
		 double tmpx = (1-2*(yy + zz))*in.x + (xy - zw)*y2 				+ (xz + yw)*z2;//(12+6)*3 = 54 operations
		 double tmpy = (xy + zw)*x2 				 + (1-2*(xx + zz))*in.y + (yz - xw)*z2;
		 dest.z = 		(xz - yw)*x2 				 + (yz + xw)*y2 				+ (1-2*(xx + yy))*in.z;
		 dest.y=tmpy;
		 dest.x=tmpx;
		 return dest;
	 }
	 
	 
	 /**
	  * rotate the vector around the inverse of this quaternion
	  * the inverse of a unit quaternion (w,x,y,z) is (w,-x,-y,-z)
	  * @param in the vector to rotate
	  * @param dest where to store the rotated vector
	  * @return dest
	  */
	 public Vector3l inverseRotateVector(Vector3l in, Vector3l dest){
		 float xx=x*x, yy=y*y, zz=z*z,
				 xy=x*y, xz=x*z, xw=x*w,
				 yz=y*z, yw=y*w,
				 zw=z*w,
				 x2=in.x*2, y2=in.y*2, z2=in.z*2;
		 float tmpx =	 (1-2*(yy + zz))*in.x  + (xy - zw)*y2 				+ (xz + yw)*z2;//(12+6)*3 = 54 operations
		 float tmpy =	 (xy + zw)*x2 				 + (1-2*(xx + zz))*in.y + (yz - xw)*z2;
		 dest.z=(long)((xz - yw)*x2 				 + (yz + xw)*y2 				+ (1-2*(xx + yy))*in.z);
		 dest.y=(long) tmpy;
		 dest.x=(long) tmpx;
		 return dest;
	 }
	 
	 
	 /**
	  * shorthand for rotateVector(in, dest)
	  * @param toRotate
	  * @return
	  */
	 public Vector3f rotateVector(Vector3f toRotate){
		 return rotateVector(toRotate, toRotate);
	 }
	 
	 
	 /**
	  * shorthand for rotateVector(in, dest)
	  * @param toRotate
	  * @return
	  */
	 public Vector3d rotateVector(Vector3d toRotate){
		 return rotateVector(toRotate, toRotate);
	 }
	 
	 
	 /**
	  * shorthand for rotateVector(in, dest)
	  * @param toRotate
	  * @return
	  */
	 public Vector3l rotateVector(Vector3l toRotate){
		 return rotateVector(toRotate, toRotate);
	 }
	 
	 
	 /**
	  * shorthand for inverseRotateVector(in, dest)
	  * @param toRotate
	  * @return
	  */
	 public Vector3f inverseRotateVector(Vector3f toRotate){
		 return inverseRotateVector(toRotate, toRotate);
	 }
	 
	 
	 /**
	  * shorthand for inverseRotateVector(in, dest)
	  * @param toRotate
	  * @return
	  */
	 public Vector3d inverseRotateVector(Vector3d toRotate){
		 return inverseRotateVector(toRotate, toRotate);
	 }
	 
	 
	 /**
	  * shorthand for inverseRotateVector(in, dest)
	  * @param toRotate
	  * @return
	  */
	 public Vector3l inverseRotateVector(Vector3l toRotate){
		 return inverseRotateVector(toRotate, toRotate);
	 }
	 
	 
	 /**
	  * get the direction this quaternion points along, no transform => (0,0,-1)
	  * @param dest location to store the vector
	  * @return dest
	  */
	 public Vector3f getVector(Vector3f dest){
		 dest.x=(x*z - y*w)*-2;
		 dest.y=(y*z + x*w)*-2;
		 dest.z=(x*x + y*y)*2-1;
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
	 * @return addToMatrix
	 */
	public Matrix4f calculateMatrix(Matrix4f addToMatrix) {
		
		//this has been extremely optimized, expect it to be difficult to read.
		
		tmp.m00=1 - 2*(y*y + z*z);
    tmp.m10=2*(x*y - z*w);
    tmp.m20=2*(x*z + y*w);
    
    tmp.m01=2*(x*y + z*w);
    tmp.m11=1 - 2*(x*x + z*z);
    tmp.m21=2*(y*z - x*w);
    
    tmp.m02=2*(x*z - y*w);
    tmp.m12=2*(y*z + x*w);
    tmp.m22=1 - 2*(x*x + y*y);
    
    tmp.m03=tmp.m13=tmp.m23=//right column
    tmp.m30=tmp.m31=tmp.m32=0;//bottom row
    tmp.m33=1;//bottom right
    
    Matrix4f.mul(addToMatrix,tmp,addToMatrix);
    return addToMatrix;
	}
	
	
	public String toString(){
		return w+":"+x+":"+y+":"+z+" length="+(float)Math.sqrt(w*w+x*x+y*y+z*z);
	}
	
}
