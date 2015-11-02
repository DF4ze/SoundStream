package client;

import java.io.IOException;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

import tools.Model;
import client.fifo.FifoAudioInput;

public class SoundPlayer extends Thread{

	public SoundPlayer() {
		
	}

	public void run(){
		try {
			SourceDataLine sourceDataLine = (SourceDataLine) AudioSystem.getLine(new DataLine.Info(SourceDataLine.class, Model.audioFormat));

	        sourceDataLine.open(Model.audioFormat);
	        sourceDataLine.start();
	        
	        System.out.print("Buffering");
	        synchronized (FifoAudioInput.getList()) {
	        	
				while( FifoAudioInput.getList().size() < Model.MIN_BUFFERED ){
					try {
						FifoAudioInput.getList().wait();
						System.out.print(".");
					} catch (InterruptedException e) {}
				}
			}
			System.out.println("");
	        
			
	        int cnt = 0;
	        try {
	        	while (true) {
	        		
	        		byte tempBuffer[] = new byte[Model.BUFF_SIZE];
	        		
	        		if( FifoAudioInput.getList().size() != 0 ){
	        			
		    	        cnt = FifoAudioInput.pop().read(tempBuffer, 0,tempBuffer.length);
			            if (cnt > 0) {
		                    // Write data to the internal buffer of
		                    // the data line where it will be
		                    // delivered to the speaker.
			            	//System.out.print("'");
			            	sourceDataLine.write(tempBuffer, 0, cnt);
		                    sourceDataLine.drain();
		                   // System.out.print("'");
		                }// end if
	        		
	        		}else{
	        			System.out.print("Buffering");
	        	        synchronized (FifoAudioInput.getList()) {
	        	        	
	        				while( FifoAudioInput.getList().size() < Model.MIN_BUFFERED ){
	        					try {
	        						FifoAudioInput.getList().wait();
	        						System.out.print(".");
	        					} catch (InterruptedException e) {}
	        				}
	        			}
	        			System.out.println("");	        			
	        		}
	            }
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        // Block and wait for internal buffer of the
	        // data line to empty.
	        sourceDataLine.drain();
	        sourceDataLine.close();		
		} catch (LineUnavailableException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}
