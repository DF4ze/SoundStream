package client;

import client.socket.SocketClient;

public class SoundReceiver {

	public SoundReceiver() {
		
	}
	
	public static void main( String[] args ){
		System.out.println("Starting client app");
		SocketClient sc = new SocketClient();
		sc.start();
		
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		SoundPlayer sp = new SoundPlayer();
		sp.start();
	}

}
