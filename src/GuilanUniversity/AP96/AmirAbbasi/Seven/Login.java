package GuilanUniversity.AP96.AmirAbbasi.Seven;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileFilter;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Login extends JFrame {

	private Panel contentPane;
	private JTextField textField;
	private JTextField textField_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login frame = new Login();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Login() {
		Player.load();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 878, 524);
		contentPane = new Panel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		getContentPane().setLayout(null);
		
		textField = new JTextField();//username
		textField.setBounds(336, 204, 203, 42);
		getContentPane().add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();//password
		textField_1.setBounds(336, 259, 203, 35);
		getContentPane().add(textField_1);
		textField_1.setColumns(10);
		JLabel lblNewLabel = new JLabel("");
		JButton btnLogin = new JButton("Login");//login
		JFrame free=this;
		btnLogin.addActionListener(new ActionListener() {//Login:action listener
			public void actionPerformed(ActionEvent arg0) {
				int t=Player.checkPass(textField.getText(), textField_1.getText());//check kardane username va password
				switch (t) {
				case -1://username vojood nadarad
					lblNewLabel.setText("Invalid username");
					break;
				case 1://username va password dorost ast
					Profile pr=new Profile(Player.getPlayer(textField.getText()));
					pr.setVisible(true);
					free.dispose();
					break;
				case 0://password ghalat ast
					lblNewLabel.setText("Passwrod is incorrect");
				}
					
				
			}
		});
		btnLogin.setFont(new Font("Hobo Std", Font.PLAIN, 13));
		btnLogin.setForeground(Color.BLUE);
		btnLogin.setBackground(Color.YELLOW);
		btnLogin.setBounds(378, 336, 97, 25);
		getContentPane().add(btnLogin);
		
		JButton btnSingUp = new JButton("Sing up");//accounte jadid
		JFrame fr=this;
		btnSingUp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				
				fr.setVisible(false);
				JFrame fe=new JFrame();
				fe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				fe.setBounds(100, 100, 878, 524);
				Panel content = new Panel();
				content.setBorder(new EmptyBorder(5, 5, 5, 5));
				fe.setContentPane(content);
				content.setLayout(null);
				
				JTextField textPane = new JTextField();//username
				textPane.setBounds(336, 204, 203, 42);
				content.add(textPane);
				JLabel lb=new JLabel("");//lable payam
				lb.setBackground(Color.BLACK);
				lb.setFont(new Font("Hobo Std", Font.PLAIN, 13));
				lb.setForeground(Color.RED);
				lb.setBounds(368, 307, 193, 16);
				JTextField textPane_1 = new JTextField();
				textPane_1.setBounds(336, 259, 203, 35);
				content.add(textPane_1);
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
				
				JButton btnPlay = new JButton("Sign up!");
			
				btnPlay.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent arg0) {
						int t=Player.checkPass(textPane.getText(), " ");//check kardane username
						if(t==-1) {//username tekrari nist va mitavan sakht
							Player.newPlayer(textPane.getText(), 0, 0, textPane_1.getText()," ");
			
							File file=fileChooser();//JFileChooser
							File f=new File("pictures/"+textPane.getText()+".png");
							try {
								Files.copy(file.toPath(), f.toPath());//copy aks
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							Player.writePlayers();//upadte file players.data
							JOptionPane.showMessageDialog(null, "Account creatd successfully!");
						Image im=null;
						try {
							im = ImageIO.read(new File("icon/key.jpg"));
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						JOptionPane.showMessageDialog(null, "", "Controls", 1, new ImageIcon(im.getScaledInstance(700, 400, 200)));//barande ra elam mikonad
						
							fr.setVisible(true);
							fe.dispose();
						}
						if(t==0) {//username tekrari ast
							lb.setText("username already exist!");
						}
					}
				});
				btnPlay.setFont(new Font("Hobo Std", Font.PLAIN, 16));
				btnPlay.setBackground(Color.yellow);
				btnPlay.setForeground(Color.red);
				btnPlay.setBounds(378, 336, 97, 25);
				content.add(btnPlay);
				
				JLabel lblUsername = new JLabel("                    Username");
				lblUsername.setForeground(Color.RED);
				lblUsername.setFont(new Font("Hobo Std", Font.PLAIN, 13));
				lblUsername.setBackground(Color.BLACK);
				lblUsername.setBounds(178, 204, 146, 35);
				content.add(lblUsername);
				
				JLabel lblPassword = new JLabel("                    Password");
				lblPassword.setForeground(Color.RED);
				lblPassword.setFont(new Font("Hobo Std", Font.PLAIN, 13));
				lblPassword.setBackground(Color.BLACK);
				lblPassword.setBounds(178, 258, 146, 35);
				content.add(lblPassword);
				fe.setVisible(true);
				
				
			}
		});
		btnSingUp.setForeground(Color.BLUE);
		btnSingUp.setBackground(Color.YELLOW);
		btnSingUp.setFont(new Font("Hobo Std", Font.PLAIN, 13));
		btnSingUp.setBounds(378, 374, 97, 25);
		getContentPane().add(btnSingUp);
		
		
		lblNewLabel.setBackground(Color.BLACK);
		lblNewLabel.setFont(new Font("Hobo Std", Font.PLAIN, 13));
		lblNewLabel.setForeground(Color.RED);
		lblNewLabel.setBounds(368, 307, 193, 16);
		getContentPane().add(lblNewLabel);
		
		JLabel lblBomberMan = new JLabel("           Bomber Man");
		lblBomberMan.setForeground(Color.BLACK);
		lblBomberMan.setFont(new Font("Jokerman", Font.ITALIC, 46));
		lblBomberMan.setBounds(172, 82, 522, 81);
		getContentPane().add(lblBomberMan);
		
		JLabel lblUsername = new JLabel("                    Username");
		lblUsername.setForeground(Color.RED);
		lblUsername.setFont(new Font("Hobo Std", Font.PLAIN, 13));
		lblUsername.setBackground(Color.BLACK);
		lblUsername.setBounds(178, 204, 146, 35);
		getContentPane().add(lblUsername);
		
		JLabel lblPassword = new JLabel("                    Password");
		lblPassword.setForeground(Color.RED);
		lblPassword.setFont(new Font("Hobo Std", Font.PLAIN, 13));
		lblPassword.setBackground(Color.BLACK);
		lblPassword.setBounds(178, 258, 146, 35);
		getContentPane().add(lblPassword);
		
		
	}
	/**
	 * panele ekhtesasi ke dar background khodash aks darad
	 * @author Asus
	 *
	 */
class Panel extends JPanel{
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Image img=null;
		try {
			img = ImageIO.read(new File("icon/background.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		g.drawImage(img.getScaledInstance(878, 524, 1),0,0,this);
	}
}
/**
 * JFileChooser baraye aks ke addresse aks ra return mikonad
 * @return
 */
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
