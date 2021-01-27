package GuilanUniversity.AP96.AmirAbbasi.Seven;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import javax.swing.JFrame;

public class Client {
	private ArrayList<String> inbox=new ArrayList<String>();
	private DataOutputStream out;
	private DataInputStream in;
	Socket socket;

	Player player;
public Client(Player player) {

	this.player=player;
	try {
	socket=new Socket(InetAddress.getByName("127.0.0.1"),1998);
		in=new DataInputStream(socket.getInputStream());
		out=new DataOutputStream(socket.getOutputStream());
		
	} catch (UnknownHostException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	ok();
}
public void ok() {
	new Inbox(this).start();
}
public void sendMessage(String  msg) {
	try {
		out.writeUTF(msg);
		out.flush();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}
public String getMessage() {
	if(inbox.size()==0) {
		synchronized (inbox) {
			try {
				inbox.wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		}
		
	}
	String msg=inbox.get(0);
	inbox.remove(0);
	return msg;
}
class Inbox extends Thread{
	Game game;
	Client client;
	boolean isConnected=true;
	public Inbox(Client client) {
		this.client=client;
	}
	public void run() {
		while(isConnected==true) {
		try {
			
			String t=in.readUTF();
			if(t.contains("Start")) {
				String []tr=t.split("#");
//				t=in.readUTF();
				inbox.clear();
				game=new Game(client,Integer.parseInt(tr[3]),Integer.parseInt(tr[6]),Integer.parseInt(tr[1]),tr[5],0,Integer.parseInt(tr[4]),Integer.parseInt(tr[2]),player);
				game.setVisible(true);
				
			}
			inbox.add(t);
			synchronized (inbox) {
			inbox.notify();	
			}
		} catch (IOException e) {
			isConnected=false;
		}
		
		}
	}
}

}
