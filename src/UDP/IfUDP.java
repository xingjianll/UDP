package UDP;

import java.net.InetAddress;

public interface IfUDP 
{
	public void init(int port, IfUDPUser user);
	public void send(String peerIP,int peerPort, String msg);
}

