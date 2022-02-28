package UDP;

public interface UDPConstants 
{
	public int PACKET_LEN = 128;
	public String SEPARATOR = "#"; //Character.toString(6);
	
	public int SLICE_PACKET = 0;
	public int SLICE_ACK = 1;
	
	public int SEND_INTERVAL = 100*5*1000;
	public int SEND_TIMEOUT = 60*60*1000;
	public int REFRESH_INTERVAL = 10*60*1000;
	
	public int SENDER_WAITTIME = 100;
	
	public int MAX_RESEND_TIMES = 3;


}
