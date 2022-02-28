package UDPUser;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import java.io.*;
import UDP.IfUDP;
import UDP.UDP;

public class ServerFrame extends JFrame implements ActionListener 
{

	public static JPanel area1;
	private  JPanel area2;
	private JPanel panel1;
	static JTextField field1;
	private JButton sendBtn;
	private JButton chooseFile;
	private JLabel label;
	private Container container;

	UDPUser user;
	InetAddress i;
	
	public ServerFrame(UDPUser user) 
	{
		this.user = user;
		// interface components
		Random rand = new Random();
		// interface components
		area1 = new JPanel();
		area1.setBackground(Color.gray);
//		area1.setLayout(new FlowLayout(FlowLayout.LEADING));

		//area1.setEditable(false);
		container = this.getContentPane();
		container.setLocation(8, 4);
		
		panel1 = new JPanel();
		field1 = new JTextField(10);
		sendBtn = new JButton("send");
		chooseFile = new JButton("choose file");

		panel1.add(field1);
		panel1.add(sendBtn);
		panel1.add(chooseFile);

		container.add(area1,BorderLayout.CENTER);
		container.add(panel1,BorderLayout.SOUTH);
		
		this.setTitle("Chat Client ");
		this.setSize(400,300);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		
		sendBtn.addActionListener(this);
		chooseFile.addActionListener(this);

	}
	
	public void showString(String str)
	{
		area2 = new JPanel();
		area2.setBackground(Color.blue);
		area2.setSize(100, 200);
		area1.add(area2);
		Graphics g  = area2.getGraphics();
		g.drawString(str, 10, 10);

	}
	
	public void showImage(Image image)
	{
		area2 = new JPanel();
		area2.setBackground(Color.blue);
		area2.setSize(100, 200);
		area1.add(area2);
		Graphics g = area2.getGraphics();
		g.drawImage(image, 50, 50, null);
	}

	public void actionPerformed(ActionEvent e)
	{
		String time = "time";
		String userID = "321";
		
		if (e.getSource()==sendBtn)
		{
				int msgType = ChatConstants.MSGTYPE_TEXTCHAT;
				String content = field1.getText();
				
				Message chat = new Message(msgType, userID, time, content);
				user.sendMsg(chat);
				field1.setText("");
			
		} 
		else if (e.getSource()==chooseFile)
		{
			byte[] image = new byte[ChatConstants.MAX_IMGSIZE];
			JFileChooser chooser = new JFileChooser();
			
			if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
			{
				FileInputStream in;
				try 
				{
					in = new FileInputStream(chooser.getSelectedFile().getAbsolutePath());
					image = new byte[in.available()];
					in.read(image);
										
					int msgType = ChatConstants.MSGTYPE_IMAGECHAT;
					String content = Base64.getEncoder().encodeToString(image);

					Message chat = new Message(msgType, userID, time, content);
					user.sendMsg(chat);
				
				} 
				catch (IOException e1) 
				{
					e1.printStackTrace();
				}
				
			}
		}
	}
	
//	public byte[] getbytes(File f) 
//	{
//		FileInputStrea
//				
//		ByteArrayOutputStream os= new ByteArrayOutputStream();
//		FileInputStream fis;
//		try 
//		{
//			fis = new FileInputStream(f);
//			int readLen;
//			int offset = 0;
//			
//			while ((readLen = fis.read(buffer))!= -1)
//			{	
//				
//				os.write(buffer,offset, readLen);
//				
//				offset += readLen;
//			}
//			fis.close();
//			os.close();
//		} 
//		
//		catch (IOException e) 
//		{
//			e.printStackTrace();
//		}
//		
//		return 	os.toByteArray();
//
//	}
}
