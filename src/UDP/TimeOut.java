package UDP;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;


import UDP.DataStructures.Slice;
import UDP.DataStructures.SlicePacket;

public class TimeOut 
{
	ArrayList<SlicePacket> slicePacketList;
	ArrayList<ArrayList<Slice>> sliceMatrix;
	
	public TimeOut(ArrayList<SlicePacket> slicePacketList, ArrayList<ArrayList<Slice>> sliceMatrix)
	{
		this.slicePacketList = slicePacketList;
		this.sliceMatrix = sliceMatrix;
		
		Timer sendTimer  = new Timer();
		sendTimer.schedule(new clearSendMsg(), 0, UDPConstants.REFRESH_INTERVAL);	
		
		Timer receiveTimer  = new Timer();
		receiveTimer.schedule(new clearReceiveMsg(), 0, UDPConstants.REFRESH_INTERVAL);	
	}
	
	class clearSendMsg extends TimerTask
	{
		SlicePacket slicePacket;

		public void run() 
		{
			synchronized(slicePacketList)
			{
				int index = 0;
				while ( index<slicePacketList.size())
				{
					slicePacket = slicePacketList.get(index);
					
					if (slicePacket.timeStamp < System.currentTimeMillis() - UDPConstants.SEND_TIMEOUT)
					{
						slicePacketList.remove(index);
					}
					else
					{
						index++;
					}
				}
				System.out.println("sender timeout messages cleared");
			}
		}
	}
	
	class clearReceiveMsg extends TimerTask
	{
		ArrayList<Slice> matrixSubList;
		Slice slice;
		
		public void run() 
		{
			synchronized(sliceMatrix)
			{
				int index = 0;
				boolean isTimeOut;
				
				while (index < sliceMatrix.size())
				{
					isTimeOut = false;
					matrixSubList = sliceMatrix.get(index);
					
					for (int j=0; j<matrixSubList.size(); j++)
					{
						slice = matrixSubList.get(j);
						
						if (slice.timeStamp < System.currentTimeMillis() - UDPConstants.SEND_TIMEOUT)
						{
							isTimeOut = true;
							break;
						}
					}
					
					if (isTimeOut)
					{
						sliceMatrix.remove(index);
					}
				}
			}
			System.out.println("receiver timeout messages cleared");
		}
		
	}
	
	
}
