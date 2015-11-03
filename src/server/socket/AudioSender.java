package server.socket;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import server.fifo.FifoMicro;
import tools.Model;

public class AudioSender extends Thread{

	private ObjectOutputStream objectOut;
	
	public AudioSender(ObjectOutputStream oos) {
		objectOut = oos;
	}
	
	public void run(){
		
		try {
			while( true ){
				
				synchronized (FifoMicro.getList()) {
					try {
						FifoMicro.getList().wait();
					}catch( Exception e ){
						e.printStackTrace();
						break;
					}
				}
				
				while( FifoMicro.getList().size() != 0 ){
					//System.out.println(FifoMicro.getList().size());
					ByteArrayOutputStream baos = FifoMicro.pop();

					objectOut.write(baos.toByteArray());
					//objectOut.write(CompressionUtils.compress(baos.toByteArray()));
					//System.out.print(",");
				}
				
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Model.setClientConnected(false);
	}

}
