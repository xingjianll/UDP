package UDP;

import java.net.DatagramPacket;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import UDP.DataStructures.*;
import UDP.UDPConstants;

public class Slicer extends Thread
{
	

	public ArrayList<Data> msgList;
	ArrayList<SlicePacket> slicePacketList;
	
	public Slicer(ArrayList msgList, ArrayList slicePacketList)
	{
		this.msgList = msgList;
		this.slicePacketList = slicePacketList;
	}
	
	public void run()
	{
		int msgId;
		InetAddress peerAddr = null;
		int peerPort;
		byte[] sliceData;
		Data message;
		int totalSlices;
		byte[] bytes;
		int sliceLen;
		String slice;
		byte[] data;
		SlicePacket slicePacket;
		
		while(true)
		{
			if (msgList.size() == 0)
			{
				try 
				{
					sleep(UDPConstants.SENDER_WAITTIME);
				} 
				catch (InterruptedException e) 
				{
					e.printStackTrace();
				}
			}
			else synchronized(msgList)
			{
				//scan msgList
				for(int i=0; i<msgList.size(); i++)
				{
					message = msgList.get(i);
					msgId = Tools.generateID();
					
					//get the components of a message in message list
					try 
					{
						peerAddr = InetAddress.getByName(message.ip);
					} 
					catch (UnknownHostException e) 
					{
						e.printStackTrace();
					}
					peerPort = message.port;
					bytes = message.data;

					
					//separate packet to several slices
					if(message.data != null)
					{
						totalSlices = Tools.divideUp(bytes.length, UDPConstants.PACKET_LEN);

						System.out.println("total slices is:" + totalSlices);
						
						for(int j=0; j<totalSlices; j++)
						{
							if(j+1<totalSlices)
							{
							//  setting slice length to 128
								sliceLen = UDPConstants.PACKET_LEN;
							}
							else
							{
								//  packet data length for the remaining portion
								sliceLen = bytes.length - (j)*UDPConstants.PACKET_LEN;
							}
							
							sliceData = Tools.getBytes(bytes, j*UDPConstants.PACKET_LEN, sliceLen);
							String ssData = new String(sliceData,  StandardCharsets.UTF_8);
							
							slice = UDPConstants.SLICE_PACKET + UDPConstants.SEPARATOR + msgId+UDPConstants.SEPARATOR+totalSlices+UDPConstants.SEPARATOR+j
									+UDPConstants.SEPARATOR+ssData+UDPConstants.SEPARATOR;		
							
							data = slice.getBytes(StandardCharsets.UTF_8);
							
							slicePacket = new SlicePacket();
							
							slicePacket.ip = message.ip;
							slicePacket.msgId = msgId;
							slicePacket.packet = new DatagramPacket(data,0,data.length, peerAddr , peerPort);
							slicePacket.port = message.port;
							slicePacket.sendTimes = 0;
							slicePacket.sliceIndex   = j;
							slicePacket.timeStamp = System.currentTimeMillis();
							String strr = new String(data, StandardCharsets.UTF_8);
//							System.out.println("sliced packet index: " + slicePacket.sliceIndex);


							synchronized(slicePacketList)
							{
								slicePacketList.add(slicePacket);
							}
						}
					}
				}	
				msgList.clear();
			}
		}		
	}
}
