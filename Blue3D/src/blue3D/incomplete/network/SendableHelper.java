package blue3D.incomplete.network;

public class SendableHelper {
	
	
	/**
	 * add a long to the given byte buffer.
	 * @param array the buffer to fill.
	 * @param currentPos current position, 8 bytes will be added starting from here
	 * @param l long to add
	 * @return the "new" current position of the buffer
	 */
	public static int getByteRep(byte[] array, int currentPos, long l){
		int end=currentPos+8;
		for (;currentPos<end;currentPos++){//first byte is least significant
			array[currentPos]=(byte)l;
			l>>=8;
		}
		return end;
	}
	
	
	/**
	 * add a double to the given byte buffer.
	 * @param array the buffer to fill.
	 * @param currentPos current position, 8 bytes will be added starting from here
	 * @param l long to add
	 * @return the "new" current position of the buffer
	 */
	public static int getByteRep(byte[] array, int currentPos, double d){//first byte is least significant
		long l=Double.doubleToRawLongBits(d);
		int end=currentPos+8;
		for (;currentPos<end;currentPos++){
			array[currentPos]=(byte)l;
			l<<=8;
		}
		return end;
	}
	
	
	/**
	 * add a int to the given byte buffer.
	 * @param array the buffer to fill.
	 * @param currentPos current position, 4 bytes will be added starting from here
	 * @param l long to add
	 * @return the "new" current position of the buffer
	 */
	public static int getByteRep(byte[] array, int currentPos, int i){//first byte is least significant
		int end=currentPos+4;
		for (;currentPos<end;currentPos++){
			array[currentPos]=(byte)i;
			i<<=8;
		}
		return end;
	}
	
	
	/**
	 * add a float to the given byte buffer.
	 * @param array the buffer to fill.
	 * @param currentPos current position, 4 bytes will be added starting from here
	 * @param l float to add
	 * @return the "new" current position of the buffer
	 */
	public static int getByteRep(byte[] array, int currentPos, float f){//first byte is least significant
		int i=Float.floatToRawIntBits(f);
		int end=currentPos+4;
		for (;currentPos<end;currentPos++){
			array[currentPos]=(byte)i;
			i<<=8;
		}
		return end;
	}
	
	
	/**
	 * get long from byte representation
	 * @param array an array of bytes, the 8 bytes representing a long
	 * @param start - where in the array to start pulling the bytes from
	 * @return the long from the array
	 */
	public static long getLongRep(byte[] array, int start){
		long l=0;//
		for(int i=start+7; i>=start ;i--){
			l=((l<<8))|array[i]&0x000000ff;
		}
		return l;
	}
	
	
	/**
	 * get double from byte representation
	 * @param array an array of bytes, the 8 bytes representing a double
	 * @param start - where in the array to start pulling the bytes from
	 * @return the double from the array
	 */
	public static double getDoubleRep(byte[] array, int start){
		long l=0;
		for(int i=start+7; i>=start ;i--){
			l=(l<<8)|array[i];
		}
		return Double.longBitsToDouble(l);
	}
	
	
	/**
	 * get int from byte representation
	 * @param array an array of bytes, the 4 bytes representing an int
	 * @param start - where in the array to start pulling the bytes from
	 * @return the int from the array
	 */
	public static int getIntRep(byte[] array, int start){
		int i=0;
		for(int j=start+7; j>=start ;j--){
			i=((i<<8))|array[j]&0x000000ff;
		}
		return i;
	}
	
	
	/**
	 * get float from byte representation
	 * @param array an array of bytes, the 4 bytes representing a float
	 * @param start - where in the array to start pulling the bytes from
	 * @return the float from the array
	 */
	public static float getFloatRep(byte[] array, int start){
		int i=0;
		for(int j=start+3; j>=start ;j--){
			i=(i<<8)|array[j];
		}
		return Float.intBitsToFloat(i);
	}
	
}
