package blue3D.incomplete.network;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class NetHandler implements Runnable{
	
	static DatagramSocket socket;
	static Thread myThread;
	static volatile boolean open=true;
	DatagramPacket recieved=new DatagramPacket(new byte[1024], 1024);
	DatagramPacket toBeSent=new DatagramPacket(new byte[0], 0);
	
	
	/**
	 * created on startup, not destroyed until the program exits.
	 */
	NetHandler(){
		
		
		try {
			socket=new DatagramSocket();
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		myThread=new Thread(this);
		myThread.start();
	}

	public void run() {
		DatagramPacket recieved=new DatagramPacket(new byte[1024], 1024);
		while(open){
			try {
				socket.receive(recieved);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
	

	public void send(byte[] b, int start, int end, Peer to){
		toBeSent.setData(b, start, end);
		toBeSent.setSocketAddress(to.getAddress());
		try {
			socket.send(toBeSent);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	
	
}
