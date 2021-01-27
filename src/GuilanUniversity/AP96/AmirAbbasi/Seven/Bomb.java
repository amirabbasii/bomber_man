package GuilanUniversity.AP96.AmirAbbasi.Seven;

import java.awt.BorderLayout;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
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
import javax.swing.JLabel;
import javax.swing.JPanel;
/**
 * Bomb
 * name lable:bomb
 * @author Asus
 *
 */
public class Bomb {
private int r;//shoae enfejar
private int x,y;
int m;
private JPanel panel;
private JLabel lble;//lable 
private JLabel[][] homes;
private Giant[] giants;//hayoola ha
private boolean flags[][];
private int shi;
private Man[] mans;//bazikon ha
private Image exp;//tasvire enfejar
private Man bomber;//bazikone bomb gozar
private int n;

public Bomb(int r,int n,int m,int x,int y,JLabel[][] homes,JPanel panel,Giant[] giants,boolean[][] flags,Man[] mans,Man bomber,int shi) {
this.mans=mans;
	this.x=x;
	this.flags=flags;
	this.shi=shi;
	this.y=y;
	this.n=n;
	this.m=m;
	this.bomber=bomber;
	this.r=r;
	this.giants=giants;
	this.panel=panel;
	this.homes=homes;
	try {
		 lble=new JLabel();
		lble.setSize(shi/m,1000/n);
		lble.setName("bomb");
		Image im=ImageIO.read(new File("icon/sprite/bmb.png"));//tasvire bomb
		lble.setIcon(new ImageIcon(im.getScaledInstance(700/m, 700/n, 1)));
	homes[x][y].add(lble,BorderLayout.CENTER);
	exp=ImageIO.read(new File("icon/exp.png"));//tasvire enfejar
	
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	/**
	 * timere omr(3 s)
	 */
	Timer timer=new Timer();
	timer.schedule(new TimerTask() {
		int t=0;
		@Override
		public void run() {
			t++;
			if(t==3) {
				explode();//enfejar
				timer.cancel();
			}
		}
	}, 1000	, 1000);
	new Sound("sounds/c4_plant.wav",500).start();//sedaye gozashtane bomb
	
}
/**
 * enfejar
 * 9 jahat darad;khane bomb gozari shode va 8 jahate khane
 * intor amal mikonad ke JLable haye atarf ba shoae tarif shode ke sang nistand ra dar nazar migirad va va tak take ashiae anha ra dar nazar migirad va agar ba "man" shoroo shavad bazikon va
 * agar ba "giant" shoroo shavad hayoola ast
 * 
 */
public void explode () {
	ArrayList<Integer> xx=new  ArrayList<>();//x  khane haye tahte enfejar
	ArrayList<Integer> yy=new  ArrayList<>();//y khane haye tahte enfejar
	
	////////////////////////////////////////////////////////////////////////asib ha///////////////////////////////////////////
	for(int i=1;i<=r;i++) {//baraye shoa ha
		for(int l1=-1;l1<=1;l1++) {//zaribe
			for(int l2=-1;l2<=1;l2++) {//zarib
				try {
					if(!homes[x+l1*i-l1][y+l2*i-l2].getName().equals("rock")&& !homes[x+l1*i][y+l2*i].getName().equals("rock")) {//agar khane dar nazar gerefte shode sang nist
						xx.add(x+l1*i);
						yy.add(y+l2*i);
					for(int j=0;j<=homes[x+l1*i][y+l2*i].getComponentCount();j++) {
						if(homes[x+l1*i][y+l2*i].getComponent(j).getName().contains("man")) {//agar bazikon vojood darad
							String[] tmp=homes[x+l1*i][y+l2*i].getComponent(j).getName().split("#");//estekhraje adade bazikon
							mans[Integer.parseInt(tmp[1])].hurt();
							if(mans[Integer.parseInt(tmp[1])].lifeGetter()==0)//agar kosht
								bomber.shuted();//afzayeshe tedade ghatl
						}if(homes[x+l1*i][y+l2*i].getComponent(j).getName().contains("giant"))//agar hayoola vojood darad
						{
							String[] tmp=homes[x+l1*i][y+l2*i].getComponent(j).getName().split("#");//estkhraje adade hayoola
							giants[Integer.parseInt(tmp[1])].kill();//estekhraje adade hayoola
							bomber.shuted();
						}
						else if(homes[x+l1*i][y+l2*i].getComponent(j).getName().equals("box")) {//az beyn bordane jabe ha
							homes[x+l1*i][y+l2*i].remove(homes[x+l1*i][y+l2*i].getComponent(j));
							
							flags[x+l1*i][y+l2*i]=true;//rah baz mishavad
						}
					}
					
				}
				}
				catch (ArrayIndexOutOfBoundsException e) {
					
				}
			}
		}

	}
	////////////ezafe akse atash be khane haye tahte enfejar///////////////
	for(int i=0;i<xx.size();i++)
	{
	JLabel fire=new JLabel();
	fire.setName("fire");
	fire.setSize(shi/m,1000/n);
	Image im=null;
	try {
		im = ImageIO.read(new File("icon/fire.png"));
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}//tasvire atash
	fire.setIcon(new ImageIcon(im.getScaledInstance(shi/m, 1000/n, 1)));
		homes[xx.get(i)][yy.get(i)].add(fire,BorderLayout.CENTER);
		panel.repaint();
	}
	lble.setIcon(new ImageIcon(exp.getScaledInstance(shi/m, 1000/n, 1)));
	
	
	///////////////Timere az beyn raftane aks haye atash bade 1 s//////////////////////////////////
	////dar khane haye tahte enfejar har ja JLable ba name "fire" bashad remove mikonad
	Timer amir=new Timer();
	amir.schedule(new TimerTask() {
		
		@Override
		public void run() {
			for(int i=0;i<xx.size();i++)
			{
			for(int j=0;j<homes[xx.get(i)][yy.get(i)].getComponentCount();j++) {
				if(homes[xx.get(i)][yy.get(i)].getComponent(j).getName().equals("fire")) {
					homes[xx.get(i)][yy.get(i)].remove(homes[xx.get(i)][yy.get(i)].getComponent(j));
				}
			}
		
		
			}
			homes[x][y].remove(lble);//hazf shodane akse bomb
			panel.repaint();
			panel.repaint();
			amir.cancel();
			
			
		}
	}, 1000, 1000);
	////////////////////////////////////////////////////////////////////////////////
	
	
	new Sound("sounds/explode.wav"	,1000).start();//sedaye enfejar

	panel.repaint();
	
}
/**
 * Ijade seda
 * @author Asus
 *
 */
class Sound extends Thread{
	String address;//addresse file
	int m;//omre seda

	/**
	 * Constructor
	 * @param address
	 * @param m
	 */
	public Sound(String address,int m) {
		this.address=address;
		this.m=m;
	}
	public void run() {
		
		Clip clip=null;//clip
		try {
			clip=AudioSystem.getClip();
			try {
				clip.open(AudioSystem.getAudioInputStream(new File(address)));
				clip.start();
				clip.flush();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (UnsupportedAudioFileException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			Thread.sleep(m);//omre seda
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		clip.close();

		

		
	}
	
}

}
