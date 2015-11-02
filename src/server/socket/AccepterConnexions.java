package server.socket;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import server.MicroRetreiver;
import tools.Model;

public class AccepterConnexions extends Thread{

	ServerSocket srvSocket;
	
	public AccepterConnexions( ServerSocket srvSocket ) {
		this.srvSocket = srvSocket;
	}
	
	public void run(){
		while( true ){
			try {
				System.out.println("Attente de connexion...");
				Socket socket = srvSocket.accept();
				
				if( !Model.isClientConnected() ){
					ObjectOutputStream outObject = new ObjectOutputStream(socket.getOutputStream());
					
					
					Thread t1 = new Thread(new AudioSender(outObject));
					t1.start();
					
					Model.setClientConnected(true);
					
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {}
					
					Thread t2 = new Thread(new MicroRetreiver()); // il faut faire un singleton...!
					t2.start();
					System.out.println("Client Connecte");
					
				}else
					System.out.println("Tentative de connexion d'un clients alors que le stream est occupe");

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Model.setClientConnected(false);
			}
		}
	}

}
