package blue3D.type;

public abstract class Quaternion {
	
	public abstract Quaternion ln();
	public abstract Quaternion exp();
	
	/**
	 * multiplication, this is NOT commutative
	 */
	public static QuaternionF mul(QuaternionF left, QuaternionF right, QuaternionF dest){
		float w = (left.w*right.w - left.x*right.x - left.y*right.y - left.z*right.z);
		float x = (left.w*right.x + left.x*right.w + left.y*right.z - left.z*right.y);
		float y = (left.w*right.y - left.x*right.z + left.y*right.w + left.z*right.x);
		dest.z = (left.w*right.z + left.x*right.y - left.y*right.x + left.z*right.w);
		dest.y=y;
		dest.x=x;
		dest.w=w;
		return dest;
	}
	
	
	/**
	 * multiplication, this is NOT commutative
	 */
	public static QuaternionF mul(QuaternionD left, QuaternionF right, QuaternionF dest){
		float w = (float) (left.w*right.w - left.x*right.x - left.y*right.y - left.z*right.z);
		float x = (float) (left.w*right.x + left.x*right.w + left.y*right.z - left.z*right.y);
		float y = (float) (left.w*right.y - left.x*right.z + left.y*right.w + left.z*right.x);
		dest.z = (float) (left.w*right.z + left.x*right.y - left.y*right.x + left.z*right.w);
		dest.y=y;
		dest.x=x;
		dest.w=w;
		return dest;
	}
	
	
	/**
	 * multiplication, this is NOT commutative
	 */
	public static QuaternionF mul(QuaternionF left, QuaternionD right, QuaternionF dest){
		float w = (float) (left.w*right.w - left.x*right.x - left.y*right.y - left.z*right.z);
		float x = (float) (left.w*right.x + left.x*right.w + left.y*right.z - left.z*right.y);
		float y = (float) (left.w*right.y - left.x*right.z + left.y*right.w + left.z*right.x);
		dest.z = (float) (left.w*right.z + left.x*right.y - left.y*right.x + left.z*right.w);
		dest.y=y;
		dest.x=x;
		dest.w=w;
		return dest;
	}
	
	
	/**
	 * multiplication, this is NOT commutative
	 */
	public static QuaternionF mul(QuaternionD left, QuaternionD right, QuaternionF dest){
		float w = (float) (left.w*right.w - left.x*right.x - left.y*right.y - left.z*right.z);
		float x = (float) (left.w*right.x + left.x*right.w + left.y*right.z - left.z*right.y);
		float y = (float) (left.w*right.y - left.x*right.z + left.y*right.w + left.z*right.x);
		dest.z = (float) (left.w*right.z + left.x*right.y - left.y*right.x + left.z*right.w);
		dest.y=y;
		dest.x=x;
		dest.w=w;
		return dest;
	}
	
	
	/**
	 * multiplication, this is NOT commutative
	 */
	public static QuaternionD mul(QuaternionF left, QuaternionF right, QuaternionD dest){
		double w = left.w*right.w - left.x*right.x - left.y*right.y - left.z*right.z;
		double x = left.w*right.x + left.x*right.w + left.y*right.z - left.z*right.y;
		double y = left.w*right.y - left.x*right.z + left.y*right.w + left.z*right.x;
		dest.z = left.w*right.z + left.x*right.y - left.y*right.x + left.z*right.w;
		dest.y=y;
		dest.x=x;
		dest.w=w;
		return dest;
	}
	
	
	/**
	 * multiplication, this is NOT commutative
	 */
	public static QuaternionD mul(QuaternionD left, QuaternionF right, QuaternionD dest){
		double w = left.w*right.w - left.x*right.x - left.y*right.y - left.z*right.z;
		double x = left.w*right.x + left.x*right.w + left.y*right.z - left.z*right.y;
		double y = left.w*right.y - left.x*right.z + left.y*right.w + left.z*right.x;
		dest.z = left.w*right.z + left.x*right.y - left.y*right.x + left.z*right.w;
		dest.y=y;
		dest.x=x;
		dest.w=w;
		return dest;
	}
	
	
	/**
	 * multiplication, this is NOT commutative
	 */
	public static QuaternionD mul(QuaternionF left, QuaternionD right, QuaternionD dest){
		double w = left.w*right.w - left.x*right.x - left.y*right.y - left.z*right.z;
		double x = left.w*right.x + left.x*right.w + left.y*right.z - left.z*right.y;
		double y = left.w*right.y - left.x*right.z + left.y*right.w + left.z*right.x;
		dest.z = left.w*right.z + left.x*right.y - left.y*right.x + left.z*right.w;
		dest.y=y;
		dest.x=x;
		dest.w=w;
		return dest;
	}
	
	
	/**
	 * multiplication, this is NOT commutative
	 */
	public static QuaternionD mul(QuaternionD left, QuaternionD right, QuaternionD dest){
		double w = left.w*right.w - left.x*right.x - left.y*right.y - left.z*right.z;
		double x = left.w*right.x + left.x*right.w + left.y*right.z - left.z*right.y;
		double y = left.w*right.y - left.x*right.z + left.y*right.w + left.z*right.x;
		dest.z = left.w*right.z + left.x*right.y - left.y*right.x + left.z*right.w;
		dest.y=y;
		dest.x=x;
		dest.w=w;
		return dest;
	}
	
}
