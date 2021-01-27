package GuilanUniversity.AP96.AmirAbbasi.Seven;

import java.awt.Image;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class Player {
private String name;//name
private double scoreOnline=0;//emtiaze online
private String passwrord;//password
private double scoreOffline=0;//emtiaze Offline
 ArrayList<String>  games=new ArrayList<>();//sabeghe bazi ha
static ArrayList<Player> players=new ArrayList<Player>();//player ha

/**
 * Constructor 
 * @param name
 * @param scoreOffline
 * @param scoreOnline
 * @param password
 * @param game
 */
public Player(String name,double scoreOffline,double scoreOnline,String password,String game) {
	this.name=name;
	this.scoreOffline=scoreOffline;
	this.scoreOnline=scoreOnline;
	this.passwrord=password;
	String[] all=game.split("@");
	for(int i=0;i<all.length;i++) {
	games.add(all[i]);
	}
}
/**
 * Getter:name
 * @return
 */
public String  nameGetter() {
	return name;
}

/**
 * Setter:name
 * @param name
 */
public void nameSetter(String name) {
	this.name=name;
}
/**
 * Getter:password
 * @return
 */
public String  passGetter() {
	return passwrord;
}

/**
 * Setter:password
 * @param scoreOff
 */
public void passSetter(String scoreOff) {
	this.passwrord=passwrord;
}

/**
 * Getter:scoreOffline
 * @return
 */
public double  scoreOffGetter() {
	return scoreOffline;
}

/**
 * Setter scoreOffline
 * @param scoreOff
 */
public void scoreOffSetter(double scoreOff) {
	this.scoreOffline=scoreOff;
}
/**
 * Getter: scoreOnline
 * @return
 */
public double  scoreOnGetter() {
	return scoreOnline;
}
/**
 * Setter:scoreOnline
 * @param scoreOn
 */
public void scoreOnSetter(double scoreOn) {
	this.scoreOnline=scoreOn;
}
/**
 * sakhte playere jadid
 * @param name
 * @param scoreOffline
 * @param scoreOnline
 * @param password
 * @param game
 */
public static void newPlayer(String name,double scoreOffline,double scoreOnline,String password,String game) {
	players.add(new Player(name,scoreOffline,scoreOnline,password,game));
}
/**
 * update file player ha(players.data)
 * player ha ba # az ham tafkik mishavand
 * 
 */
public static void writePlayers() {
	try {
		OutputStream out=new FileOutputStream("Players.data");
		for(int i=0;i<players.size();i++) {
			try {
				out.write(new String(players.get(i).toString()+"#").getBytes());
			} catch (IOException e) {
				
			}
		}
		try {
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	} catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
}
/**
 * load kardane player ha az players.data
 */
public static void load() {
	String txt="";
	try {
		InputStream in=new FileInputStream("players.data");
	
		int buffer=0;
		while(buffer!=-1) {
			try {
				buffer=in.read();
				txt+=(char)buffer;
			} catch (IOException e) {
			}
			
		}
		try {
			in.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	} catch (FileNotFoundException e) {
		
	}
	if(txt.length()!=0) {
	txt=txt.substring(0,txt.length()-1);
	String[] pl=txt.split("#");
	for(int i=0;i<pl.length;i++) {
		String[] tmp=pl[i].split(":");
		newPlayer(tmp[0],Float.parseFloat(tmp[1]),Float.parseFloat(tmp[2]),tmp[3],tmp[4]);
		
	}
	}
}
/**
 * overridee toString
 */
public String toString() {
	return name+":"+scoreOffline+":"+scoreOnline+":"+passwrord+":"+GameforWrite();
}

/**
 * sabte emtiaze jadid(Offline)
 * @param time
 * @param w
 * @param bomb
 * @param giants
 */
public void record(int time,int w,int bomb,int giants) {
	double x=0;;
	try {
x=(Math.pow(giants, w))/(time+(Math.log10(bomb)/Math.log10(2)));
	}
	catch (ArithmeticException e) {
		x=0;
	}
	scoreOffline+=x;
	Player.writePlayers();
}
/**
 * sabte emtiaze jadid(Online)
 * @param k
 * @param bomb
 * @param giants
 */
public void record(int k,int bomb,int giants) {
	double x=(Math.pow(giants, k))/(Math.log10(bomb)/Math.log10(2));
	scoreOnline+=x;
	Player.writePlayers();
}
/**
 * chack kardane passworde vared shode
 * agra username mojood nabashad -1,agar bashad vali password dorost nabashad 0 ,agar bashad va password ham dorost bashad 1 ra return mikonad
 * @param username
 * @param password
 * @return
 */
public static int checkPass(String username,String password) {
	int st=-1;
	for(int i=0;i<players.size();i++) {
		if(players.get(i).name.equals(username)) {
			if(players.get(i).passwrord.equals(password))
				st=1;
			else
				st=0;
		}
	}
	return st;
}
/**
 * Getter:Player
 * @param name
 * @return
 */
public static Player getPlayer(String name) {
	Player pl=null;
	for(int i=0;i<players.size();i++) {
		if(players.get(i).name.equals(name))
			pl=players.get(i);
	}
	return pl;
}
/**
 * arraye moratab shode az player ha ra bar asase emtiaze Offline midahad
 * algoritm:buble sort
 * @return
 */
public static String[] sortOffline() {
	String[] pls=new String [players.size()];
	double[] scores=new double [players.size()];
	for(int i=0;i<pls.length;i++) {
		pls[i]=players.get(i).name;
		scores[i]=players.get(i).scoreOffline;
	}
	for(int i=0;i<scores.length;i++) {
		for(int j=0;j<scores.length-1;j++) {
			if(scores[j]>scores[j+1]) {
				double tmp1=scores[j];
				scores[j]=scores[j+1];
				scores[j+1]=tmp1;
				String tmp2=pls[j];
				pls[j]=pls[j+1];
				pls[j+1]=tmp2;
			}
		}
	}
	String ans[]=new String[pls.length];
	for(int i=pls.length-1;i>=0;i--) {
		ans[i]=pls[i]+":"+scores[i];
	}
	return ans;
}
/**
 * arraye moratab shode az player ha ra bar asase emtiaze Online midahad
 * algoritm:buble sort
 * @return
 */
public static String[] sortOnline() {
	String[] pls=new String [players.size()];
	double[] scores=new double [players.size()];
	for(int i=0;i<pls.length;i++) {
		pls[i]=players.get(i).name;
		scores[i]=players.get(i).scoreOnline;
	}
	for(int i=0;i<scores.length;i++) {
		for(int j=0;j<scores.length-1;j++) {
			if(scores[j]>scores[j+1]) {
				double tmp1=scores[j];
				scores[j]=scores[j+1];
				scores[j+1]=tmp1;
				String tmp2=pls[j];
				pls[j]=pls[j+1];
				pls[j+1]=tmp2;
			}
		}
	}
	String ans[]=new String[pls.length];
	for(int i=pls.length-1;i>=0;i--) {
		ans[i]=pls[i]+":"+scores[i];
	}
	return ans;
}
/**
 * sabte sabeghe bazie jadide Online
 * @param k
 * @param bomb
 * @param giants
 * @param time
 * @param kills
 */
public void newGame(int k,int bomb,int giants,int time,int kills) {
	double x=(Math.pow(giants, k))/(Math.log10(bomb)/Math.log10(2));
games.add("Online"+"|time="+String.valueOf(time)+"|killed="+String.valueOf(kills)+"|bombs="+String.valueOf(bomb)+"|giants="+String.valueOf(giants)+"|score="+String.valueOf(x));
	Player.writePlayers();
}
/**
 * sabte sabeghe bazie jadide Offline
 * @param k
 * @param bomb
 * @param giants
 * @param time
 * @param kills
 */
public void newGame(int time,int w,int bomb,int giants) {
	double x=0;
	try {
x=(Math.pow(giants, w))/(time+(Math.log10(bomb)/Math.log10(2)));
	}
	catch (ArithmeticException e) {
		x=0;
	}
	String ty="";
	if(w==1)
		ty="game over";
	else
		ty="winner";
games.add("Offline"+"|time="+String.valueOf(time)+"|"+ty+"|bombs="+String.valueOf(bomb)+"|giants="+String.valueOf(giants)+"|score="+String.format("%.2f", x));
	Player.writePlayers();
}
public String GameforWrite() {
	String out="";
	for(int i=0;i<games.size();i++) {
		out=games.get(i)+"@";
	}
	return out;
}
}
