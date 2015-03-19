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
	private Container container;		//容器本身
	public static JDesktopPane desktopPane;		//子空控件
	private JLabel backLabel;
	// 创建窗体的Map类型集合对象
	public static Map<String, JInternalFrame> ifs = new HashMap<String, JInternalFrame>();
	
	public GLXT_Frame()
	{
		this.frame = new JFrame("管理系统");
		this.frame.setVisible(true);
		this.frame.setLayout(new BorderLayout());
		frame.getContentPane().setBackground(new Color(147,114, 79));
		frame.addComponentListener(new FrameListener());
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		backLabel = new JLabel();// 背景标签
		backLabel.setVerticalAlignment(SwingConstants.TOP);
		backLabel.setHorizontalAlignment(SwingConstants.CENTER);
		updateBackImage(); // 更新或初始化背景图片
		this.frame.setBounds(200, 20, 1000, 710);
		this.container = this.frame.getContentPane();
		this.desktopPane = new JDesktopPane();
		desktopPane.add(backLabel, new Integer(Integer.MIN_VALUE));	
		JTabbedPane navigationPanel = createNavigationPanel(); // 创建导航标签面板
		this.container.add(navigationPanel , BorderLayout.NORTH);
		this.container.add(this.desktopPane);
	}
	
	//创建窗体导航面板
	public JTabbedPane createNavigationPanel()
	{
		JTabbedPane jtp = new JTabbedPane();
		jtp.setFocusable(false);
		jtp.setBorder(new BevelBorder(BevelBorder.RAISED));
		
		JPanel jinhuo = new JPanel();		//进货管理
		jinhuo.setLayout(new BoxLayout(jinhuo , BoxLayout.X_AXIS));
		
		if((TbUser.getInstance().getQuan() ==-1 ) || (TbUser.getInstance().getQuan() > 0
				&& TbUser.getInstance().getQuan() <= 10))
		{
			jinhuo.add(this.createButton("添加进货信息", "tjjh"));
		}
		jinhuo.add(this.createButton("查看进货单", "czjh"));
		
		
		JPanel xiaoshou = new JPanel();		//销售管理
		xiaoshou.setLayout(new BoxLayout(xiaoshou , BoxLayout.X_AXIS));
		if((TbUser.getInstance().getQuan() ==-1 ) || (TbUser.getInstance().getQuan() > 0
				&& TbUser.getInstance().getQuan() <= 10))
		{
			xiaoshou.add(this.createButton("添加销售信息", "tjdd"));
		}
		xiaoshou.add(this.createButton("销售信息查看", "ddgl"));
		
//		JPanel kucun = new JPanel();		//库存管理
//		kucun.setLayout(new BoxLayout(kucun , BoxLayout.X_AXIS));
//		kucun.add(this.createButton("添加库存信息", "tjkc"));
//		kucun.add(this.createButton("进货库存查看", "kcxg"));
//		kucun.add(this.createButton("商品库存查看", "kcck"));
		
		JPanel zhichu = new JPanel();		//支出管理
		zhichu.setBounds(0, 0, 600, 41);
		zhichu.setName("zhichustaticPanel");
		zhichu.setLayout(new BoxLayout(zhichu , BoxLayout.X_AXIS));
		if((TbUser.getInstance().getQuan() ==-1 ) || (TbUser.getInstance().getQuan() > 10
				&& TbUser.getInstance().getQuan() <= 20))
		{
			zhichu.add(this.createButton("快速添加日薪", "kstjrx"));
			zhichu.add(this.createButton("管理员工", "glyg"));
			zhichu.add(this.createButton("年度工资报表", "ndgzbb"));
		}
		
//		zhichu.add(this.createButton("添加信息", "tjxx"));		
		zhichu.add(this.createButton("个人工资查看", "grgzck"));		
		
		JPanel xitong = new JPanel();	//系统管理
		xitong.setLayout(new BoxLayout(xitong , BoxLayout.X_AXIS));
		xitong.add(this.createButton("注销系统", "zxxt"));
		
		//一般业务
		jtp.addTab("     进货管理      " , null, jinhuo, "进货管理");
		jtp.addTab("     销售管理      " , null , xiaoshou , "销售管理");
		
		//工资管理
		jtp.addTab("     工资管理      ", null, zhichu, "工资管理");
		
//		jtp.addTab("     系统管理      ", null, xitong, "系统管理");
		return jtp;
	}
	
	//创建按钮方法
	private JButton createButton(String fName , String cName)
	{
		//配置按钮图标路径
//		String imgURL = "res/ActionIcon/"+fName+".png";
//		String imgURL_roll = "res/ActionIcon/"+fName+"_roll.png";
//		String imgURL_down = "res/ActionIcon/"+fName+"_down.png";
		
		//初始化图标
//		Icon icon = new ImageIcon(imgURL);
//		Icon icon_roll = new ImageIcon(imgURL_roll);
//		Icon icon_down = new ImageIcon(imgURL_down);
		
		//设置按钮点击动作
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
	
	// 主窗体菜单项的单击事件监听器
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
		
		// 更新背景图片的方法
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
		// 窗体监听器
		private final class FrameListener extends ComponentAdapter {
			public void componentResized(final ComponentEvent e) {
				updateBackImage();
			}
		}
		
		public JFrame getFrame()
		{
			return frame;
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
