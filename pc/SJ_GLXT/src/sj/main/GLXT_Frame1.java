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
	private Container container;		//容器本身
	public static JDesktopPane desktopPane;		//子空控件
	// 创建窗体的Map类型集合对象
	public static  Map<String, JInternalFrame> ifs = new HashMap<String, JInternalFrame>();
	
	public GLXT_Frame1()
	{
		this.frame = new JFrame("管理系统");
		this.frame.setVisible(true);
		this.frame.setLayout(new BorderLayout());
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.frame.setBounds(200, 20, 1000, 710);
		this.container = this.frame.getContentPane();
		this.desktopPane = new JDesktopPane();
		this.frame.setJMenuBar(createNavigationPanel());	// 创建导航标签面板
		this.container.add(this.desktopPane , BorderLayout.CENTER);
	}
	
	//创建窗体导航面板
	public JMenuBar createNavigationPanel()
	{
		JMenuBar menubar = new JMenuBar();
		
		JMenu jinhuo = new JMenu("   进货管理       ");		//进货管理
		jinhuo.add(this.createItem("添加进货信息", "tjjh"));
		jinhuo.addSeparator();
		jinhuo.add(this.createItem("查看进货单", "czjh"));
		
		
		JMenu xiaoshou = new JMenu("   销售管理       ");		//销售管理
		xiaoshou.add(this.createItem("添加销售信息", "tjdd"));
		xiaoshou.addSeparator();
		xiaoshou.add(this.createItem("销售信息查看", "xsck"));
		
//		JMenu kucun = new JMenu("   库存管理       ");		//库存管理
//		kucun.add(this.createItem("添加库存信息", "tjkc"));
//		kucun.add(this.createItem("库存修改", "kcxg"));
//		kucun.add(this.createItem("库存查看", "kcck"));
		
		JMenu zhichu = new JMenu("   工资管理       ");		//支出管理
		zhichu.add(this.createItem("快速添加日薪", "kstjrx"));
		zhichu.add(this.createItem("添加信息", "tjxx"));
		zhichu.add(this.createItem("管理员工", "glyg"));
		zhichu.add(this.createItem("个人工资查看", "grgzck"));
		zhichu.add(this.createItem("年度工资报表", "ndgzbb"));
		
		JMenu xitong = new JMenu();	//系统管理
		xitong.add(this.createItem("个人信息修改", "grxx"));
		
		
		menubar.add(jinhuo);
		menubar.add(xiaoshou);
//		menubar.add(kucun);
		menubar.add(zhichu);
		return menubar;
	}
	
	//创建按钮方法
	private JMenuItem createItem(String fName , String cName)
	{

		
		//设置按钮点击动作
		Action action = new openFrameAction(fName, cName);
		JMenuItem button = new JMenuItem(action);
		return button;
	}
	
	// 主窗体菜单项的单击事件监听器
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
				// 在内部窗体闭关时，从内部窗体容器ifs对象中清除该窗体。
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
	
		
		//获取内部窗体的唯一实例对象
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
							, "打开窗口错误，实例化可能不唯一\n或者程序中没有该窗口,代码错误"+e.toString());
					e.printStackTrace();
				}
			}
			else
			{
				jf = ifs.get(frameName);
			}
			return jf;			
		}
	
	    /*统一外观*/
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
