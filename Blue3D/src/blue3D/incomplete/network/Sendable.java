package blue3D.incomplete.network;


/**
 * used to send entire objects
 * @author Kent
 *
 */
public interface Sendable {
	
	/**
	 * @param array array to fill with data about this object
	 * @param start location in the array to start filling
	 * @return the location to start the next block of data
	 */
	abstract int getByteRepresentation(byte[] array, int start);
	
	/**
	 * @param array the array containing the data for this object
	 * @param start where in the array the data starts
	 * @return the start of the next block of data
	 */
	abstract int updateFromByteRep(byte[] array, int start);
	
}
