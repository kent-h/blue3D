package blue3D.incomplete.network;
import java.net.SocketAddress;

public class Peer {
	
	private long id;//global identifier for this user, unique
	SocketAddress address;//how to contact
	long lastComunicationTime;//time of the last communication received, in ticks.
	
	Peer(long peerId){
		id=peerId;
	}
	
	
	public long getId(){
		return id;
	}
	
	
	public void send(){
		//NetHandler.send(address, data);
	}


	public SocketAddress getAddress() {
		return address;
	}
	
}
