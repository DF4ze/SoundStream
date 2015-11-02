package client.fifo;

import java.util.LinkedList;

import javax.sound.sampled.AudioInputStream;

public class FifoAudioInput {

	private static LinkedList<AudioInputStream> list;
	
	private FifoAudioInput() {
		// TODO Auto-generated constructor stub
	}
	
	public static LinkedList<AudioInputStream> getList(){
		if( list == null )
			list = new LinkedList<AudioInputStream>();
		
		return list;
	}
	
	public static void add( AudioInputStream ais ){
		LinkedList<AudioInputStream> list = getList();
		synchronized (list) {
			list.addLast(ais);
			
			list.notifyAll();
		}
	}	
	
	public static AudioInputStream pop(){
		LinkedList<AudioInputStream> list = getList();
		AudioInputStream ais;
		synchronized (list) {
			ais = list.pop();
		}
		return ais;
	}

}
