package sj.main;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.beans.PropertyVetoException;
import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.BevelBorder;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;





import sj.login.Login;
import sj.panel.*;
import sj.util.TbUser;

public class GLXT_Frame
{	
	
	
	private JFrame frame;
	private Container container;		//��������
	public static JDesktopPane desktopPane;		//�ӿտؼ�
	private JLabel backLabel;
	// ���������Map���ͼ��϶���
	public static Map<String, JInternalFrame> ifs = new HashMap<String, JInternalFrame>();
	
	public GLXT_Frame()
	{
		this.frame = new JFrame("����ϵͳ");
		this.frame.setVisible(true);
		this.frame.setLayout(new BorderLayout());
		frame.getContentPane().setBackground(new Color(147,114, 79));
		frame.addComponentListener(new FrameListener());
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		backLabel = new JLabel();// ������ǩ
		backLabel.setVerticalAlignment(SwingConstants.TOP);
		backLabel.setHorizontalAlignment(SwingConstants.CENTER);
		updateBackImage(); // ���»��ʼ������ͼƬ
		this.frame.setBounds(200, 20, 1000, 710);
		this.container = this.frame.getContentPane();
		this.desktopPane = new JDesktopPane();
		desktopPane.add(backLabel, new Integer(Integer.MIN_VALUE));	
		JTabbedPane navigationPanel = createNavigationPanel(); // ����������ǩ���
		this.container.add(navigationPanel , BorderLayout.NORTH);
		this.container.add(this.desktopPane);
	}
	
	//�������嵼�����
	public JTabbedPane createNavigationPanel()
	{
		JTabbedPane jtp = new JTabbedPane();
		jtp.setFocusable(false);
		jtp.setBorder(new BevelBorder(BevelBorder.RAISED));
		
		JPanel jinhuo = new JPanel();		//��������
		jinhuo.setLayout(new BoxLayout(jinhuo , BoxLayout.X_AXIS));
		
		if((TbUser.getInstance().getQuan() ==-1 ) || (TbUser.getInstance().getQuan() > 0
				&& TbUser.getInstance().getQuan() <= 10))
		{
			jinhuo.add(this.createButton("��ӽ�����Ϣ", "tjjh"));
		}
		jinhuo.add(this.createButton("�鿴������", "czjh"));
		
		
		JPanel xiaoshou = new JPanel();		//���۹���
		xiaoshou.setLayout(new BoxLayout(xiaoshou , BoxLayout.X_AXIS));
		if((TbUser.getInstance().getQuan() ==-1 ) || (TbUser.getInstance().getQuan() > 0
				&& TbUser.getInstance().getQuan() <= 10))
		{
			xiaoshou.add(this.createButton("���������Ϣ", "tjdd"));
		}
		xiaoshou.add(this.createButton("������Ϣ�鿴", "ddgl"));
		
//		JPanel kucun = new JPanel();		//������
//		kucun.setLayout(new BoxLayout(kucun , BoxLayout.X_AXIS));
//		kucun.add(this.createButton("��ӿ����Ϣ", "tjkc"));
//		kucun.add(this.createButton("�������鿴", "kcxg"));
//		kucun.add(this.createButton("��Ʒ���鿴", "kcck"));
		
		JPanel zhichu = new JPanel();		//֧������
		zhichu.setBounds(0, 0, 600, 41);
		zhichu.setName("zhichustaticPanel");
		zhichu.setLayout(new BoxLayout(zhichu , BoxLayout.X_AXIS));
		if((TbUser.getInstance().getQuan() ==-1 ) || (TbUser.getInstance().getQuan() > 10
				&& TbUser.getInstance().getQuan() <= 20))
		{
			zhichu.add(this.createButton("���������н", "kstjrx"));
			zhichu.add(this.createButton("����Ա��", "glyg"));
			zhichu.add(this.createButton("��ȹ��ʱ���", "ndgzbb"));
		}
		
//		zhichu.add(this.createButton("�����Ϣ", "tjxx"));		
		zhichu.add(this.createButton("���˹��ʲ鿴", "grgzck"));		
		
		JPanel xitong = new JPanel();	//ϵͳ����
		xitong.setLayout(new BoxLayout(xitong , BoxLayout.X_AXIS));
		xitong.add(this.createButton("ע��ϵͳ", "zxxt"));
		
		//һ��ҵ��
		jtp.addTab("     ��������      " , null, jinhuo, "��������");
		jtp.addTab("     ���۹���      " , null , xiaoshou , "���۹���");
		
		//���ʹ���
		jtp.addTab("     ���ʹ���      ", null, zhichu, "���ʹ���");
		
//		jtp.addTab("     ϵͳ����      ", null, xitong, "ϵͳ����");
		return jtp;
	}
	
	//������ť����
	private JButton createButton(String fName , String cName)
	{
		//���ð�ťͼ��·��
//		String imgURL = "res/ActionIcon/"+fName+".png";
//		String imgURL_roll = "res/ActionIcon/"+fName+"_roll.png";
//		String imgURL_down = "res/ActionIcon/"+fName+"_down.png";
		
		//��ʼ��ͼ��
//		Icon icon = new ImageIcon(imgURL);
//		Icon icon_roll = new ImageIcon(imgURL_roll);
//		Icon icon_down = new ImageIcon(imgURL_down);
		
		//���ð�ť�������
		Action action = new openFrameAction(fName, cName);
		JButton button = new JButton(action);
		button.setText(fName);
		button.setMargin(new Insets(0, 0, 0, 0));
		button.setHideActionText(false);
		button.setFocusPainted(true);
		button.setBorderPainted(true);
		button.setContentAreaFilled(true);
		/*if(icon != null)
		{
			button.setIcon(icon);
		}*/
		/*if(icon_roll != null)
		{
			button.setRolloverIcon(icon_roll);
		}
		if(icon_down != null)
		{
			button.setPressedIcon(icon_down);
		}*/
		return button;
	}
	
	// ������˵���ĵ����¼�������
		protected final class openFrameAction extends AbstractAction {
			private String frameName = null;
			private openFrameAction() {
			}
			public openFrameAction(String cname, String frameName) {
				this.frameName = frameName;
				putValue(Action.NAME, cname);
				putValue(Action.SHORT_DESCRIPTION, cname);
				//putValue(Action.SMALL_ICON, icon);
			}
			public void actionPerformed(final ActionEvent e) {
				JInternalFrame jf = getIFrame(frameName);
				// ���ڲ�����չ�ʱ�����ڲ���������ifs����������ô��塣
				jf.addInternalFrameListener(new InternalFrameAdapter() 
				{
					public void internalFrameClosed(InternalFrameEvent e) 
					{
						ifs.remove(frameName);
					}
				});
				if (jf.getDesktopPane() == null) {
					desktopPane.add(jf);
					jf.setVisible(true);
				}
				try 
				{
					jf.setSelected(true);
				} 
				catch (PropertyVetoException e1) 
				{
					e1.printStackTrace();
				}
			}
		}
	
		
		//��ȡ�ڲ������Ψһʵ������
		private JInternalFrame getIFrame(String frameName)
		{
			final String pac = "internalframe.";
			JInternalFrame jf = null;
			if(!this.ifs.containsKey(frameName))
			{
				try
				{
					Class fClass = Class.forName(pac+frameName);
					Constructor constructor = fClass.getConstructor(null);
					jf = (JInternalFrame) constructor.newInstance(null);
					this.ifs.put(frameName, jf);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(this.frame.getContentPane()
							, "�򿪴��ڴ���ʵ�������ܲ�Ψһ\n���߳�����û�иô���,�������"+e.toString());
					e.printStackTrace();
				}
			}
			else
			{
				jf = ifs.get(frameName);
			}
			return jf;			
		}
		
		// ���±���ͼƬ�ķ���
		private void updateBackImage() {
			if (backLabel != null) {
				int backw = this.frame.getWidth();
				int backh = frame.getHeight();
				backLabel.setSize(backw, backh);
				backLabel.setText("<html><body><image width='" + backw
						+ "' height='" + (backh - 110) + "' src="
						+ GLXT_Frame.this.getClass().getResource("welcome.jpg")
						+ "'></img></body></html>");
			}
		}
		// ���������
		private final class FrameListener extends ComponentAdapter {
			public void componentResized(final ComponentEvent e) {
				updateBackImage();
			}
		}
		
		public JFrame getFrame()
		{
			return frame;
		}
	
	    /*ͳһ���*/
	    static
	    {
			try 
			{
				UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
			}
	    }
}
