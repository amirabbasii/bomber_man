package GuilanUniversity.AP96.AmirAbbasi.Seven;

import java.awt.BorderLayout;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
/**
 * hayoola
 * name lable:giant
 * @author Asus
 *
 */
public class Giant {
	private JLabel[][] homes;
	private JLabel lable;
	private boolean flags[][];
	private Image[] images=new Image[2];
	private int x,y;
	private int n;
	private Timer timer;
	private Timer mind;
	private int index;
	private Man man;
	int m;
	private JPanel panel;
public Giant(int n,int m,JLabel[][] homes,boolean[][] flags,JPanel panel,int index,Man man,int shi) {
	this.n=n;
	this.m=m;
	this.man=man;
	this.homes=homes;
	this.flags=flags;
	this.index=index;
	this.panel=panel;
	lable=new JLabel();
	lable.setName("giant#"+String.valueOf(index));
	lable.setSize(shi/m,1000/n);
	try {
		Image im=ImageIO.read(new File("icon/sprite/Enemies/blockerBody.png"));
		images[1]=im;
		 im=ImageIO.read(new File("icon/sprite/Enemies/blockerMad.png"));
		 images[0]=im;
		 lable.setIcon(new ImageIcon(images[0].getScaledInstance(shi/m, 1000/n, 1)));
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	Random rnd=new Random();
	boolean flag=false;
	int o=0,u=0;
	while(flag==false) {
		o=rnd.nextInt(n);
		 u=rnd.nextInt(m);
		if(flags[o][u]==true && (o!=1 && u!=0) && (o!=n-2 && u!=m-1) && (o!=1 && u!=m-1) && (o!=n-2 && u!=0))
			flag=true;
	}
	x=o;
	y=u;
	homes[x][y].add(lable,BorderLayout.CENTER);
	mind=new Timer();
	mind.schedule(new TimerTask() {
		
		@Override
		public void run() {
			for(int i=0;i<homes[x][y].getComponentCount();i++) {
				if(homes[x][y].getComponent(i).getName().equals("man#0")) {
					man.hurt();
					man.shuted();
					kill();
					}
			}
			
		}
	}, 0	, 100);
 timer=new Timer();
	timer.schedule(new TimerTask() {
		
		@Override
		public void run() {
	
			Random rnd=new Random();
			boolean flag=false;
			while(flag==false) {
			int r=rnd.nextInt(4);
			try {
			switch (r) {
			case 0:
				if(flags[x-1][y]==true) {
					up();
					flag=true;
				}
				break;
			case 1:
				if(flags[x+1][y]==true) {
					down();
					flag=true;
				}
			case 2:
				if(flags[x][y-1]==true) {
					left();
					flag=true;
				}
				break;
			case 3:
				if(flags[x][y+1]==true) {
					right();
					flag=true;
				}
			}}
			catch (ArrayIndexOutOfBoundsException e) {
				// TODO: handle exception
			}
			}
			
			panel.repaint();
		}
	}, 1000, 1000);
	
}
public void right() {

	if(y+1<m) {
	homes[x][y].remove(lable);
	y++;
	homes[x][y].add(lable);}
}
public void left() {

	if(y-1>=0) {
	homes[x][y].remove(lable);
	y--;
	homes[x][y].add(lable);}
}
public void down() {

	if(x+1<n) {
	homes[x][y].remove(lable);
	x++;
	homes[x][y].add(lable);}
}
public void up() {

	if(x-1>=0) {
	homes[x][y].remove(lable);
	x--;
	homes[x][y].add(lable);}
}
public void kill() {
	timer.cancel();
	mind.cancel();
	homes[x][y].remove(lable);
	panel.repaint();
}

}
