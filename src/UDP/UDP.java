package UDP;

import java.io.IOException;

import java.net.*;
import java.util.ArrayList;

import UDP.DataStructures.Data;
import UDP.DataStructures.Slice;
import UDP.DataStructures.SlicePacket;
public class UDP extends Thread implements IfUDP
{
	public static DatagramSocket socket;
	private IfUDPUser user;
	
	private Sender sender;
	private Slicer slicer;
	private PacketHandler handler;
	private Matrix matrix;
	private Constructor constructor;
	private Receiver receiver;
	private TimeOut timeOut;
	
	private ArrayList<Data> msgList;
	private ArrayList<SlicePacket> slicePacketList;
	private ArrayList<SlicePacket> resendPacketList;
	private ArrayList<Slice> sliceList;
	private ArrayList<ArrayList<Slice>> sliceMatrix;
//	private ArrayList<SlicePacket> tOutSlicePacketList = new ArrayList<SlicePacket>();

	
	public UDP()
	{
		msgList = new ArrayList<Data>();
		slicePacketList = new ArrayList<SlicePacket>();
		sliceList = new ArrayList<Slice>();
		sliceMatrix = new ArrayList<ArrayList<Slice>>();		
	}
	
	public void init(int port, IfUDPUser user)
	{
		try 
		{
			//socket to receive data packet
			socket = new DatagramSocket(port);
		} 
		catch (SocketException e) 
		{
			e.printStackTrace();
		}
		
		this.user = user;
		
		receiver = new Receiver(sliceList, slicePacketList);
		receiver.start();
		
		matrix = new Matrix(sliceList, sliceMatrix);
		matrix.start();
		
		constructor = new Constructor(sliceMatrix, user);
		constructor.start();
		
		sender = new Sender(msgList);
		
		slicer = new Slicer(msgList, slicePacketList);
		slicer.start();
		
		handler = new PacketHandler(slicePacketList, resendPacketList);
		handler.start();

		timeOut = new TimeOut(slicePacketList, sliceMatrix);
	}
	
	public void send(String peerIP,int peerPort, String msg)
	{
		sender.send(peerIP, peerPort, msg);
	}
	
	public void onMsg(String data)
	{
		
	}
	
	

}
