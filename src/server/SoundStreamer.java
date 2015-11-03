package server;

import java.io.IOException;
import java.net.ServerSocket;

import server.socket.AccepterConnexions;
import tools.MicrophoneModel;

public class SoundStreamer {

	public SoundStreamer() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		System.out.println("Démarrage du server");
		// TODO Auto-generated method stub
		ServerSocket srvSocketCommandes;
		try {
			srvSocketCommandes = new ServerSocket( MicrophoneModel.PORT );
			
			Thread thread = new Thread(new AccepterConnexions( srvSocketCommandes ));
			thread.start();
			
			System.out.println("ServerSocket Sound started");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
