package UDP;

public interface IfUDPUser 
{
	public void onMsg(String msg, String peerIP, int peerPort);
}
