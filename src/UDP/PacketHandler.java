package UDP;

import java.io.IOException;
import java.net.DatagramPacket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import UDP.DataStructures.SlicePacket;

public class PacketHandler extends Thread
{
	ArrayList<SlicePacket> slicePacketList;
	
	public PacketHandler(ArrayList<SlicePacket> slicePacketList, ArrayList<SlicePacket> resendPacketList)
	{
		this.slicePacketList = slicePacketList;
	}
	

	public void run()
	{
		SlicePacket slicePacket;
		while (true)
		{
			//if there are no data in the packet list, sleep for 0.1seconds and release CPU

			if (slicePacketList.size() == 0)
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
			else 
			{
				//scan msgList
				synchronized (slicePacketList)
				{
					for (int i=0;i<slicePacketList.size();i++)
					{
						slicePacket = slicePacketList.get(0);
						try 
						{
							//instantly send slice for the first time
							UDP.socket.send(slicePacket.packet);
							slicePacket.sendTimes ++;
							slicePacket.timeStamp = System.currentTimeMillis();
							System.out.println("sent slice packet uid:" + slicePacket.sliceIndex);
							slicePacketList.remove(0);
//								//resend
//								interval = (int) (System.currentTimeMillis() - slicePacket.timeStamp);
//
//								if (interval > UDPConstants.SEND_INTERVAL && slicePacket.sendTimes < UDPConstants.MAX_RESEND_TIMES)
//								{
//									try 
//									{
//										UDP.socket.send(slicePacket.packet);
//										slicePacket.sendTimes ++;
//										System.out.println("resend slice packet!!!" + slicePacket.sendTimes);
//
//										slicePacket.timeStamp = System.currentTimeMillis();
//									} 
//									catch (IOException e) 
//									{
//										e.printStackTrace();
//									}
//								}
//								index++;
						}
						catch (IOException e) 
						{
							e.printStackTrace();
						}
					}
				}	
			}
		}
	}
}

