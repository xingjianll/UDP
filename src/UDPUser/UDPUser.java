package UDPUser;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Base64;

import javax.imageio.ImageIO;
import UDP.IfUDP;
import UDP.IfUDPUser;
import UDP.UDP;

public class UDPUser implements IfUDPUser
{
	//test test. Testing github commit
	//test test. Testing github commit

	//test test. Testing github commit

	//test test. Testing github commit

	//test test. Testing github commit

	//test test. Testing github commit

	//test test. Testing github commit

	public static IfUDP udp;
	byte[] text;
	ServerFrame frame;
	
	UDPUser()
	{}

	private void init()
	{
		udp = new UDP();
		udp.init(4396, this);
		frame = new ServerFrame(this);
	}
	
	public void login() 
	{
		//InetAddress i = null;
		Message login = new Message(ChatConstants.MSGTYPE_LOGIN, "321", "time", "login");
		sendMsg(login);
	}
	
	public void sendMsg(Message msg)
	{
		String msgStr = Integer.toString(msg.msgType) + ChatConstants.SPR + msg.time + ChatConstants.SPR
				+ msg.userID + ChatConstants.SPR + msg.content;
							
		udp.send("192.168.2.11", 4396, msgStr);
	}
	
	
	
	public void onMsg(String msg, String peerIP, int peerPort)
	{
	//	String stringMsg = new String(msg, StandardCharsets.UTF_8);
		
		int sep1 = msg.indexOf(ChatConstants.SPR);
		int  sep2 = msg.indexOf(ChatConstants.SPR, sep1+1);
		int  sep3 = msg.indexOf(ChatConstants.SPR, sep2+1);
		byte[] imageBytes;
		
		int msgId = Integer.valueOf(msg.substring(0, sep1));
		String content = msg.substring(sep3+1);
		System.out.println("=========================="+msgId+"+++++++++========");


		if (msgId == ChatConstants.MSGTYPE_LOGIN)
		{
			frame.showString("login");
		}
					
		else if (msgId == ChatConstants.MSGTYPE_TEXTCHAT)
		{
			frame.showString(content);
		}
		
		else if (msgId == ChatConstants.MSGTYPE_IMAGECHAT)
		{
			//System.out.println(msg);
			//System.out.println(msg.length());

//			System.out.println(content);
//			System.out.println(content.length());

			imageBytes = Base64.getDecoder().decode(content);
			ByteArrayInputStream bais = new ByteArrayInputStream(imageBytes);
			try 
			{
				BufferedImage image = ImageIO.read(bais);
				frame.showImage(image);
	
			} 
			catch (IOException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
//					
//		//else if (subtext.equals("CLOSE*****"))
//		//{
//			//close client connection
//		//}	
//
//		else
//		{
//			ServerFrame.area1.append("unidentified message - "+subtext+System.lineSeparator());
//		}
	}
	
	public static void main(String[] args)  
	{
		UDPUser user = new UDPUser();
		user.init();
		user.login();
	}
	
}
