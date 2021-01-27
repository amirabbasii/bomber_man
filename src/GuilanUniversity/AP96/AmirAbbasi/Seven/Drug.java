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
 * darooe gheib shodan
 * name lable:drug
 * @author Asus
 *
 */
public class Drug {
private JLabel lbl=new JLabel();
private Timer mind;
private Timer timer;
private int n,x,y;
private JLabel[][] homes;
private Man[] mans;
private JPanel panel;

public Drug(int n,int m,int x,int y, JLabel[][] homes,boolean[][] flags,JPanel panel,Man[] mans,int shi) {
this.panel=panel;
	this.n = n;
	lbl.setName("star");
	lbl.setSize(shi/m, 800/n);
		this.x=x;
		this.y=y;
	try {
		Image img=ImageIO.read(new File("icon/drug.png"));
		lbl.setIcon(new ImageIcon(img.getScaledInstance(shi/m, 1000/n, 10)));
		homes[this.x][this.y].add(lbl);
		panel.repaint();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	this.mans=mans;
	this.homes = homes;
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
	mind=new Timer();
	int b=this.x;
	int e=this.y;
	mind.schedule(new TimerTask() {
		
		@Override
		public void run() {
			for(int i=0;i<homes[b][e].getComponentCount();i++) {
				if(homes[b][e].getComponent(i).getName().contains("man")) {
					String[] tmp=homes[b][e].getComponent(i).getName().split("#");
					mans[Integer.parseInt(tmp[1])].client.sendMessage("Q"+tmp[1]);
					kill();
					break;
				}
			}
			
		}
	}, 100	, 100);
}
public void kill() {
	timer.cancel();
	mind.cancel();
	homes[x][y].remove(lbl);
	panel.repaint();
	
}
}
