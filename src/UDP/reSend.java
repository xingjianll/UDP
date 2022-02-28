package UDP;

import java.io.IOException;
import java.util.ArrayList;
import UDP.DataStructures.SlicePacket;

public class reSend extends Thread
{
	ArrayList<SlicePacket> slicePacketList;
	ArrayList<SlicePacket> resendPacketList;
	
	public reSend(ArrayList<SlicePacket> slicePacketList, ArrayList<SlicePacket> resendPacketList)
	{
		this.slicePacketList = slicePacketList;
		this.resendPacketList = resendPacketList;
	}
	
	public void run()
	{
		SlicePacket slicePacket;
		while (true)
		{
			{
				//if there are no data in the packet list, sleep for 0.1seconds and release CPU

				if (resendPacketList.size() == 0)
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
					synchronized (resendPacketList)
					{
						int interval;
						for (int i=0;i<resendPacketList.size();i++)
						{
							slicePacket = slicePacketList.get(i);
							interval = (int) (System.currentTimeMillis() - slicePacket.timeStamp);
							if (interval > UDPConstants.SEND_INTERVAL && slicePacket.sendTimes < UDPConstants.MAX_RESEND_TIMES)
							{
								try 
								{
									UDP.socket.send(slicePacket.packet);
									slicePacket.sendTimes ++;
									System.out.println("resend slice packet!!!" + slicePacket.sendTimes);

									slicePacket.timeStamp = System.currentTimeMillis();
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
	}
}
