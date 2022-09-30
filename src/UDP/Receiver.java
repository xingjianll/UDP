package UDP;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;

import javax.xml.crypto.Data;

import UDP.DataStructures.Slice;
import UDP.DataStructures.SliceAck;
import UDP.DataStructures.SlicePacket;

public class Receiver extends Thread
{
	
	ArrayList<Slice> sliceList;
	ArrayList<SlicePacket> slicePacketList;
	int count = 0;

	public Receiver(ArrayList sliceList, ArrayList slicePacketList)
	{
		this.sliceList = sliceList;
		this.slicePacketList = slicePacketList;
	}
	
	public void run()
	{
		Slice slice;
		String dataStr;
		
		int type;
		int port;
		String ip;
		String msgId;
		int totalSlices;
		int sliceIndex;
		String sliceData;
		int sep;
	

		while (true)
		{ 
			try 
			{
				byte[] buffer =new byte[UDPConstants.PACKET_LEN*2];
				DatagramPacket slicePacket  = new DatagramPacket(buffer,0,buffer.length);

				//receive packet
				UDP.socket.receive(slicePacket);
				port = slicePacket.getPort();
				System.out.println(port);
				ip = slicePacket.getAddress().getHostAddress();
				System.out.println(ip);
				dataStr = new String(slicePacket.getData(), StandardCharsets.UTF_8);

				// find the index of the three separators
				sep = dataStr.indexOf(UDPConstants.SEPARATOR);
				type = Integer.valueOf(dataStr.substring(0, sep));
				//					System.out.println("type is: "+ type);

				if (type == UDPConstants.SLICE_ACK)
				{
					handleAck(dataStr.substring(sep+1), ip, port);
				}
				else
				{
					handleSlice(dataStr.substring(sep+1), ip, port);
					count ++;

				}			
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			} 
			
		}
	}
	
	public void handleAck(String dataStr, String ip, int port)
	{
		SlicePacket packet;
		
		int sep1 = dataStr.indexOf(UDPConstants.SEPARATOR);
		int sep2 = dataStr.indexOf(UDPConstants.SEPARATOR, sep1+1);

		SliceAck ack = new SliceAck();
		ack.ip  = ip;
		ack.port =  port;
		ack.msgId = Integer.valueOf(dataStr.substring(0, sep1));
		ack.sliceIndex = Integer.valueOf(dataStr.substring(sep1+1, sep2));

		synchronized(slicePacketList)
		{
//			int index = 0;
//			while (index < slicePacketList.size())
//			{
//				packet = slicePacketList.get(index);
//				if (ack.ip.equals(packet.ip) && ack.port == packet.port && ack.msgId == packet.msgId && ack.sliceIndex == packet.sliceIndex)
//				{
//					slicePacketList.remove(index);
//					System.out.println("removed slice uid: " + packet.sliceIndex );
//				}
//				else
//				{
//					index++;
//				}
//			}
		}
	}
	
	public void handleSlice(String dataStr, String ip, int port)
	{	
		Slice slice = new Slice();

		int sep1 = dataStr.indexOf(UDPConstants.SEPARATOR);
		int sep2 = dataStr.indexOf(UDPConstants.SEPARATOR, sep1+1);
		int sep3 = dataStr.indexOf(UDPConstants.SEPARATOR, sep2+1);
		int sep4 = dataStr.indexOf(UDPConstants.SEPARATOR, sep3+1);

		
		// find corresponding data using the separator indexes

		slice.ip = ip;
		slice.port = port;
		slice.msgId = dataStr.substring(0, sep1);
		slice.totalSlices = Integer.valueOf(dataStr.substring(sep1+1, sep2));
		slice.sliceIndex = Integer.valueOf(dataStr.substring(sep2+1, sep3));
		slice.sliceData = dataStr.substring(sep3+1,sep4);
		slice.timeStamp = System.currentTimeMillis();
		System.out.println("received slice uid: " + slice.sliceIndex);

		synchronized(sliceList)
		{
			sliceList.add(slice);
		}
		
//		SliceAck sliceAck = new SliceAck();
//		
//		sliceAck.ip = ip;
//		sliceAck.port = port;
//		sliceAck.msgId = Integer.valueOf(slice.msgId);
//		sliceAck.sliceIndex = slice.sliceIndex;
//		
//		String strAck = UDPConstants.SLICE_ACK + UDPConstants.SEPARATOR + 
//				sliceAck.msgId + UDPConstants.SEPARATOR + sliceAck.sliceIndex + UDPConstants.SEPARATOR;		
//		
//		byte[] data = strAck.getBytes(StandardCharsets.UTF_8);
//		
//		DatagramPacket ack;
//		try 
//		{
//			ack = new DatagramPacket(data,0,data.length, InetAddress.getByName(ip) , port);
//			
//			UDP.socket.send(ack);
//		} 
//		catch (Exception e) 
//		{
//			e.printStackTrace();
//		}
	}
}
