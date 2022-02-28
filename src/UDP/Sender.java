package UDP;


import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import UDP.DataStructures.Data;

public class Sender
{
	private ArrayList<Data> msgList;

	
	public Sender(ArrayList<Data> msgList)
	{
		this.msgList = msgList;
	}
	
	public void send(String peerIP, int peerPort, String msg)
	{
		Data data = new Data();
		data.ip = peerIP;
		data.port = peerPort;
		data.data = msg.getBytes(StandardCharsets.UTF_8);
		
		System.out.println("========================");

		System.out.println("send message length: " + data.data.length);
		
		synchronized(msgList)
		{
			msgList.add(data);
		}
	}
}
