package sj.main;


import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Insets;
import java.awt.event.ActionEvent;
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
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.border.BevelBorder;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;



import sj.login.Login;
import sj.panel.*;

public class GLXT_Frame1
{
	public static void main1(String args[])
	{
		new Login();
	}
	
	private JFrame frame;
	private Container container;		//��������
	public static JDesktopPane desktopPane;		//�ӿտؼ�
	// ���������Map���ͼ��϶���
	public static  Map<String, JInternalFrame> ifs = new HashMap<String, JInternalFrame>();
	
	public GLXT_Frame1()
	{
		this.frame = new JFrame("����ϵͳ");
		this.frame.setVisible(true);
		this.frame.setLayout(new BorderLayout());
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.frame.setBounds(200, 20, 1000, 710);
		this.container = this.frame.getContentPane();
		this.desktopPane = new JDesktopPane();
		this.frame.setJMenuBar(createNavigationPanel());	// ����������ǩ���
		this.container.add(this.desktopPane , BorderLayout.CENTER);
	}
	
	//�������嵼�����
	public JMenuBar createNavigationPanel()
	{
		JMenuBar menubar = new JMenuBar();
		
		JMenu jinhuo = new JMenu("   ��������       ");		//��������
		jinhuo.add(this.createItem("��ӽ�����Ϣ", "tjjh"));
		jinhuo.addSeparator();
		jinhuo.add(this.createItem("�鿴������", "czjh"));
		
		
		JMenu xiaoshou = new JMenu("   ���۹���       ");		//���۹���
		xiaoshou.add(this.createItem("���������Ϣ", "tjdd"));
		xiaoshou.addSeparator();
		xiaoshou.add(this.createItem("������Ϣ�鿴", "xsck"));
		
//		JMenu kucun = new JMenu("   ������       ");		//������
//		kucun.add(this.createItem("��ӿ����Ϣ", "tjkc"));
//		kucun.add(this.createItem("����޸�", "kcxg"));
//		kucun.add(this.createItem("���鿴", "kcck"));
		
		JMenu zhichu = new JMenu("   ���ʹ���       ");		//֧������
		zhichu.add(this.createItem("���������н", "kstjrx"));
		zhichu.add(this.createItem("�����Ϣ", "tjxx"));
		zhichu.add(this.createItem("����Ա��", "glyg"));
		zhichu.add(this.createItem("���˹��ʲ鿴", "grgzck"));
		zhichu.add(this.createItem("��ȹ��ʱ���", "ndgzbb"));
		
		JMenu xitong = new JMenu();	//ϵͳ����
		xitong.add(this.createItem("������Ϣ�޸�", "grxx"));
		
		
		menubar.add(jinhuo);
		menubar.add(xiaoshou);
//		menubar.add(kucun);
		menubar.add(zhichu);
		return menubar;
	}
	
	//������ť����
	private JMenuItem createItem(String fName , String cName)
	{

		
		//���ð�ť�������
		Action action = new openFrameAction(fName, cName);
		JMenuItem button = new JMenuItem(action);
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
