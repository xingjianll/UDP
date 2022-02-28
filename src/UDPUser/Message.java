package UDPUser;

public class Message 
{
	int msgType;
	String userID;
	String time;
	String content;
	 
	Message(int msgType, String userID, String time, String content)
	{
		this.msgType = msgType;
		this.userID = userID;
		this.time = time;
		this.content = content;
	}
}
