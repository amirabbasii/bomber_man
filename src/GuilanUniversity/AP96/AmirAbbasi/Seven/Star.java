package GuilanUniversity.AP96.AmirAbbasi.Seven;

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
 * setare
 * name lable:star
 * @author Asus
 *
 */
public class Star {
private JLabel lbl=new JLabel();//lable Star
private Timer mind;//timere baresie barkhord
private Timer timer;//timere omr
private int n,x,y;
private JLabel[][] homes;//safhe bazi
private Man[] mans;//bazikon ha
private JPanel panel;//panele bazi

/**
 * Constructor
 * @param n
 * @param x
 * @param y
 * @param homes
 * @param flags
 * @param panel
 * @param mans
 */
public Star(int n,int m,int x,int y, JLabel[][] homes,boolean[][] flags,JPanel panel,Man[] mans,int shi) {
this.panel=panel;
	this.n = n;
	lbl.setName("star");
	lbl.setSize(shi/m, 1000/n);
		this.x=x;
		this.y=y;
	
	try {
		Image img=ImageIO.read(new File("icon/sprite/star.png"));//tasvir
		lbl.setIcon(new ImageIcon(img.getScaledInstance(shi/m, 1000/n, 10)));
		homes[this.x][this.y].add(lbl);
		panel.repaint();
	} catch (IOException e) {
	}
	this.mans=mans;
	this.homes = homes;
	/**
	 * timere omr;bade 20 sanie az beyn miravad
	 */
	timer=new Timer();
	timer.schedule(new TimerTask() {
		int t=0;
		@Override
		public void run() {
			t++;
			if(t==20) {
				kill();
			}
			
		}
	}, 1000, 1000);
	
	/**
	 * timere barkhord;har 0.1 sanie ashiae mojood dar khane ra check mikonad;dar soorate vojood lable ba name man yani barkhord  va shoae enfejar 2 barabar mishavad
	 */
	mind=new Timer();
	int b=this.x;
	int e=this.y;
	mind.schedule(new TimerTask() {
		
		@Override
		public void run() {
			for(int i=0;i<homes[b][e].getComponentCount();i++) {
				if(homes[b][e].getComponent(i).getName().contains("man")) {//bazikon dar khane vojood darad
					String[] tmp=homes[b][e].getComponent(i).getName().split("#");//estekhraje adade bazikon
					mans[Integer.parseInt(tmp[1])].eatStar();//
					kill();//az beyn miravad
					break;
				}
			}
			
		}
	}, 100	, 100);
}
/**
 * timer ha az beyn miravand va lbl az khane pak mishavad
 */
public void kill() {
	timer.cancel();
	mind.cancel();
	homes[x][y].remove(lbl);
	panel.repaint();
	
}
}
