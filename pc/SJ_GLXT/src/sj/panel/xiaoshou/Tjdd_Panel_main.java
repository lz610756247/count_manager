package sj.panel.xiaoshou;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;

import dao.DB_Opreat;

public class Tjdd_Panel_main extends JPanel
{
	
	private JTextField khmc;
	private JTextField dianhua;
	private JTextField jsr;
	private JCheckBox isfinish;
	Map map;	//基础信息
	public boolean isChanged = false;
	
	public Tjdd_Panel_main()
	{
		super();
		setLayout(new GridBagLayout());
		setBounds(0, 0, 400, 50);
		this.setBorder(new BevelBorder(BevelBorder.RAISED));
		
		//客户名称
		this.setupComponet(new JLabel("客户名称："), 0, 0, 1, 0, false);
		this.khmc = new JTextField();
		this.khmc.setFocusable(true);
		this.setupComponet(this.khmc, 1, 0, 2, 300, true);
		
		//电话
		this.setupComponet(new JLabel("联系电话："),3 , 0, 1, 0, false);
		this.dianhua = new JTextField();
		this.setupComponet(this.dianhua, 4, 0, 2, 120, true);
		//经手人
		this.setupComponet(new JLabel("经办人："),6 , 0, 1, 0, false);
		this.jsr = new JTextField();
		this.setupComponet(this.jsr, 7, 0, 1, 120, true);
		
		//完成
		this.setupComponet(new JLabel("是否已经完成?"),8 , 0, 0, 0, false);
		this.isfinish = new JCheckBox();
		this.setupComponet(this.isfinish, 9, 0, 0, 0, false);
		
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
		
		info.put("客户名称", this.khmc.getText());
		info.put("电话", this.dianhua.getText());
		info.put("经手人", this.jsr.getText());
		info.put("完成", this.isfinish.isSelected());
		return info;
	}
	
	public void setBaseInfo(Map map)
	{
		this.map = map;
		this.khmc.setText(map.get("客户名称").toString());
		this.dianhua.setText(map.get("电话").toString());
		this.jsr.setText(map.get("经手人").toString());
		if(map.get("完成").equals("true"))
		{
			this.isfinish.setSelected(true);
		}
		else
		{
			this.isfinish.setSelected(false);
		}
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
						, "请填写客户名称、电话、经办人。");
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
		if(!this.khmc.getText().equals("")
				&&!this.dianhua.getText().equals("")
				&&!this.jsr.getText().equals(""))
		{			
			if(!DB_Opreat.insert_dd_main(getInfo()))	//插入基本信息
			{
				return -1;
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
			int i = DB_Opreat.get_dd_main_Id(getInfo());
			if(i != -1)
			{
				return i;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(this
					, "数据库连接错误。"+e.toString());
			e.printStackTrace();
		}
		return -1;
	}
	
		
}
