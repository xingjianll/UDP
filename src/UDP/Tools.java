package UDP;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;

import UDP.DataStructures.Slice;

public class Tools 
{

	public static byte[] getBytes(byte[] byteArr,int loc, int len)
	{
		
		byte[] retByteArr = null;
		String b = new String(byteArr,  StandardCharsets.UTF_8);

		if(byteArr != null & len > 0 && loc >= 0 && byteArr.length >= loc + len)
		{
			retByteArr = new byte[len];
			
			for(int i=0; i<len; i++)
			{
				retByteArr[i] = byteArr[loc + i];
			}
		}
		
		return retByteArr;
	}
	
	public static int divideUp(int numerator, int denominator)
	{
		int residual;
		int quotient = 0;
		
		if(denominator != 0)
		{
			quotient = numerator / denominator;
			residual = numerator % denominator;
			
			if(residual != 0) quotient++;
			
		}
		
		return quotient;
	}

	
	public static int id = 0;
	public static int generateID()
	{
		if(id == Integer.MAX_VALUE) id = 0;
		id++;
		
		return id;
		
	}
}
