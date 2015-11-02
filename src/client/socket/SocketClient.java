package client.socket;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import tools.Model;

public class SocketClient {
	Socket socket;
	public SocketClient() {
		// TODO Auto-generated constructor stub
	}

	public void start(){

		try {
			socket = new Socket( Model.IP , Model.PORT );
		
			System.out.println("Connexion établie avec le serveur, "); // Si le message s'affiche c'est que je suis connecté
		
			SoundGrabber sg = new SoundGrabber(socket);
			//sg.setDaemon(true);
			sg.start();
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
