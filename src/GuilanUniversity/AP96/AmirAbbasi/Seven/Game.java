package GuilanUniversity.AP96.AmirAbbasi.Seven;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

public class Game extends JFrame {
	
private Image rock;//tasvire sang
private Image earth;//tasvire zamin
private Giant[] giants;//hayoola ha
private Client client;
private JLabel usedBombs=new JLabel("used bomb:0");//lable tedade bomb
private int numberOfPlayers;//tedade bazikon
private int pl;//adade bazikon
private int timee;//zaman
private Man[] mans;//bazikon ha
private JLabel killed=new JLabel("kills:0");//lable tedade ghatl
private Player player;
	private JPanel contentPane;

	/**
	 * Constructor
	 * @param clinet
	 * @param n
	 * @param pl
	 * @param indx
	 * @param numberOfGiants
	 * @param numberOfPlayers
	 * @param timee
	 * @param player
	 */
	public Game(Client clinet ,int n,int m,int pl,String indx,int numberOfGiants,int numberOfPlayers,int timee,Player player) {
//		this.socket=soc
		JPanel panel = new JPanel();
		this.player=player;
		this.client=clinet;
		this.pl=pl;
		/////////////////////////////////////////////////////////////
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		int shi=1000;
		if(m>13 && m <20)
			shi=m*100;
		if(m>20)
			shi=19*100;
		setBounds(100, 0, shi, 1000);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5,5,5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		panel.setLayout(new GridLayout(n,m));
		JLabel[][] board=new JLabel[n][m];

	
		contentPane.add(panel,BorderLayout.CENTER);
		setContentPane(contentPane);
		try {
	 earth=ImageIO.read(new File("icon/grassCenter.png"));
	rock=ImageIO.read(new File("icon/castleCenter.png"));}
		catch (IOException e) {
			// TODO: handle exception
		}
		//////////////////////////////////////////////////////por kardane flags[][]///////////////////////////
		boolean[][] flags=new boolean[n][m];
	for(int i=0;i<n;i++) {
		for(int j=0;j<m;j++) {
			if(i%2==0) {
				board[i][j]=new JLabel();
				board[i][j].setIcon(new ImageIcon(rock.getScaledInstance(shi/m, 1000/n, 2)));
				board[i][j].setLayout(new BorderLayout(0,0));
				board[i][j].setName("rock");
				flags[i][j]=false;
			}else {
				board[i][j]=new JLabel();
				board[i][j].setIcon(new ImageIcon(earth.getScaledInstance(shi/m, 1000/n, 2)));
				board[i][j].setLayout(new BorderLayout(0,0));
				board[i][j].setName("earth");
				flags[i][j]=true;
			}
		}
	}
	for(int i=2;i<n-2;i++) {
		for(int j=0;j<m;j++) {
			if(j%2!=0) {
				board[i][j].setIcon(new ImageIcon(earth.getScaledInstance(shi/m, 1000/n, 2)));
				board[i][j].setName("earth");
				flags[i][j]=true;
			}
		}
	}
	for(int i=0;i<n;i++) {
		for(int j=0;j<m;j++) {
			board[i][j].setBounds(j*shi/m, i*1000/n, shi/m, 1000/n);
		
			panel.add(board[i][j]);
		}
	}	
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	JLabel timerLbl=new JLabel();//lable timer
	timerLbl.setName("continue");
	
	///////////////////////////sakhtane shakhsiat haye bazi///////////////////////////////////
	mans=new Man[numberOfPlayers];
	JLabel col=new JLabel(player.nameGetter());
	Image imn=null;
	col.setFont(new Font("Hobo Std", Font.PLAIN, 30));
	
	col.setSize(80,100);
	for(int i=0;i<numberOfPlayers;i++) {
		String sr="";
		int x1=0,y1=0;
		switch (i) {
		case 0:
			sr="Blue";
			x1=1;
			y1=0;
			try {
				imn=ImageIO.read(new File("icon/sprite/Characters/Blue_Right3.png"));
			} catch (IOException e3) {
				// TODO Auto-generated catch block
				e3.printStackTrace();
			}
			col.setIcon(new ImageIcon(imn.getScaledInstance(80, 100, 1)));
			break;
		case 1:
			sr="Red";
			x1=n-2;
			y1=m-1;
			try {
				imn=ImageIO.read(new File("icon/sprite/Characters/Red_Right3.png"));
			} catch (IOException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			col.setIcon(new ImageIcon(imn.getScaledInstance(80, 100, 1)));
			break;
		case 2:
			sr="Yellow";
			x1=1;
			y1=m-1;
			try {
				imn=ImageIO.read(new File("icon/sprite/Characters/Yellow_Right3.png"));
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			col.setIcon(new ImageIcon(imn.getScaledInstance(80, 100, 1)));
			break;
		case 3:
			sr="LightBlue";
			y1=0;
			x1=n-2;
			try {
				imn=ImageIO.read(new File("icon/sprite/Characters/LightBlue_Right3.png"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			col.setIcon(new ImageIcon(imn.getScaledInstance(80, 100, 1)));
		}
		mans[i]=new Man(n,m, x1, y1, sr, board, flags, i, clinet, timerLbl,panel,numberOfGiants,killed,shi);
	}
/////////////////////////////////////////////////////////////////////////////////////////////////////////////
		
	JPanel panel_1 = new JPanel();//panele balaye safhe ke shamele zaman va ... ast
		contentPane.add(panel_1,BorderLayout.NORTH);
		panel_1.setLayout(new BorderLayout(50,0));
panel_1.add(col,BorderLayout.WEST);
panel_1.setBackground(Color.yellow);
killed.setSize(200,100);
killed.setFont(new Font("Hobo Std", Font.PLAIN, 40));
	timerLbl.setSize(200, 100);
	JPanel pol=new JPanel();
	pol.setBackground(Color.yellow);
	pol.setBounds(0, 0, 500	, 200);
	pol.setLayout(new BorderLayout(50,0));
	panel_1.add(pol,BorderLayout.CENTER);
	timerLbl.setFont(new Font("Hobo Std", Font.PLAIN, 40));
	panel_1.add(timerLbl,BorderLayout.EAST);
	usedBombs.setSize(200, 100);
	usedBombs.setFont(new Font("Hobo Std", Font.PLAIN, 40));
	pol.add(usedBombs,BorderLayout.WEST);
	pol.add(killed,BorderLayout.CENTER);
	pol.add(col,BorderLayout.EAST);
	
	////////////////////////////////////////Timer zaman///////////////////////////////
	Timer mainTime=new Timer();
	
	//////////////////////////////HALATE OFFLINE////////////////////////////
	if(timee==0) {
	mainTime.schedule(new TimerTask() {
		int second=0,minute=0,hour=0;
		@Override
		public void run() {
		second++;
			String a,b,c;
			if(second==60) {
				second=0;
				minute++;
			}
			if(minute==60) {
				minute=0;
				hour++;
			}
			if(second<10)
				a="0"+String.valueOf(second);
			else
				a=String.valueOf(second);
			if(minute<10)
				b="0"+String.valueOf(minute);
			else
				b=String.valueOf(minute);
			if(hour<10)
				c="0"+String.valueOf(hour);
			else
				c=String.valueOf(hour);
			timerLbl.setText(c+":"+b+":"+a);
			if(timerLbl.getName().equals("end")) {
				int tt=second+minute*60+hour*3600;
				int w=0;
				if(mans[0].lifeGetter()==0)
					w=1;
				else
					w=4;
				finish(tt, w, mans[0].bombGetter()	, numberOfGiants);
				mainTime.cancel();
			}
			
		}
	}, 1000, 1000);
	}
	//////////////////////////////////////////HALATE ONNLINE/////////////////////////////////
	else {
		final int ho=timee/3600;
		timee=timee%3600;
		final int mi=timee/60;
		timee=timee%60;
		final int se=timee;
		mainTime.schedule(new TimerTask() {
			int hour=ho,minute=mi,second=se;
			@Override
			public void run() {
			second--;
				String a,b,c;
				if(second==-1) {
					second=59;
					minute--;
				}
				if(minute==-1) {
					minute=59;
					hour--;
				}
				if(second<10)
					a="0"+String.valueOf(second);
				else
					a=String.valueOf(second);
				if(minute<10)
					b="0"+String.valueOf(minute);
				else
					b=String.valueOf(minute);
				if(hour<10)
					c="0"+String.valueOf(hour);
				else
					c=String.valueOf(hour);
				timerLbl.setText(c+":"+b+":"+a);
				if(second==0 && minute==0 && hour==0)
				{
					finish(mans[pl].killsGetter()	, mans[pl].bombGetter(), numberOfPlayers);
					mainTime.cancel();
				}
				
				
			}
		}, 1000, 1000);
	}
	///////////////////////////////////////////////////////////////////////////////////////////////////
	
	//////////////////////////////KEY LISTENER//////////////////////////////////////////////
	final int uyu=shi;
	addKeyListener(new KeyListener() {
		
		String type="";
		@Override
		public void keyTyped(KeyEvent arg0) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void keyReleased(KeyEvent arg0) {

		}
		
		@Override
		public void keyPressed(KeyEvent arg0) {
			if(numberOfPlayers>1) {//////////////////////////HALATE ONLINE

					switch (arg0.getKeyCode()) {
					case 37://Chap
						try {
						if(flags[mans[pl].xGetter()][mans[pl].yGetter()-1]==true)
						clinet.sendMessage("L"+String.valueOf(pl));}
						catch (ArrayIndexOutOfBoundsException e) {
							
						}
						break;
					case 38://Bala
						try {
						if(flags[mans[pl].xGetter()-1][mans[pl].yGetter()]==true)
						clinet.sendMessage("U"+String.valueOf(pl));}
						catch (ArrayIndexOutOfBoundsException e) {
							
						}
						break;
					case 39://Rast
						try {
						if(flags[mans[pl].xGetter()][mans[pl].yGetter()+1]==true)
						clinet.sendMessage("R"+String.valueOf(pl));}
						catch (ArrayIndexOutOfBoundsException e) {
							
						}
						break;
					case 40://Payin
						try {
						if(flags[mans[pl].xGetter()+1][mans[pl].yGetter()]==true)
						clinet.sendMessage("D"+String.valueOf(pl));}
						catch (ArrayIndexOutOfBoundsException e) {
							
						}
						break;
					case 10://Bomb
						
						usedBombs.setText("used bombs:"+String.valueOf(mans[pl].bombGetter()));
					
						clinet.sendMessage("B"+String.valueOf(pl));
					}

			}

			else {/////////////////////////////////HALATE OFFLINE
			
			switch (arg0.getKeyCode()) {
			case 37:
				mans[0].left();
				panel.repaint();
				break;
			case 38:
				mans[0].up();
				panel.repaint();
				break;
			case 40:
				mans[0].down();
				panel.repaint();
				break;
			case 39:
				mans[0].right();
				panel.repaint();
				break;
			case 10:
				mans[0].bomb(giants, mans);
				usedBombs.setText("used bombs:"+String.valueOf(mans[0].bombGetter()));
			}
			
			panel.repaint();

		}}
	});

	////////////////////////Thread daryaft payam az server va update safhe////////////////////////////////////
	if(numberOfPlayers>1) {
	new Thread(new Runnable() {
		
		@Override
		public void run() {
			while(clinet.socket.isConnected()) {
			
			String msg=clinet.getMessage();
			if(!msg.contains("Start")) {
			String piece[]=msg.split("_");//tafkike harekate ha az item ha
			if(!piece[0].equals("-1")) {
				String moves[]=piece[0].split("#");//tafkike harekat ha
				for(int i=0;i<moves.length;i++) {
					switch (moves[i].charAt(0)) {
					case 'L':
						mans[Integer.parseInt(String.valueOf(moves[i].charAt(1)))].left();
						panel.repaint();
						break;
					case 'R':
						mans[Integer.parseInt(String.valueOf(moves[i].charAt(1)))].right();
						panel.repaint();
						break;
					case 'D':
						mans[Integer.parseInt(String.valueOf(moves[i].charAt(1)))].down();
						panel.repaint();
						break;
					case 'U':
						mans[Integer.parseInt(String.valueOf(moves[i].charAt(1)))].up();
						panel.repaint();
						break;
					case 'B':
						mans[Integer.parseInt(String.valueOf(moves[i].charAt(1)))].bomb(giants, mans);
			
						break;
					case 'Q':
						if(pl!=Integer.parseInt(String.valueOf(moves[i].charAt(1))))
							mans[Integer.parseInt(String.valueOf(moves[i].charAt(1)))].drug();
					}
				}
			}
			if(!piece[1].equals("-1")) {
			
				String mb[]=piece[1].split("#");
				for(int i=0;i<mb.length;i++) {
					String hg[]=mb[i].split(":");
					switch (hg[0]) {
					case "L":
						new Life(n,m, Integer.parseInt(hg[1]),  Integer.parseInt(hg[2]), board, flags, panel, mans,uyu);
						break;
					case "S":
						new Star(n,m, Integer.parseInt(hg[1]),  Integer.parseInt(hg[2]), board, flags, panel, mans,uyu);
						break;
					case "Q":
						new Drug(n,m, Integer.parseInt(hg[1]),  Integer.parseInt(hg[2]), board, flags, panel, mans,uyu);
					}
				
						
				}
			}

			}
			}
		}
	}).start();}
/////////////////////////////////////////////////////////////////////////////////
	
	///////////////////////////////////////////////OFFLINE;SAKHTE HAYOOLA VA BOX/////////////////////////////
	if(numberOfPlayers==1) {

panel_1.add(mans[0].lifeLabel,BorderLayout.WEST);
//Timer lifeMaker=new Timer();
Random rnd=new Random();
//lifeMaker.schedule(new TimerTask() {
//	
//	@Override
//	public void run() {
//		
//		Life li=new Life(n, -1, -1, board, flags, panel, mans);
//		
//	}
//}, 40000, 40000);
//Timer makeStar=new Timer();
//int p=rnd.nextInt(9)+1;
//makeStar.schedule(new TimerTask() {
//	int t=0;
//	int rand=p;
//	@Override
//	public void run() {
//		if(t==rand*10000) {
//			rand=rnd.nextInt(9)+1;
//		
//Star st=new Star(n, -1, -1, board, flags, panel, mans);
//		}
//		
//	}
//}, 10000, 10000);
giants=new Giant[numberOfGiants];
for(int i=0;i<numberOfGiants;i++) {
	giants[i]=new Giant(n,m, board, flags, panel, i, mans[0],shi);
}
int count=rnd.nextInt(2*n);
for(int i=1;i<=count;i++) {
	boolean flag=false;
	int o=0,u=0;
	while(flag==false) {
		o=rnd.nextInt(n);
		u=rnd.nextInt(m);
		if(flags[o][u]==true && (o!=1 && u!=0) && (o!=n-2 && u!=m-1) && (o!=1 && u!=m-1) && (o!=n-2 && u!=0))
			flag=true;
	}
	JLabel lb=new JLabel();
	lb.setName("box");
	lb.setSize(shi/m, 1000/n);
	try {
		Image im=ImageIO.read(new File("icon/sprite/box.png"));
		lb.setIcon(new ImageIcon(im.getScaledInstance(shi/m, 1000/n, 1)));
		board[o][u].add(lb,BorderLayout.CENTER);
		flags[o][u]=false;//rah baste mishavad
	} catch (IOException e) {
		
		e.printStackTrace();
	}
	
}
	}else {
		switch (pl) {//////////////////////ONLINE;SAKHTE LABLE JAN////////////////////
		case 0:
			panel_1.add(mans[0].lifeLabel,BorderLayout.WEST);
			break;
		case 1:
			panel_1.add(mans[1].lifeLabel,BorderLayout.WEST);
			break;
		case 2:
			panel_1.add(mans[2].lifeLabel,BorderLayout.WEST);
			break;
		case 3:
			panel_1.add(mans[3].lifeLabel,BorderLayout.WEST);
			break;
		}
		
			
	}
	
	////////////////////////////////////ONLINE;SAKHTE BOX//////////////////////////////////////////
	if(!indx.equals("") && !indx.equals("-1")) {

	String[] indxx=indx.split("@");//tafkike moshakhaste box ha
	for(int i=0;i<indxx.length;i++) {
		String[]  tp=indxx[i].split(":");//moshakhasate box
		JLabel lb=new JLabel();
		lb.setName("box");
		lb.setSize(shi/m, 1000/n);
		try {
			Image im=ImageIO.read(new File("icon/sprite/box.png"));
			lb.setIcon(new ImageIcon(im.getScaledInstance(shi/m, 1000/n, 1)));
			board[Integer.parseInt(tp[0])][Integer.parseInt(tp[1])].add(lb,BorderLayout.CENTER);
			flags[Integer.parseInt(tp[0])][Integer.parseInt(tp[1])]=false;//rah baste mishavad
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}}
	///////////////////////////////////////////////////////////////////////
	}
	/**
	 * etmame bazi baraye halate OFFLINE
	 * @param time
	 * @param w
	 * @param bomb
	 * @param giants
	 */
	public void finish(int time,int w,int bomb,int giants) {
player.record(time, w, bomb, giants);
player.newGame(time, w, bomb, giants);
		Profile pr=new Profile(player);
		pr.setVisible(true);
		this.dispose();
		
	}
	/**
	 * etmame bazi baraye halate ONLINE
	 * @param k
	 * @param bomb
	 * @param giants
	 */
	public void finish(int k,int bomb,int giants) {
player.record(k, bomb, giants);
player.newGame(k, bomb, giants, timee, mans[pl].killsGetter());
try {
	client.socket.close();
} catch (IOException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}
Profile pr=new Profile(player);
pr.setVisible(true);
		this.dispose();
		
	}
	
	}

