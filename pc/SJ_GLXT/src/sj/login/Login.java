package sj.login;
import java.awt.Color;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import dao.DB_Opreat;

import sj.main.GLXT_Frame;
import sj.main.GLXT_Frame1;
import sj.util.TbUser;

public class Login extends JFrame {
	
	public static void main(String args[])
	{
		new Login();
	}
	
	private JLabel userLabel;
	private JLabel passLabel;
	private JLabel message;
	private JButton exit;
	private JButton login;
	private static TbUser user;
	public Login() {
		setTitle("登录企业进销存管理系统");
		final JPanel panel = new LoginPanel();
		panel.setLayout(null);
		getContentPane().add(panel);
		setSize(panel.getWidth(), panel.getHeight());
		
		int screen_width = Toolkit.getDefaultToolkit().getScreenSize().width;  
        int screen_height = Toolkit.getDefaultToolkit().getScreenSize().height; 
        
        setLocation((screen_width - getWidth()) / 2,  
                (screen_height - getHeight()) / 2);  
		
		userLabel = new JLabel();
		userLabel.setForeground(Color.WHITE);
		userLabel.setText("电  话：");
		userLabel.setBounds(100, 135, 200, 18);
		panel.add(userLabel);
		final JTextField userName = new JTextField();
		userName.setBounds(150, 135, 200, 18);
		panel.add(userName);
		passLabel = new JLabel();
		passLabel.setForeground(Color.WHITE);
		passLabel.setText("密  码：");
		passLabel.setBounds(100, 165, 200, 18);
		panel.add(passLabel);
		final JPasswordField userPassword = new JPasswordField();
		userPassword.addKeyListener(new KeyAdapter() {
			public void keyPressed(final KeyEvent e) {
				if (e.getKeyCode() == 10)
					login.doClick();
			}
		});
		userPassword.setBounds(150, 165, 200, 18);
		panel.add(userPassword);
		login = new JButton();
		
		login.setText("登录");
		login.setBounds(180, 195, 60, 25);
		panel.add(login);
		exit = new JButton();
		
		exit.setText("退出");
		exit.setBounds(260, 195, 60, 25);
		panel.add(exit);
		
		message = new JLabel("");
		message.setBounds(200, 215, 100, 30);		
		panel.add(message);
		
		setVisible(true);
		setResizable(false);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		
		exit.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				System.exit(0);
			}
		});
		
		login.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				TbUser user = DB_Opreat.login(userName.getText(), userPassword.getText());
				//判断登录信息
				if("no_user".equals(user.getName()))
				{
					//用户不存在
					message.setForeground(Color.red);
					message.setText("员工不存在!!!");
				}
				else
				{
					//密码错误
					if("no_password".equals(user.getPass()))
					{
						message.setForeground(Color.red);
						message.setText("密码错误!!!");
					}
					else
					{
						setVisible(false);
						TbUser.getInstance().setGf(new GLXT_Frame());
					}
				}				
			}
		});
	}
	public static TbUser getUser() {
		return user;
	}
	public static void setUser(TbUser user) {
		Login.user = user;
	}
}
