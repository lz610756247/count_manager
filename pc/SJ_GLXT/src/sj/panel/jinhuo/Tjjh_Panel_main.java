package sj.panel.jinhuo;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;

import sj.util.DateChooser;

import dao.DB_Opreat;

public class Tjjh_Panel_main extends JPanel
{
	
	private JTextField gonghuoshang;
	private JTextField dianhua;
	private DateChooser riqi;
	Map map;	//基础信息
	public boolean isChanged = false;
	
	public Tjjh_Panel_main()
	{
		super();
		setLayout(new GridBagLayout());
		setBounds(0, 0, 400, 50);
		this.setBorder(new BevelBorder(BevelBorder.RAISED));
		
		//供货商名称
		this.setupComponet(new JLabel("供货商名称："), 0, 0, 1, 0, false);
		this.gonghuoshang = new JTextField();
		this.gonghuoshang.setFocusable(true);
		this.setupComponet(this.gonghuoshang, 1, 0, 2, 300, true);
		
		//电话
		this.setupComponet(new JLabel("联系电话："),3 , 0, 1, 0, false);
		this.dianhua = new JTextField();
		this.setupComponet(this.dianhua, 4, 0, 2, 120, true);
		//日期
		this.setupComponet(new JLabel("日期："),6 , 0, 1, 0, false);
		this.riqi = new DateChooser("yyyy-MM-dd");
		this.setupComponet(this.riqi, 7, 0, 2, 120, true);
		
	}
	
	
	// 设置组件位置并添加到容器中
	private void setupComponet(JComponent component, int gridx, int gridy,
			int gridwidth, int ipadx, boolean fill)
	{
			final GridBagConstraints gridbagconstraints = new GridBagConstraints();
			gridbagconstraints.gridx = gridx;
			gridbagconstraints.gridy = gridy;
			gridbagconstraints.insets = new Insets(5 , 5 , 5 , 5);
			if(gridwidth > 1)	//如果占用单元格大于一个就改变，否则就用一个
			{
				gridbagconstraints.gridwidth = gridwidth;
			}
			if(ipadx > 0)
			{
				gridbagconstraints.ipadx = ipadx;
			}
			if(fill)
			{
				gridbagconstraints.fill = GridBagConstraints.HORIZONTAL;
			}
			this.add(component , gridbagconstraints);
	}
	
	
	//获取信息Map
	public Map getInfo()
	{
		Map info = new HashMap();
		
		if(this.map != null)
		{
			info.put("Id", this.map.get("Id"));
		}
		
		info.put("供货商", this.gonghuoshang.getText());
		info.put("电话", this.dianhua.getText());
		info.put("日期", this.riqi.getText());
		return info;
	}
	
	public void setBaseInfo(Map map)
	{
		this.map = map;
		this.gonghuoshang.setText(map.get("供货商").toString());
		this.dianhua.setText(map.get("电话").toString());
		this.riqi.setText(map.get("日期").toString());
	}
	
	
	public String getInfoId()
	{
		return this.map.get("Id").toString();
	}
	
	
	//检测是否有变动
	public void checkChanged()
	{
		if(this.map != this.getInfo())
		{
			this.isChanged = true;
		}	
	}
	
	
	//设置变动
	public void setChanged()
	{
		this.map = this.getInfo();
		this.isChanged = false;
	}
		
	
	//获取插入信息 , 返回插入成功后的记录Id
	public int insertInfo()
	{
		switch(this.insert())
		{
			case -1:
			{
				JOptionPane.showMessageDialog(this
						, "数据库错误。");
				return -1;
			}
			case 0:
			{
				JOptionPane.showMessageDialog(this
						, "请填写供货商姓名、电话、日。");
				break;
			}
			case 1:
			{
				return this.getId();
			}
			default:
			{
				break;
			}
		}
		return -1;
	}
	
	//插入
	private int insert()
	{
		if(!this.gonghuoshang.getText().equals("")
				&&!this.dianhua.getText().equals("")
				&&!this.riqi.getText().equals(""))
		{			
			try 
			{
				
				if(!DB_Opreat.inset_jj_main(getInfo()))	//插入基本信息
				{
					return -1;
				}
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				JOptionPane.showMessageDialog(this
						, "驱动加载错误。"+e.toString());
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				JOptionPane.showMessageDialog(this
						, "数据库连接错误。"+e.toString());
				e.printStackTrace();
			}
			return 1;
		}
		else
		{
			return 0;
		}
	}
	
	//获取订单Id
	private int getId()
	{
		try 
		{
			int i = DB_Opreat.get_jj_main_Id(getInfo());
			if(i != -1)
			{
				return i;
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(this
					, "驱动加载错误。"+e.toString());
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(this
					, "数据库连接错误。"+e.toString());
			e.printStackTrace();
		}
		return -1;
	}
	
		
}
