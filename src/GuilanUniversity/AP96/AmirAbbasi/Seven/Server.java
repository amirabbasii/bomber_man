package GuilanUniversity.AP96.AmirAbbasi.Seven;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
/**
 * Server;port:1998
 * @author Asus
 *
 */
public class Server {
	static ArrayList<Group> groups=new ArrayList<Group>();//arraye group ha
public static void main(String[] args) {
	System.out.println("Server started");
	ServerSocket server=null;
	try {
	server=new ServerSocket(1998);//port 1998
		
	/**
	 * Thread baraye voroodi gerftan mesle khamoosh kardane server
	 */
		Thread Input=new Thread(new Runnable() {
			
			@Override
			public void run() {
				Scanner input=new Scanner(System.in);
				while(true) {
					String txt=input.next();
					switch (txt) {
					case "exit":
						System.out.println("Server closed");
						System.exit(0);
						break;
				

					}
				}
				
			}
		});
		Input.start();
				
		
		while(true) {
			new Connect(server.accept()).start();//ertebate jadid
		}
		
	} catch (IOException e) {
		
	}
	finally {
		try {
			server.close();
		} catch (IOException e) {
			
		}
	}
}
/**
 * Thread ertebat
 * @author Asus
 *
 */
static class Connect extends Thread{
	Socket socket;
	
	/**
	 * Constructor
	 * @param socket
	 */
	public Connect(Socket socket) {
		this.socket=socket;
	}

	public void run() {
		try {
			DataOutputStream out=new DataOutputStream(socket.getOutputStream());//voroodie socket
			DataInputStream input=new DataInputStream(socket.getInputStream());//khoroojie socket
			String type=input.readUTF();//gereftane noe client(Host,Join)
			if(type.equals("Host")) {//Host
				String name=input.readUTF();//gereftane name
			String n=input.readUTF();//gereftane abaad
			String m=input.readUTF();
			String timee=input.readUTF();//gereftane zaman
			String gi=input.readUTF();//gereftane tedade bazikon
				Group gp=new Group(name,Integer.parseInt(n),Integer.parseInt(m),Integer.parseInt(timee),Integer.parseInt(gi));//ijad goroohe jadid
				gp.join(name, out,input);//ezafe shodan be gorooh
				groups.add(gp);//ezafe kardane gorooh be arraye gorooh ha
			
			}else {//Join
				String name=input.readUTF();//gereftane name
				String txt="";// liste name gorooh ha be soorate string
				for(int i=0;i<groups.size();i++)
					txt+=groups.get(i).name+"#";
			
				out.writeUTF(txt);//ersale txt
				out.flush();
				int k=Integer.parseInt(input.readUTF());//gerftane indexe goroohe entekhab shode
				groups.get(k).join(name,out,input);//ezafe shodan be goroohe entekhabi 
				if(groups.get(k).numberOfmembers()==groups.get(k).numberOfGiants) {//agar zarfiate gorooh por shode
					groups.get(k).startGame();
					
				}
				
			}
		} catch (IOException e) {
			
		}
	}
}
/**
 * shamele DataOutputStream va DataIntputStream haye client hayi ast ke bayad ba ham bazi konand
 * @author Asus
 *
 */
static class Group {
	private String name;//name gorooh(name Host ke sazande boode)
	private int n;//abaade safhe
	private int m;
	private boolean isPlaying=true;//flage inke bazi tamam shode ya na
	private int numberOfGiants;//tedade bazikonan
	private int time;//zaman
	//payame ersale: inbox|add1#add2#add3
	private String add1="",add2="",add3="";// 
	private boolean[][] flags;//araye do bodi az khanehaye bazi ke agar mae vojood dashte bashad false vagarna true ast;
	private ArrayList<String> inbox=new ArrayList<>();//payamha
	private ArrayList<DataOutputStream> outers=new ArrayList<DataOutputStream>();//DataOutputStream ha
	private ArrayList<DataInputStream> ins=new ArrayList<DataInputStream>();//DataInputStream ha
	private ArrayList<String> names=new ArrayList<String>();//arraye name bazikonan
	
	/**
	 * Constructor
	 * @param name
	 * @param n
	 * @param timee
	 * @param num
	 */
public Group(String name,int n,int m,int timee,int num) {
	this.name=name;
this.n=n;
this.m=m;
this.time=timee;
numberOfGiants=num;
}
/**
 * ezafe be gorooh
 * @param name
 * @param out
 * @param input
 */
public void join(String name,DataOutputStream out,DataInputStream input) {
	names.add(name);
ins.add(input);
		outers.add(out);
}
/**
 * Getter:tedade bazikonha
 * @return
 */
public int numberOfmembers() {
	return names.size();
}
/**
 * tabe shorooe bazi
 * ebteda falgs[][] por mishavad va bad be soorate random tedade Box ha va entekhabe randome khne baraye Box ha
 */
public void startGame() {

	//////////////////por kardane flags[][]//////////////////
flags=new boolean[n][m];
	for(int i=0;i<n;i++) {
		for(int j=0;j<m;j++) {
			if(i%2==0) {
				flags[i][j]=false;
			}else {
				flags[i][j]=true;
			}
		}
	}
	for(int i=2;i<n-2;i++) {
		for(int j=0;j<m;j++) {
			if(j%2!=0) {
				flags[i][j]=true;
			}
		}
	}
	flags[1][0]=false;
	flags[n-2][m-1]=false;
	flags[1][m-1]=false;
	flags[n-2][0]=false;
	
	/////////////////////////////////////////////////////////
	
	
	////////////////////////Box ha/////////////////////////
	Random rnd=new Random();
	String ms="";
	int count=rnd.nextInt(n*m);
	for(int i=0;i<count;i++) {
		boolean flag=false;
		int o=0,u=0;
		while(flag==false) {
			o=rnd.nextInt(n);
			u=rnd.nextInt(m);
			if(flags[o][u]==true) 
				flag=true;
			
		}
		ms+=String.valueOf(o)+":"+String.valueOf(u)+"@";
		flags[o][u]=false;
	}
	if(count==0)
		ms="-1";
	//////////////////////////////////////////////////
	
	///////////////ersale moshkasate bazi be bazikonan////////////////////
	for(int i=0;i<outers.size();i++)
		try {
			
			outers.get(i).writeUTF("Start"+"#"+String.valueOf(i)+"#"+time+"#"+n+"#"+numberOfGiants+"#"+ms+"#"+m);
			outers.get(i).flush();
		} catch (IOException e) {
			isPlaying=false;
		}
////////////////////////////////////////////////////////////////////
	
	//////////////////////////////Thread haye ertebatha//////////////
	for(int i=0;i<numberOfmembers();i++) {
		final int k=i;
new Thread(new Runnable() {
	
	@Override
	public void run() {
		while(isPlaying==true) {
			try {
				String txt=ins.get(k).readUTF();
				inbox.add(txt);//payame gerfete shode be inbox ezafe mishavad
				
			} catch (IOException e) {
				isPlaying=false;
				for(int i=0;i<groups.size();i++) {
					if(groups.get(i).name.equals(name))
					{
						groups.remove(groups.get(i));
						break;
					}
				}
			}
		}
		
	}
}).start();
	}
	/////////////////////////////////////////////////
	
	//////////////////Timer baraye sakhte setare va ersal be bazikon ha(random sanie)////////////////
Timer makeStar=new Timer();
int p=rnd.nextInt(9)+1;//sakhte adade random baraye bare aval
makeStar.schedule(new TimerTask() {
	int t=0;
	int rand=p;
	@Override
	public void run() {
		if(isPlaying==false)
			makeStar.cancel();
		if(t==rand*10000) {//sanie zaribi az 10000 ast yani beyne 10 ta 90 sanie
			rand=rnd.nextInt(9)+1;
			boolean flag=false;
			int o=0,u=0;
			while(flag==false) {
			o=rnd.nextInt(n);
			u=rnd.nextInt(m);
			if(flags[o][u]==true)
				flag=true;
			}
add1="S"+":"+String.valueOf(o)+":"+String.valueOf(u);
		}
		
	}
}, 10000, 10000);

///////////////////////////////////////////////////

//////////////////Timer baraye sakhte ghalb va ersal be bazikonan(40 s)////////////////////
Timer makeLife=new Timer();
makeLife.schedule(new TimerTask() {
	
	@Override
	public void run() {
		if(isPlaying==false)
			makeStar.cancel();
			boolean flag=false;
			int o=0,u=0;
			while(flag==false) {
			o=rnd.nextInt(n);
			u=rnd.nextInt(m);
			if(flags[o][u]==true)
				flag=true;
			}
add2="J"+":"+String.valueOf(o)+":"+String.valueOf(u);//kalame J neshane life ast;(J:x:y)

		
	}
}, 40000, 40000);
////////////////////////////////////////////////

/////////////////////Timer baraye daroo va ersal be bazikonan(50 s)///////////////////
Timer drug=new Timer();
drug.schedule(new TimerTask() {
	
	@Override
	public void run() {
		if(isPlaying==false)
			makeStar.cancel();
		boolean flag=false;//flage inke khane peyda shode ya na
		int o=0,u=0;
		while(flag==false) {
		o=rnd.nextInt(n);//x
		u=rnd.nextInt(m);//y
		if(flags[o][u]==true)//agar khane mane nadarad
			flag=true;
		}
add3="Q"+":"+String.valueOf(o)+":"+String.valueOf(u);//kalame Q neshane drug ast;(Q:x:y)
		
	}
}, 50000, 50000);

///////////////////////////////////////////////////////////////

////////////////Timer baraye ersale taghirat va update safhe bazikonan////////////////
Timer update=new Timer();
update.schedule(new TimerTask() {
	
	@Override
	public void run() {
		if(isPlaying==false)
			makeStar.cancel();
		String msg="";//ghesmate avale payame ersali ke shamele harekat hast
		for(int i=0;i<inbox.size();i++)
			msg+=inbox.get(i)+"#";//rikhtane karekat haye gerefte shode da msg
		inbox.clear();
		if(msg.equals(""))
			msg="-1";
		String bh="";//ghesmate dovome payam ke shamele item haye komakie bazi ast
		if(add1.equals("") && add2.equals("") )
			bh="-1";
		if(!add1.equals(""))
			bh+=add1+"#";
		if(!add2.equals(""))
			bh+=add2+"#";
		if(!add3.equals(""))
			bh+=add3+"#";
		broadCast(msg+"_"+bh);//ersal
		add1="";//update add1
		add2="";//update add2
		add3="";//update add3
	}
}, 500	, 500);
/////////////////////////////
}
/**
 * ersale payam be kole gorooh
 * @param message
 */
public void broadCast(String message) {
	for(int i=0;i<outers.size();i++)
		try {
			
			outers.get(i).writeUTF(message);
			outers.get(i).flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
}

}
}
