package UDP;

import java.net.DatagramPacket;
import java.util.ArrayList;

public interface DataStructures 
{
	public class Slice
	{
		String msgId;
		int port;
		String ip  = "";
		int totalSlices;
		int count;	//used for counting how many slices currently received by the matrix sublist
		int sliceIndex;
		String sliceData;
		long  timeStamp;
	}
	
	public class Data
	{
		String ip  = "";
		int port;
		byte[] data;
	}	

	public class SlicePacket
	{
		int msgId;
		int sliceIndex;
		String ip = "";
		int port;
		long  timeStamp;
		int sendTimes = 0;
		DatagramPacket packet;
	}	
	
	public class Packet // determines whether the packet sent is a slice or a ack
	{
		int type; //packet type 0 is slice packet, 1 is slice packet ack
		String content;
	}
	
	public class SliceAck
	{
		int msgId;
		String ip  = "";
		int port;
		int sliceIndex;
	}	
}
