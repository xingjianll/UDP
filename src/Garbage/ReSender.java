package Garbage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import UDP.DataStructures.SlicePacket;

public class ReSender extends Thread
{
//	ArrayList<SlicePacket> tOutSlicePacketList;
//	SlicePacket slicePacket;
//
//	public ReSender(ArrayList<SlicePacket> tOutSlicePacketList) 
//	{
//		this.tOutSlicePacketList = tOutSlicePacketList;		
//	}
//	
//	public void run()
//	{
//		int index;
//		int interval;
//		boolean canNotSleep;
//
//		while (true)
//		{
//			canNotSleep = false;
//			
//			if (!canNotSleep)
//			{
//				try 
//				{
//					sleep(UDPConstants.SENDER_WAITTIME);
//				} 
//				catch (InterruptedException e) 
//				{
//					e.printStackTrace();
//				}
//			}
//			else synchronized(tOutSlicePacketList)
//			{ 
//				index = 0;
//				interval = 0;
//
//				while (index < tOutSlicePacketList.size())
//				{
//					SlicePacket slicePacket = tOutSlicePacketList.get(index);
//					interval = (int) (System.currentTimeMillis() - slicePacket.timeStamp);
//					
//					if (interval > UDPConstants.SEND_INTERVAL && slicePacket.sendTimes < UDPConstants.MAX_RESEND_TIMES)
//					{
//						try 
//						{
//							UDP.socket.send(slicePacket.packet);
//							slicePacket.sendTimes ++;
//							slicePacket.timeStamp = System.currentTimeMillis();
//							canNotSleep = true;
//							index++;
//						} 
//						catch (IOException e) 
//						{
//							e.printStackTrace();
//						}
//					}
//					else if (slicePacket.sendTimes >= UDPConstants.MAX_RESEND_TIMES)
//					{
//						tOutSlicePacketList.remove(index);
//						canNotSleep = true;
//					}
//				}
//			}
//		}
//		boolean sliceExists = false;
//
//		try 
//		{
//			TimeUnit.SECONDS.sleep(5);
//		} 
//		
//		catch (InterruptedException e) 
//		{
//			e.printStackTrace();
//		}
//
//			//check if ack is received (when slice is deleted)
//			for (int i=0; i<tOutSlicePacketList.size(); i++)
//			{
//				SlicePacket slicePacket2 = tOutSlicePacketList.get(i);
//
//				if (slicePacket2.ip.equals(slicePacket.ip) && slicePacket2.port == slicePacket.port
//						&& slicePacket2.msgId == slicePacket.msgId)
//				{
//					sliceExists = true;
//				}
//			}
//		
//			if (sliceExists == true)
//			{
//				try 
//				{
//					UDP.socket.send(slicePacket.packet);
//					sliceExists = false;
//					
//					for (int count = 0; count<2; count++)
//					{
//						try 
//						{
//							TimeUnit.SECONDS.sleep(5);
//						} 
//						
//						catch (InterruptedException e) 
//						{
//							e.printStackTrace();
//						}
//						
//						//check if ack is received (when slice is deleted)
//						for (int j=0; j<tOutSlicePacketList.size(); j++)
//						{
//							SlicePacket slicePacket2 = tOutSlicePacketList.get(j);
//
//							if (slicePacket2.ip.equals(slicePacket.ip) && slicePacket2.port == slicePacket.port
//									&& slicePacket2.msgId == slicePacket.msgId)
//							{
//								sliceExists = true;
//							}
//						}
//						
//						//execute the following when slice exists
//						if (sliceExists == true)
//						{
//							try 
//							{
//								UDP.socket.send(slicePacket.packet);
//								sliceExists = false;
//							} 
//							catch (IOException e) 
//							{
//								e.printStackTrace();
//							}
//						}
//					}
//				} 
//				catch (IOException e) 
//				{
//					e.printStackTrace();
//				}
//			}
//	}
}
