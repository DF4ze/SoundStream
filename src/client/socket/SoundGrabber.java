package client.socket;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

import javax.sound.sampled.AudioInputStream;

import tools.Model;
import client.fifo.FifoAudioInput;

public class SoundGrabber extends Thread{

	private Socket socket;
	
	public SoundGrabber( Socket sock ) {
		socket = sock;
	}

	public void run(){
		System.out.println("En attente de reception audio");
    	;

        ObjectInputStream inObject = null;
		try {
			inObject = new ObjectInputStream(socket.getInputStream());
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}	
    	byte [] buf = new byte[ Model.BUFF_SIZE ];
        while( true ){
	        try {

	        	//long t0 = new Date().getTime();
				inObject.readFully(buf);
	        	//long t1 = new Date().getTime();
				//System.out.println("delay : "+(t1-t0));
			    
				//buf = CompressionUtils.decompress(buf);
				
		        AudioInputStream audioInputStream = new AudioInputStream(new ByteArrayInputStream(buf),Model.audioFormat, buf.length / Model.audioFormat.getFrameSize());
		        
		        FifoAudioInput.add(audioInputStream);
		       // System.out.print(",");
		        //baos.close();
				
	        }catch( Exception e ){
	        	e.printStackTrace();
	        	System.exit(0);
	        	break;
	        }
		}
				
	}
}
