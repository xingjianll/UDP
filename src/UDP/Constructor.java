package UDP;

import java.io.IOException;
import java.net.DatagramPacket;
import java.util.ArrayList;

import javax.xml.crypto.Data;

import UDP.DataStructures.*;
import UDPUser.UDPUser;

public class Constructor extends Thread
{
	ArrayList<ArrayList> sliceMatrix;
	ArrayList<Slice> matrixSubList;
	IfUDPUser callback;
	
	public Constructor(ArrayList sliceMatrix, IfUDPUser user)
	{
		this.sliceMatrix = sliceMatrix;
		callback = user;
	}
	
	public void run()
	{
		Slice slice;
		String msg;
		String peerIP = null;
		int peerPort = -1;
		
		while (true)
		{
			msg = "";
			
			if (sliceMatrix.size() == 0)
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
			else synchronized(sliceMatrix)
			{
				for (int i=0;i<sliceMatrix.size();i++)
				{
					//get a list from matrix
					matrixSubList = sliceMatrix.get(0);
					slice = matrixSubList.get(0);

					//check if list is complete
					if (slice.count == matrixSubList.size())
					{
						peerIP = slice.ip;
						peerPort = slice.port;
						//construct the slices into message
						for (int j=0;j<slice.totalSlices;j++)
						{
							slice = matrixSubList.get(j);
//							System.out.println("constructor: " + slice.totalSlices);
//							System.out.println(j);
//							System.out.println("slice data: " + slice.sliceData);

							
							msg += slice.sliceData;	

						}
						sliceMatrix.remove(0);
						break;
					}
				}
			}

			if (msg.length() > 0)
			{
				callback.onMsg(msg,peerIP,peerPort);
//				System.out.println(msg);
			}
		}
	}
}

