package GuilanUniversity.AP96.AmirAbbasi.Seven;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileFilter;

import GuilanUniversity.AP96.AmirAbbasi.Seven.Login.Panel;

import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Profile extends JFrame {
private Player player;
	private Panel contentPane;

private JFrame fr;

	public Profile(Player player) {
		this.player=player;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 955, 532);
		contentPane = new Panel(" ");
		contentPane.setBackground(Color.YELLOW);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel label = new JLabel("");//lable aks
		Image img;//aks
		try {
			img = ImageIO.read(new File("pictures/"+player.nameGetter()+".png"));
			label.setIcon(new ImageIcon(img.getScaledInstance(313, 305, 0)));
		} catch (IOException e) {
			
		}
		
		
		label.setBackground(Color.BLUE);
		label.setBounds(97, 73, 313, 305);
		contentPane.add(label);
		
		JButton btnNewButton = new JButton("Offline");//offline
		fr=this;
		btnNewButton.addActionListener(new ActionListener() {
		
			public void actionPerformed(ActionEvent arg0) {
				fr.dispose();
				JFrame fe=new JFrame();
				fe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				fe.setBounds(100, 100, 450, 400);
				JPanel content = new JPanel();
				content.setBackground(Color.yellow);
				content.setBorder(new EmptyBorder(5, 5, 5, 5));
				fe.setContentPane(content);
				content.setLayout(null);
				
				JTextField textPane = new JTextField();//tedade hayoola
				textPane.setBounds(109, 50, 200, 41);
				content.add(textPane);
				JTextField textPane_2 = new JTextField();//tedade hayoola
				textPane_2.setBounds(109, 190, 200, 41);
				content.add(textPane_2);
				JTextField textPane_1 = new JTextField();//abaad
				textPane_1.setBounds(109, 130, 200, 41);
				content.add(textPane_1);
				JButton btnBack = new JButton("back");//back
				btnBack.setForeground(Color.WHITE);
				btnBack.setBackground(Color.BLACK);
				btnBack.setFont(new Font("Hobo Std", Font.PLAIN, 16));
				btnBack.setBounds(109, 250, 97, 25);
				content.add(btnBack);
				btnBack.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent arg0) {
						Profile plo=new Profile(player);
						plo.setVisible(true);
						fe.dispose();
					}
				});
				JButton btnPlay = new JButton("Play!");
				
				btnPlay.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent arg0) {
						if(Integer.parseInt(textPane.getText())<=0) {
							JOptionPane.showMessageDialog(null, "Invalid number for giants");//voroodi namotabar
						}else {
						Game ga=new Game(null, Integer.parseInt(textPane_1.getText()),Integer.parseInt(textPane_2.getText()), 0, "",Integer.parseInt(textPane.getText()),1,0,player);
						fe.dispose();
						ga.setVisible(true);}
					}
				});
				btnPlay.setFont(new Font("Hobo Std", Font.PLAIN, 16));
				btnPlay.setBounds(218, 250, 97, 25);
				content.add(btnPlay);
				
				JLabel lblGiants = new JLabel("giants");
				lblGiants.setFont(new Font("Hobo Std", Font.PLAIN, 15));
				lblGiants.setBounds(41, 67, 56, 24);
				content.add(lblGiants);
				
				JLabel lblN = new JLabel("n");
				lblN.setFont(new Font("Hobo Std", Font.PLAIN, 15));
				lblN.setBounds(61, 146, 20, 16);
				content.add(lblN);
				JLabel lblM = new JLabel("m");
				lblM.setFont(new Font("Hobo Std", Font.PLAIN, 15));
				lblM.setBounds(61, 200, 20, 16);
				content.add(lblM);
				fe.setVisible(true);
				
			}
		});
		btnNewButton.setForeground(Color.WHITE);
		btnNewButton.setBackground(Color.BLUE);
		btnNewButton.setFont(new Font("Showcard Gothic", Font.PLAIN, 32));
		btnNewButton.setBounds(696, 380, 199, 64);
		contentPane.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Online");//Online
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				JFrame ff=new JFrame();
				JPanel p=new JPanel();
				p.setBackground(Color.yellow);
				ff.setBounds(20, 20, 200, 200);
				ff.getContentPane().add(p);
				p.setBounds(0, 0, 200, 200);
				JButton join=new JButton("Join");
				JButton host=new JButton("Host");
				ff.setVisible(true);
				join.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent arg0) {
						Client client=new Client(player);
						client.sendMessage("Join");//ferestadane noe join
						client.sendMessage(player.nameGetter());//ferestadane name
						
						String tx=client.getMessage();//liste host ha
						
						String[] hosts=tx.split("#");//tafkik
						String h=(String)JOptionPane.showInputDialog(null, "Please choose", "Hosts", 1, null, hosts, null);//entekhabe host
						int index=-1;
						for(int i=0;i<hosts.length;i++) {
							if(h.equals(hosts[i])) {
								index=i;
								break;}}
						client.sendMessage(String.valueOf(index));//ferestadane indexe hoste entekhabi be server
						join.setEnabled(false);
					}
				});
				host.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent arg0) {
						Client client=new Client(player);
						client.sendMessage("Host");
						client.sendMessage(player.nameGetter());
						String nn=JOptionPane.showInputDialog("please enter n");
						client.sendMessage(nn);
						String mm=JOptionPane.showInputDialog("please enter m");
						client.sendMessage(mm);
							
						String timee=JOptionPane.showInputDialog("please enter time");
						String giants=JOptionPane.showInputDialog("please enter giants");
						while(Integer.parseInt(giants)<2 || Integer.parseInt(giants)>4) {//voroodi na motabar
							giants=JOptionPane.showInputDialog("It soulbe be betwin 2 and 4!please enter n");
						}
						
					
				
						client.sendMessage(timee);
						client.sendMessage(giants);
						
						
					}
				});
				p.add(join);
				p.add(host);
				
			}
		});
		btnNewButton_1.setForeground(Color.WHITE);
		btnNewButton_1.setBackground(Color.BLUE);
		btnNewButton_1.setFont(new Font("Showcard Gothic", Font.PLAIN, 32));
		btnNewButton_1.setBounds(479, 379, 174, 65);
		contentPane.add(btnNewButton_1);
		
		JLabel lblName = new JLabel("Name:"+player.nameGetter());
		lblName.setForeground(Color.BLUE);
		lblName.setFont(new Font("Haettenschweiler", Font.PLAIN, 32));
		lblName.setBounds(490, 80, 225, 43);
		contentPane.add(lblName);
		String tmp=String.valueOf(player.scoreOnGetter());
		if(tmp.length()>5)
			tmp=tmp.substring(0,5);
		JLabel lblOnlineScore = new JLabel("Online Score:"+tmp);
		lblOnlineScore.setForeground(Color.BLUE);
		lblOnlineScore.setFont(new Font("Haettenschweiler", Font.PLAIN, 34));
		lblOnlineScore.setBounds(487, 136, 246, 43);
		contentPane.add(lblOnlineScore);
		 tmp=String.valueOf(player.scoreOffGetter());
		if(tmp.length()>5)
			tmp=tmp.substring(0,5);
		JLabel label_1 = new JLabel("Offline Score:"+tmp);
		label_1.setForeground(Color.BLUE);
		label_1.setFont(new Font("Haettenschweiler", Font.PLAIN, 34));
		label_1.setBounds(490, 192, 246, 43);
		contentPane.add(label_1);
		
		JButton btnScores = new JButton("Scores");
		btnScores.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Panel scorePanel=new Panel("score");
				scorePanel.setBounds(0, 0, 955, 532);
				scorePanel.setLayout(new BorderLayout(0,0));
				
				fr.setContentPane(scorePanel);
				String[][] names=new String[Player.players.size()][2];
				String p1[]=Player.sortOffline();
				String p2[]=Player.sortOnline();
				for(int i=0;i<names.length;i++) {
					names[i][0]=p1[i];
				names[i][1]=p2[i];}
				String jk[]= {"Offline","Online"};


				JScrollPane pan = new JScrollPane();

				pan.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
				pan.setBounds(60, 10,800	, 400);
				scorePanel.add(pan,BorderLayout.CENTER);
JButton backk=new JButton("Back");
backk.setBounds(80, 420, 100, 50);
backk.setBackground(Color.black);
backk.setForeground(Color.red);
backk.setFont(new Font("Hobo Std", Font.PLAIN, 20));
scorePanel.add(backk);
backk.addActionListener(new ActionListener() {
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		fr.setContentPane(contentPane);
		fr.repaint();
		
	}
});
				JTable table=new JTable(names,jk);
				pan.setViewportView(table);
table.setForeground(Color.red);
table.setBackground(Color.black);
pan.setBackground(Color.black);
pan.setForeground(Color.black);
				table.setRowHeight(50);
				scorePanel.add(new JButton("SD"),BorderLayout.NORTH);
				fr.repaint();

			}
		});
		btnScores.setForeground(Color.WHITE);
		btnScores.setFont(new Font("Showcard Gothic", Font.PLAIN, 22));
		btnScores.setBackground(Color.BLUE);
		btnScores.setBounds(727, 94, 123, 53);
		contentPane.add(btnScores);
		
		JButton btnEditProfile = new JButton("Edit");//tagihre account
		btnEditProfile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				fr.setVisible(false);
				JFrame fe=new JFrame();
				fe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				fe.setBounds(100, 100, 878, 524);
				Panel content = new Panel("score");
				content.setBorder(new EmptyBorder(5, 5, 5, 5));
				fe.setContentPane(content);
				content.setLayout(null);

				JLabel lb=new JLabel("");
				lb.setBackground(Color.BLACK);
				lb.setFont(new Font("Hobo Std", Font.PLAIN, 13));
				lb.setForeground(Color.RED);
				lb.setBounds(368, 307, 193, 16);

				JButton editName = new JButton("Edit username");//taghire username
				editName.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent arg0) {
						String newUser=JOptionPane.showInputDialog("Please enter new username");
						int t=Player.checkPass(newUser, " ");//check kardane username
						if(t==-1) {
							player.nameSetter(newUser);
							Player.writePlayers();}
						else//username tekrari
							JOptionPane.showMessageDialog(null, "Username already exist!");
						
					}
				});
				editName.setForeground(Color.red);
				editName.setBackground(Color.yellow);
				editName.setFont(new Font("Hobo Std", Font.PLAIN, 16));
				editName.setBounds(336, 204, 203, 42);
				content.add(editName);
				
				JButton editPass = new JButton("Edit password");//taghire ramz
				editPass.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent arg0) {
						String newPass=JOptionPane.showInputDialog("Enter new password");//password bare 1
						String newPass2=JOptionPane.showInputDialog("Confirm new password");//password bare 2
						if(newPass.equals(newPass2)) {
							player.passSetter(newPass);
							Player.writePlayers();
							}
						else//agar yeksan nabood
							JOptionPane.showMessageDialog(null, "The entered passwords aren't same");
						
					}
				});
				editPass.setForeground(Color.red);
				editPass.setBackground(Color.yellow);
				editPass.setFont(new Font("Hobo Std", Font.PLAIN, 16));
				editPass.setBounds(336, 259, 203, 35);
				content.add(editPass);
				
				JButton btnBack = new JButton("back");//back
				btnBack.setForeground(Color.red);
				btnBack.setBackground(Color.yellow);
				btnBack.setFont(new Font("Hobo Std", Font.PLAIN, 16));
				btnBack.setBounds(378, 374, 97, 25);
				content.add(btnBack);
			
				btnBack.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent arg0) {
						fr.setVisible(true);
						fe.dispose();
					}
				});
				
				JButton btnPlay = new JButton("Edit picture");//taghire tasvir
			
				btnPlay.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent arg0) {
						File file=fileChooser();
						try {
						Files.copy(file.toPath(), new File("pictures/"+player.nameGetter()+".png").toPath());}
						catch (IOException e) {
							// TODO: handle exception
						}
					}
				});
				btnPlay.setFont(new Font("Hobo Std", Font.PLAIN, 16));
				btnPlay.setBackground(Color.yellow);
				btnPlay.setForeground(Color.red);
				btnPlay.setBounds(336, 310, 203, 35);
				content.add(btnPlay);
			
				fe.setVisible(true);
				
				
				
			}
		});
		btnEditProfile.setForeground(Color.WHITE);
		btnEditProfile.setFont(new Font("Showcard Gothic", Font.PLAIN, 22));
		btnEditProfile.setBackground(Color.BLUE);
		btnEditProfile.setBounds(727, 169, 123, 53);
		contentPane.add(btnEditProfile);
		
		JButton btnYourGames = new JButton("Your games");//dokme bazi ha
		btnYourGames.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Panel scorePanel=new Panel("score");
				scorePanel.setBounds(0, 0, 955, 532);
				scorePanel.setLayout(new BorderLayout(0,0));
				
				fr.setContentPane(scorePanel);
				String[][] names=new String[Player.players.size()][1];
				String p1[]=player.games.toArray(new String[Player.players.size()]);
				String p2[]=player.games.toArray(new String[Player.players.size()]);
				for(int i=0;i<names.length;i++) {
					names[i][0]=p1[i];
				}
				String jk[]= {"Your games"};


				JScrollPane pan = new JScrollPane();

				pan.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
				pan.setBounds(60, 10,800	, 400);
				scorePanel.add(pan,BorderLayout.CENTER);
JButton backk=new JButton("Back");//back
backk.setBounds(80, 420, 100, 50);
backk.setBackground(Color.black);
backk.setForeground(Color.red);
backk.setFont(new Font("Hobo Std", Font.PLAIN, 20));
scorePanel.add(backk);
backk.addActionListener(new ActionListener() {
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		fr.setContentPane(contentPane);
		fr.repaint();
		
	}
});
				JTable table=new JTable(names,jk);//table emtiazat
				pan.setViewportView(table);
table.setForeground(Color.red);
table.setBackground(Color.black);
table.setFont(new Font("Showcard Gothic", Font.PLAIN, 22));
pan.setBackground(Color.black);
pan.setForeground(Color.black);
				table.setRowHeight(50);
				scorePanel.add(new JButton("SD"),BorderLayout.NORTH);
				fr.repaint();

			}
		});
		btnYourGames.setForeground(Color.WHITE);
		btnYourGames.setFont(new Font("Haettenschweiler", Font.PLAIN, 29));
		btnYourGames.setBackground(Color.BLUE);
		btnYourGames.setBounds(568, 270, 199, 64);
		contentPane.add(btnYourGames);
	}
	/**
	 * panele ekhtesasi ba akse background
	 * @author Asus
	 *
	 */
	class Panel extends JPanel{
		String type;
		public Panel(String type) {
			this.type=type;
		}
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			Image img=null;
			try {
				img = ImageIO.read(new File("icon/background.png"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			g.drawImage(img.getScaledInstance(955, 532, 1),0,0,this);
		
			try {
				img = ImageIO.read(new File("icon/black.png"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(!type.equals("score")) {
			g.drawImage(img.getScaledInstance(450, 190, 1),469,70,this);
		}}
	}
//	public void record(int time,int w,int bomb,int giants) {
//		player.record(time, w, bomb, giants);
//	}
//	public void record(int k,int bomb,int giants) {
//		player.record(k, bomb, giants);
//	}
	public static  File fileChooser() {
//////////////////JFileChooser va kar haye tasvir/////////////////////
File picAddress=null;
JFileChooser choose=new JFileChooser();
choose.setAcceptAllFileFilterUsed(false);
choose.setFileFilter(new FileFilter() {

@Override
public String getDescription() {
return ".jpg";//mahdoodiat JPG

}

@Override
public boolean accept(File file) {
if(file.isDirectory())
return true;
else {
if(file.getName().lastIndexOf(".jpg")!=-1)
return true;
}
return false;
}
});


choose.setFileSelectionMode(JFileChooser.FILES_ONLY);
if(choose.showOpenDialog(new JFrame())==JFileChooser.APPROVE_OPTION) {
picAddress=choose.getSelectedFile();

}
return picAddress;
}
}
