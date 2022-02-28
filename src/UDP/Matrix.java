package UDP;

import java.io.IOException;
import java.net.DatagramPacket;
import java.util.ArrayList;

import javax.xml.crypto.Data;

import UDP.DataStructures.*;

public class Matrix extends Thread
{
	ArrayList<Slice> sliceList;
	ArrayList<ArrayList<Slice>> sliceMatrix;
	ArrayList<Slice> matrixSubList;
	
	public Matrix(ArrayList<Slice> sliceList, ArrayList<ArrayList<Slice>> sliceMatrix)
	{
		this.sliceList = sliceList;
		this.sliceMatrix = sliceMatrix;
	}
	
	public void run()
	{
		Slice slice;
		Boolean isMsgExisted;

		
		while (true)
		{
			slice = null;
			isMsgExisted = false;

			if (sliceList.size() == 0)
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
			else synchronized(sliceList) //get a slice from slice list
			{

				if (sliceList.size()>=1)
				{
					slice = sliceList.remove(0);
				}
			}

			if (slice != null)
			{	
				//scan the lists in the matrix
				synchronized(sliceMatrix)
				{
					for (int j=0;sliceMatrix.size()!=0 && j<sliceMatrix.size(); j++)
					{
						matrixSubList = sliceMatrix.get(j);
						
						// check if the slice belongs to the current matrix list
						if (slice.msgId.equals(matrixSubList.get(0).msgId))
						{
							//add slice to the matrix list
							
							matrixSubList.set(slice.sliceIndex, slice);
							matrixSubList.get(0).count++;	//slice counter plus one
							
							//isMsgExisted
							isMsgExisted = true;
						}
					}
					
					//if message is not existed 
					if (!isMsgExisted)
					{
						//create a new matrix list
						matrixSubList = new ArrayList<Slice>(slice.totalSlices);
						for (int i = 0; i < slice.totalSlices; i++) matrixSubList.add(null);
//						System.out.println("total slice is: " + slice.totalSlices);
//						System.out.println("the size is: " + matrixSubList.size());
//						System.out.println("the index in sublist is:" + slice.sliceIndex);
//						
						slice.count = 1; //the first slice of a message
						matrixSubList.set(slice.sliceIndex, slice);
						sliceMatrix.add(matrixSubList);
					}
				}	
			}
		}
	}
}

