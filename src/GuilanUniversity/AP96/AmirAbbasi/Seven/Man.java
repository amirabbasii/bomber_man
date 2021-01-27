package GuilanUniversity.AP96.AmirAbbasi.Seven;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
 /**
  * bazikon
  * name lable:man
  * @author Asus
  *
  */
public class Man {
	private Image left[]=new Image[3];//araye aks ha az chap
	private Image right[]=new Image[3];//araye aks ha az rast
	private Image up[]=new Image[3];//araye aks ha az bala
	private Image down[]=new Image[3];//araye aks ha az payin
	private int x;//x alan
	private int kills=0;//tedade ghatl ha
	private int y;//y alan
	private final int X,Y;//x,y ebteda va default
	int shi;
	private int n;//abaad
 Client client;
	private int life=2;//jan
	private int index;//adade bazikon
	private int r=1;//shoae enfejare bomb
	private JLabel timeLbl;//lable timer
	private int bombs=0;//tedade bombe estefade sohde
	 JLabel lifeLabel=new JLabel();//lable jan
	private JLabel lable=new JLabel("1");//lable bazikon
	private JLabel[][] homes;
	private JLabel killed;
	int m;
	private int  numberOfGiants;//tedade bazikon ha
	private boolean flags[][];
	private JPanel panel;
	public Man(int n,int m,int x,int y,String color,JLabel[][] homes,boolean[][] flags,int index,Client client,JLabel timeLlbl,JPanel panel,int numberOfGiants,JLabel killed,int shi) {
////////loadi me/////////////
		this.client=client;
		this.shi=shi;
		this.numberOfGiants=numberOfGiants;
		this.timeLbl=timeLlbl;
		this.index=index;
		this.killed=killed;
		this.panel=panel;
		this.m=m;
		try {
			Image img = ImageIO.read(new File("icon/sprite/life.png"));
			lifeLabel.setIcon(new ImageIcon(img.getScaledInstance(shi/m, 1000/n, 1)));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	
		this.lable=lable;
		lable.setName("man#"+String.valueOf(index));
		this.lable.setSize(shi/m,1000/n);
		this.homes=homes;
		this.n=n;
		this.x=x;
		this.y=y;
		X=x;
		Y=y;
		this.flags=flags;
		///////////////////////////////load kardane tasavire bala////////////////////////////////////////////
		 for(int i=1;i<=3;i++) {
		 try {
			Image im=ImageIO.read(new File("icon/sprite/Characters/"+color+"_Back"+String.valueOf(i)+".png")).getScaledInstance(shi/m, 800/n, 2);
			
			up[i-1]=im;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 }
		 /////////////////////////////////////////////////////////////////////////////////////////////////
		 
		 ///////////////////////////load karadnae tasavire payin//////////////////////////////////////////
		 for(int i=1;i<=3;i++) {
			 try {
				Image im=ImageIO.read(new File("icon/sprite/Characters/"+color+"_Front"+String.valueOf(i)+".png")).getScaledInstance(shi/m, 800/n, 2);
				down[i-1]=im;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 }
		 //////////////////////////////////////////////////////////////////////////////////////////////
		 
		 //////////////////////////////////////////////////load kardane tasavire chap/////////////////////////////////
		 for(int i=1;i<=3;i++) {
			 try {
				Image im=ImageIO.read(new File("icon/sprite/Characters/"+color+"_Left"+String.valueOf(i)+".png")).getScaledInstance(shi/m, 800/n, 2);
				left[i-1]=im;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 }
		 /////////////////////////////////////////////////////////////////////////////////////////////////////////
		 
		 /////////////////////////////////////////////////////load kardane tasavire rast////////////////////////////////////////
		 for(int i=1;i<=3;i++) {
			 try {
				Image im=ImageIO.read(new File("icon/sprite/Characters/"+color+"_Right"+String.valueOf(i)+".png")).getScaledInstance(shi/m, 800/n, 2);
				right[i-1]=im;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 }
		 ////////////////////////////////////////////////////////////////////////////////////////////////////////////
		
		 ////////////tasvre ebtedaye bazi/////////////////////
		 switch (color) {
		case "Blue":
			 this.lable.setIcon(new ImageIcon(right[0]));
			break;
		case "Red":
			 this.lable.setIcon(new ImageIcon(left[0]));
			break;
		case "White":
			 this.lable.setIcon(new ImageIcon(left[0]));
			 break;
		case "LightBlue":
			 this.lable.setIcon(new ImageIcon(right[0]));
		}
		 //////////////////////////////////////////////////
		
			
		 homes[x][y].add(lable);
			
	}
	/**
	 * karekat be rast
	 */
	public void right() {
		try {
		if(flags[x][y+1]==true) {
		lable.setIcon(new ImageIcon(right[Integer.parseInt(lable.getText())]));
		lable.setText(String.valueOf((Integer.parseInt(lable.getText()))%2+1));
		
		homes[x][y].remove(lable);
		y++;
		homes[x][y].add(lable);}}
		catch (ArrayIndexOutOfBoundsException e) {
			// TODO: handle exception
		}
	}
	/**
	 * harekat be chap
	 */
	public void left() {
		try {
		if(flags[x][y-1]==true) {
		lable.setIcon(new ImageIcon(left[Integer.parseInt(lable.getText())]));
		lable.setText(String.valueOf((Integer.parseInt(lable.getText()))%2+1));
		homes[x][y].remove(lable);
		y--;
		homes[x][y].add(lable);}}
		catch (ArrayIndexOutOfBoundsException e) {
			// TODO: handle exception
		}
	}
	/**
	 * harekat be payin
	 */
	public void down() {
		try {
		if(flags[x+1][y]==true) {
		lable.setIcon(new ImageIcon(down[Integer.parseInt(lable.getText())]));
		lable.setText(String.valueOf((Integer.parseInt(lable.getText()))%2+1));
		homes[x][y].remove(lable);
		x++;
		homes[x][y].add(lable);}}
		catch (ArrayIndexOutOfBoundsException e) {
			// TODO: handle exception
		}
	}
	/**
	 * harekat be bala
	 */
	public void up() {
		try {
		if(flags[x-1][y]==true) {
		lable.setIcon(new ImageIcon(up[Integer.parseInt(lable.getText())]));
		lable.setText(String.valueOf((Integer.parseInt(lable.getText()))%2+1));
		homes[x][y].remove(lable);
		x--;
		homes[x][y].add(lable);}}
		catch (ArrayIndexOutOfBoundsException e) {
			// TODO: handle exception
		}
	}
	/**
	 * bomb gozari
	 * @param giants
	 * @param mans
	 */
	public void bomb(Giant[] giants,Man[] mans) {
		Bomb bomb=new Bomb(r,n,m,x, y, homes,panel,giants,flags,mans,this,shi);
		bombPlus();
		
	}
	/**
	 * asib didane bazikon va nesf shodane janash
	 */
	public void hurt() {
		life--;
		try {
			Image img=ImageIO.read(new File("icon/sprite/hurted.png"));
			lifeLabel.setIcon(new ImageIcon(img.getScaledInstance(100, 100, 10)));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(life==0) {
			if(client!=null) {

					homes[x][y].remove(lable);
					lifeLabel.setVisible(false);
				Timer timeOut=new Timer();
				timeOut.schedule(new TimerTask() {
					
					@Override
					public void run() {
						homes[X][Y].add(lable);
						x=X;
						y=Y;
						lifePlus();
						lifeLabel.setVisible(true);
						life=2;
						panel.repaint();
						timeOut.cancel();
						
					}
				},3000, 3000);
			}else {
			timeLbl.setName("end");
			}
		}
		
		
	}
	/**
	 * ezafe shodane nesfe jan
	 */
	public void lifePlus() {
		life++;
		try {
			Image img=ImageIO.read(new File("icon/sprite/life.png"));
			lifeLabel.setIcon(new ImageIcon(img.getScaledInstance(100, 100, 10)));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void eatStar() {
		if(r!=8)
		r=r*2;
		Timer t=new Timer();
		t.schedule(new TimerTask() {
			
			@Override
			public void run() {
				r=r/2;
				t.cancel();
			}
		}, 40000, 40000);
	}
	/**
	 * afazayeshe tedade ghatl
	 */
	public void shuted() {
		kills++;
		killed.setText("kills:"+String.valueOf(kills));
		if(numberOfGiants!=0) {
			if(kills==numberOfGiants)
				timeLbl.setName("end");
		}
	}
	/**
	 * kohrdane daroo va gheib shodan
	 */
	public void drug() {
	lable.setVisible(false);
		Timer t=new Timer();
		t.schedule(new TimerTask() {
			
			@Override
			public void run() {
			lable.setVisible(true);
				t.cancel();
			}
		}, 10000, 10000);
	}
	/**
	 * Getter x
	 * @return
	 */
	public int xGetter() {
		return x;
	}
	/**
	 * Setter x
	 * @param x
	 */
	public void xSetter(int x) {
		this.x=x;
	}
	/**
	 * Getter y
	 * @return
	 */
	public int yGetter() {
		return y;
	}
	/**
	 * Setter y
	 * @param y
	 */
	public void ySetter(int y) {
		this.y=y;
	}
	/**
	 * Getter life
	 * @return
	 */
	public int lifeGetter() {
		return life;
	}
	/**
	 * Setterlife
	 * @param life
	 */
	public void lifeSetter(int life) {
		this.life=life;
	}
	/**
	 * Getter bomb
	 * @return
	 */
	public int bombGetter() {
		return bombs;
	}
	/**
	 * Setter bomb
	 * @param bomb
	 */
	public void bombSetter(int bomb) {
		this.bombs=bomb;
	}
	/**
	 * Getter kills
	 * @return
	 */
	public int killsGetter() {
		return kills;
	}
	/**
	 * Setter kills
	 * @param kills
	 */
	public void killsSetter(int kills) {
		this.kills=kills;
	} 
	/**
	 * afzayeshe tedade bombe estefade shode
	 */
	public void bombPlus() {
		bombs++;
	}
}
