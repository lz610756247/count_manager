package internalframe;


import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import sj.panel.jinhuo.Tjjh_Panel_detail;
import sj.panel.jinhuo.Tjjh_Panel_main;
import sj.panel.xiaoshou.Tjdd_Panel_detail;
import sj.panel.xiaoshou.Tjdd_Panel_main;


public class tjdd extends JInternalFrame
{

	JInternalFrame jf = this;
	Tjdd_Panel_main tpm;
	Tjdd_Panel_detail tpd;
	
	public tjdd()
	{
		this.setVisible(true);
		this.setTitle("添加订单");
		this.setBounds(0 , 0 , 970 , 620);
		this.setIconifiable(true);
		this.setClosable(true);
		this.setMaximizable(false);
		this.setLayout(null);
		
		
		tpm = new Tjdd_Panel_main();	//基础信息
		tpm.setBounds(0, 0, 970, 50);
		this.add(tpm);
				
		tpd = new Tjdd_Panel_detail();	//详细信息
		tpd.setBounds(0, 50, 970, 500);
		this.add(tpd);
		
		JButton insertButton = new JButton("添加订单");
		insertButton.addActionListener(new AddListener());
		insertButton.setBounds(0, 550, 970, 40);
		this.add(insertButton);
		
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
