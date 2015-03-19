package internalframe;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import dao.DB_Connection;


import sj.panel.jinhuo.Tjjh_Panel_detail;
import sj.panel.jinhuo.Tjjh_Panel_main;

public class tjjh extends JInternalFrame
{
	JInternalFrame jf = this;
	Tjjh_Panel_main tpm;
	Tjjh_Panel_detail tpd;
	public tjjh()
	{
		this.setVisible(true);
		this.setTitle("添加进货信息");
		this.setIconifiable(true);
		this.setClosable(true);
		this.setMaximizable(false);
		this.setBounds(0, 0, 900, 620);
		this.setLayout(null);
		
		tpm = new Tjjh_Panel_main();	//基础信息
		tpm.setBounds(0, 0, 900, 50);
		this.add(tpm);
		
		
		tpd = new Tjjh_Panel_detail();	//详细信息
		tpd.setBounds(0, 50, 900, 500);
		this.add(tpd);
		
		JButton insertButton = new JButton("添加进货单");
		insertButton.addActionListener(new AddListener());
		insertButton.setBounds(0, 550, 900, 40);
		this.add(insertButton);

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
	
	
	//添加订单
	private class AddListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{		
			try 
			{
				int id_index = tpm.insertInfo();	//保存后获取订单编号
				
				if(id_index == -1)		//如果为-1，插入失败
				{
					return;
				}

				if(tpd.inset_detail(id_index))		//插入详细信息
				{
					JOptionPane.showMessageDialog(jf
							, "插入成功");
					try {
						jf.setClosed(true);
					} catch (PropertyVetoException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			} catch (ClassNotFoundException e2) {
				// TODO Auto-generated catch block
				JOptionPane.showMessageDialog(jf
						, "驱动加载错误。"+e2.toString());
				e2.printStackTrace();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				JOptionPane.showMessageDialog(jf
						, "数据库连接错误。"+e1.toString());
				e1.printStackTrace();
			}
			
		}		
	}
}
